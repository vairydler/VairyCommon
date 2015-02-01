package vairy.pcap;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

import vairy.util.VairyTimeAvgTask;

public class PcapTask4j extends Thread implements PcapTask {

	private final PcapNetworkInterface nif;
	private final PcapHandle handle;
	private final ConcurrentLinkedQueue<PacketData4j> dataque = new ConcurrentLinkedQueue<>();
	private final PacketDataListener<PacketData4j> listener;

	public PcapTask4j(final Integer nif, PacketDataListener<PacketData4j> listener,final Integer caplen,final Integer tmout)
			throws PcapNativeException {
		this.nif = Pcaps.findAllDevs().get(nif);
		this.listener = listener;
		handle = this.nif.openLive(caplen, PromiscuousMode.NONPROMISCUOUS, tmout);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see vairy.pcap.PcapTask#setFilter(java.lang.String)
	 */
	@Override
	public void setFilter(final String bpfexp) {
		try {
			handle.setFilter(bpfexp, BpfCompileMode.OPTIMIZE);
		} catch (PcapNativeException | NotOpenException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see vairy.pcap.PcapTask#getDataque()
	 */
	@Override
	public final ConcurrentLinkedQueue<PacketData4j> getDataque() {
		return dataque;
	}

	@Override
	public void run() {
		ExecutorService pool = Executors.newCachedThreadPool();

		try {
			handle.loop(-1, new AddDataQue()/* ,pool */);
		} catch (PcapNativeException | InterruptedException | NotOpenException e) {
			e.printStackTrace();
		}

		pool.shutdown();
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see vairy.pcap.PcapTask#dispose()
	 */
	@Override
	public void dispose() {
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		handle.close();
		super.finalize();
	}

	/**
	 * 最新のパケットデータをデータキューに追加する。
	 *
	 * @author vairydler
	 *
	 */
	private class AddDataQue implements PacketListener {
		private Long presec = 0L;
		private Integer preusec = 0;
		@Override
		public void gotPacket(Packet packet) {
			if(null != packet){
				Timestamp ts = new Timestamp(handle.getTimestampInts() * 1000L);
				ts.setNanos(handle.getTimestampMicros() * 1000);
				PacketData4j packetData = new PacketData4j(ts, packet);
				dataque.add(packetData);
				listener.gotPacket(packetData);
			}else{
				Long diff = Math.abs(presec - handle.getTimestampInts());
				if(diff < 1){
					int abs = Math.abs(this.preusec - handle.getTimestampMicros());
					VairyTimeAvgTask.addTime((long) abs);
				}
				presec = handle.getTimestampInts();
				preusec = handle.getTimestampMicros();
			}
		}
	}

	public static List<String> getDevList() {
		List<String> rtn = new ArrayList<>();
		try {
//			nif = new NifSelector().selectNetworkInterface();
			List<PcapNetworkInterface> findAllDevs = Pcaps.findAllDevs();

			if(findAllDevs == null || findAllDevs.size() <= 0){
				throw new IOException("No NIF to capture.");
			}

			for (PcapNetworkInterface pcapNetworkInterface : findAllDevs) {
				rtn.add(pcapNetworkInterface.toString());
			}
		} catch (IOException | PcapNativeException e) {
			e.printStackTrace();
		}

		return rtn;
	}
}