package vairy.pcap;

import java.sql.Timestamp;
import java.util.Map.Entry;

import org.pcap4j.packet.Packet;

/**
 * タイムスタンプとパケットを保持する。
 *
 * @author vairydler
 *
 */
public class PacketData4j implements Entry<Timestamp, Packet> {
	private Timestamp key;
	private Packet value;

	public PacketData4j(final Timestamp key, final Packet value) {
		setKey(key);
		setValue(value);
	}

	public Timestamp setKey(Timestamp key) {
		return this.key = key;
	}

	@Override
	public Timestamp getKey() {
		return this.key;
	}

	@Override
	public Packet getValue() {
		return this.value;
	}

	@Override
	public Packet setValue(Packet value) {
		return this.value = value;
	}
}