package com.darkmoon.network;

import java.util.concurrent.TimeUnit;

/**
 * 设置参数
 * 
 * @author wuwei
 */
public class Configs {

	/**
	 * 线程池维护线程的最少数量
	 */
	public static int corePoolSize = 10;

	/**
	 * 线程池维护线程的最大数量
	 */
	public static int maximumPoolSize = 20;

	/**
	 * 线程池维护线程所允许的空闲时间
	 */
	public static long keepAliveTime = 15l;

	/**
	 * 线程池维护线程所允许的空闲时间的单位
	 */
	public static TimeUnit unit = TimeUnit.SECONDS;

	/**
	 * 请求超时，单位毫秒，0=不设超时
	 */
	public static int TIME_OUT_SOCKET = 10;

	/**
	 * 连接超时，单位毫秒，0=不设超时
	 */
	public static int TIME_OUT_CONNECT = 10;
}
