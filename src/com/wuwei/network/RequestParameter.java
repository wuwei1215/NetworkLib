package com.wuwei.network;

/**
 * 请求参数
 * 
 * @author wuwei
 * 
 */
public class RequestParameter {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String value;

	public RequestParameter(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (null == o) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof RequestParameter) {
			RequestParameter parameter = (RequestParameter) o;
			return name.equals(parameter.name) && value.equals(parameter.value);
		}

		return false;
	}

}
