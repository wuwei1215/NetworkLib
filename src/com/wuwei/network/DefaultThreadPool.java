package com.wuwei.network;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import com.wuwei.network.request.BaseRequest;

/**
 * 线程池
 * 
 * @author wuwei
 * 
 */
public class DefaultThreadPool {

	/**
	 * BaseRequest任务队列
	 * 
	 * 阻塞队列。当核心线程都被占用，且阻塞队列已满的情况下，才会开启额外线程。
	 */
	private static ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(
			15);

	private static ThreadFactory tf = Executors.defaultThreadFactory();
	/**
	 * 线程池
	 */
	private static ThreadPoolExecutor pool = new ThreadPoolExecutor(
			Configs.corePoolSize, Configs.maximumPoolSize,
			Configs.keepAliveTime, Configs.unit, blockingQueue, tf,
			new ThreadPoolExecutor.CallerRunsPolicy());

	private static HashMap<Integer, SoftReference<BaseRequest>> requestMap = new HashMap<Integer, SoftReference<BaseRequest>>();

	private static int id = 0;

	private static DefaultThreadPool instance = new DefaultThreadPool();

	public static DefaultThreadPool getInstance() {
		return instance;
	}

	public int execute(BaseRequest r) {

		id++;

		requestMap.put(id, new SoftReference<BaseRequest>(r));

		pool.execute(r);

		return id;
	}

	public void cancleRequest(int id) {
		BaseRequest request = requestMap.get(id).get();
		if (request == null) {
			return;
		}

		blockingQueue.remove(request);

		requestMap.remove(id);

		request.setResponse(null);
	}
}
