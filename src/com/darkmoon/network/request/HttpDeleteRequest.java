package com.darkmoon.network.request;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;

/**
 * 删除请求类
 * 
 * @author wuwei
 */
public class HttpDeleteRequest extends Request {

	@Override
	public void sentRequest() throws Exception {
		mHttpUriRequest = new HttpDelete(mURL);
		if (mHeaders != null && mHeaders.length > 0) {
			mHttpUriRequest.setHeaders(mHeaders);
		}

		HttpResponse response = getHttpClient().execute(mHttpUriRequest);
		dealWithResponse(response);
	}

}
