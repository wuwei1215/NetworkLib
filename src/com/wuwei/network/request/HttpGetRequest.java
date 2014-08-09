package com.wuwei.network.request;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.wuwei.network.RequestParameter;
import com.wuwei.network.util.CodeUtil;
import com.wuwei.network.util.LogUtil;

/**
 * Get请求类，需要发送Get请求时，继承此类，填入参数
 * 
 * @author wuwei
 */
public class HttpGetRequest extends BaseRequest {

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

		mRequest = new HttpGet(mURL);

		// mRequest.setHeader("charset", HTTP.UTF_8);
		if (mHeaders != null && mHeaders.length > 0) {
			mRequest.setHeaders(mHeaders);
		}

		mRequest.addHeader("Accept-Language", "zh-CN");
		mRequest.addHeader("Accept-Encoding", "gzip");

		HttpResponse response = httpClient.execute(mRequest);

		dealWithResponse(response);
	}
}
