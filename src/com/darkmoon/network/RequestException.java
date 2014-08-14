package com.darkmoon.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;

/**
 * 请求异常
 * 
 * @author wuwei
 * 
 */
public class RequestException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8808769741459594708L;
	public int code = 0;
	public String detail = "";

	/**
	 * 未知异常
	 */
	public final static int UNKNOWN_EXCEPTION = 0x01;

	/**
	 * {@link EOFException} 抛出此类异常，表示连接丢失，也就是说网络连接的另一端非正常关闭连接（可能是主机断电、网线出现故障等导致）
	 */
	public final static int SERVER_CLOSED_EXCEPTION = 0x02;

	/**
	 * {@link SocketException} 抛出此类异常，表示无法连接，也就是说当前主机不存在
	 */
	public final static int CONNECT_EXCEPTION = 0X03;

	/**
	 * {@link SocketException} 抛出此类异常，表示
	 * <ul>
	 * <li>1、连接正常关闭，也就是说另一端主动关闭连接</li>
	 * <li>2、表示一端关闭连接，而另一端此时在读数据</li>
	 * <li>3、表示一端关闭连接，而另一端此时在发送数据</li>
	 * <li>4、表示连接已关闭，但还继续使用（也就是读/写操作）此连接</li>
	 * </ul>
	 */
	public final static int SOCKET_EXCEPTION = 0x04;

	/**
	 * {@link BindException} 抛出此类异常，表示端口已经被占用
	 */
	public final static int BIND_EXCEPTION = 0x05;

	/**
	 * {@link ConnectTimeoutException} 连接超时
	 */
	public final static int CONNECT_TIMEOUT_EXCEPTION = 0x06;

	/**
	 * {@link UnsupportedEncodingException} 不支持的编码异常
	 */
	public final static int UNSUPPORTED_ENCODEING_EXCEPTION = 0x07;

	/**
	 * {@link SocketTimeoutException} socket 超时异常
	 */
	public final static int SOCKET_TIMEOUT_EXCEPTION = 0x08;

	/**
	 * {@link ClientProtocolException} 客户端协议异常
	 */
	public final static int CLIENT_PROTOL_EXCEPTION = 0x09;

	/**
	 * {@link IOException} 读取异常
	 */
	public final static int IO_EXCEPTION = 0x0A;

	/**
	 * 协议解析异常
	 */
	public final static int PROTOL_EXCEPTION = 0x0B;

	/**
	 * 协议請求异常
	 */
	public final static int PROTOL_REQUEST_EXCEPTION = 0x0C;

	public RequestException(int code, String msg, Throwable throwable) {
		super(msg, throwable);
		this.code = code;
		this.detail = msg;
	}

	public RequestException(int code, String msg) {
		super(msg, null);
		this.code = code;
		this.detail = msg;
	}
}
