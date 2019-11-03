package com.starzone.utils;

/**
 * 传参的分页信息
 * @doc 说明
 * @FileName PageBean.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2018年12月23日
 * @history 1.0.0.0 2018年12月23日 下午7:46:32 created by【qiu_hf】
 */
public class AppPage<T> {

	private int currentPage;
	private int pageSize;
	private T bean;//相应对象集合
	private int pageNum;
	private T param;// 其他参数
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public T getBean() {
		return bean;
	}
	public void setBean(T bean) {
		this.bean = bean;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public T getParam() {
		return param;
	}
	public void setParam(T param) {
		this.param = param;
	}
}
