/**
 * @filename:ApplyForLeaveController 2019年9月27日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.utils.CalculateUtils;
import com.starzone.utils.JsonResult;
import com.starzone.utils.RedisUtil;
import com.starzone.vo.ActivitiNode;
import com.starzone.pojo.ApplyForLeave;
import com.starzone.pojo.SzUser;
import com.starzone.service.master.ApplyForLeaveService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

/**   
 * @Description:  请假申请接口层
 * @Author:       qiu_hf   
 * @CreateDate:   2019年9月27日
 * @Version:      V1.0
 */
@Api(description = "请假申请", value="请假申请" )
@RestController
@RequestMapping("/api/applyForLeave")
public class ApplyForLeaveController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public ApplyForLeaveService applyForLeaveServiceImpl;
	
	@Autowired
	RestTemplate restTmpl;
	
	@Autowired
	private RedisUtil redisUtil;
	
	private static String bpmnName = "ApplyForLeave";
	
	private static String submitNodeName = "提交请假申请"; // 用来驳回后重新提交的标识、过滤审批历史中提交的数据（这类数据不属于审批任务）
	
	/**
	 * 获取我的申请流程详情
	 * @doc 说明
	 * @param processId 流程实例id
	 * @return 我的申请流程详情
	 * @author qiu_hf
	 * @history 2019年9月27日 下午8:51:35 Create by 【qiu_hf】
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/getMyApplyForLeaveDetailByProcessId")
	@ApiOperation(value = "获取我的申请流程详情", notes = "获取我的申请流程详情， 作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "processId", value = "流程实例id", required = true, dataType = "String")
	public JsonResult<ApplyForLeave> getMyApplyForLeaveDetailByProcessId(String processId){
		JsonResult<ApplyForLeave> result = new JsonResult<ApplyForLeave>();
		Map<String, Object> resultMap = restTmpl.getForEntity("http://star-zone-mobile-common/api/activiti/findHistory?processId=" + processId, Map.class).getBody();
		if (null != resultMap.get("historyList")) {
			List<HistoricTaskInstance> historyList = (List<HistoricTaskInstance>) resultMap.get("historyList");
			ApplyForLeave afl = new ApplyForLeave();
			afl.setHistoryList(historyList);
			result.setData(afl);
		}
		return result;
	}
	
	/**
	 * 获取流程跟踪图片
	 * @doc 说明
	 * @param processId 流程实例id
	 * @return 流程跟踪图片
	 * @author qiu_hf
	 * @history 2019年9月28日 下午1:13:16 Create by 【qiu_hf】
	 */
	@GetMapping("/queryApplyFollow")
	@ApiOperation(value = "获取流程跟踪图片", notes = "获取流程跟踪图片， 作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "processId", value = "流程实例id", required = true, dataType = "String")
	public JsonResult<ApplyForLeave> queryApplyFollow(String processId){
		JsonResult<ApplyForLeave> result = new JsonResult<ApplyForLeave>();
		String imgFollowNginxUrl = restTmpl.getForEntity("http://star-zone-mobile-common/api/activiti/queryProHighLighted?processId=" + processId, String.class).getBody();
		ApplyForLeave afl = new ApplyForLeave();
		afl.setImgFollowNginxUrl(imgFollowNginxUrl);
		result.setData(afl);
		return result;
	}
	
//	*******************************************************
	
	/**
	 * @explain 查询请假申请对象  <swagger GET请求>
	 * @param   对象参数：id
	 * @return  applyForLeave
	 * @author  qiu_hf
	 * @time    2019年9月27日
	 */
	@GetMapping("/getApplyForLeaveById")
	@ApiOperation(value = "获取请假申请信息", notes = "获取请假申请信息[applyForLeave]， 作者：qiu_hf")
	@ApiImplicitParam(paramType="path", name = "id", value = "请假申请id", required = true, dataType = "String")
	public JsonResult<ApplyForLeave> getApplyForLeaveById(String id){
		JsonResult<ApplyForLeave> result = new JsonResult<ApplyForLeave>();
		try {
			ApplyForLeave applyForLeave = applyForLeaveServiceImpl.selectByPrimaryKey(id);
			if (applyForLeave!=null) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(applyForLeave);
			} else {
				logger.error("获取请假申请失败ID："+id);
				result.setCode(-1);
				result.setMessage("你获取的请假申请不存在");
			}
		} catch (Exception e) {
			logger.error("获取请假申请执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 查询请假申请对象  <swagger GET请求>（通过processId）
	 * @param   对象参数：id
	 * @return  applyForLeave
	 * @author  qiu_hf
	 * @time    2019年9月27日
	 */
	@GetMapping("/queryApplyInfoByProcessId")
	@ApiOperation(value = "获取请假申请信息（通过processId）", notes = "获取请假申请信息（通过processId）[applyForLeave]， 作者：qiu_hf")
	@ApiImplicitParam(paramType="path", name = "processId", value = "流程实例id", required = true, dataType = "String")
	public JsonResult<ApplyForLeave> queryApplyInfoByProcessId(String processId){
		JsonResult<ApplyForLeave> result = new JsonResult<ApplyForLeave>();
		try {
			ApplyForLeave applyForLeave = applyForLeaveServiceImpl.queryApplyInfoByProcessId(processId);
			if (applyForLeave != null) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(applyForLeave);
			} else {
				logger.error("获取请假申请失败processId："+ processId);
				result.setCode(-1);
				result.setMessage("你获取的请假申请不存在");
			}
		} catch (Exception e) {
			logger.error("获取请假申请执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 添加请假申请对象
	 * @param   对象参数：applyForLeave
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年9月27日
	 */
	@PostMapping("/insertApplyForLeave")
	@ApiOperation(value = "添加请假申请", notes = "添加请假申请[applyForLeave], 作者：qiu_hf")
	@Transactional
	public JsonResult<ApplyForLeave> insertApplyForLeave(@RequestBody ApplyForLeave applyForLeave, HttpServletRequest request){
		JsonResult<ApplyForLeave> result = new JsonResult<ApplyForLeave>();
		try {
			String token = request.getHeader("__token__");
			SzUser user = (SzUser) redisUtil.get(token);
			// 启动流程
			String processId = restTmpl.getForEntity("http://star-zone-mobile-common/api/activiti/start?userId=" + user.getExt3() + "&&bpmnName=" + bpmnName, String.class).getBody();
			// 提交流程
			ActivitiNode activitiNode = new ActivitiNode();
			activitiNode.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(applyForLeave.getStartDate()));
			activitiNode.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(applyForLeave.getEndDate()));
			activitiNode.setTotalDay(CalculateUtils.differentDays(new SimpleDateFormat("yyyy-MM-dd").parse(applyForLeave.getStartDate()), new SimpleDateFormat("yyyy-MM-dd").parse(applyForLeave.getEndDate())));
			activitiNode.setCondition(String.valueOf(CalculateUtils.differentDays(new SimpleDateFormat("yyyy-MM-dd").parse(applyForLeave.getStartDate()), new SimpleDateFormat("yyyy-MM-dd").parse(applyForLeave.getEndDate()))));
			activitiNode.setDesc(applyForLeave.getApplyReason());
			activitiNode.setProcessId(processId);
			activitiNode = restTmpl.postForEntity("http://star-zone-mobile-common/api/activiti/apply", activitiNode, ActivitiNode.class).getBody();
			// 插入业务数据
			applyForLeave.setProcessId(processId);
			applyForLeave.setOwner(user.getId());
			applyForLeave.setApplyDate(String.valueOf(activitiNode.getTotalDay()));
			applyForLeave.setUserId(user.getExt3());
			applyForLeave.setId(new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date()));
			int rg = applyForLeaveServiceImpl.insertSelective(applyForLeave);
			if (rg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(applyForLeave);
			} else {
				logger.error("添加请假申请执行失败："+applyForLeave.toString());
				result.setCode(-1);
				result.setMessage("执行失败，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("添加请假申请执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 提交审批（通过或驳回，通过前端传过来的condition，流程里面会自动判断，这里只管推流程就好）
	 * @param   对象参数：applyForLeave
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年9月27日
	 */
	@PostMapping("/submitApprove")
	@ApiOperation(value = "添加请假申请", notes = "添加请假申请[applyForLeave], 作者：qiu_hf")
	@Transactional
	public JsonResult<ApplyForLeave> submitApprove(@RequestBody ApplyForLeave applyForLeave, HttpServletRequest request){
		JsonResult<ApplyForLeave> result = new JsonResult<ApplyForLeave>();
		try {
			String token = request.getHeader("__token__");
			SzUser user = (SzUser) redisUtil.get(token);
			// 审批流程
			ActivitiNode activitiNode = new ActivitiNode();
			activitiNode.setCondition(applyForLeave.getCondition()); // 设置流程怎么走
			activitiNode.setDesc(applyForLeave.getDescription());    // 设置审批意见
			activitiNode.setProcessId(applyForLeave.getProcessId()); // 设置审批意见和获取任务信息时会用到流程实例id
//			activitiNode.setUserId(null != applyForLeave.getCondition() && !"0".equals(applyForLeave.getCondition()) ? user.getExt3() : applyForLeave.getUserId()); // 设置审批人
			activitiNode.setUserId(user.getExt3()); // 设置审批人
			activitiNode.setAssignee(applyForLeave.getUserId()); // 驳回的时候有用（直接驳回到跟节点）
			activitiNode = restTmpl.postForEntity("http://star-zone-mobile-common/api/activiti/approve", activitiNode, ActivitiNode.class).getBody();
			if (null != activitiNode) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(applyForLeave);
			} else {
				logger.error("提交审批请执行失败：" + applyForLeave.toString());
				result.setCode(-1);
				result.setMessage("执行失败，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("提交审批执行异常：" + e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 删除请假申请对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年9月27日
	 */
	@DeleteMapping("/deleteApplyForLeaveByPrimaryKey")
	@ApiOperation(value = "删除请假申请", notes = "删除请假申请, 作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "id", value = "请假申请id", required = true, dataType = "String")
	public JsonResult<Object> deleteApplyForLeaveByPrimaryKey(String id){
		JsonResult<Object> result = new JsonResult<Object>();
		try {
			int reg = applyForLeaveServiceImpl.deleteByPrimaryKey(id);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(id);
			} else {
				logger.error("删除请假申请失败ID："+id);
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("删除请假申请执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 修改请假申请对象
	 * @param   对象参数：applyForLeave
	 * @return  applyForLeave
	 * @author  qiu_hf
	 * @time    2019年9月27日
	 */
	@ApiOperation(value = "修改请假申请", notes = "修改请假申请[applyForLeave], 作者：qiu_hf")
	@PutMapping("/updateApplyForLeaveByPrimaryKey")
	public JsonResult<ApplyForLeave> updateApplyForLeaveByPrimaryKey(@RequestBody ApplyForLeave applyForLeave){
		JsonResult<ApplyForLeave> result = new JsonResult<ApplyForLeave>();
		try {
			int reg = applyForLeaveServiceImpl.updateByPrimaryKeySelective(applyForLeave);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(applyForLeave);
			} else {
				logger.error("修改请假申请失败ID："+applyForLeave.toString());
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("修改请假申请执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 获取匹配请假申请
	 * @param   对象参数：applyForLeave
	 * @return  List<ApplyForLeave>
	 * @author  qiu_hf
	 * @time    2019年9月27日
	 */
	@ApiOperation(value = "条件查询请假申请", notes = "条件查询[applyForLeave], 作者：qiu_hf")
	@GetMapping("/queryApplyForLeaveList")
	public JsonResult<List<ApplyForLeave>> queryApplyForLeaveList(ApplyForLeave applyForLeave, HttpServletRequest request){
		JsonResult<List<ApplyForLeave>> result = new JsonResult<List<ApplyForLeave>>();
		try {
			String token = request.getParameter("__token__");
			SzUser user = (SzUser) redisUtil.get(token);
			applyForLeave.setUserId(user.getExt3());
			List<ApplyForLeave> list = applyForLeaveServiceImpl.queryApplyForLeaveList(applyForLeave);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取请假申请执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 分页条件查询请假申请   
	 * @param   对象参数：AppPage<ApplyForLeave>
	 * @return  PageInfo<ApplyForLeave>
	 * @author  qiu_hf
	 * @time    2019年9月27日
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/getApplyForLeavePage")
	@ApiOperation(value = "分页查询", notes = "分页查询返回对象[PageInfo<ApplyForLeave>], 作者：qiu_hf")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "页行数", required = true, dataType = "int")
    })
	public JsonResult<PageInfo<?>> getApplyForLeavePage(Integer pageNum, Integer pageSize, ApplyForLeave applyForLeave, HttpServletRequest request){
		JsonResult<PageInfo<?>> result = new JsonResult<PageInfo<?>>();
		String token = request.getParameter("__token__");
		SzUser user = (SzUser) redisUtil.get(token);
		applyForLeave.setUserId(user.getExt3());
		
		AppPage<ApplyForLeave> page = new AppPage<ApplyForLeave>();
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		// 设置前端传过来的其他参数
		page.setParam(applyForLeave);
		//分页数据
		try {
			PageInfo<ApplyForLeave> pageInfo = new PageInfo<ApplyForLeave>();
			if (null != applyForLeave.getActivedTitle() && "我的申请".equals(applyForLeave.getActivedTitle())) {
				pageInfo = applyForLeaveServiceImpl.getApplyForLeaveBySearch(page);
				result.setData(pageInfo);
			} else if (null != applyForLeave.getActivedTitle() && "我的审批".equals(applyForLeave.getActivedTitle())) {
				Map<String, Object> resultMaps = restTmpl.getForEntity("http://star-zone-mobile-common/api/activiti/findUserTaskByPage?userId=" + applyForLeave.getUserId() + "&&pageNum=" + pageNum + "&&pageSize=" + pageSize + "&&bpmnName=" + bpmnName + "&&submitNodeName=" + submitNodeName, Map.class).getBody();
				if (null != resultMaps.get("userTaskPageInfo")) {
					Object pInfo = (Object) resultMaps.get("userTaskPageInfo");
					Map classMap = new HashMap();
					classMap.put("list", ActivitiNode.class); // list中的数据对应的javaBean是ActivitiNode
					JSONObject jsonObject = JSONObject.fromObject(pInfo);
					PageInfo<ActivitiNode> pageInfos = (PageInfo<ActivitiNode>)JSONObject.toBean(jsonObject, PageInfo.class, classMap);
					result.setData(pageInfos);
				}
			} else if (null != applyForLeave.getActivedTitle() && "审批历史".equals(applyForLeave.getActivedTitle())) {
				Map<String, Object> resultMaps = restTmpl.getForEntity("http://star-zone-mobile-common/api/activiti/findUserApprovedTaskByPage?userId=" + applyForLeave.getUserId() + "&&pageNum=" + pageNum + "&&pageSize=" + pageSize + "&&bpmnName=" + bpmnName + "&&submitNodeName=" + submitNodeName, Map.class).getBody();
				if (null != resultMaps.get("userApprovedTaskPageInfo")) {
					Object pInfo = (Object) resultMaps.get("userApprovedTaskPageInfo");
					Map classMap = new HashMap();
					classMap.put("list", ActivitiNode.class); // list中的数据对应的javaBean是ActivitiNode
					JSONObject jsonObject = JSONObject.fromObject(pInfo);
					PageInfo<ActivitiNode> pageInfos = (PageInfo<ActivitiNode>)JSONObject.toBean(jsonObject, PageInfo.class, classMap);
					result.setData(pageInfos);
				}
			}
			result.setCode(1);
			result.setMessage("成功");
		} catch (Exception e) {
			logger.error("分页查询请假申请执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 驳回后重新提交，修改请假申请对象
	 * @param   对象参数：applyForLeave
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年9月27日
	 */
	@PostMapping("/reSubmitApply")
	@ApiOperation(value = "添加请假申请", notes = "添加请假申请[applyForLeave], 作者：qiu_hf")
	@Transactional
	public JsonResult<ApplyForLeave> reSubmitApply(@RequestBody ApplyForLeave applyForLeave, HttpServletRequest request){
		JsonResult<ApplyForLeave> result = new JsonResult<ApplyForLeave>();
		try {
			// 提交流程
			ActivitiNode activitiNode = new ActivitiNode();
			activitiNode.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(applyForLeave.getStartDate()));
			activitiNode.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(applyForLeave.getEndDate()));
			activitiNode.setTotalDay(CalculateUtils.differentDays(new SimpleDateFormat("yyyy-MM-dd").parse(applyForLeave.getStartDate()), new SimpleDateFormat("yyyy-MM-dd").parse(applyForLeave.getEndDate())));
			activitiNode.setCondition(String.valueOf(CalculateUtils.differentDays(new SimpleDateFormat("yyyy-MM-dd").parse(applyForLeave.getStartDate()), new SimpleDateFormat("yyyy-MM-dd").parse(applyForLeave.getEndDate()))));
			activitiNode.setDesc(applyForLeave.getApplyReason());
			activitiNode.setProcessId(applyForLeave.getProcessId());
			activitiNode = restTmpl.postForEntity("http://star-zone-mobile-common/api/activiti/apply", activitiNode, ActivitiNode.class).getBody();
			// 修改业务数据
			applyForLeave.setApplyDate(String.valueOf(activitiNode.getTotalDay()));
			int rg = applyForLeaveServiceImpl.updateByProcessId(applyForLeave);
			if (rg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(applyForLeave);
			} else {
				logger.error("驳回后重新提交，修改请假申请对象执行失败：" + applyForLeave.toString());
				result.setCode(-1);
				result.setMessage("执行失败，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("驳回后重新提交，修改请假申请对象执行异常：" + e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	public static void main(String[] args) throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2019-12-12");
		System.out.println(date);
	}
}