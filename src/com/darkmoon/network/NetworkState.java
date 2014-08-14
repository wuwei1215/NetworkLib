package com.darkmoon.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络状态
 * 
 * @author wuwei
 * 
 */
public class NetworkState {

	public static final int NET_STATE_UNCONNECTED = 0;
	public static final int NET_STATE_MOBILE = 1;
	public static final int NET_STATE_WIFI = 2;

	private static ConnectivityManager connectivity;

	public static int checkNetworkState(Context context) {
		if (connectivity == null) {
			connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
		}

		if (isWifiConnected()) {
			return NET_STATE_WIFI;
		} else if (isMobileConnected()) {
			return NET_STATE_MOBILE;
		} else {
			return NET_STATE_UNCONNECTED;
		}
	}

	public static boolean isNetworkConnected() {

		NetworkInfo mNetworkInfo = connectivity.getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			return mNetworkInfo.isConnected();
		}

		return false;
	}

	public static boolean isWifiConnected() {

		NetworkInfo mWiFiNetworkInfo = connectivity
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (mWiFiNetworkInfo != null) {
			return mWiFiNetworkInfo.isConnected();
		}

		return false;
	}

	public static boolean isMobileConnected() {

		NetworkInfo mMobileNetworkInfo = connectivity
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mMobileNetworkInfo != null) {
			return mMobileNetworkInfo.isConnected();
		}

		return false;
	}

}
