package vairy.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import vairy.io.FileReader;
import vairy.io.FileSearch;
import vairy.io.FileWriter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orangesignal.csv.Csv;
import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.handlers.CsvEntityListHandler;

/**
 * アサインテーブルを元に、キーに対応したJsonを取得する。
 * 取得条件が複雑な場合があるので、複雑な処理を噛ませるロジックも持たせる。
 * @author vairydler
 */
public class JsonBeanFactory {
	private final ObjectMapper om;
	private final String jsonAsignFilepath;
	private String keyCreateScript;
	private ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");
	private ConcurrentHashMap<String,Class<?>> assignmap = new ConcurrentHashMap<String, Class<?>>();

	private void createAsignFile() throws IOException{
		ObjectMapper om = new ObjectMapper();
		File file = new File(jsonAsignFilepath);

		JsonBeanAssign jsonBeanAssign = new JsonBeanAssign();
		om.writerWithDefaultPrettyPrinter().writeValue(file, jsonBeanAssign);
	}

	public JsonBeanFactory(final String JsonAsignFilepath) throws IOException {
		this.om = new ObjectMapper();
		this.jsonAsignFilepath = JsonAsignFilepath;
		readAsignFile();
	}

	public void readAsignFile() throws JsonParseException, JsonMappingException, IOException{
		File asignfile = new File(jsonAsignFilepath);
		this.keyCreateScript = null;
		this.assignmap.clear();

		JsonBeanAssign assign = null;
		if(asignfile.isFile()){
			assign = om.readValue(asignfile, JsonBeanAssign.class);
		}else{
			createAsignFile();
		}

		if (null != assign) {
			/* キークリエイターをロード */
			String keyCreateScriptPath = assign.getKeyCreateScriptPath();
			if(null != keyCreateScriptPath){
				File file = new File(keyCreateScriptPath);
				if(file.exists()){
					FileReader fr = new FileReader(file);
					String readbuff = null;
					StringBuilder builder = new StringBuilder();
					while(null != (readbuff = fr.ReadLine())){
						builder.append(readbuff);
					}
					this.keyCreateScript = builder.toString();
					fr.close();
				}else{
					throw new FileNotFoundException(keyCreateScriptPath);
				}
			}
			/* キーアサインをロード */
			Class<?> loadClass;
			String assignCsvPath = assign.getAssignCsvPath();
			if(null != assignCsvPath){
				File file = new File(assignCsvPath);
				if(file.exists()){
					List<JsonBeanAssignData> clslist = Csv.load(file, new CsvConfig(), new CsvEntityListHandler<JsonBeanAssignData>(JsonBeanAssignData.class));
					{
						for (JsonBeanAssignData jsonBeanAssignData : clslist) {
							loadClass = loadClass(jsonBeanAssignData);

							if(null != loadClass ){
								assignmap.put(jsonBeanAssignData.classkey, loadClass);
							}else{
								throw new InvalidClassException(jsonBeanAssignData.toString());
							}
						}
					}
				}else{
					throw new FileNotFoundException(assignCsvPath);
				}
			}else{
				throw new FileNotFoundException("CSVファイルのパスがNULLです。");
			}
		}
	}

	/**
	 * 対象のクラスをロードする。
	 * @param asign ロードするクラスのデータ
	 * @return ロード失敗：NULL ロード成功：クラス
	 */
	private Class<?> loadClass(final JsonBeanAssignData asign){
		Class<?> rtn = null;
		String beanjarpath = asign.beanjarpath;

		if (null != beanjarpath) {
			File jarfile = new File(beanjarpath);
			String classname = asign.classpackage + "." + asign.classname;
			try {
				URL[] urls = { jarfile.toURI().toURL()};
				ClassLoader loader = URLClassLoader.newInstance(urls);
				rtn = loader.loadClass(classname);
			} catch (Exception e) {
				rtn = null;
			}
		}

		return rtn;
	}

	/**
	 * キーとJsonデータに対応したJsonBeanを取得する。
	 * @param key キー
	 * @param json Jsonデータ
	 * @return JsonBean
	 */
	public Object getJsonObject(final String key, final String json){
		Object rtn = null;
		String localkey;

		if(null != keyCreateScript){
			Map<String, Object> bindmap = new HashMap<String, Object>();
			bindmap.put("key", key);
			Bindings bind = new SimpleBindings(bindmap);

			try {
				localkey = (String) se.eval(keyCreateScript, bind);
			} catch (ScriptException e) {
				localkey = key;
			}
		}else{
			localkey = key;
		}

		Class<?> loadclass = assignmap.get(localkey);
		if(null != loadclass){
			try {
				rtn = om.readValue(json, loadclass);
			} catch (JsonParseException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		return rtn;
	}
}
