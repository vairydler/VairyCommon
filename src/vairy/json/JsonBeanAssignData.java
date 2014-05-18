package vairy.json;

import java.lang.reflect.Field;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvColumnAccessType;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity(header = true)
public class JsonBeanAssignData {
	@CsvColumn(access=CsvColumnAccessType.READ_WRITE, position = 0, name = "KEY", required=true, defaultValue="")
	public String classkey;
	@CsvColumn(access=CsvColumnAccessType.READ_WRITE, position = 1, name = "JAR_PATH", required=true, defaultValue="")
	public String beanjarpath;
	@CsvColumn(access=CsvColumnAccessType.READ_WRITE, position = 2, name = "Class_Package", required=true, defaultValue="" )
	public String classpackage;
	@CsvColumn(access=CsvColumnAccessType.READ_WRITE, position = 3, name = "Class_Name", required=true, defaultValue="")
	public String classname;

	public String getClasskey() {
		return classkey;
	}
	public void setClasskey(String classkey) {
		this.classkey = classkey;
	}
	public String getBeanjarpath() {
		return beanjarpath;
	}
	public void setBeanjarpath(String beanjarpath) {
		this.beanjarpath = beanjarpath;
	}
	public String getClasspackage() {
		return classpackage;
	}
	public void setClasspackage(String classpackage) {
		this.classpackage = classpackage;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}

	@Override
	public String toString() {
		StringBuilder rtn = new StringBuilder();
		Field[] fields = this.getClass().getFields();
		for (Field field : fields) {
			try {
				Object object = field.get(this);
				rtn.append(field.getName()+" : " +object + "\r\n");
			} catch (IllegalArgumentException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return rtn.toString();
	}
}