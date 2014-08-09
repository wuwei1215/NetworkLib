package com.wuwei.network.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.wuwei.network.RequestParameter;

/**
 * Put请求类，需要发送Put请求时，继承此类，填入参数
 * 
 * @author wuwei
 */
public class HttpPutRequest extends BaseRequest {

	@Override
	public void sentRequest() throws Exception {
		mRequest = new HttpPut(mURL);

		if (mHeaders != null && mHeaders.length > 0) {
			mRequest.setHeaders(mHeaders);
		}
		if (mRequestParameters != null && mRequestParameters.size() > 0) {
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			for (RequestParameter p : mRequestParameters) {
				list.add(new BasicNameValuePair(p.getName(), p.getValue()));
			}
			((HttpPut) mRequest).setEntity(new UrlEncodedFormEntity(list,
					HTTP.UTF_8));
		}

		HttpResponse response = httpClient.execute(mRequest);
		dealWithResponse(response);

	}
}
