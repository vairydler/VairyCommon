package vairy.pcap;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Map.Entry;

public class PacketDataJNet implements Entry<Timestamp, ByteBuffer>{
	private final Timestamp ts;
	private final ByteBuffer bf;

	public PacketDataJNet(final Timestamp ts,final ByteBuffer bf) {
		this.ts = ts;
		this.bf = bf;
	}
	@Override
	public Timestamp getKey() {
		return ts;
	}

	@Override
	public ByteBuffer getValue() {
		// TODO 自動生成されたメソッド・スタブ
		return bf;
	}

	@Override
	public ByteBuffer setValue(ByteBuffer value) {
		return null;
	}
}
