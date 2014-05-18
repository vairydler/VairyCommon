package vairy.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Set;
import java.util.SortedMap;

public class VairyUtil {
	static public void dispCharSets(){
		SortedMap<String, Charset> availableCharsets = Charset.availableCharsets();
		Set<String> keySet = availableCharsets.keySet();
		for(String key : keySet){
			System.out.println(key);
		}
	}

	static public String classToGetterString(Class<?> clazz,Object o){
		StringBuilder rtn = new StringBuilder();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String substring = method.getName().substring(0, 3);
			if(!substring.equals("get") || method.getParameterTypes().length != 0 || method.getName() == "getClass"){
				continue;
			}
			try {
				rtn.append(method.getName() + " : ");

				Object invoke = method.invoke(o);
				Class<?> returnType = method.getReturnType();
				if(returnType.isArray() && null != invoke){
					rtn.append(Arrays.deepToString((Object[]) invoke)+"\r\n");
				}else{
					if(!isSimpleValue(returnType)){
						rtn.append("\r\n");
					}
					rtn.append(invoke + "\r\n");
				}
			} catch (IllegalArgumentException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		return rtn.toString();
	}
	static public String classToFieldString(Class<?> clazz,Object o){
		StringBuilder rtn = new StringBuilder();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			PropertyDescriptor pd;
			try {
				rtn.append(field.getName() + " : ");

				pd = new PropertyDescriptor(field.getName(), clazz);
				Object invoke = pd.getReadMethod().invoke(o);
				Class<?> returnType = pd.getReadMethod().getReturnType();
				if(returnType.isArray() && null != invoke){
					rtn.append(Arrays.deepToString((Object[]) invoke)+"\r\n");
				}else{
					if(!isSimpleValue(returnType)){
						rtn.append("\r\n");
					}
					rtn.append(invoke + "\r\n");
				}
			} catch (IntrospectionException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		return rtn.toString();
	}
	private static boolean isSimpleValue(Class<?> returnType) {
		Boolean rtn = false;
		rtn |= returnType.isPrimitive();
		rtn |= (returnType == String.class);
		rtn |= (returnType.getSuperclass() == Number.class);

		return rtn;
	}
}
