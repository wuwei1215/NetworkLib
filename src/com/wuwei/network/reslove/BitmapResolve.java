/**
 *@filename BitmapResolve.java
 *@author wuwei
 *@creatdate 2013 2013-9-21
 *@description
 */

package com.wuwei.network.reslove;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.wuwei.network.RequestException;

/**
 * 图片解析
 * 
 * @author wuwei
 */
public class BitmapResolve implements BaseResolve {

	private final static String TAG = "BitmapResolve";

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.letou.network.BaseResolve#recvAndParseRsp(org.apache.http.HttpResponse
	 * )
	 */
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

			// 解析得到图片
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			// 关闭数据流
			is.close();

			if (bitmap != null) {
				return bitmap;
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
		} catch (Exception e) {
			RequestException exception = new RequestException(
					RequestException.UNKNOWN_EXCEPTION, "未知异常", e);
			throw exception;
		} finally {

		}
	}

}
