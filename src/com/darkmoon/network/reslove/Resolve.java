package com.darkmoon.network.reslove;

import org.apache.http.HttpResponse;

import com.darkmoon.network.RequestException;

/**
 * 协议解析接口
 * 
 * @author wuwei
 */
public interface Resolve {

	public Object recvAndParseRsp(HttpResponse response)
			throws RequestException;

}
