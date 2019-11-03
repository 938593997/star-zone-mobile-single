/**
 * @filename:SzMenuController 2019年4月13日
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
import com.starzone.pojo.SzMenu;
import com.starzone.service.master.SzMenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**   
 * 
 * @Description:  菜单接口层
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
@Api(description = "菜单",value="菜单" )
@RestController
@RequestMapping("/api/szMenu")
public class SzMenuController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public SzMenuService szMenuServiceImpl;
	
	/**
	 * @explain 查询菜单对象  <swagger GET请求>
	 * @param   对象参数：id
	 * @return  szMenu
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@GetMapping("/getSzMenuById/{id}")
	@ApiOperation(value = "获取菜单信息", notes = "获取菜单信息[szMenu]，作者：qiu_hf")
	@ApiImplicitParam(paramType="path", name = "id", value = "菜单id", required = true, dataType = "String")
	public JsonResult<SzMenu> getSzMenuById(@PathVariable("id")String id){
		JsonResult<SzMenu> result=new JsonResult<SzMenu>();
		try {
			SzMenu szMenu = szMenuServiceImpl.selectByPrimaryKey(id);
			if (szMenu!=null) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szMenu);
			} else {
				logger.error("获取菜单失败ID："+id);
				result.setCode(-1);
				result.setMessage("你获取的菜单不存在");
			}
		} catch (Exception e) {
			logger.error("获取菜单执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 添加菜单对象
	 * @param   对象参数：szMenu
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/insertSzMenu")
	@ApiOperation(value = "添加菜单", notes = "添加菜单[szMenu],作者：qiu_hf")
	public JsonResult<SzMenu> insertSzMenu(SzMenu szMenu){
		JsonResult<SzMenu> result = new JsonResult<SzMenu>();
		try {
			int rg = szMenuServiceImpl.insertSelective(szMenu);
			if (rg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szMenu);
			} else {
				logger.error("添加菜单执行失败："+szMenu.toString());
				result.setCode(-1);
				result.setMessage("执行失败，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("添加菜单执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 删除菜单对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/deleteSzMenuByPrimaryKey")
	@ApiOperation(value = "删除菜单", notes = "删除菜单,作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "id", value = "菜单id", required = true, dataType = "String")
	public JsonResult<Object> deleteSzMenuByPrimaryKey(String id){
		JsonResult<Object> result = new JsonResult<Object>();
		try {
			int reg = szMenuServiceImpl.deleteByPrimaryKey(id);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(id);
			} else {
				logger.error("删除菜单失败ID："+id);
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("删除菜单执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 修改菜单对象
	 * @param   对象参数：szMenu
	 * @return  szMenu
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "修改菜单", notes = "修改菜单[szMenu],作者：qiu_hf")
	@PostMapping("/updateSzMenuByPrimaryKey")
	public JsonResult<SzMenu> updateSzMenuByPrimaryKey(SzMenu szMenu){
		JsonResult<SzMenu> result = new JsonResult<SzMenu>();
		try {
			int reg = szMenuServiceImpl.updateByPrimaryKeySelective(szMenu);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szMenu);
			} else {
				logger.error("修改菜单失败ID："+szMenu.toString());
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("修改菜单执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 获取匹配菜单
	 * @param   对象参数：szMenu
	 * @return  List<SzMenu>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "条件查询菜单", notes = "条件查询[szMenu],作者：qiu_hf")
	@GetMapping("/querySzMenuList")
	public JsonResult<List<SzMenu>> querySzMenuList(SzMenu szMenu){
		JsonResult<List<SzMenu>> result = new JsonResult<List<SzMenu>>();
		try {
			List<SzMenu> list = szMenuServiceImpl.querySzMenuList(szMenu);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取菜单执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 分页条件查询菜单   
	 * @param   对象参数：AppPage<SzMenu>
	 * @return  PageInfo<SzMenu>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/getSzMenuPage")
	@ApiOperation(value = "分页查询", notes = "分页查询返回对象[PageInfo<SzMenu>],作者：qiu_hf")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "页行数", required = true, dataType = "int")
    })
	public JsonResult<PageInfo<SzMenu>> getSzMenuPage(Integer pageNum, Integer pageSize, SzMenu szMenu){
		JsonResult<PageInfo<SzMenu>> result = new JsonResult<PageInfo<SzMenu>>();
		AppPage<SzMenu> page = new AppPage<SzMenu>();
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		// 设置前端传过来的其他参数
		page.setParam(szMenu);
		//分页数据
		try {
			PageInfo<SzMenu> pageInfo = szMenuServiceImpl.getSzMenuBySearch(page);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(pageInfo);
		} catch (Exception e) {
			logger.error("分页查询菜单执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
}