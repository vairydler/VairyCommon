package vairy.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import vairy.io.FileAccessor;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

public class JsonAccecor<T> implements FileAccessor<T>{
	protected final String filename;
	protected final Class<T> c;
	private ObjectMapper om;

	public JsonAccecor(final String filename,final Class<T> c) {
		this.filename = filename;
		this.c = c;
		om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.GETTER, Visibility.PUBLIC_ONLY);
		om.setVisibility(PropertyAccessor.SETTER, Visibility.PUBLIC_ONLY);
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

	public Boolean writeFile(T src,Boolean isPretty) {
		boolean rtn = false;
		try {
				if(isPretty){
					om.writerWithDefaultPrettyPrinter().writeValue(new File(filename), src);
				}
				else
				{
					om.writeValue(new File(filename), src);
				}
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
