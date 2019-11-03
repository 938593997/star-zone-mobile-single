package com.starzone.web;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.starzone.dao.WorkTaskDao;
import com.starzone.pojo.SzSpendType;
import com.starzone.pojo.SzUser;
import com.starzone.pojo.WorkTask;
import com.starzone.service.WorkTaskService;
import com.starzone.utils.AppPage;
//import com.starzone.utils.PageBean;
import com.starzone.utils.JsonResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 任务管理视图控制器
 * @doc 说明
 * @FileName WorkTaskController.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2018年12月15日
 * @history 1.0.0.0 2018年12月15日 下午6:27:02 created by【qiu_hf】
 */
@SuppressWarnings("unused")
@RestController
//@CrossOrigin(origins = {"http://127.0.0.1:8000", "null"})
@RequestMapping(value = "/star_zone")
public class WorkTaskController {

	@Autowired
	private WorkTaskService workTaskService;
	
	@Autowired
	private WorkTaskDao workTaskDao;
	
	/**
	 * 获取所有任务信息
	 * @doc 说明
	 * @param workTask 前端传过来的过滤信息对象
	 * @param currentPage 当前页
	 * @param pageSize 每页显示数
	 * @return 所有任务信息集合
	 * @author qiu_hf
	 * @history 2018年12月15日 下午6:45:32 Create by 【qiu_hf】
	 */
//	@RequestMapping(value = "/getAllWorkTasks", method = RequestMethod.POST)
//	public Page<WorkTask> getAllWorkTasks(@RequestBody PageBean<WorkTask> pageBean){
//		
//		Page<WorkTask> pages = null;
//		try {
//			pages = workTaskService.getPageSort(pageBean.getCurrentPage(), pageBean.getPageSize());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return pages;
//	}
	
	/**
	 * 获取所有任务信息
	 * @doc 说明
	 * @param workTask 前端传过来的过滤信息对象
	 * @return 所有任务信息集合
	 * @author qiu_hf
	 * @history 2018年12月15日 下午6:45:32 Create by 【qiu_hf】
	 */
	@RequestMapping(value = "/getAllWorkTask", method = RequestMethod.GET)
	public List<WorkTask> getAllWorkTask(){
		
		List<WorkTask> list = new ArrayList<WorkTask>();
		
		try {
			list = workTaskService.getAllWorkTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 删除任务
	 * @doc 说明
	 * @param id 要删除的任务id
	 * @author qiu_hf
	 * @history 2018年12月15日 下午10:41:07 Create by 【qiu_hf】
	 */
	@RequestMapping(value = "/deleteWorkTask", method = RequestMethod.DELETE)
	public void deleteWorkTask(@RequestBody WorkTask workTask){
		
		try {
			workTaskService.deleteWorkTask(workTask.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/createOrModifyWorkTask", method = RequestMethod.POST)
	public void createOrModifyWorkTask(@RequestBody WorkTask workTask){
		
		try {
			
			if(workTask.getId() == 0){
				workTaskService.createWorkTask(workTask);
			}else{
				workTaskService.modifyWorkTask(workTask);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 任务数量信息（供前端饼状图、柱状图使用）
	 * @doc 说明
	 * @return 任务数量信息
	 * @author qiu_hf
	 * @history 2018年12月22日 下午3:59:58 Create by 【qiu_hf】
	 */
	@RequestMapping(value = "/findTaskMessages", method = RequestMethod.GET)
	public List<WorkTask> findTaskMessages(){
		
		List<WorkTask> workTasks = new ArrayList<WorkTask>();
		
		try {
			
			workTasks = workTaskService.findTaskMessages();
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return workTasks;
	}
	
	/**
	 * Specification动态拼接参数 基本用法
	 * @explain 获取匹配star-zone用户
	 * @param   对象参数：szUser
	 * @return  List<SzUser>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "条件查询star-zone用户，Specification动态拼接参数 基本用法", notes = "条件查询[szUser],作者：qiu_hf")
	@GetMapping("/querySzUserListSpecificationJPA")
//	@Transactional(value = "transactionManager")
	public JsonResult<List<WorkTask>> querySzUserListSpecificationJPA(WorkTask workTask){
		JsonResult<List<WorkTask>> result = new JsonResult<List<WorkTask>>();
		try {
			Specification<WorkTask> querySpecifi = new Specification<WorkTask>() {
	            @Override
	            public Predicate toPredicate(Root<WorkTask> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	 
	                List<Predicate> predicates = new ArrayList<>();
	                if (null != workTask.getStartDate()) { // 时间范围
	                    //大于或等于传入时间
	                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createDate").as(String.class), workTask.getStartDate()));
	                }
	                if (null != workTask.getEndDate()) { // 时间范围
	                    //小于或等于传入时间
	                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createDate").as(String.class), workTask.getEndDate()));
	                }
//	                if (null != workTask.getName()) { // like
//	                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + workTask.getName() + "%"));
//	                }
	                if (null != workTask.getName()) { // 精确查
	                    predicates.add(criteriaBuilder.equal(root.get("name"), workTask.getName()));
	                }
//                    if (devices.length > 0) { // in 查询
//                        Expression<String> exp = root.<String>get("netbarWacode");
//                        predicatesAnd.add(exp.in(devices));
//                    }
	                // and到一起的话所有条件就是且关系，or就是或关系
	                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	            }
	        };
	        List<WorkTask> list = workTaskService.querySzUserListSpecificationJPA(querySpecifi);
	        
			result.setCode(1);
			result.setMessage("成功");
			result.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * Example动态拼接参数 基本用法
	 * @explain 获取匹配star-zone用户
	 * @param   对象参数：szUser
	 * @return  List<SzUser>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "条件查询star-zone用户，Example动态拼接参数 基本用法", notes = "条件查询[szUser],作者：qiu_hf")
	@GetMapping("/querySzUserListExampleJPA")
	public JsonResult<List<WorkTask>> querySzUserListExampleJPA(WorkTask workTask){
		JsonResult<List<WorkTask>> result = new JsonResult<List<WorkTask>>();
		try {
			ExampleMatcher matcher = ExampleMatcher.matching()
					.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains()) // 模糊拼配
					.withIgnorePaths("id", "isFinish"); // 忽略字段，就算有值也不加入过滤条件（实体中这两个字段的类型分别为Long和Integer，这里查询时会默认赋值为0一起作为过滤条件，所以这里去掉）
			Example<WorkTask> example = Example.of(workTask, matcher);
	        List<WorkTask> list = workTaskService.querySzUserListExampleJPA(example);
	        
			result.setCode(1);
			result.setMessage("成功");
			result.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 分页条件任务
	 * @param   对象参数：AppPage<WorkTask>
	 * @return  PageInfo<WorkTask>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@GetMapping("/getWorkTaskPageBySpecification")
	@ApiOperation(value = "分页查询", notes = "分页查询返回对象[PageInfo<WorkTask>],作者：qiu_hf")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "页行数", required = true, dataType = "int")
    })
	public JsonResult<Page<WorkTask>> getWorkTaskPageBySpecification(Integer pageNum, Integer pageSize, WorkTask workTask){
		JsonResult<Page<WorkTask>> result = new JsonResult<Page<WorkTask>>();
		AppPage<WorkTask> page = new AppPage<WorkTask>();
		page.setPageNum(pageNum); // 次框架分页查询从1开始
		page.setPageSize(pageSize);
		// 设置前端传过来的其他参数
		page.setParam(workTask);
		//分页数据
		try {
			Page<WorkTask> pageInfo = workTaskService.getWorkTaskPageBySpecification(page);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
}
