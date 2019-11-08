package com.starzone.flow;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starzone.utils.FileUtil;
import com.starzone.utils.ResultMapHelper;
import com.starzone.vo.ActivitiNode;

/**
 * 工作流视图控制器
 * @doc 说明
 * 1.部署及定义流程，并返回流程实例id
 * 2.启动流程（注册流程）
 * 3.提交申请
 * 4.审批（可以是通过，也可以是不通过）
 * 5.查询用户流程（通过用户id）
 * 6.查询审批历史记录
 * 7.获取历史审批意见及审批人
 * 8.查询流程所有节点
 * 9.查询流程当前节点
 * 10.查看历史流程明细
 * 11.历史行为查询
 * 流程都是创建在配置好审批部门的情况下
 * 用法：
 *    流程一般不需要部署了，设置了启动自动部署；
 *    在业务工程调本工程提供的以上这些方法：
 *       a.在提交页面首次提交审批时，先调启动流程接口，再获取到流程实例id，再将这流程实例id跟业务数据存到业务表，
 *       就是说业务表要多设置一个流程实例id字段，在后面的审批页面中，就是通过流程实例id来回显当前审批流程的数据。
 *       b.当是驳回到第一个节点，重新提交的任务时，就不需要再启动流程了，直接推流程就好。
 *       c.第一个节点Main config assignee 设置为 ${activitiNode.userId}
 *       d.后面的节点Main config Candidate group 直接设置组表中配置好的角色名，到时候审批的时候，在这个角色下的人就会收到审批任务。
 *       e.流程图的线上，什么条件走哪里，在其Main config中的Condition 中设置 ${activitiNode.condition==true} 这样来控制
 *       
 * @FileName ActivitiController.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年9月26日
 * @history 1.0.0.0 2019年9月26日 下午7:18:05 created by【qiu_hf】
 */
@Component
public class FlowUtil {

	public static final Logger log = LoggerFactory.getLogger(FlowUtil.class);
	
	@Autowired
	private RuntimeService runtimeService;
	 
	@Autowired
	private TaskService taskService;
	 
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired  
	private HistoryService historyService;
	
	@Autowired  
	private ProcessEngineConfigurationImpl processEngineConfiguration;
	
	@Value("${file.uploadFolder}")
    private String basePath;
	
	@Value("${sys.nginx.address}")
    private String nginxPath;
	
	/**
	 * 部署及定义流程，并返回流程实例id
	 * @doc 说明 通过画的流程相对路径，如：process/AskForLeaveProcess.bpmn
	 * @param bpmnNamePath 流程名
	 * @return 流程实例id
	 * @author qiu_hf
	 * @history 2019年9月26日 下午8:06:20 Create by 【qiu_hf】
	 */
	public String deployProcess(String bpmnNamePath) {
		log.info("开始部署及定义流程");
		// 根据bpmn文件部署流程
		Deployment deployment = repositoryService.createDeployment().addClasspathResource(bpmnNamePath).deploy();
		// 获取流程定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
		// 启动流程定义，返回流程实例
		ProcessInstance pi = runtimeService.startProcessInstanceById(processDefinition.getId());
		log.info("流程部署及定义完成，返回流程实例id：" + pi.getProcessInstanceId());
		return pi.getProcessInstanceId();
	}
	
	/**
	 * 启动流程（注册流程）
	 * @param userId 可以是用户名
	 * @param bpmnName 流程图名 用来启动流程（流程定义表中的key）
	 * 每启动一次就会创建一个流程实例id
	 * @return 流程实例id processId
	 */
	public String start(String userId, String bpmnName){
		log.info("流程开始启动");
	    Map<String, Object> vars = new HashMap<String, Object>();
	    ActivitiNode activitiNode = new ActivitiNode();
	    
	    activitiNode.setUserId(userId); // 设置流程发起人
	    vars.put("activitiNode", activitiNode);
	    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(bpmnName, vars);
	    String processId = processInstance.getId();
	    activitiNode.setProcessId(processId);
	    log.info("流程启动成功，返回流程实例id：" + processId);
	    return processId;
	}
	
	/**
	 * 提交申请
	 * @param activitiNode 流程节点信息
	 *            1.processId 用来获取当前任务id
	 */
	public ActivitiNode apply(ActivitiNode activitiNode){
		Task task = taskService.createTaskQuery().processInstanceId(activitiNode.getProcessId()).singleResult();
		log.info("第一次执行前，任务名称：" + task.getName() + "任务id：" + task.getId());
	    Map<String, Object> vars = new HashMap<String, Object>();
	    ActivitiNode origin = (ActivitiNode) taskService.getVariable(task.getId(), "activitiNode");
	    origin.setStartDate(activitiNode.getStartDate());
	    origin.setEndDate(activitiNode.getEndDate());
	    origin.setTotalDay(activitiNode.getTotalDay());
	    origin.setCondition(activitiNode.getCondition()); // 通过这个条件可以用来设置流程将来怎么走
	    vars.put("activitiNode", origin);
	    taskService.addComment(task.getId(), activitiNode.getProcessId(), activitiNode.getDesc()); // 设置审批意见
	    taskService.complete(task.getId(), vars);
	    Task taskEnd = taskService.createTaskQuery().processInstanceId(activitiNode.getProcessId()).singleResult();
	    log.info("流程提交成功，当前流程实例id：" + activitiNode.getProcessId() +"，提交后任务id：" + taskEnd.getId() + "任务名：" + task.getName());
		return activitiNode;
	}
	
	/**
	 * 推流程/流程审批（可以是通过，也可以是不通过）
	 * @param activitiNode 流程节点信息
	 *          1.processId 用来获取当前任务id
	 *          2.userId 当前节点审批人
	 */
	public ActivitiNode approve(ActivitiNode activitiNode){
		log.info("开始提交流程审批，主要参数processId=" + activitiNode.getProcessId());
	    Task task = taskService.createTaskQuery().processInstanceId(activitiNode.getProcessId()).singleResult();
	    Map<String, Object> vars = new HashMap<String, Object>();
	    ActivitiNode origin = (ActivitiNode) taskService.getVariable(task.getId(), "activitiNode");
	    origin.setCondition(activitiNode.getCondition()); // 通过这个条件可以用来设置流程将来怎么走
	    origin.setUserId(activitiNode.getAssignee()); // 驳回的时候有用（直接驳回到跟节点）
	    vars.put("activitiNode", origin);
	    taskService.addComment(task.getId(), activitiNode.getProcessId(), activitiNode.getDesc()); // 设置审批意见
	    taskService.setAssignee(task.getId(), activitiNode.getUserId()); // 拾取任务 TODO
	    taskService.complete(task.getId(), vars);
	    
	    task = taskService.createTaskQuery().processInstanceId(activitiNode.getProcessId()).singleResult();
	    if (null == task) {
	    	log.info("流程已走完");
	    } else {
	    	log.info("流程审批提交成功，提交成功后的任务id=" + task.getId() + "任务名：" + task.getName());
	    }
		return activitiNode;
	}
	
	/**
	 * 查询用户提交及审批过的流程（通过用户id）
	 * @param userId 这个可以是审批人姓名
	 * @return 当前用户的任务列表 userTaskList
	 */
	public Map<String, Object> findUserTaskList(String userId){
		log.info("开始查询用户流程（通过用户id）userId：" + userId);
		List<ActivitiNode> resultList = new ArrayList<ActivitiNode>();
		if (null != userId && !"".equals(userId)) {
			List<Task> taskList = taskService.createTaskQuery().taskAssignee(userId).list();
			if(!CollectionUtils.isEmpty(taskList)){
				for(Task task : taskList){
					ActivitiNode an = new ActivitiNode();
					List<Comment> commomentList = taskService.getProcessInstanceComments(task.getProcessInstanceId()); // TODO
					an.setTaskId(task.getId());
					an.setTaskName(task.getName());
					an.setUserId(task.getAssignee());
					an.setProcessId(task.getProcessInstanceId());
					an.setDesc(!CollectionUtils.isEmpty(commomentList) ? commomentList.get(0).getFullMessage() : null);
					resultList.add(an);
				}
			}
		}
	    Map<String, Object> resultMap = ResultMapHelper.getSuccessMap();
	    resultMap.put("userTaskList", resultList);
	    log.info("查询用户流程（通过用户id）结束, userTaskList=" + resultList.toString());
	    return resultMap;
	}
	
	/**
	 * 分页查询用户待审批流程（通过用户id）
	 * @param userId 这个可以是审批人姓名
	 * @return 当前用户的任务列表 userTaskList
	 */
	public Map<String, Object> findUserTaskByPage(Integer pageNum, Integer pageSize, String userId, String bpmnName, String submitNodeName){
		log.info("分页开始查询用户待审批流程（通过用户id）userId：" + userId);
		List<ActivitiNode> resultList = new ArrayList<ActivitiNode>();
		if (null != userId && !"".equals(userId)) {
			PageHelper.startPage(pageNum, pageSize); // 设置分页信息
			List<Task> taskRejectedList = taskService.createTaskQuery().taskAssignee(userId).list(); // 被退回的
			List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(userId).processDefinitionKey(bpmnName).list();
			if (null != taskRejectedList && taskRejectedList.size() > 0) { // 将驳回的任务一起加进去，构造带审批任务
				taskList.addAll(taskRejectedList);
			}
			if(!CollectionUtils.isEmpty(taskList)){
				for(Task task : taskList){
					ActivitiNode an = new ActivitiNode();
//	        	    List<Comment> commomentList = taskService.getProcessInstanceComments(task.getProcessInstanceId()); // TODO
					an.setTaskId(task.getId());
					an.setTaskName(task.getName());
					an.setUserId(task.getAssignee());
					an.setProcessId(task.getProcessInstanceId());
					an.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(task.getCreateTime()));
					an.setIsReject(submitNodeName.equals(task.getName()) ? true : false);
//	                an.setDesc(!CollectionUtils.isEmpty(commomentList) ? commomentList.get(0).getFullMessage() : null);
					resultList.add(an);
				}
			}
		}
	    PageInfo<ActivitiNode> pageInfo = new PageInfo<ActivitiNode>(resultList);
	    Map<String, Object> resultMap = ResultMapHelper.getSuccessMap();
	    resultMap.put("userTaskPageInfo", pageInfo);
	    log.info("分页查询用户待审批流程（通过用户id）结束, userTaskPageInfo=" + pageInfo.toString());
	    return resultMap;
	}
	
	/**
	 * 分页查询用户已审批流程（通过用户id）
	 * @param userId 这个可以是审批人姓名
	 * @return 当前用户的任务列表 userTaskList
	 */
	public Map<String, Object> findUserApprovedTaskByPage(Integer pageNum, Integer pageSize, String userId, String bpmnName, String submitNodeName){
		log.info("分页开始查询用户已审批流程（通过用户id）userId：" + userId);
		PageHelper.startPage(pageNum, pageSize); // 设置分页信息
//	    List<Task> taskList = taskService.createTaskQuery().taskAssignee(userId).list();
	    
	 // 历史流程明细查询
//	    List<HistoricDetail> detailList = historyService.createHistoricDetailQuery().(processId).list();
	    List<HistoricTaskInstance> taskList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).list();
	    
//		List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(userId).processDefinitionKey(bpmnName).list();
	    List<ActivitiNode> resultList = new ArrayList<ActivitiNode>();
	    if(!CollectionUtils.isEmpty(taskList)){
	        for(HistoricTaskInstance task : taskList){
	        	ActivitiNode an = new ActivitiNode();
//	        	List<Comment> commomentList = taskService.getProcessInstanceComments(task.getProcessInstanceId()); // TODO
	        	an.setTaskId(task.getId());
	            an.setTaskName(task.getName());
	            an.setUserId(task.getAssignee());
	            an.setProcessId(task.getProcessInstanceId());
	            an.setEndTime(null != task.getEndTime() ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getEndTime()) : null);
	            an.setDeleteReason(task.getDeleteReason());
	            an.setDurationInMillis(null != task.getDurationInMillis() ? task.getDurationInMillis() : null);
//	            an.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(task.getCreateTime()));
//	            an.setDesc(!CollectionUtils.isEmpty(commomentList) ? commomentList.get(0).getFullMessage() : null);
	            if (!submitNodeName.equals(task.getName())) {
	            	resultList.add(an);
	            }
	        }
	    }
	    PageInfo<ActivitiNode> pageInfo = new PageInfo<ActivitiNode>(resultList);
	    Map<String, Object> resultMap = ResultMapHelper.getSuccessMap();
	    resultMap.put("userApprovedTaskPageInfo", pageInfo);
	    log.info("分页查询用户已审批流程（通过用户id）结束, userApprovedTaskPageInfo=" + pageInfo.toString());
	    return resultMap;
	}
	
	/**
	 * 查询审批历史记录
	 * @param processId 流程实例id
	 * @return 审批历史记录 historyList
	 */
	public Map<String, Object> findHistory(String processId){
		log.info("开始查询审批历史记录，参数processId=" + processId);
	    HistoryService historyService = processEngine.getHistoryService();
	    List<HistoricTaskInstance> datas = historyService.createHistoricTaskInstanceQuery().processInstanceId(processId).list();
	    
	    Map<String, Object> resultMap = ResultMapHelper.getSuccessMap();
	    resultMap.put("historyList", datas);
	    log.info("查询审批历史记录结束，historyList=" + datas.toString());
	    return resultMap;
	}
	
	/**
	 * 查询流程所有节点
	 * @param processId 流程实例id
	 * @return 流程所有节点 flowAllNode
	 */
	public Map<String, Object> findFlowAllNode(String processId){
		log.info("开始查询流程所有节点，参数processId=" + processId);
	    HistoryService historyService = processEngine.getHistoryService();
	    String processDefinitionId = historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult().getProcessDefinitionId();  
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinitionId);  
        // 获得当前任务的所有节点  
        List<ActivityImpl> activitiList = def.getActivities();
	    
	    Map<String, Object> resultMap = ResultMapHelper.getSuccessMap();
	    resultMap.put("flowAllNode", activitiList);
	    log.info("查询流程所有节点结束，flowAllNode：" + activitiList.toString());
	    return resultMap;
	}
	
	/**
	 * 查询流程当前节点
	 * @param processId 流程实例id
	 * @return 当前节点 currentNode
	 */
	public Map<String, Object> findCurrentNode(String processId){
		log.info("开始查询流程当前节点，参数：processId=" + processId);
        // 执行实例  
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();  
        // 当前实例的执行到哪个节点  
        String activitiId = execution.getActivityId();  

        Map<String, Object> resultMap = ResultMapHelper.getSuccessMap();
	    resultMap.put("currentNode", activitiId);
	    log.info("查询流程当前节点结束， currentNode：" + activitiId);
	    return resultMap;
	}
	
	/**
	 * 查看历史流程明细
	 * @param processId 流程实例id
	 * @return 历史流程明细 historyDetail
	 */
	public Map<String, Object> findHistoryDetail(String processId){
		log.info("开始查看历史流程明细，参数：processId=" + processId);
	    HistoryService historyService = processEngine.getHistoryService();
	    // 历史流程明细查询
	    List<HistoricDetail> detailList = historyService.createHistoricDetailQuery().processInstanceId(processId).list();
	    
	    Map<String, Object> resultMap = ResultMapHelper.getSuccessMap();
	    resultMap.put("historyDetail", detailList);
	    log.info("查询历史流程明细结束， historyDetail：" + detailList.toString());
	    return resultMap;
	}
	
	/**
	 * 历史行为查询
	 * @param processId 流程实例id
	 * @return 历史行为 historyAction
	 */
	public Map<String, Object> findHistoryAction(String processId){
		log.info("开始历史行为查询，参数：processId=" + processId);
	    HistoryService historyService = processEngine.getHistoryService();
	 
	    // 历史行为查询
	    List<HistoricActivityInstance> historyActionList = historyService.createHistoricActivityInstanceQuery().processInstanceId("37511").list();
	    
	    Map<String, Object> resultMap = ResultMapHelper.getSuccessMap();
	    resultMap.put("historyAction", historyActionList);
	    
	    log.info("查询历史行为查询结束， historyAction：" + historyActionList.toString());
	    return resultMap;
	}
	
	/**
	 * 查询流程所有审批意见
	 * @param processId 流程实例id
	 * @return 历史行为 commentList
	 */
	public Map<String, Object> findApproveComment(String processId){
		log.info("开始查询流程所有审批意见，参数：processId=" + processId);
		
		List<Comment> list = new ArrayList<Comment>();
		list = taskService.getProcessInstanceComments(processId);
	    Map<String, Object> resultMap = ResultMapHelper.getSuccessMap();
	    resultMap.put("commentList", list);
	    
	    log.info("查询流程所有审批意见结束， commentList：" + list.toString());
	    return resultMap;
	}
	
	/**
	 * 获取流程图高亮显示
	 * @doc 说明
	 * @return nginx上流程图访问路径
	 * @param processId 流程实例id
	 * @throws Exception
	 * @author qiu_hf
	 * @history 2019年9月28日 上午10:39:19 Create by 【qiu_hf】
	 */
	public String queryProHighLighted(String processId) throws Exception {
		log.info("开始生成图片，查询流程图高亮显示，参数：processId=" + processId);
        //获取历史流程实例  
        HistoricProcessInstance processInstance =  historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();  
        //获取流程图  
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());  
  
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();  
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());  
  
        List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(processId).list();  
        //高亮环节id集合  
        List<String> highLightedActivitis = new ArrayList<String>();  
          
        //高亮线路id集合  
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity,highLightedActivitList);  
  
        for(HistoricActivityInstance tempActivity : highLightedActivitList){  
            String activityId = tempActivity.getActivityId();  
            highLightedActivitis.add(activityId);  
        }  
        //配置字体
//        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, highLightedFlows,"宋体","微软雅黑","黑体",null,2.0);
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, highLightedFlows,"宋体","宋体","宋体",null,2.0);
        BufferedImage bi = ImageIO.read(imageStream);
        
        String imgPath = "";
        String imgName = "";
        imgName = "activitiImg/" + processId + ".png";
        imgPath = basePath + "/activitiImg/" + processId + ".png"; // 保存路径
        
        File file = new File(imgPath);
        FileUtil.judeDirExists(new File(basePath + "/activitiImg/"));
        FileUtil.judeFileExists(file);
        FileOutputStream fos = new FileOutputStream(file);
        ImageIO.write(bi, "png", fos);
        fos.close();
        imageStream.close();
        log.info("图片生成成功，获取流程图高亮显示结束，图片生成在：" + imgPath + "，通过:" + nginxPath + imgName + "可以访问流程图片");
        return nginxPath + imgName;
    }
	
    /**  
     * 获取需要高亮的线  
     * @param processDefinitionEntity  
     * @param historicActivityInstances  
     * @return 需要高亮的线
     */  
    private List<String> getHighLightedFlows(  
            ProcessDefinitionEntity processDefinitionEntity,  
            List<HistoricActivityInstance> historicActivityInstances) { 
          
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId  
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历  
            ActivityImpl activityImpl = processDefinitionEntity  
                    .findActivity(historicActivityInstances.get(i)  
                            .getActivityId());// 得到节点定义的详细信息  
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点  
            ActivityImpl sameActivityImpl1 = processDefinitionEntity  
                    .findActivity(historicActivityInstances.get(i + 1)  
                            .getActivityId());  
            // 将后面第一个节点放在时间相同节点的集合里  
            sameStartTimeNodes.add(sameActivityImpl1);  
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {  
                HistoricActivityInstance activityImpl1 = historicActivityInstances  
                        .get(j);// 后续第一个节点  
                HistoricActivityInstance activityImpl2 = historicActivityInstances  
                        .get(j + 1);// 后续第二个节点  
                if (activityImpl1.getStartTime().equals(  
                        activityImpl2.getStartTime())) {  
                    // 如果第一个节点和第二个节点开始时间相同保存  
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity  
                            .findActivity(activityImpl2.getActivityId());  
                    sameStartTimeNodes.add(sameActivityImpl2);  
                } else {  
                    // 有不相同跳出循环  
                    break;  
                }  
            }  
            List<PvmTransition> pvmTransitions = activityImpl  
                    .getOutgoingTransitions();// 取出节点的所有出去的线  
            for (PvmTransition pvmTransition : pvmTransitions) {  
                // 对所有的线进行遍历  
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition  
                        .getDestination();  
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示  
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {  
                    highFlows.add(pvmTransition.getId());  
                }  
            }  
        }  
        return highFlows;  
    }
	
}
