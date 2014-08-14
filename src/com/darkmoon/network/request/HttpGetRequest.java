package com.darkmoon.network.request;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.darkmoon.network.RequestParameter;
import com.darkmoon.network.util.CodeUtil;
import com.darkmoon.network.util.LogUtil;

/**
 * Get请求类，需要发送Get请求时，继承此类，填入参数
 * 
 * @author wuwei
 */
public class HttpGetRequest extends Request {

	@Override
	public void sentRequest() throws Exception {
		if (mRequestParameters != null && mRequestParameters.size() > 0) {
			StringBuilder bulider = new StringBuilder();
			for (RequestParameter p : mRequestParameters) {
				if (bulider.length() != 0) {
					bulider.append("&");
				}
				bulider.append(CodeUtil.encode(p.getName()));
				bulider.append("=");
				bulider.append(CodeUtil.encode(p.getValue()));
			}
			mURL += "?" + bulider.toString();
		}
		LogUtil.e("url", mURL);

		mHttpUriRequest = new HttpGet(mURL);

		// mRequest.setHeader("charset", HTTP.UTF_8);
		if (mHeaders != null && mHeaders.length > 0) {
			mHttpUriRequest.setHeaders(mHeaders);
		}

		mHttpUriRequest.addHeader("Accept-Language", "zh-CN");
		mHttpUriRequest.addHeader("Accept-Encoding", "gzip");

		HttpResponse response = getHttpClient().execute(mHttpUriRequest);

		dealWithResponse(response);
	}
}
