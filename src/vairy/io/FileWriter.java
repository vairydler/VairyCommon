package vairy.io;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class FileWriter {

	private FileOutputStream fosWrite;
	private OutputStreamWriter oswWrite;
	private BufferedWriter bwWrite;

	private String WriteFileName;
	private String charsetname;

	public FileWriter(){
	}
	public FileWriter(final String order_FileName_S) throws IOException
	{
		setWriteFile(order_FileName_S, false, Charset.defaultCharset().name());
	}
	public FileWriter(final String order_FileName_S, final String charset) throws IOException{
		setWriteFile(order_FileName_S, false, charset);
	}
	public FileWriter(final String order_FileName_S, final boolean order_append_b) throws IOException
	{
		setWriteFile(order_FileName_S, order_append_b,Charset.defaultCharset().name());
	}
	public FileWriter(final String order_FileName_S, final String charset, final boolean order_append_b) throws IOException
	{
		setWriteFile(order_FileName_S, order_append_b,charset);
	}
	/**
	 * 書き込むファイルを指定し、開く。
	 * 既に別のファイルが開かれている場合は、閉じてから開く。
	 * @param FileName 書き込み対象のファイル名、ファイルパス。
	 * @param append trueの場合、上書きでなく、追記。
	 * @throws IOException
	 */
	public void setWriteFile(String FileName,boolean append) throws IOException
	{
		setWriteFile(FileName, append, Charset.defaultCharset().name());
	}	/**
	 * 書き込むファイルを指定し、開く。
	 * 既に別のファイルが開かれている場合は、閉じてから開く。
	 * @param FileName 書き込み対象のファイル名、ファイルパス。
	 * @param append trueの場合、上書きでなく、追記。
	 * @param charset 文字コード
	 * @throws IOException
	 */
	public void setWriteFile(String FileName,boolean append, String charset) throws IOException
	{
		close();
		this.WriteFileName = FileName;
		this.charsetname = charset;
		open(append);
	}

	/**
	 * 書き込むファイルを開く。
	 * @param append trueの場合、上書きでなく、追記。
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	private void open(boolean append) throws FileNotFoundException, UnsupportedEncodingException
	{
		fosWrite = new FileOutputStream(WriteFileName,append);
		oswWrite = new OutputStreamWriter(fosWrite,this.charsetname);
		bwWrite = new BufferedWriter(oswWrite);
	}

	/**
	 * 現在書き込み中のファイルを閉じる。
	 * @throws IOException
	 */
	public void close() throws IOException
	{
		if(fosWrite != null)fosWrite.close();
		if(oswWrite != null)oswWrite.close();
		if(bwWrite != null)bwWrite.close();
	}

	/**
	 * 対象ファイルに文字列を書き込む。
	 * @param order 書き込む文字列
	 * @return true:書き込み成功<br>
	 * false:書き込み失敗
	 */
	public Boolean writeLine(String order)
	{
		Boolean rtn = false;
		try {
			bwWrite.write(order);
			bwWrite.flush();
			rtn = true;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return rtn;
	}
}