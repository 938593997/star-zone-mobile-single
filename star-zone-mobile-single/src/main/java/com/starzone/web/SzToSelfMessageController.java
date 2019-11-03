/**
 * @filename:SzToSelfMessageController 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.utils.JsonResult;
import com.starzone.pojo.SzToSelfMessage;
import com.starzone.service.master.SzToSelfMessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**   
 * @Description:  给自己的信息接口层
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@Api(description = "给自己的信息",value="给自己的信息" )
@RestController
@RequestMapping("/api/szToSelfMessage")
//@CrossOrigin(origins = {"http://127.0.0.1:8000", "null"})
public class SzToSelfMessageController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public SzToSelfMessageService szToSelfMessageServiceImpl;
	
	/**
	 * @explain 查询给自己的信息对象  <swagger GET请求>
	 * @param   对象参数：id
	 * @return  szToSelfMessage
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@GetMapping("/getSzToSelfMessageById/{id}")
	@ApiOperation(value = "获取给自己的信息信息", notes = "获取给自己的信息信息[szToSelfMessage]，作者：qiu_hf")
	@ApiImplicitParam(paramType="path", name = "id", value = "给自己的信息id", required = true, dataType = "String")
	public JsonResult<SzToSelfMessage> getSzToSelfMessageById(@PathVariable("id")String id){
		JsonResult<SzToSelfMessage> result=new JsonResult<SzToSelfMessage>();
		try {
			SzToSelfMessage szToSelfMessage = szToSelfMessageServiceImpl.selectByPrimaryKey(id);
			if (szToSelfMessage!=null) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szToSelfMessage);
			} else {
				logger.error("获取给自己的信息失败ID："+id);
				result.setCode(-1);
				result.setMessage("你获取的给自己的信息不存在");
			}
		} catch (Exception e) {
			logger.error("获取给自己的信息执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 添加给自己的信息对象
	 * @param   对象参数：szToSelfMessage
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/insertSzToSelfMessage")
	@ApiOperation(value = "添加给自己的信息", notes = "添加给自己的信息[szToSelfMessage],作者：qiu_hf")
	public JsonResult<SzToSelfMessage> insertSzToSelfMessage(SzToSelfMessage szToSelfMessage){
		JsonResult<SzToSelfMessage> result = new JsonResult<SzToSelfMessage>();
		try {
			int rg = szToSelfMessageServiceImpl.insertSelective(szToSelfMessage);
			if (rg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szToSelfMessage);
			} else {
				logger.error("添加给自己的信息执行失败："+szToSelfMessage.toString());
				result.setCode(-1);
				result.setMessage("执行失败，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("添加给自己的信息执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 删除给自己的信息对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/deleteSzToSelfMessageByPrimaryKey")
	@ApiOperation(value = "删除给自己的信息", notes = "删除给自己的信息,作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "id", value = "给自己的信息id", required = true, dataType = "String")
	public JsonResult<Object> deleteSzToSelfMessageByPrimaryKey(String id){
		JsonResult<Object> result = new JsonResult<Object>();
		try {
			int reg = szToSelfMessageServiceImpl.deleteByPrimaryKey(id);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(id);
			} else {
				logger.error("删除给自己的信息失败ID："+id);
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("删除给自己的信息执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 修改给自己的信息对象
	 * @param   对象参数：szToSelfMessage
	 * @return  szToSelfMessage
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "修改给自己的信息", notes = "修改给自己的信息[szToSelfMessage],作者：qiu_hf")
	@PostMapping("/updateSzToSelfMessageByPrimaryKey")
	public JsonResult<SzToSelfMessage> updateSzToSelfMessageByPrimaryKey(SzToSelfMessage szToSelfMessage){
		JsonResult<SzToSelfMessage> result = new JsonResult<SzToSelfMessage>();
		try {
			int reg = szToSelfMessageServiceImpl.updateByPrimaryKeySelective(szToSelfMessage);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szToSelfMessage);
			} else {
				logger.error("修改给自己的信息失败ID："+szToSelfMessage.toString());
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("修改给自己的信息执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 获取匹配给自己的信息
	 * @param   对象参数：szToSelfMessage
	 * @return  List<SzToSelfMessage>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "条件查询给自己的信息", notes = "条件查询[szToSelfMessage],作者：qiu_hf")
	@PostMapping("/querySzToSelfMessageList")
	public JsonResult<List<SzToSelfMessage>> querySzToSelfMessageList(SzToSelfMessage szToSelfMessage){
		JsonResult<List<SzToSelfMessage>> result = new JsonResult<List<SzToSelfMessage>>();
		try {
			List<SzToSelfMessage> list = szToSelfMessageServiceImpl.querySzToSelfMessageList(szToSelfMessage);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取给自己的信息执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 分页条件查询给自己的信息   
	 * @param   对象参数：AppPage<SzToSelfMessage>
	 * @return  PageInfo<SzToSelfMessage>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/getSzToSelfMessagePage")
	@ApiOperation(value = "分页查询", notes = "分页查询返回对象[PageInfo<SzToSelfMessage>],作者：qiu_hf")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "页行数", required = true, dataType = "int")
    })
	public JsonResult<PageInfo<SzToSelfMessage>> getSzToSelfMessagePage(Integer pageNum, Integer pageSize, SzToSelfMessage szToSelfMessage){
		JsonResult<PageInfo<SzToSelfMessage>> result = new JsonResult<PageInfo<SzToSelfMessage>>();
		AppPage<SzToSelfMessage> page = new AppPage<SzToSelfMessage>();
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		// 设置前端传过来的其他参数
		page.setParam(szToSelfMessage);
		//分页数据
		try {
			PageInfo<SzToSelfMessage> pageInfo = szToSelfMessageServiceImpl.getSzToSelfMessageBySearch(page);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(pageInfo);
		} catch (Exception e) {
			logger.error("分页查询给自己的信息执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
}