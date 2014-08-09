package com.wuwei.network;

import com.wuwei.network.request.BaseRequest;
import com.wuwei.network.reslove.BaseResolve;
import com.wuwei.network.response.BaseResponse;

/**
 * 网络通讯管理类，由这个类负责发起请求
 * 
 * @author wuwei
 * 
 */

public class RequestManager {

	/**
	 * 
	 * @param mBaseRequest
	 *            请求
	 * @param mBaseResolve
	 *            解析器
	 * @param mBaseResponse
	 *            响应接口
	 * @return 请求ID
	 */
	public static int getResponse(BaseRequest mBaseRequest,
			BaseResolve mBaseResolve, BaseResponse mBaseResponse) {
		mBaseRequest.setResolve(mBaseResolve);
		mBaseRequest.setResponse(mBaseResponse);
		return DefaultThreadPool.getInstance().execute(mBaseRequest);
	}

	public static void cancleRequest(int id) {
		DefaultThreadPool.getInstance().cancleRequest(id);
	}

}
