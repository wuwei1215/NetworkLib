package com.darkmoon.network.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.darkmoon.network.RequestParameter;

/**
 * Post请求类，需要发送Post请求时，继承此类，填入参数
 * 
 * @author wuwei
 */
public class HttpPostRequest extends Request {

	private static final String TAG = "HttpPostRequest";

	@Override
	public void sentRequest() throws Exception {
		mHttpUriRequest = new HttpPost(mURL);

		if (mHeaders != null && mHeaders.length > 0) {
			mHttpUriRequest.setHeaders(mHeaders);
		}

		mHttpUriRequest.setHeader("Accept-Language", "zh-CN");
		mHttpUriRequest.setHeader("Accept-Encoding", "gzip");

		// 设定需要post的参数
		if (mRequestParameters != null && mRequestParameters.size() > 0) {
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			for (RequestParameter p : mRequestParameters) {
				list.add(new BasicNameValuePair(p.getName(), p.getValue()));
			}
			((HttpPost) mHttpUriRequest).setEntity(new UrlEncodedFormEntity(
					list, HTTP.UTF_8));
		}
		HttpResponse response = getHttpClient().execute(mHttpUriRequest);
		dealWithResponse(response);

	}
}
