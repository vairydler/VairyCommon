package vairy.invoker;

public interface VCommand {
	/**
	 * コマンド実行
	 * @param value コマンドパラメータ
	 * @return	true:コマンド呼び出し成功
	 * 			false:コマンド呼び出し失敗
	 */
	public Boolean execute(Object value);
}
