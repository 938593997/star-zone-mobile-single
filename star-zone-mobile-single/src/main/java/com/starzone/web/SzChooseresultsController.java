/**
 * @filename:SzChooseresultsController 2019年4月13日
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
import com.starzone.pojo.SzChooseresults;
import com.starzone.service.master.SzChooseresultsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**   
 * @Description:  选择结果接口层
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@Api(description = "选择结果",value="选择结果" )
@RestController
@RequestMapping("/api/szChooseresults")
//@CrossOrigin(origins = {"http://127.0.0.1:8000", "null"})
public class SzChooseresultsController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public SzChooseresultsService szChooseresultsServiceImpl;
	
	/**
	 * @explain 查询选择结果对象  <swagger GET请求>
	 * @param   对象参数：id
	 * @return  szChooseresults
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@GetMapping("/getSzChooseresultsById/{id}")
	@ApiOperation(value = "获取选择结果信息", notes = "获取选择结果信息[szChooseresults]，作者：qiu_hf")
	@ApiImplicitParam(paramType="path", name = "id", value = "选择结果id", required = true, dataType = "String")
	public JsonResult<SzChooseresults> getSzChooseresultsById(@PathVariable("id")String id){
		JsonResult<SzChooseresults> result=new JsonResult<SzChooseresults>();
		try {
			SzChooseresults szChooseresults = szChooseresultsServiceImpl.selectByPrimaryKey(id);
			if (szChooseresults!=null) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szChooseresults);
			} else {
				logger.error("获取选择结果失败ID："+id);
				result.setCode(-1);
				result.setMessage("你获取的选择结果不存在");
			}
		} catch (Exception e) {
			logger.error("获取选择结果执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 添加选择结果对象
	 * @param   对象参数：szChooseresults
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/insertSzChooseresults")
	@ApiOperation(value = "添加选择结果", notes = "添加选择结果[szChooseresults],作者：qiu_hf")
	public JsonResult<SzChooseresults> insertSzChooseresults(SzChooseresults szChooseresults){
		JsonResult<SzChooseresults> result = new JsonResult<SzChooseresults>();
		try {
			int rg = szChooseresultsServiceImpl.insertSelective(szChooseresults);
			if (rg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szChooseresults);
			} else {
				logger.error("添加选择结果执行失败："+szChooseresults.toString());
				result.setCode(-1);
				result.setMessage("执行失败，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("添加选择结果执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 删除选择结果对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/deleteSzChooseresultsByPrimaryKey")
	@ApiOperation(value = "删除选择结果", notes = "删除选择结果,作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "id", value = "选择结果id", required = true, dataType = "String")
	public JsonResult<Object> deleteSzChooseresultsByPrimaryKey(String id){
		JsonResult<Object> result = new JsonResult<Object>();
		try {
			int reg = szChooseresultsServiceImpl.deleteByPrimaryKey(id);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(id);
			} else {
				logger.error("删除选择结果失败ID："+id);
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("删除选择结果执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 修改选择结果对象
	 * @param   对象参数：szChooseresults
	 * @return  szChooseresults
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "修改选择结果", notes = "修改选择结果[szChooseresults],作者：qiu_hf")
	@PostMapping("/updateSzChooseresultsByPrimaryKey")
	public JsonResult<SzChooseresults> updateSzChooseresultsByPrimaryKey(SzChooseresults szChooseresults){
		JsonResult<SzChooseresults> result = new JsonResult<SzChooseresults>();
		try {
			int reg = szChooseresultsServiceImpl.updateByPrimaryKeySelective(szChooseresults);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szChooseresults);
			} else {
				logger.error("修改选择结果失败ID："+szChooseresults.toString());
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("修改选择结果执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 获取匹配选择结果
	 * @param   对象参数：szChooseresults
	 * @return  List<SzChooseresults>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "条件查询选择结果", notes = "条件查询[szChooseresults],作者：qiu_hf")
	@GetMapping("/querySzChooseresultsList")
	public JsonResult<List<SzChooseresults>> querySzChooseresultsList(SzChooseresults szChooseresults){
		JsonResult<List<SzChooseresults>> result = new JsonResult<List<SzChooseresults>>();
		try {
			List<SzChooseresults> list = szChooseresultsServiceImpl.querySzChooseresultsList(szChooseresults);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取选择结果执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 分页条件查询选择结果   
	 * @param   对象参数：AppPage<SzChooseresults>
	 * @return  PageInfo<SzChooseresults>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/getSzChooseresultsPage")
	@ApiOperation(value = "分页查询", notes = "分页查询返回对象[PageInfo<SzChooseresults>],作者：qiu_hf")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "页行数", required = true, dataType = "int")
    })
	public JsonResult<PageInfo<SzChooseresults>> getSzChooseresultsPage(Integer pageNum, Integer pageSize, SzChooseresults szChooseresults){
		JsonResult<PageInfo<SzChooseresults>> result = new JsonResult<PageInfo<SzChooseresults>>();
		AppPage<SzChooseresults> page = new AppPage<SzChooseresults>();
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		// 设置前端传过来的其他参数
		page.setParam(szChooseresults);
		//分页数据
		try {
			PageInfo<SzChooseresults> pageInfo = szChooseresultsServiceImpl.getSzChooseresultsBySearch(page);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(pageInfo);
		} catch (Exception e) {
			logger.error("分页查询选择结果执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
}