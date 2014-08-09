package com.wuwei.network.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.security.KeyStore;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.wuwei.network.Configs;
import com.wuwei.network.RequestException;
import com.wuwei.network.RequestParameter;
import com.wuwei.network.reslove.BaseResolve;
import com.wuwei.network.response.BaseResponse;
import com.wuwei.network.util.MySSLSocketFactory;

/**
 * 请求基类
 * 
 * @author wuwei
 */
public abstract class BaseRequest implements Runnable {

	protected int mRequestCode = -1;// 协议号
	protected Header[] mHeaders;// 请求头
	protected ArrayList<RequestParameter> mRequestParameters;// 请求参数
	protected BaseResponse mBaseResponse;// 请求响应监听
	protected String mURL;// 接口地址
	protected BaseResolve mBaseResolve;// 解析器
	protected HttpClient httpClient;
	protected boolean isSSL = true;
	/**
	 * default is 5 seconds ,to set .
	 */
	private int connectTimeout = Configs.TIME_OUT_CONNECT;

	/**
	 * default is 5 seconds ,to set .
	 */
	private int socketTimeout = Configs.TIME_OUT_SOCKET;

	protected HttpUriRequest mRequest = null;

	protected void setConnectTimeout(int timeout) {
		this.connectTimeout = timeout;
	}

	protected void setSocketTimeout(int timeout) {
		this.socketTimeout = timeout;
	}

	public HttpUriRequest getRequest() {
		return mRequest;
	}

	@Override
	public void run() {

		httpClient = isSSL ? getNewHttpClient() : new DefaultHttpClient();

		try {
			httpClient.getParams().setIntParameter(
					HttpConnectionParams.SO_TIMEOUT, socketTimeout); // 请求超时
			httpClient.getParams().setIntParameter(
					HttpConnectionParams.CONNECTION_TIMEOUT, connectTimeout);// 连接超时

			sentRequest();

		} catch (java.lang.IllegalArgumentException e) {
			makeException(RequestException.IO_EXCEPTION, "连接错误", e);
		} catch (org.apache.http.conn.ConnectTimeoutException e) {
			makeException(RequestException.SOCKET_TIMEOUT_EXCEPTION, "连接超时", e);
		} catch (java.net.SocketTimeoutException e) {
			makeException(RequestException.SOCKET_TIMEOUT_EXCEPTION, "读取超时", e);
		} catch (UnsupportedEncodingException e) {
			makeException(RequestException.UNSUPPORTED_ENCODEING_EXCEPTION,
					"编码错误", e);
		} catch (org.apache.http.conn.HttpHostConnectException e) {
			makeException(RequestException.CONNECT_EXCEPTION, "连接错误", e);
		} catch (ClientProtocolException e) {
			makeException(RequestException.CLIENT_PROTOL_EXCEPTION, "客户端协议异常",
					e);
		} catch (IOException e) {
			makeException(RequestException.IO_EXCEPTION, "数据读取异常", e);
		} catch (RequestException exception) {// 协议解析异常
			makeException(exception);
		} catch (Exception e) {
			makeException(RequestException.UNKNOWN_EXCEPTION, "未知异常", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return;
	}

	private void makeException(int code, String msg, Exception e) {
		RequestException exception = new RequestException(code, msg, e);
		makeException(exception);
	}

	private void makeException(RequestException exception) {
		mBaseResponse.onError(mRequestCode, exception);
		exception.printStackTrace();
	}

	public int getRequestCode() {
		return mRequestCode;
	}

	public void setRequestCode(int mRequestCode) {
		this.mRequestCode = mRequestCode;
	}

	public void setRequestParameter(ArrayList<RequestParameter> mParameters) {
		this.mRequestParameters = mParameters;
	}

	public void addRequestParameter(RequestParameter mParameter) {
		this.mRequestParameters.add(mParameter);
	}

	public void addRequestParameter(String name, String value) {
		addRequestParameter(new RequestParameter(name, value));
	}

	public void setResponse(SoftReference<BaseResponse> mBaseResponseReference) {
		this.mBaseResponse = mBaseResponseReference.get();
	}

	public void setResolve(BaseResolve mBaseResolve) {
		this.mBaseResolve = mBaseResolve;
	}

	public void setHeaders(Header[] headers) {
		mHeaders = headers;
	}

	public abstract void sentRequest() throws Exception;

	public void dealWithResponse(HttpResponse response) throws RequestException {
		int statusCode = response.getStatusLine().getStatusCode();
		if (mBaseResponse == null) {
			return;
		}
		if (statusCode == HttpStatus.SC_OK) {
			if (mBaseResolve != null) {
				Object result = mBaseResolve.recvAndParseRsp(response);
				mBaseResponse.onSuccess(mRequestCode, result);
			} else {
				RequestException exception = new RequestException(
						RequestException.PROTOL_EXCEPTION, "协议解析异常（无解析器）");
				mBaseResponse.onError(mRequestCode, exception);
			}
		} else {
			RequestException exception = new RequestException(
					RequestException.IO_EXCEPTION, "响应码异常,响应码：" + statusCode,
					null);
			mBaseResponse.onError(mRequestCode, exception);
		}
	}

	public static HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			// SSLSocketFactory sf = new SSLSocketFactory(trustStore);
			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

}
