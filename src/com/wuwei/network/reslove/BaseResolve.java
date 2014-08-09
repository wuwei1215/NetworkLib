package com.wuwei.network.reslove;

import org.apache.http.HttpResponse;

import com.wuwei.network.RequestException;

/**
 * 协议解析接口
 * 
 * @author wuwei
 */
public interface BaseResolve {

	public Object recvAndParseRsp(HttpResponse response)
			throws RequestException;

}
