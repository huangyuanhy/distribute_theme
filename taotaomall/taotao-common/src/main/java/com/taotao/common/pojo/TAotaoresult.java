package com.taotao.common.pojo;

public class TAotaoresult {
private Integer status;
private String msg;
private Object data;
public Integer getStatus() {
	return status;
}
public void setStatus(Integer status) {
	this.status = status;
}
public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
public Object getData() {
	return data;
}
public void setData(Object data) {
	this.data = data;
}
public TAotaoresult(Integer status, String msg, Object data) {
	super();
	this.status = status;
	this.msg = msg;
	this.data = data;
}
public TAotaoresult(Object data) {
	this.data = data;
	msg="OK";
	status=200;
}
public static TAotaoresult ok(Object data) {
	return new TAotaoresult( data);
	
}
public static TAotaoresult ok() {
	return new TAotaoresult( null);
	
}
public static TAotaoresult build(Integer status, String msg, Object data) {
	return new TAotaoresult(status, msg, data);
}
public static TAotaoresult build(Integer status, String msg) {
	return new TAotaoresult(status, msg, null);
}
}
