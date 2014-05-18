package vairy.debug;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class DebugWriter {
	private static String	debugfolder = "debuglog/";
	private static Boolean	DebugOn = false;
	private static String	FileNameHeader = "";

	private FileOutputStream fos;
	private OutputStreamWriter osw;
	private BufferedWriter bw;
	private final String charset;
	private final Boolean isInitial;
	public DebugWriter(final String filename,final String charset){
		this.charset = charset;
		Boolean tempinitial = false;
		if(DebugOn){
			Boolean dirok = checkDir();

			if(dirok){
				File f = new File(debugfolder + FileNameHeader +filename);

				try {
					fos = new FileOutputStream(f,true);
					osw = new OutputStreamWriter(fos, this.charset);
					bw = new BufferedWriter(osw);
					tempinitial = true;
				} catch (FileNotFoundException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}
		isInitial = tempinitial;
	}
	private Boolean checkDir() {
		Boolean rtn = false;
		File dir = new File(debugfolder);
		if(!dir.exists()){
			rtn = dir.mkdirs();
		}else{
			rtn = true;
		}
		return rtn;
	}

	public void close(){
		if(DebugOn && isInitial){
			try {
				if(bw != null)		bw.close();
				if(osw != null)		osw.close();
				if(fos != null)		fos.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
//				e.printStackTrace();
			}
		}
	}

	public void writeFile(final Object o){
		if(DebugOn && isInitial){
			try {
				if(bw != null){
					if(o != null){
						bw.write(o.toString());
						bw.flush();
					}else{
						bw.write("\n nullobj \n");
						bw.flush();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void setDebugOn(final Boolean b){
		DebugOn = b;
	}
	public static final Boolean getDebugOn(){
		return DebugOn;
	}
	public static void setDebugFileHeader(final String header){
		if(null != header){
			FileNameHeader = header;
		}
	}
	public static final void setDebugfolder(final String debugfolder) {
		if(null != debugfolder){
			DebugWriter.debugfolder = debugfolder;
		}
	}
}
