package vairy.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import vairy.io.FileAccessor;

public class JsonAccecor<T> implements FileAccessor<T>{
	protected final String filename;
	protected final Class<T> c;
	private ObjectMapper om;

	public JsonAccecor(final String filename,final Class<T> c) {
		this.filename = filename;
		this.c = c;
		om = new ObjectMapper();
	}
	public T readFile(){
		T rtn = null;

		try {
			rtn = om.readValue(new File(filename), c);
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return rtn;

	}
	public Boolean writeFile(T src) {
		boolean rtn = false;
		try {
				om.writeValue(new File(filename), src);
				rtn = true;
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return rtn;
	}
}
