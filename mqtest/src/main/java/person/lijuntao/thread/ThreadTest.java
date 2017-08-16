package person.lijuntao.thread;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.tools.json.JSONUtil;

public class ThreadTest implements Runnable,Serializable{
	public static ThreadLocal<ThreadTest> threadTest = new ThreadLocal<ThreadTest>();
	public static Map<String,Thread> thread = new HashMap<String,Thread>();
	public int i = 0;
	public ThreadTest(){
		i++;
	}
	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			execute();
			System.out.println(JSONObject.toJSON(thread));
		}
		System.out.println("finllay" + JSONObject.toJSON(thread));
	}

	public static void execute() {
		ThreadTest test = new ThreadTest();
		threadTest.set(test);
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		cachedThreadPool.execute(test);
	}

	public void run() {
		ThreadTest test = new ThreadTest();
		threadTest.set(test);
		thread.put(Thread.currentThread().getName(), Thread.currentThread());
		System.out.println(Thread.currentThread().getName() + ":"+test.i);
	}
}
