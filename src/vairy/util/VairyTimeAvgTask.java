package vairy.util;

import java.util.ArrayList;
import java.util.List;

public class VairyTimeAvgTask{
	private static List<Long> tm = new ArrayList<Long>();
	public static void addTime(Long time){
		tm.add(time);
	}
	public static void clearTime(){
		tm.clear();
	}

	public static void start(){
		Thread thread = new Thread(){
			@Override
			public void run() {
				while(true){
					try {
						dummyWait();
					} catch (InterruptedException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
					Long sum = 0L;
					for (Long t : tm) {
						sum += t;
					}
					if(!tm.isEmpty()){
						System.out.println("avg=" + sum / tm.size());
					}
				}
			}
			private synchronized void dummyWait() throws InterruptedException{
				this.wait(5000);
			}
		};
		thread.start();
	}
}
