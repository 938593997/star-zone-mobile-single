package com.starzone.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.starzone.dao.WorkTaskDao;
import com.starzone.pojo.WorkTask;
import com.starzone.service.WorkTaskService;
import com.starzone.utils.AppPage;

/**
 * 任务管理业务层接口实现类
 * @doc 说明
 * @FileName WorkTaskServiceImpl.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2018年12月15日
 * @history 1.0.0.0 2018年12月15日 下午6:29:16 created by【qiu_hf】
 */
@Service
public class WorkTaskServiceImpl implements WorkTaskService{

	@Autowired
	private WorkTaskDao workTaskDao;
	
//	@Autowired
//    @PersistenceContext
//    private EntityManager entityManager;

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
	@Override
	public List<WorkTask> getAllWorkTasks(WorkTask workTask, int currentPage, int pageSize) {

		List<WorkTask> list = new ArrayList<WorkTask>();
//		list = workTaskDao.getAllWorkTasks(workTask, currentPage, pageSize);
		list = workTaskDao.findAll();
		return list;
	}

	@Override
	public List<WorkTask> getAllWorkTask() {

		List<WorkTask> list = new ArrayList<WorkTask>();
		list = workTaskDao.findAll();
		return list;
	}

	@Override
	public void deleteWorkTask(long id) {
		workTaskDao.delete(id);
	}

	@Override
	public void createWorkTask(WorkTask workTask) {
		workTaskDao.save(workTask);
	}

	@Override
	public void modifyWorkTask(WorkTask workTask) {
		workTaskDao.modifyWorkTasks(workTask.getName(),workTask.getTaskContent(),workTask.getCreateDate(),workTask.getFinishDate(),workTask.getIsFinish(),workTask.getId());
	}

	@Override
	public List<WorkTask> findTaskMessages() {

		List<WorkTask> workTasks = new ArrayList<WorkTask>();
		workTasks = workTaskDao.findTaskMessages();
		return workTasks;
	}

	@Override
	@Transactional(readOnly = true)  
	public Page<WorkTask> getPageSort(Integer pageNum, Integer pageLimit) {
		
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(pageNum - 1, pageLimit, sort);
        return workTaskDao.findAll(pageable);
	}

	@Override
	public List<WorkTask> querySzUserListSpecificationJPA(Specification<WorkTask> querySpecifi) {
		return workTaskDao.findAll(querySpecifi);
	}

	@Override
	public List<WorkTask> querySzUserListExampleJPA(Example<WorkTask> example) {
		return workTaskDao.findAll(example);
	}

	@Override
	public Page<WorkTask> getWorkTaskPageBySpecification(AppPage<WorkTask> page) {
		
		Specification<WorkTask> querySpecifi = new Specification<WorkTask>() {
            @Override
            public Predicate toPredicate(Root<WorkTask> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
 
            	WorkTask workTask = page.getParam();
                List<Predicate> predicates = new ArrayList<>();
                if (null != workTask.getStartDate()) { // 时间范围
                    //大于或等于传入时间
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createDate").as(String.class), workTask.getStartDate()));
                }
                if (null != workTask.getEndDate()) { // 时间范围
                    //小于或等于传入时间
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createDate").as(String.class), workTask.getEndDate()));
                }
                if (null != workTask.getName()) { // like
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + workTask.getName() + "%"));
                }
//                if (null != workTask.getName()) { // 精确查
//                    predicates.add(criteriaBuilder.equal(root.get("name"), workTask.getName()));
//                }
//                if (devices.length > 0) { // in 查询
//                    Expression<String> exp = root.<String>get("netbarWacode");
//                    predicatesAnd.add(exp.in(devices));
//                }
                // and到一起的话所有条件就是且关系，or就是或关系
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        
		Pageable pageable = new PageRequest(page.getPageNum() - 1, page.getPageSize(), Sort.Direction.DESC, "id");
		Page<WorkTask> pages = workTaskDao.findAll(querySpecifi, pageable);
		return pages;
	}
	
}
