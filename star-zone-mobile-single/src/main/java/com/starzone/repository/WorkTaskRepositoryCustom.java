package com.starzone.repository;

import com.starzone.pojo.WorkTask;

/**
 * 任务管理自定义查询接口
 * @doc 说明
 * @FileName WorkTaskRepositoryCustom.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2018年12月16日
 * @history 1.0.0.0 2018年12月16日 上午1:40:32 created by【qiu_hf】
 */
public interface WorkTaskRepositoryCustom {

	void modifyWorkTasks(WorkTask workTask);
}
