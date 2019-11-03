/**
 * @filename:SzSpendDetailsController 2019年5月17日
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @Description:  消费详情接口层
 * @Author:       qiu_hf   
 * @CreateDate:   2019年5月17日
 * @Version:      V1.0
 */
@Api(description = "消费详情", value="消费详情" )
@RestController
@RequestMapping("/api/szSpendDetails")
public class SzSpendDetailsController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public SzSpendDetailsService szSpendDetailsServiceImpl;
	
	@Autowired
	public SzSpendTypeService szSpendTypeServiceImpl;
	
	/**
	 * @explain 查询消费详情对象  <swagger GET请求>
	 * @param   对象参数：id
	 * @return  szSpendDetails
	 * @author  qiu_hf
	 * @time    2019年5月17日
	 */
	@GetMapping("/getSzSpendDetailsById")
	@ApiOperation(value = "获取消费详情信息", notes = "获取消费详情信息[szSpendDetails]， 作者：qiu_hf")
	@ApiImplicitParam(paramType="path", name = "id", value = "消费详情id", required = true, dataType = "String")
	public JsonResult<SzSpendDetails> getSzSpendDetailsById(String id){
		JsonResult<SzSpendDetails> result=new JsonResult<SzSpendDetails>();
		try {
			SzSpendDetails szSpendDetails = szSpendDetailsServiceImpl.selectByPrimaryKey(id);
			if (szSpendDetails!=null) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szSpendDetails);
			} else {
				logger.error("获取消费详情失败ID："+id);
				result.setCode(-1);
				result.setMessage("你获取的消费详情不存在");
			}
		} catch (Exception e) {
			logger.error("获取消费详情执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 添加消费详情对象
	 * @param   对象参数：szSpendDetails
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年5月17日
	 */
	@PostMapping("/insertSzSpendDetails")
	@ApiOperation(value = "添加消费详情", notes = "添加消费详情[szSpendDetails], 作者：qiu_hf")
	public JsonResult<SzSpendDetails> insertSzSpendDetails(@RequestBody SzSpendDetails szSpendDetails){
		JsonResult<SzSpendDetails> result = new JsonResult<SzSpendDetails>();
		try {
			szSpendDetails.setId(UuidUtil.get32UUID());
			szSpendDetails.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(new Date()));
			int rg = szSpendDetailsServiceImpl.insertSelective(szSpendDetails);
			if (rg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szSpendDetails);
			} else {
				logger.error("添加消费详情执行失败："+szSpendDetails.toString());
				result.setCode(-1);
				result.setMessage("执行失败，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("添加消费详情执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 删除消费详情对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年5月17日
	 */
	@DeleteMapping("/deleteSzSpendDetailsByPrimaryKey")
	@ApiOperation(value = "删除消费详情", notes = "删除消费详情, 作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "id", value = "消费详情id", required = true, dataType = "String")
	public JsonResult<Object> deleteSzSpendDetailsByPrimaryKey(String id){
		JsonResult<Object> result = new JsonResult<Object>();
		try {
			int reg = szSpendDetailsServiceImpl.deleteByPrimaryKey(id);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(id);
			} else {
				logger.error("删除消费详情失败ID："+id);
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("删除消费详情执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 修改消费详情对象
	 * @param   对象参数：szSpendDetails
	 * @return  szSpendDetails
	 * @author  qiu_hf
	 * @time    2019年5月17日
	 */
	@ApiOperation(value = "修改消费详情", notes = "修改消费详情[szSpendDetails], 作者：qiu_hf")
	@PutMapping("/updateSzSpendDetailsByPrimaryKey")
	public JsonResult<SzSpendDetails> updateSzSpendDetailsByPrimaryKey(@RequestBody SzSpendDetails szSpendDetails){
		JsonResult<SzSpendDetails> result = new JsonResult<SzSpendDetails>();
		try {
			int reg = szSpendDetailsServiceImpl.updateByPrimaryKeySelective(szSpendDetails);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szSpendDetails);
			} else {
				logger.error("修改消费详情失败ID："+szSpendDetails.toString());
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("修改消费详情执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 获取匹配消费详情
	 * @param   对象参数：szSpendDetails
	 * @return  List<SzSpendDetails>
	 * @author  qiu_hf
	 * @time    2019年5月17日
	 */
	@ApiOperation(value = "条件查询消费详情", notes = "条件查询[szSpendDetails], 作者：qiu_hf")
	@GetMapping("/querySzSpendDetailsList")
	public JsonResult<List<SzSpendDetails>> querySzSpendDetailsList(SzSpendDetails szSpendDetails){
		JsonResult<List<SzSpendDetails>> result = new JsonResult<List<SzSpendDetails>>();
		try {
			List<SzSpendDetails> list = szSpendDetailsServiceImpl.querySzSpendDetailsList(szSpendDetails);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取消费详情执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 分页条件查询消费详情   
	 * @param   对象参数：AppPage<SzSpendDetails>
	 * @return  PageInfo<SzSpendDetails>
	 * @author  qiu_hf
	 * @time    2019年5月17日
	 */
	@GetMapping("/getSzSpendDetailsPage")
	@ApiOperation(value = "分页查询", notes = "分页查询返回对象[PageInfo<SzSpendDetails>], 作者：qiu_hf")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "页行数", required = true, dataType = "int")
    })
	public JsonResult<PageInfo<SzSpendDetails>> getSzSpendDetailsPage(Integer pageNum, Integer pageSize, SzSpendDetails szSpendDetails){
		JsonResult<PageInfo<SzSpendDetails>> result = new JsonResult<PageInfo<SzSpendDetails>>();
		AppPage<SzSpendDetails> page = new AppPage<SzSpendDetails>();
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		// 设置前端传过来的其他参数
		page.setParam(szSpendDetails);
		//分页数据
		try {
			PageInfo<SzSpendDetails> pageInfo = szSpendDetailsServiceImpl.getSzSpendDetailsBySearch(page);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(pageInfo);
		} catch (Exception e) {
			logger.error("分页查询消费详情执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 查询我的花费具体分类图表数据
	 * @param   对象参数：SzSpendDetails
	 * @return  List<SzSpendDetails>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "查询我的花费具体分类图表数据", notes = "条件查询[szSpendType],作者：qiu_hf")
	@GetMapping("/findPieAndBarDatas")
	public JsonResult<Map<String, Object>> findPieAndBarDatas(SzSpendDetails szSpendDetails){
		JsonResult<Map<String, Object>> result = new JsonResult<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SzSpendType szSpendType = new SzSpendType();
			szSpendType.setOwner(szSpendDetails.getOwner());
			szSpendType.setDetailShowWay(szSpendDetails.getDetailShowWay());
			List<SzSpendDetails> listGroupByYear = szSpendTypeServiceImpl.findPieAndBarDatasGroupByYear(szSpendType);
			map.put("dataGroupByYear", listGroupByYear); // pie、Bar加具体的detail_show_way按年分组查询数据
			result.setCode(1);
			result.setMessage("成功");
			result.setData(map);
		} catch (Exception e) {
			logger.error("查询我的花费具体分类图表数据：" + e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
}