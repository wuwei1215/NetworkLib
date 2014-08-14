package com.darkmoon.network;

import android.os.Handler;

import com.darkmoon.network.request.Request;
import com.darkmoon.network.reslove.Resolve;

/**
 * 网络通讯管理类，由这个类负责发起请求
 * 
 * @author wuwei
 * 
 */

public class RequestManager {

	/**
	 * 
	 * @param request
	 *            请求
	 * @param resolve
	 *            解析器
	 * @param baseResponse
	 *            响应
	 * @return 请求ID
	 */
	// public static int getResponse(Request request, Resolve resolve,
	// Response baseResponse) {
	// request.setResolve(resolve);
	// request.setResponse(baseResponse);
	// return DefaultThreadPool.getInstance().execute(request);
	// }

	public static int getResponse(Request request, Resolve resolve,
			Handler responseHandler) {
		request.setResolve(resolve);
		request.setResponseHandler(responseHandler);
		return DefaultThreadPool.getInstance().execute(request);
	}

	public static void cancleRequest(int id) {
		DefaultThreadPool.getInstance().cancleRequest(id);
	}

}
