package vairy.invoker;

import java.util.HashMap;
import java.util.Map;

public class VInvoker {
	private Map<String, VCommand> commandmap = new HashMap<String, VCommand>();

	/**
	 * 登録済みのコマンドを呼び出す
	 * @param strCommand コマンド文字列
	 * @param value コマンドパラメータ。あったりなかったり。
	 * @return	true:呼び出し成功<br>
	 * 			false:呼び出し失敗、または各コマンドによるfalse要因
	 */
	public Boolean invoke(final String strCommand, final Object value){
		Boolean rtn = false;
		VCommand command;

		command = commandmap.get(strCommand);
		if(null != command)
		{
			rtn = command.execute(value);
		}
		else
		{
			rtn = false;
		}

		return rtn;
	}

	/**
	 * コマンドを登録する。
	 * @param strCommand コマンド文字列
	 * @param command 登録するコマンド
	 */
	public void regCommand(final String strCommand, final VCommand command){
		commandmap.put(strCommand, command);
	}
}
