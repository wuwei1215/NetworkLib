package com.wuwei.network.request;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import com.wuwei.network.RequestParameter;
import com.wuwei.network.util.LogUtil;

/**
 * 表单上传文件请求类，需要上传文件时，继承此类，填入参数
 * 
 * @author wuwei
 */
public class HttpPostMulRequest extends BaseRequest {

	private static final String TAG = "HttpPostMulRequest";

	protected HashMap<String, String> mFileMap = new HashMap<String, String>();

	public void setFileMap(HashMap<String, String> fileMap) {
		mFileMap = fileMap;
	}

	public void setRequestEntity() throws IOException {
		String BOUNDARY = "---------------------------"
				+ System.currentTimeMillis();// 分割符
		String PREFIX = "--"; // 前缀
		String LINEND = "\r\n"; // 换行符
		String MULTIPART_FROM_DATA = "multipart/form-data";// 数据类型
		String CHARSET = "UTF-8";// 字符编码

		mRequest.setHeader("Content-Type", MULTIPART_FROM_DATA + ";boundary="
				+ BOUNDARY);
		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		for (RequestParameter p : mRequestParameters) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\"" + p.getName()
					+ "\"" + LINEND);
			sb.append(LINEND);
			sb.append(p.getValue());
			sb.append(LINEND);
		}

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		outStream.write(sb.toString().getBytes());
		if (!mFileMap.isEmpty()) {
			// 发送文件数据
			for (Iterator<String> it = mFileMap.keySet().iterator(); it
					.hasNext();) {
				File file = null;
				String key = it.next();
				if (mFileMap.get(key) != null)
					file = new File((mFileMap.get(key)));
				if (file != null) {
					LogUtil.e(TAG + "-Size", String.valueOf(file.length()));
					StringBuilder sb1 = new StringBuilder();
					sb1.append(PREFIX);
					sb1.append(BOUNDARY);
					sb1.append(LINEND);
					// name是post中传参的键 filename是文件的名称
					sb1.append("Content-Disposition: form-data; name=\"" + key
							+ "\"; filename=\"" + file + "\"" + LINEND);
					sb1.append(LINEND);
					outStream.write(sb1.toString().getBytes());
					InputStream is = new FileInputStream(file);
					// int bytesAvailable;
					byte[] buffer = new byte[8 * 1024];
					while ((is.available()) > 0) {
						// int bufferSize = Math.min(bytesAvailable, 4096);
						// byte[] buffer = new byte[bufferSize];
						// int bytesRead = is.read(buffer, 0,is.read(buffer));
						outStream.write(buffer, 0, is.read(buffer));
					}
					is.close();
					outStream.write(LINEND.getBytes());
					// 请求结束标志

				}
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND)
						.getBytes();
				outStream.write(end_data);
			}
		}
		// LogUtil.d(TAG, outStream.toString());
		outStream.flush();
		outStream.close();
		((HttpPost) mRequest).setEntity(new ByteArrayEntity(outStream
				.toByteArray()));

	}

	@Override
	public void sentRequest() throws Exception {
		mRequest = new HttpPost(mURL);
		LogUtil.d(TAG, mURL);
		LogUtil.e(TAG + "-Start", String.valueOf(System.currentTimeMillis()));
		// mRequest.getParams().setParameter(
		// CoreConnectionPNames.CONNECTION_TIMEOUT, connectTimeout);

		if (mHeaders != null && mHeaders.length > 0) {
			mRequest.setHeaders(mHeaders);
		}

		mRequest.setHeader("Accept-Language", "zh-CN");
		mRequest.setHeader("Accept-Encoding", "gzip");

		setRequestEntity();

		HttpResponse response = httpClient.execute(mRequest);
		dealWithResponse(response);
		LogUtil.e(TAG + "-End", String.valueOf(System.currentTimeMillis()));
	}

}
