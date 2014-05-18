package vairy.invoker;

import java.lang.annotation.Annotation;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import vairy.invoker.annotation.VCmdParamAnnotation;
import vairy.invoker.exeption.IllegalParamException;

public abstract class VCommandParam {
	public boolean setParam(String[] params) throws IllegalParamException,IllegalClassFormatException,Exception{
		boolean bProc = true;
		Map<Integer,Method> setterlist = new TreeMap<>();

		/* アノテーション付きメソッドのリストを作る */
		if (bProc) {
			Method[] methods = this.getClass().getMethods();
			VCmdParamAnnotation annotation;
			for (Method method : methods) {
				annotation = method.getAnnotation(VCmdParamAnnotation.class);

				if(null != annotation){
					int num = annotation.num();
					if(num != -1 && num < params.length ){
						setterlist.put(num,method);
					}else{
						throw new IllegalParamException("パラメータの数か、パラメータクラスの設定がおかしいです。");
					}
				}
			}
		}

		/* アノテーション付きメソッドが引数一個かどうか確認 */
		if (bProc) {
			Collection<Method> values = setterlist.values();
			for (Method method : values) {
				bProc &= (method.getParameterTypes().length == 1);
			}
		}

		/* アノテーション付きメソッドをコールする。その際、引数キャストを忘れずに。 */
		if (bProc) {
			Set<Integer> keySet = setterlist.keySet();
			Object castedObject;
			Class<?> class1;

			for (Integer integer : keySet) {
				class1 = setterlist.get(integer).getParameterTypes()[0];
				castedObject = castedObject(params[integer], class1);
				if(null != castedObject){
					setterlist.get(integer).invoke(this, castedObject);
				}else{
					throw new IllegalClassFormatException("valueOfメソッドで単純キャストできない引数です。");
				}
			}
		}

		return bProc;
	}

	/**
	 * staticメソッドのvalueOfを使用してキャストする。
	 * @param value 文字列
	 * @param castedClass 文字列型をキャストするクラス
	 * @return キャスト後のクラスか、ダメならnull。
	 */
	private Object castedObject(String value,Class<?> castedClass){
		Object rtn = null;
		Method[] methods = castedClass.getMethods();
		if(value.getClass() == castedClass){
			rtn = value;
		}
		else
		{
			for (Method method : methods) {
				if(		"valueOf".equals(method.getName())
					&&	Modifier.isStatic(method.getModifiers())
					&&	method.getParameterTypes()[0] == value.getClass()){
					try {
						rtn = method.invoke(null, value);
						break;
					} catch (IllegalAccessException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
			}
		}
		return rtn;
	}
}
