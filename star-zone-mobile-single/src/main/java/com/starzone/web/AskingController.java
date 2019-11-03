/**
 * @filename:AskingController 2019年4月13日
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
import com.starzone.pojo.Asking;
import com.starzone.service.master.AskingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**   
 * @Description:  问卷调查接口层
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@Api(description = "问卷调查",value="问卷调查" )
@RestController
@RequestMapping("/api/asking")
//@CrossOrigin(origins = {"http://127.0.0.1:8000", "null"})
public class AskingController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public AskingService askingServiceImpl;
	
	/**
	 * @explain 查询问卷调查对象  <swagger GET请求>
	 * @param   对象参数：id
	 * @return  asking
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@GetMapping("/getAskingById/{id}")
	@ApiOperation(value = "获取问卷调查信息", notes = "获取问卷调查信息[asking]，作者：qiu_hf")
	@ApiImplicitParam(paramType="path", name = "id", value = "问卷调查id", required = true, dataType = "String")
	public JsonResult<Asking> getAskingById(@PathVariable("id")String id){
		JsonResult<Asking> result=new JsonResult<Asking>();
		try {
			Asking asking = askingServiceImpl.selectByPrimaryKey(id);
			if (asking!=null) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(asking);
			} else {
				logger.error("获取问卷调查失败ID："+id);
				result.setCode(-1);
				result.setMessage("你获取的问卷调查不存在");
			}
		} catch (Exception e) {
			logger.error("获取问卷调查执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 添加问卷调查对象
	 * @param   对象参数：asking
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/insertAsking")
	@ApiOperation(value = "添加问卷调查", notes = "添加问卷调查[asking],作者：qiu_hf")
	public JsonResult<Asking> insertAsking(Asking asking){
		JsonResult<Asking> result = new JsonResult<Asking>();
		try {
			int rg = askingServiceImpl.insertSelective(asking);
			if (rg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(asking);
			} else {
				logger.error("添加问卷调查执行失败："+asking.toString());
				result.setCode(-1);
				result.setMessage("执行失败，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("添加问卷调查执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 删除问卷调查对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/deleteAskingByPrimaryKey")
	@ApiOperation(value = "删除问卷调查", notes = "删除问卷调查,作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "id", value = "问卷调查id", required = true, dataType = "String")
	public JsonResult<Object> deleteAskingByPrimaryKey(String id){
		JsonResult<Object> result = new JsonResult<Object>();
		try {
			int reg = askingServiceImpl.deleteByPrimaryKey(id);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(id);
			} else {
				logger.error("删除问卷调查失败ID："+id);
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("删除问卷调查执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 修改问卷调查对象
	 * @param   对象参数：asking
	 * @return  asking
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "修改问卷调查", notes = "修改问卷调查[asking],作者：qiu_hf")
	@PostMapping("/updateAskingByPrimaryKey")
	public JsonResult<Asking> updateAskingByPrimaryKey(Asking asking){
		JsonResult<Asking> result = new JsonResult<Asking>();
		try {
			int reg = askingServiceImpl.updateByPrimaryKeySelective(asking);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(asking);
			} else {
				logger.error("修改问卷调查失败ID："+asking.toString());
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("修改问卷调查执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 获取匹配问卷调查
	 * @param   对象参数：asking
	 * @return  List<Asking>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "条件查询问卷调查", notes = "条件查询[asking],作者：qiu_hf")
	@PostMapping("/queryAskingList")
	public JsonResult<List<Asking>> queryAskingList(Asking asking){
		JsonResult<List<Asking>> result = new JsonResult<List<Asking>>();
		try {
			List<Asking> list = askingServiceImpl.queryAskingList(asking);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取问卷调查执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 分页条件查询问卷调查   
	 * @param   对象参数：AppPage<Asking>
	 * @return  PageInfo<Asking>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/getAskingPage")
	@ApiOperation(value = "分页查询", notes = "分页查询返回对象[PageInfo<Asking>],作者：qiu_hf")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "页行数", required = true, dataType = "int")
    })
	public JsonResult<PageInfo<Asking>> getAskingPage(Integer pageNum, Integer pageSize, Asking asking){
		JsonResult<PageInfo<Asking>> result = new JsonResult<PageInfo<Asking>>();
		AppPage<Asking> page = new AppPage<Asking>();
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		// 设置前端传过来的其他参数
		page.setParam(asking);
		//分页数据
		try {
			PageInfo<Asking> pageInfo = askingServiceImpl.getAskingBySearch(page);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(pageInfo);
		} catch (Exception e) {
			logger.error("分页查询问卷调查执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
}