package com.wuwei.network.response;

import com.wuwei.network.RequestException;

/**
 * 对server响应做相应操作的接口
 * 
 * @author wuwei
 */
public interface BaseResponse {

	public void onSuccess(int mRequestCode, Object result);

	public void onError(int mRequestCode, RequestException mRequestException);

}
