package vairy.pcap;

import java.io.IOError;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.jnetpcap.ByteBufferHandler;
import org.jnetpcap.JBufferHandler;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.PcapIf;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.JScanner;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.winpcap.WinPcap;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UnknownPacket;
import org.pcap4j.packet.factory.PacketFactories;
import org.pcap4j.packet.factory.PacketFactory;
import org.pcap4j.packet.namednumber.DataLinkType;

import com.slytechs.library.JNILibrary;
import com.sun.jna.IntegerType;
import com.sun.jna.NativeLong;

import vairy.pcap.PcapTask.PacketDataListener;
import vairy.util.VairyTimeAvgTask;

public class PcapTaskJNet extends Thread implements PcapTask {
	public class BuildData {
		private final PcapHeader header;
		private final byte[] databuf;
		public BuildData(PcapHeader header, byte[] databuf) {
			this.header = header;
			this.databuf = databuf;
		}
	}

	public class AddDataque implements PcapPacketHandler<Integer> {
		@Override
		public void nextPacket(PcapPacket packet, Integer user){
			packet.getCaptureHeader();
			byte[] dataarray = packet.getByteArray(0, packet.getPacketWirelen());
			BuildData buildData = new BuildData(packet.getCaptureHeader(),dataarray);
			buildque.add(buildData);
		}
	}

	private class DataProcess implements Runnable{
		private final long ms;
		private final long ns;
		private final byte[] buffer;

		public DataProcess(PcapHeader header, byte[] databuf) {
			this.ms = header.timestampInMillis();
			this.ns = header.timestampInNanos() % 1000000;
			this.buffer = databuf;
		}
		@Override
		public void run() {
			int nano = (int) (ns%1000000);
			Timestamp ts = new Timestamp(ms);
			ts.setNanos(nano);

			Packet newInstance = factory.newInstance(buffer,DataLinkType.getInstance(datalink));

			PacketData4j packetData4j = new PacketData4j(ts, newInstance);
			dataque.add(packetData4j);
			listener.gotPacket(packetData4j);
		}
	}

	private class DataQueMonitor extends Thread{
		@Override
		public void run() {
			BuildData poll;
			while(true){
				if((poll = buildque.poll()) != null){
					new DataProcess(poll.header, poll.databuf).run();
				}else{
					try {
						dummyWait();
					} catch (InterruptedException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
			}
		}
		private synchronized void dummyWait() throws InterruptedException{
			this.wait(2000);
		}
	}
	private final Pcap pcap;
	private final PacketFactory<Packet, DataLinkType> factory = PacketFactories.getFactory(Packet.class, DataLinkType.class);
	private final Integer datalink;
	private final ConcurrentLinkedQueue<BuildData> buildque = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<PacketData4j> dataque = new ConcurrentLinkedQueue<>();
	private final PacketDataListener<PacketData4j> listener;

	public PcapTaskJNet(final Integer nif, PacketDataListener<PacketData4j> listener,final Integer caplen,final Integer tmout) throws IOException{
		List<PcapIf> IfList = new ArrayList<PcapIf>();
		StringBuilder errbuff = new StringBuilder();
		int result = WinPcap.findAllDevs(IfList, errbuff);

		if(Pcap.OK == result && !IfList.isEmpty()){
			String devname = IfList.get(nif).getName();
			pcap = Pcap.openLive(devname,caplen , Pcap.MODE_NON_PROMISCUOUS, tmout, errbuff);
			this.datalink = pcap.datalink();
			this.listener = listener;
		}else{
			this.pcap = null;
			this.datalink = null;
			this.listener = null;
			throw new IOError(new Throwable("Device Not Found"));
		}
	}

	public static List<String> getDevList(){
		List<String> rtn = new ArrayList<>();

		ArrayList<PcapIf> IfList = new ArrayList<PcapIf>();
		StringBuilder errbuff = new StringBuilder();
		int result = Pcap.findAllDevs(IfList, errbuff);

		if(Pcap.OK == result && !IfList.isEmpty()){
			for (PcapIf pcapIf : IfList) {
				rtn.add(pcapIf.toString());
			}
		}

		return rtn;
	}

	@Override
	public void setFilter(String bpfexp) {
		PcapBpfProgram bpf = new PcapBpfProgram();
		int compile = pcap.compile(bpf, bpfexp, 1, 0x00000000);
		if(Pcap.OK == compile){
			pcap.setFilter(bpf);
		}
	}

	@Override
	public ConcurrentLinkedQueue<PacketData4j> getDataque() {
		return dataque;
	}

	@Override
	public void dispose() {
		pcap.close();
	}

	@Override
	public void run() {
		DataQueMonitor dataQueMonitor = new DataQueMonitor();
		dataQueMonitor.start();
		pcap.loop(0, new AddDataque(), 0);
	}
}