package vairy.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FileReader {
	private FileInputStream fisRead;
	private InputStreamReader isrRead;
	private BufferedReader brRead;

	/**
	 * 読み込むファイル
	 */
	private File ReadFile;

	/**
	 * 特に動作しない。
	 */
	public FileReader(){};
	/**
	 * 読み込むファイルを指定し、開く。
	 * @param FileName 読み込むファイルの名前
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public FileReader(String FileName) throws FileNotFoundException, UnsupportedEncodingException
	{
		this(new File(FileName));
	}
	/**
	 * 読み込むファイルを指定し、開く。
	 * @param file 読み込むファイル
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public FileReader(final File file) throws FileNotFoundException, UnsupportedEncodingException{
		this(file,(String)null);
	}
	/**
	 * 読み込むファイルを指定し、開く。
	 * @param FileName 読み込むファイルの名前
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 *
	 */
	public FileReader(final String FileName,final String charset) throws FileNotFoundException, UnsupportedEncodingException
	{
		this(new File(FileName),charset);
	}
	/**
	 * 読み込むファイルを指定し、開く。
	 * @param file 読み込むファイル
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public FileReader(final File file,final String charset) throws FileNotFoundException, UnsupportedEncodingException
	{
		setReadFile(file,charset);
	}

	/**
	 * 読み込むファイルを指定し、開く。
	 * 既に開かれている場合は、閉じてから開く。
	 * @param FileName 読み込むファイルの名前
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public void setReadFile(final String FileName) throws FileNotFoundException, UnsupportedEncodingException
	{
		setReadFile(new File(FileName),null);
	}
	/**
	 * 読み込むファイルを指定し、開く。
	 * 既に開かれている場合は、閉じてから開く。
	 * @param FileName 読み込むファイルの名前
	 * @param charsetname 文字セットの名前
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public void setReadFile(String FileName,String charsetname) throws FileNotFoundException, UnsupportedEncodingException
	{
		setReadFile(new File(FileName),charsetname);
	}
	/**
	 * 読み込むファイルを指定し、開く。
	 * 既に開かれている場合は、閉じてから開く。
	 * @param file
	 * @param charsetname 文字セットの名前
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void setReadFile(final File file,String charsetname) throws FileNotFoundException,UnsupportedEncodingException
	{
		close();
		/*バッファ等の設定*/
		this.ReadFile = file;
		if(charsetname == null){
			open();
		}else{
			open(charsetname);
		}
	}

	/**
	 * リーダーを開く。setReadFileから呼び出される。
	 * @throws FileNotFoundException
	 */
	private void open() throws FileNotFoundException
	{
		fisRead= new FileInputStream(this.ReadFile);
		isrRead = new InputStreamReader(fisRead);
		brRead = new BufferedReader(isrRead);
	}
	/**
	 * リーダーを開く。setReadFileから呼び出される。
	 * @param charsetname 文字セットの名前
	 * @throws FileNotFoundException
	 */
	private void open(String charsetname) throws FileNotFoundException, UnsupportedEncodingException{
		fisRead= new FileInputStream(this.ReadFile);
		isrRead = new InputStreamReader(fisRead,charsetname);
		brRead = new BufferedReader(isrRead);
	}
	/**
	 * リーダーを閉じる。
	 * @throws IOException
	 */
	public void close()
	{
		try {
			if(brRead != null)brRead.close();
			if(isrRead != null)isrRead.close();
			if(fisRead != null)fisRead.close();

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * ファイルから一行データを読み込む。
	 * ファイルが終わりに達している場合、nullを返す。
	 * @return 読み込んだ行データ
	 * @throws IOException
	 */
	public final String ReadLine() throws IOException,NullPointerException
	{
		return brRead.readLine();
	}
}