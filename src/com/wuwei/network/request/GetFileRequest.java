/**
 *@filename GetFileRequest.java
 *@author wuwei
 *@creatdate 2013 2013-9-26
 *@description
 */

package com.wuwei.network.request;

/**
 * 获取文件的请求类
 * 
 * @author wuwei
 */

public class GetFileRequest extends HttpGetRequest {

	{
		mRequestCode = 0;
	}

	public GetFileRequest(String url) {
		mURL = url;
	}
}
