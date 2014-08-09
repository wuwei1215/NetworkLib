/**
 *@filename GetFileReslove.java
 *@author wuwei
 *@creatdate 2013 2013-9-26
 *@description
 */

package com.wuwei.network.reslove;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.wuwei.network.RequestException;

/**
 * 文件解析
 * 
 * @author wuwei
 */
public class GetFileReslove implements BaseResolve {

	private final static String TAG = "GetFileReslove";

	private final File mFile;

	public GetFileReslove(File file) {
		this.mFile = file;
	}

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

			FileOutputStream FOS = new FileOutputStream(mFile); // 创建写入文件内存流，

			// 通过此流向目标写文件

			byte buf[] = new byte[1024];
			// int downLoadFilePosition = 0;

			int numread;

			while ((numread = is.read(buf)) != -1) {
				FOS.write(buf, 0, numread);
				// downLoadFilePosition += numread;
			}

			FOS.flush();
			FOS.close();

			// 关闭数据流
			is.close();

			if (mFile != null) {
				return mFile;
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
