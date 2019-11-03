/**
 * @filename:SzChooseforyousetController 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.utils.JsonResult;
import com.starzone.utils.UuidUtil;
import com.starzone.pojo.SzChooseforyouset;
import com.starzone.pojo.SzChooseresults;
import com.starzone.pojo.SzUser;
import com.starzone.service.master.SzChooseforyousetService;
import com.starzone.service.master.SzChooseresultsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**   
 * @Description:  我帮你选选项定义接口层
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@Api(description = "我帮你选选项定义",value="我帮你选选项定义" )
@RestController
@RequestMapping("/api/szChooseforyouset")
//@CrossOrigin(origins = {"http://127.0.0.1:8000", "null"})
public class SzChooseforyousetController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public SzChooseforyousetService szChooseforyousetServiceImpl;
	
	@Autowired
	public SzChooseresultsService szChooseresultsServiceImpl;
	
	/**
	 * @explain 查询我帮你选选项定义对象  <swagger GET请求>
	 * @param   对象参数：id
	 * @return  szChooseforyouset
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@GetMapping("/getSzChooseforyousetById/{id}")
	@ApiOperation(value = "获取我帮你选选项定义信息", notes = "获取我帮你选选项定义信息[szChooseforyouset]，作者：qiu_hf")
	@ApiImplicitParam(paramType="path", name = "id", value = "我帮你选选项定义id", required = true, dataType = "Integer")
	public JsonResult<SzChooseforyouset> getSzChooseforyousetById(@PathVariable("id")Integer id){
		JsonResult<SzChooseforyouset> result=new JsonResult<SzChooseforyouset>();
		try {
			SzChooseforyouset szChooseforyouset = szChooseforyousetServiceImpl.selectByPrimaryKey(id);
			if (szChooseforyouset!=null) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szChooseforyouset);
			} else {
				logger.error("获取我帮你选选项定义失败ID："+id);
				result.setCode(-1);
				result.setMessage("你获取的我帮你选选项定义不存在");
			}
		} catch (Exception e) {
			logger.error("获取我帮你选选项定义执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 添加我帮你选选项定义对象
	 * @param   对象参数：szChooseforyouset
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/insertSzChooseforyouset")
	@ApiOperation(value = "添加我帮你选选项定义", notes = "添加我帮你选选项定义[szChooseforyouset],作者：qiu_hf")
	public JsonResult<SzChooseforyouset> insertSzChooseforyouset(@RequestBody SzChooseforyouset szChooseforyouset){
		JsonResult<SzChooseforyouset> result = new JsonResult<SzChooseforyouset>();
		try {
			List<String> setList = szChooseforyouset.getSetList();
			List<Integer> rgList = new ArrayList<Integer>();
			int inserId = 0; // 更新批量插入时的主键ID
			StringBuffer sb = new StringBuffer();
			for (String str : setList) {
				sb.append(str + "-");
			}
			String datas = sb.toString();
			String[] datas_name = datas.substring(0, datas.length()-1).split("-");// 用来设置未填写的选项
			
			for (int i=0; i < 8-datas_name.length; i++) { // 补充选项
	        	int num = (int)(Math.random()*datas_name.length);
	        	setList.add(datas_name[num]);
	        }
			
			for (int i=0; i < setList.size(); i++) { // 插入数据
				szChooseforyouset.setPosition(String.valueOf(i));
				szChooseforyouset.setName(setList.get(i));
				if (i != 0) { // 第一次的插入是自动查数据库的，第二次之后的插入都要加1，因为会话还没提交（commit）
					szChooseforyouset.setId(inserId + i);
				}
				szChooseforyouset.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(new Date()));
				int rg = szChooseforyousetServiceImpl.insertSelective(szChooseforyouset);
				rgList.add(rg);
				if (i == 0) {
					inserId = szChooseforyouset.getId();
				}
			}
			if (rgList.size() == setList.size()) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szChooseforyouset);
			} else {
				logger.error("添加我帮你选选项定义执行失败："+szChooseforyouset.toString());
				result.setCode(-1);
				result.setMessage("执行失败，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("添加我帮你选选项定义执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 删除我帮你选选项定义对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/deleteSzChooseforyousetByPrimaryKey")
	@ApiOperation(value = "删除我帮你选选项定义", notes = "删除我帮你选选项定义,作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "id", value = "我帮你选选项定义id", required = true, dataType = "Integer")
	public JsonResult<Object> deleteSzChooseforyousetByPrimaryKey(Integer id){
		JsonResult<Object> result = new JsonResult<Object>();
		try {
			int reg = szChooseforyousetServiceImpl.deleteByPrimaryKey(id);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(id);
			} else {
				logger.error("删除我帮你选选项定义失败ID："+id);
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("删除我帮你选选项定义执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 修改我帮你选选项定义对象
	 * @param   对象参数：szChooseforyouset
	 * @return  szChooseforyouset
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "修改我帮你选选项定义", notes = "修改我帮你选选项定义[szChooseforyouset],作者：qiu_hf")
	@PostMapping("/updateSzChooseforyousetByPrimaryKey")
	public JsonResult<SzChooseforyouset> updateSzChooseforyousetByPrimaryKey(SzChooseforyouset szChooseforyouset){
		JsonResult<SzChooseforyouset> result = new JsonResult<SzChooseforyouset>();
		try {
			int reg = szChooseforyousetServiceImpl.updateByPrimaryKeySelective(szChooseforyouset);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szChooseforyouset);
			} else {
				logger.error("修改我帮你选选项定义失败ID："+szChooseforyouset.toString());
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("修改我帮你选选项定义执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 获取匹配我帮你选选项定义
	 * @param   对象参数：szChooseforyouset
	 * @return  List<SzChooseforyouset>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "条件查询我帮你选选项定义", notes = "条件查询[szChooseforyouset],作者：qiu_hf")
	@GetMapping("/querySzChooseforyousetList")
	public JsonResult<List<SzChooseforyouset>> querySzChooseforyousetList(SzChooseforyouset szChooseforyouset){
		JsonResult<List<SzChooseforyouset>> result = new JsonResult<List<SzChooseforyouset>>();
		try {
			List<SzChooseforyouset> list = szChooseforyousetServiceImpl.querySzChooseforyousetList(szChooseforyouset);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取我帮你选选项定义执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 分页条件查询我帮你选选项定义   
	 * @param   对象参数：AppPage<SzChooseforyouset>
	 * @return  PageInfo<SzChooseforyouset>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/getSzChooseforyousetPage")
	@ApiOperation(value = "分页查询", notes = "分页查询返回对象[PageInfo<SzChooseforyouset>],作者：qiu_hf")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "页行数", required = true, dataType = "int")
    })
	public JsonResult<PageInfo<SzChooseforyouset>> getSzChooseforyousetPage(Integer pageNum, Integer pageSize, SzChooseforyouset szChooseforyouset){
		JsonResult<PageInfo<SzChooseforyouset>> result = new JsonResult<PageInfo<SzChooseforyouset>>();
		AppPage<SzChooseforyouset> page = new AppPage<SzChooseforyouset>();
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		// 设置前端传过来的其他参数
		page.setParam(szChooseforyouset);
		//分页数据
		try {
			PageInfo<SzChooseforyouset> pageInfo = szChooseforyousetServiceImpl.getSzChooseforyousetBySearch(page);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(pageInfo);
		} catch (Exception e) {
			logger.error("分页查询我帮你选选项定义执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 选择方法（点击开始按钮对应的方法）
	 * @param   对象参数：szChooseforyouset
	 * @return  List<SzChooseforyouset>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "选择方法（点击开始按钮对应的方法）", notes = "条件查询[szChooseforyouset],作者：qiu_hf")
	@GetMapping("/choice")
	//TODO 加事务
	public JsonResult<List<SzChooseforyouset>> choice(SzChooseforyouset szChooseforyouset, HttpServletRequest request){
		JsonResult<List<SzChooseforyouset>> result = new JsonResult<List<SzChooseforyouset>>();
		try {
			List<SzChooseforyouset> list = szChooseforyousetServiceImpl.querySzChooseforyousetList(szChooseforyouset);
			if (null != list && list.size() > 0) {
				int temp = (int)(Math.random() * list.size()); // 随机获取一个作为选中的
				List<SzChooseforyouset> newList = new ArrayList<SzChooseforyouset>();
				newList.add(list.get(temp));
				
				SzChooseforyouset chooseset = newList.get(0);
				SzChooseresults res = new SzChooseresults();
				res.setId(UuidUtil.get32UUID());
				res.setChooseId(chooseset.getChooseId());
				res.setChoosedName(chooseset.getName());
				res.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				res.setExt1(new SimpleDateFormat("MM/dd HH:mm").format(new Date()));
				
				HttpSession session = request.getSession();
				SzUser user = (SzUser) session.getAttribute("LoginUser");
				res.setUserid(user.getId());
				
				int reg = szChooseresultsServiceImpl.insertSelective(res);//保存选择信息
				if (reg >= 1) {
					result.setCode(1);
					result.setMessage("成功");
					result.setData(newList); // 将随机选中的返回
				}
			}
		} catch (Exception e) {
			logger.error("获取我帮你选选项定义执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
}