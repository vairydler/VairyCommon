package vairy.pcap;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface PcapTask{

	public void setFilter(String bpfexp);

	public ConcurrentLinkedQueue<PacketData4j> getDataque();

	public void dispose();

	public void start();

	public interface PacketDataListener<T>{
		public abstract void gotPacket(T packet);
	}

	public class TCPKey {
		private String clientPort;
		private String serverPort;

		public TCPKey(String clientPort, String serverPort) {
			this.clientPort = clientPort;
			this.serverPort = serverPort;
		}
		public final String getClientPort() {
			return clientPort;
		}
		public final void setClientPort(String clientPort) {
			this.clientPort = clientPort;
		}
		public final String getServerPort() {
			return serverPort;
		}
		public final void setServerPort(String serverPort) {
			this.serverPort = serverPort;
		}

		@Override
		public boolean equals(Object obj) {
			boolean rtn = true;

			if (rtn) {
				rtn &= (obj.getClass() == this.getClass());
			}

			if (rtn) {
				TCPKey key = (TCPKey) obj;
				rtn &= (key.getClientPort().equals(this.getClientPort()));
				rtn &= (key.getServerPort().equals(this.getServerPort()));
			}
			return rtn;
		}
		@Override
		public int hashCode() {
			return (getClientPort()+getServerPort()).hashCode();
		}
	}
}