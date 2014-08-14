package com.darkmoon.network.util;

import android.util.Log;

/**
 * 日志工具类
 * 
 * @author wuwei
 * 
 */
public class LogUtil {

	public static boolean NEED_LOG = true;

	public static void e(String tag, String msg) {
		if (NEED_LOG) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (NEED_LOG) {
			Log.e(tag, msg, tr);
		}
	}

	public static void d(String tag, String msg) {
		if (NEED_LOG) {
			Log.d(tag, msg);
		}
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (NEED_LOG) {
			Log.d(tag, msg, tr);
		}
	}

	public static void i(String tag, String msg) {
		if (NEED_LOG) {
			Log.i(tag, msg);
		}
	}

	public static void v(String tag, String msg) {
		if (NEED_LOG) {
			Log.v(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (NEED_LOG) {
			Log.w(tag, msg);
		}
	}
}
