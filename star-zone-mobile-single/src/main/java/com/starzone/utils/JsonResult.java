package com.starzone.utils;

import java.io.Serializable;

/**
 * 用于封装服务器到客户端的Json返回值
 * @doc 说明
 * @FileName JsonResult.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年4月13日
 * @history 1.0.0.0 2019年4月13日 下午5:30:20 created by【qiu_hf】
 * @param <T>
 */
public class JsonResult<T> implements Serializable {
    //Serializable将对象的状态保存在存储媒体中以便可以在以后重新创建出完全相同的副本
    public static final int SUCCESS=1;
    public static final int ERROR=-1;
    public static final int OTHER=2;

    private String message = "";
    private T data;
    private static final long serialVersionUID = 123126L;
	
	private Integer code;
	
    public JsonResult(){
    	code = SUCCESS;
    }
    //为了方便，重载n个构造器
    public JsonResult(int code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public JsonResult(int state,String error){
        this(state,error,null);
    }
    public JsonResult(int state,T data){
        this(state,"",data);
    }
    public JsonResult(String error){
        this(ERROR,error,null);
    }

    public JsonResult(T data){
        this(SUCCESS,"",data);
    }
    public JsonResult(int state){
        this(state,"",null);
    }
    public JsonResult(Throwable e){
        this(ERROR,e.getMessage(),null);
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public static int getSuccess() {
        return SUCCESS;
    }
    public static int getError() {
        return ERROR;
    }
    public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	@Override
    public String toString() {
        return "JsonResult [state=" + code + ", message=" + message + ", data=" + data + "]";
    }
}