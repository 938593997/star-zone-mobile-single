/**
 * @filename:SzChooseforyouController 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.utils.JsonResult;
import com.starzone.utils.UuidUtil;
import com.starzone.pojo.SzChooseforyou;
import com.starzone.pojo.SzUser;
import com.starzone.service.master.SzChooseforyouService;
import com.starzone.service.master.SzChooseforyousetService;
import com.starzone.service.master.SzChooseresultsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**   
 * @Description:  我帮你选接口层
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@Api(description = "我帮你选",value="我帮你选" )
@RestController
@RequestMapping("/api/szChooseforyou")
//@CrossOrigin(origins = {"http://127.0.0.1:8000", "null"})
public class SzChooseforyouController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public SzChooseforyouService szChooseforyouServiceImpl;
	
	@Autowired
	public SzChooseforyousetService szChooseforyousetServiceImpl;
	
	@Autowired
	public SzChooseresultsService szChooseresultsServiceImpl;
	
	/**
	 * @explain 查询我帮你选对象  <swagger GET请求>
	 * @param   对象参数：id
	 * @return  szChooseforyou
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@GetMapping("/getSzChooseforyouById/{id}")
	@ApiOperation(value = "获取我帮你选信息", notes = "获取我帮你选信息[szChooseforyou]，作者：qiu_hf")
	@ApiImplicitParam(paramType="path", name = "id", value = "我帮你选id", required = true, dataType = "String")
	public JsonResult<SzChooseforyou> getSzChooseforyouById(@PathVariable("id")String id){
		JsonResult<SzChooseforyou> result=new JsonResult<SzChooseforyou>();
		try {
			SzChooseforyou szChooseforyou = szChooseforyouServiceImpl.selectByPrimaryKey(id);
			if (szChooseforyou!=null) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szChooseforyou);
			} else {
				logger.error("获取我帮你选失败ID："+id);
				result.setCode(-1);
				result.setMessage("你获取的我帮你选不存在");
			}
		} catch (Exception e) {
			logger.error("获取我帮你选执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 添加我帮你选对象
	 * @param   对象参数：szChooseforyou
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/insertSzChooseforyou")
	@ApiOperation(value = "添加我帮你选", notes = "添加我帮你选[szChooseforyou],作者：qiu_hf")
	public JsonResult<SzChooseforyou> insertSzChooseforyou(@RequestBody SzChooseforyou szChooseforyou){
		JsonResult<SzChooseforyou> result = new JsonResult<SzChooseforyou>();
		try {
			szChooseforyou.setId(UuidUtil.get32UUID());
			szChooseforyou.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(new Date()));
			int rg = szChooseforyouServiceImpl.insertSelective(szChooseforyou);
			if (rg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szChooseforyou);
			} else {
				logger.error("添加我帮你选执行失败："+szChooseforyou.toString());
				result.setCode(-1);
				result.setMessage("执行失败，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("添加我帮你选执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 删除我帮你选对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@DeleteMapping("/deleteSzChooseforyouByPrimaryKey")
	@ApiOperation(value = "删除我帮你选", notes = "删除我帮你选,作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "id", value = "我帮你选id", required = true, dataType = "String")
	@Transactional
	public JsonResult<Object> deleteSzChooseforyouByPrimaryKey(String id){
		JsonResult<Object> result = new JsonResult<Object>();
		
		int reg = szChooseforyousetServiceImpl.deleteByChooseId(id); // 删除之前选择的选项
		reg = szChooseresultsServiceImpl.deleteByChooseId(id); // 删除之前选择的选择结果
		reg = szChooseforyouServiceImpl.deleteByPrimaryKey(id); // 删除选择
		if (reg > 0) {
			result.setCode(1);
			result.setMessage("成功");
			result.setData(id);
		} else {
			logger.error("删除我帮你选失败ID："+id);
			result.setCode(-1);
			result.setMessage("执行错误，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 修改我帮你选对象
	 * @param   对象参数：szChooseforyou
	 * @return  szChooseforyou
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "修改我帮你选", notes = "修改我帮你选[szChooseforyou],作者：qiu_hf")
	@PutMapping("/updateSzChooseforyouByPrimaryKey")
	@Transactional
	public JsonResult<SzChooseforyou> updateSzChooseforyouByPrimaryKey(@RequestBody SzChooseforyou szChooseforyou){
		
		JsonResult<SzChooseforyou> result = new JsonResult<SzChooseforyou>();
		int reg = szChooseforyouServiceImpl.updateByPrimaryKeySelective(szChooseforyou); // 修改选择信息
		reg = szChooseforyousetServiceImpl.deleteByChooseId(szChooseforyou.getId()); // 删除之前选择的选项
		reg = szChooseresultsServiceImpl.deleteByChooseId(szChooseforyou.getId()); // 删除之前选择的选择结果
		if (reg >= 0) {
			result.setCode(1);
			result.setMessage("成功");
			result.setData(szChooseforyou);
		} else {
			logger.error("修改我帮你选失败ID："+szChooseforyou.toString());
			result.setCode(-1);
			result.setMessage("执行错误，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 获取匹配我帮你选
	 * @param   对象参数：szChooseforyou
	 * @return  List<SzChooseforyou>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "条件查询我帮你选", notes = "条件查询[szChooseforyou],作者：qiu_hf")
	@GetMapping("/querySzChooseforyouList")
	public JsonResult<List<SzChooseforyou>> querySzChooseforyouList(SzChooseforyou szChooseforyou, HttpServletRequest request){
		JsonResult<List<SzChooseforyou>> result = new JsonResult<List<SzChooseforyou>>();
		try {
			HttpSession session = request.getSession();
			SzUser user = (SzUser) session.getAttribute("LoginUser");
			szChooseforyou.setUserid(user.getId());
			List<SzChooseforyou> list = szChooseforyouServiceImpl.querySzChooseforyouList(szChooseforyou);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取我帮你选执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 分页条件查询我帮你选   
	 * @param   对象参数：AppPage<SzChooseforyou>
	 * @return  PageInfo<SzChooseforyou>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@GetMapping("/getSzChooseforyouPage")
	@ApiOperation(value = "分页查询", notes = "分页查询返回对象[PageInfo<SzChooseforyou>],作者：qiu_hf")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "页行数", required = true, dataType = "int")
    })
	public JsonResult<PageInfo<SzChooseforyou>> getSzChooseforyouPage(Integer pageNum, Integer pageSize, SzChooseforyou szChooseforyou){
		JsonResult<PageInfo<SzChooseforyou>> result = new JsonResult<PageInfo<SzChooseforyou>>();
		AppPage<SzChooseforyou> page = new AppPage<SzChooseforyou>();
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		// 设置前端传过来的其他参数
		page.setParam(szChooseforyou);
		//分页数据
		try {
			PageInfo<SzChooseforyou> pageInfo = szChooseforyouServiceImpl.getSzChooseforyouBySearch(page);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(pageInfo);
		} catch (Exception e) {
			logger.error("分页查询我帮你选执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * Example的方式查询
	 * @explain 获取匹配我帮你选
	 * @param   对象参数：szChooseforyou
	 * @return  List<SzChooseforyou>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "Example的方式查询，条件查询我帮你选", notes = "条件查询[szChooseforyou],作者：qiu_hf")
	@GetMapping("/querySzChooseforyouListExample")
	public JsonResult<List<SzChooseforyou>> querySzChooseforyouListExample(SzChooseforyou szChooseforyou, HttpServletRequest request){
		JsonResult<List<SzChooseforyou>> result = new JsonResult<List<SzChooseforyou>>();
		try {
			HttpSession session = request.getSession();
			SzUser user = (SzUser) session.getAttribute("LoginUser");
			szChooseforyou.setUserid(user.getId());
			ExampleMatcher matcher = ExampleMatcher.matching()
					.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
					.withMatcher("userid", ExampleMatcher.GenericPropertyMatchers.exact());
			Example<SzChooseforyou> example = Example.of(szChooseforyou, matcher);
			List<SzChooseforyou> list = szChooseforyouServiceImpl.querySzChooseforyouListExample(example);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取我帮你选执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
}