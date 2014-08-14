package com.darkmoon.network.response;

import android.os.Handler;

/**
 * 对server响应做相应操作
 * 
 * @author wuwei
 */
public abstract class Response extends Handler {
	public static int SUCCESS_CODE = 0x9000;
	public static int ERROR_CODE = 0x9001;

	// public void onSuccess(int requestID, int requestCode, Object result) {
	// Message message = new Message();
	// message.what = Response.SUCCESS_CODE;
	// message.arg1 = requestID;
	// message.arg2 = requestCode;
	// message.obj = result;
	// sendMessage(message);
	// }
	//
	// public void onError(int requestID, int requestCode,
	// RequestException requestException) {
	// Message message = new Message();
	// message.what = Response.ERROR_CODE;
	// message.arg1 = requestID;
	// message.arg2 = requestCode;
	// message.obj = requestException;
	// sendMessage(message);
	// }

}
