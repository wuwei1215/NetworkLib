package com.wuwei.network.reslove;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.wuwei.network.RequestException;
import com.wuwei.network.util.LogUtil;

/**
 * 通用解析类，已将数据流解析成字符串，使用者只需继承此类并实现resloveData(String data)方法即可
 * 
 * @author wuwei
 */
public abstract class HttpResolve implements BaseResolve {

	private final static String TAG = "HttpResolve";

	@Override
	public Object recvAndParseRsp(HttpResponse response)
			throws RequestException {
		try {

			HttpEntity httpEntity = response.getEntity();
			InputStream is = httpEntity.getContent();

			if (httpEntity.getContentEncoding() != null
					&& httpEntity.getContentEncoding().getValue()
							.contains("gzip")) {
				is = new GZIPInputStream(is);
			}

			ByteArrayOutputStream content = new ByteArrayOutputStream();

			int i = -1;
			while ((i = is.read()) != -1) {
				content.write(i);
			}

			// response.getEntity().writeTo(content);
			String ret = new String(content.toByteArray()).trim();
			content.close();
			is.close();

			// String ret = EntityUtils.toString(response.getEntity(), "UTF-8");

			LogUtil.d(TAG, ret);
			Object object = resloveData(ret);

			if (object != null) {
				if (object instanceof RequestException) {
					throw (RequestException) object;
				} else {
					return object;
				}
			} else {
				RequestException exception = new RequestException(
						RequestException.PROTOL_EXCEPTION, "协议解析异常");
				throw exception;
			}

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RequestException exception = new RequestException(
					RequestException.IO_EXCEPTION, "数据读取异常", e);
			throw exception;
		} catch (RequestException e) {
			throw e;
		} catch (Exception e) {
			RequestException exception = new RequestException(
					RequestException.UNKNOWN_EXCEPTION, "未知异常", e);
			throw exception;
		} finally {

		}
	}

	public abstract Object resloveData(String data);

}
