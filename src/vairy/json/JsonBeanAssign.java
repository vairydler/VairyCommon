package vairy.json;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonBeanAssign {
	private String keyCreateScriptPath;
	private String assignCsvPath;
	private Map<String,JsonBeanAssignData> classmap = new ConcurrentHashMap<String, JsonBeanAssignData>();

	public final String getKeyCreateScriptPath() {
		return keyCreateScriptPath;
	}
	public final void setKeyCreateScriptPath(String keyCreateScriptPath) {
		this.keyCreateScriptPath = keyCreateScriptPath;
	}
	public String getAssignCsvPath() {
		return assignCsvPath;
	}
	public void setAssignCsvPath(String assignCsvPath) {
		this.assignCsvPath = assignCsvPath;
	}
	public final Map<String, JsonBeanAssignData> getClassmap() {
		return classmap;
	}
	public final void setClassmap(Map<String, JsonBeanAssignData> classmap) {
		this.classmap = classmap;
	}
}
