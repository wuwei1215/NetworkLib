package com.wuwei.network.request;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;

/**
 * 删除请求类
 * 
 * @author wuwei
 */
public class HttpDeleteRequest extends BaseRequest {

	@Override
	public void sentRequest() throws Exception {
		mRequest = new HttpDelete(mURL);
		if (mHeaders != null && mHeaders.length > 0) {
			mRequest.setHeaders(mHeaders);
		}

		HttpResponse response = httpClient.execute(mRequest);
		dealWithResponse(response);
	}

}
