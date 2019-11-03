/**
 * @filename:SzSpendTypeController 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.utils.JsonResult;
import com.starzone.utils.UuidUtil;
import com.starzone.pojo.SzSpendDetails;
import com.starzone.pojo.SzSpendType;
import com.starzone.service.master.SzSpendDetailsService;
import com.starzone.service.master.SzSpendTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**   
 * @Description:  花费类型接口层
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@Api(description = "花费类型",value="花费类型" )
@RestController
@RequestMapping("/api/szSpendType")
//@CrossOrigin(origins = {"http://127.0.0.1:8000", "null"})
public class SzSpendTypeController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public SzSpendTypeService szSpendTypeServiceImpl;
	
	@Autowired
	public SzSpendDetailsService szSpendDetailsServiceImpl;
	
	/**
	 * @explain 查询花费类型对象  <swagger GET请求>
	 * @param   对象参数：id
	 * @return  szSpendType
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@GetMapping("/getSzSpendTypeById")
	@ApiOperation(value = "获取花费类型信息", notes = "获取花费类型信息[szSpendType]，作者：qiu_hf")
	@ApiImplicitParam(paramType="path", name = "id", value = "花费类型id", required = true, dataType = "String")
	public JsonResult<SzSpendType> getSzSpendTypeById(String id){
		JsonResult<SzSpendType> result=new JsonResult<SzSpendType>();
		try {
			SzSpendType szSpendType = szSpendTypeServiceImpl.selectByPrimaryKey(id);
			if (szSpendType!=null) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szSpendType);
			} else {
				logger.error("获取花费类型失败ID："+id);
				result.setCode(-1);
				result.setMessage("你获取的花费类型不存在");
			}
		} catch (Exception e) {
			logger.error("获取花费类型执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 添加花费类型对象
	 * @param   对象参数：szSpendType
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/insertSzSpendType")
	@ApiOperation(value = "添加花费类型", notes = "添加花费类型[szSpendType],作者：qiu_hf")
	public JsonResult<SzSpendType> insertSzSpendType(@RequestBody SzSpendType szSpendType){
		JsonResult<SzSpendType> result = new JsonResult<SzSpendType>();
		try {
			SzSpendType saveQuery = new SzSpendType();
			saveQuery.setOwner(szSpendType.getOwner());
			saveQuery.setDetailShowWay(szSpendType.getDetailShowWay());
			List<SzSpendType> list = szSpendTypeServiceImpl.querySzSpendTypeList(saveQuery);
			if (list.size() > 0) {
				result.setCode(1);
				result.setMessage("-2");
				result.setData(saveQuery);
				return result;
			}
			szSpendType.setId(UuidUtil.get32UUID());
			szSpendType.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(new Date()));
			int rg = szSpendTypeServiceImpl.insertSelective(szSpendType);
			if (rg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szSpendType);
			} else {
				logger.error("添加花费类型执行失败："+szSpendType.toString());
				result.setCode(-1);
				result.setMessage("执行失败，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("添加花费类型执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 删除花费类型对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@SuppressWarnings("unused")
	@DeleteMapping("/deleteSzSpendTypeByPrimaryKey")
	@ApiOperation(value = "删除花费类型", notes = "删除花费类型,作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "id", value = "花费类型id", required = true, dataType = "String")
	@Transactional
	public JsonResult<Object> deleteSzSpendTypeByPrimaryKey(String id){
		JsonResult<Object> result = new JsonResult<Object>();
		int regDetails = szSpendDetailsServiceImpl.deleteByType(id); // 根据type删除消费详情
		int reg = szSpendTypeServiceImpl.deleteByPrimaryKey(id);
		
		if (reg > 0) {
			result.setCode(1);
			result.setMessage("成功");
			result.setData(id);
		} else {
			logger.error("删除花费类型失败ID："+id);
			result.setCode(-1);
			result.setMessage("执行错误，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 修改花费类型对象
	 * @param   对象参数：szSpendType
	 * @return  szSpendType
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "修改花费类型", notes = "修改花费类型[szSpendType],作者：qiu_hf")
	@PutMapping("/updateSzSpendTypeByPrimaryKey")
	public JsonResult<SzSpendType> updateSzSpendTypeByPrimaryKey(@RequestBody SzSpendType szSpendType){
		JsonResult<SzSpendType> result = new JsonResult<SzSpendType>();
		try {
			if (!"true".equals(szSpendType.getExt1())) { // 有改过消费类型
				SzSpendType updateQuery = new SzSpendType();
				updateQuery.setOwner(szSpendType.getOwner());
				updateQuery.setDetailShowWay(szSpendType.getDetailShowWay());
				List<SzSpendType> list = szSpendTypeServiceImpl.querySzSpendTypeList(updateQuery);
				if (list.size() > 0) { // 判断修改之后的消费类型是否已存在
					result.setCode(1);
					result.setMessage("-2");
					result.setData(updateQuery);
					return result;
				}
			}
			int reg = szSpendTypeServiceImpl.updateByPrimaryKeySelective(szSpendType);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szSpendType);
			} else {
				logger.error("修改花费类型失败ID：" + szSpendType.toString());
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("修改花费类型执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 获取匹配花费类型
	 * @param   对象参数：szSpendType
	 * @return  List<SzSpendType>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "条件查询花费类型", notes = "条件查询[szSpendType],作者：qiu_hf")
	@GetMapping("/querySzSpendTypeList")
	public JsonResult<List<SzSpendType>> querySzSpendTypeList(SzSpendType szSpendType){
		JsonResult<List<SzSpendType>> result = new JsonResult<List<SzSpendType>>();
		try {
			List<SzSpendType> list = szSpendTypeServiceImpl.querySzSpendTypeList(szSpendType);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取花费类型执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 分页条件查询花费类型   
	 * @param   对象参数：AppPage<SzSpendType>
	 * @return  PageInfo<SzSpendType>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@GetMapping("/getSzSpendTypePage")
	@ApiOperation(value = "分页查询", notes = "分页查询返回对象[PageInfo<SzSpendType>],作者：qiu_hf")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "页行数", required = true, dataType = "int")
    })
	public JsonResult<PageInfo<SzSpendType>> getSzSpendTypePage(Integer pageNum, Integer pageSize, SzSpendType szSpendType){
		JsonResult<PageInfo<SzSpendType>> result = new JsonResult<PageInfo<SzSpendType>>();
		AppPage<SzSpendType> page = new AppPage<SzSpendType>();
		page.setPageNum(pageNum); // 次框架分页查询从1开始
		page.setPageSize(pageSize);
		// 设置前端传过来的其他参数
		page.setParam(szSpendType);
		//分页数据
		try {
			PageInfo<SzSpendType> pageInfo = szSpendTypeServiceImpl.getSzSpendTypeBySearch(page);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(pageInfo);
		} catch (Exception e) {
			logger.error("分页查询花费类型执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 查询我的花费图表数据
	 * @param   对象参数：szSpendType
	 * @return  List<SzSpendType>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "查询我的花费图表数据", notes = "条件查询[szSpendType],作者：qiu_hf")
	@GetMapping("/findPieAndBarDatas")
	public JsonResult<Map<String, Object>> findPieAndBarDatas(SzSpendType szSpendType){
		JsonResult<Map<String, Object>> result = new JsonResult<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<SzSpendDetails> listGroupByYear = szSpendTypeServiceImpl.findPieAndBarDatasGroupByYear(szSpendType);
			List<SzSpendDetails> listGroupByType = szSpendTypeServiceImpl.findPieAndBarDatasGroupByType(szSpendType);
			map.put("dataGroupByYear", listGroupByYear); // pie、Bar按年分组查询数据
			map.put("dataGroupByType", listGroupByType); // pie、Bar按消费类型分组查的数据，可以按年过滤
			result.setCode(1);
			result.setMessage("成功");
			result.setData(map);
		} catch (Exception e) {
			logger.error("获取花费类型执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
}