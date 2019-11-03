package com.starzone.portal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.starzone.vo.UsageUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 返回String、对象、List、Map的用法视图控制器
 * @doc 说明
 * @FileName UsageController.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年9月21日
 * @history 1.0.0.0 2019年9月21日 下午9:43:13 created by【qiu_hf】
 */
@RestController
@Api(description = "返回String、对象、List、Map的用法",value="返回String、对象、List、Map的用法" )
@RequestMapping("/usage")
public class UsageController {

	@Autowired
	RestTemplate restTmpl;
	
	/**
	 * 1 /first
	 * @doc 说明:返回String，请求不带任何参数
	 * @return Hello, my first rest servivce. Host:qiu-PC, ServiceId:star-zone-mobile-common
	 * @author qiu_hf
	 * @history 2019年9月21日 下午10:11:41 Create by 【qiu_hf】
	 */
	@ApiOperation(value = "返回String，请求不带任何参数", notes = "/first，作者：qiu_hf")
	@RequestMapping(value="/first", method=RequestMethod.GET)
	public String firstService(){
		return restTmpl.getForEntity("http://star-zone-mobile-common/usage/first", String.class).getBody();
	}
	
	/**
	 * 2 /getUserNameByIdPathVariable/1
	 * @doc 说明: 返回String，请求带动态参数
	 * @param id 从1开始
	 * @return Mary
	 * @author qiu_hf
	 * @history 2019年9月21日 下午10:16:05 Create by 【qiu_hf】
	 */
	@ApiOperation(value = "返回String，请求带动态参数", notes = "/getUserNameByIdPathVariable/1，作者：qiu_hf")
	@ApiImplicitParam(paramType="path", name = "id", value = "id", required = true, dataType = "Long")
	@RequestMapping(value="/getUserNameByIdPathVariable/{id}", method=RequestMethod.GET)
	public String getUserNameByIdPathVariable(@PathVariable(value="id") long id) {
		return restTmpl.getForEntity("http://star-zone-mobile-common/usage/getUserNameByIdPathVariable/" + id, String.class).getBody();
	}
	
	/**
	 * 3 /getUserNameById?id=1
	 * @doc 说明：返回String拼接参数
	 * @param id
	 * @return Mary
	 * @author qiu_hf
	 * @history 2019年9月21日 下午10:23:02 Create by 【qiu_hf】
	 */
	@ApiOperation(value = "返回String拼接参数", notes = "/getUserNameById?id=1，作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "id", value = "id", required = true, dataType = "Long")
	@RequestMapping(value="/getUserNameById", method=RequestMethod.GET)
	public String getUserNameById(@RequestParam(value="id") long id) {
		return restTmpl.getForEntity("http://star-zone-mobile-common/usage/getUserNameById?id=" + id, String.class).getBody();
	}
	
	/**
	 * 4 /getUserById?id=1
	 * @doc 说明：获取UsageUser对象
	 * @param id
	 * @return {"id":1,"name":"Mary","sex":"Female"}
	 * @author qiu_hf
	 * @history 2019年9月21日 下午10:25:07 Create by 【qiu_hf】
	 */
	@ApiOperation(value = "获取UsageUser对象", notes = "/getUserById?id=1，作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "id", value = "id", required = true, dataType = "Long")
	@RequestMapping(value="/getUserById", method=RequestMethod.GET)
	public UsageUser getUserById(@RequestParam(value="id") long id) {
		return restTmpl.getForEntity("http://star-zone-mobile-common/usage/getUserById?id=" + id, UsageUser.class).getBody();
	}
	
	/**
	 * 5 /getUserListByIds?ids=2,3
	 * @doc 说明: 获取对象集合
	 * @param idStr
	 * @return [{"id":2,"name":"John","sex":"Male"},{"id":3,"name":"王小明","sex":"Male"}]
	 * @author qiu_hf
	 * @history 2019年9月21日 下午10:30:45 Create by 【qiu_hf】
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "获取对象集合", notes = "/getUserListByIds?ids=2,3，作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "ids", value = "ids", required = true, dataType = "String")
	@RequestMapping(value="/getUserListByIds", method=RequestMethod.GET)
	public List<UsageUser> getUserListByIds(@RequestParam(value="ids") String ids) {
		return restTmpl.getForEntity("http://star-zone-mobile-common/usage/getUserListByIds?ids=" + ids, List.class).getBody();
	}
	
	/**
	 * 6 /getUserMapByIds?ids=2,3
	 * @doc 说明：通过ids返回Map
	 * @param idStr
	 * @return {"2":{"id":2,"name":"John","sex":"Male"},"3":{"id":3,"name":"王小明","sex":"Male"}}
	 * @author qiu_hf
	 * @history 2019年9月21日 下午10:39:15 Create by 【qiu_hf】
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "通过ids返回Map", notes = "/getUserMapByIds?ids=2,3，作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "ids", value = "ids", required = true, dataType = "String")
	@RequestMapping(value="/getUserMapByIds", method=RequestMethod.GET)
	public Map<Long, UsageUser> getUserListMapByIds(@RequestParam(value="ids") String ids) {
		return restTmpl.getForEntity("http://star-zone-mobile-common/usage/getUserMapByIds?ids=" + ids, Map.class).getBody();
	}
	
	/**
	 * 7 /getUserById
	 * @doc 说明：获取UsageUser对象
	 * @param id
	 * @return {"id":1,"name":"Mary","sex":"Female"}
	 * @author qiu_hf
	 * @history 2019年9月21日 下午10:25:07 Create by 【qiu_hf】
	 */
	@ApiOperation(value = "获取UsageUser对象", notes = "{\"id\": 1}，作者：qiu_hf")
	@RequestMapping(value="/getUserById", method=RequestMethod.POST)
	public UsageUser getUserById(@RequestBody UsageUser usageUser) {
		return restTmpl.postForEntity("http://star-zone-mobile-common/usage/getUserById", usageUser, UsageUser.class).getBody();
	}
	
	/**
	 * 8 /getUserListByIds?ids=2,3
	 * @doc 说明: 获取对象集合
	 * @param idStr
	 * @return [{"id":2,"name":"John","sex":"Male"},{"id":3,"name":"王小明","sex":"Male"}]
	 * @author qiu_hf
	 * @history 2019年9月21日 下午10:30:45 Create by 【qiu_hf】
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "获取对象集合", notes = "{\"ids\": \"1,2\"}，作者：qiu_hf")
	@RequestMapping(value="/getUserListByIds", method=RequestMethod.POST)
	public List<UsageUser> getUserListByIds(@RequestBody UsageUser usageUser) {
		return restTmpl.postForEntity("http://star-zone-mobile-common/usage/getUserListByIds", usageUser, List.class).getBody();
	}
	
	/**
	 * 9 /getUserMapByIds?ids=2,3
	 * @doc 说明：通过ids返回Map
	 * @param idStr
	 * @return {"2":{"id":2,"name":"John","sex":"Male"},"3":{"id":3,"name":"王小明","sex":"Male"}}
	 * @author qiu_hf
	 * @history 2019年9月21日 下午10:39:15 Create by 【qiu_hf】
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "通过ids返回Map", notes = "{\"ids\": \"1,2\"}，作者：qiu_hf")
	@RequestMapping(value="/getUserMapByIds", method=RequestMethod.POST)
	public Map<Long, UsageUser> getUserMapByIds(@RequestBody UsageUser usageUser) {
		// 直接设置Object request(UsageUser usageUser)
		Map<Long, UsageUser> mapEntity = restTmpl.postForEntity("http://star-zone-mobile-common/usage/getUserMapByIds", usageUser, Map.class).getBody();
		// 设置Object request(Map<String, Object> param)
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", usageUser.getIds());
		Map<Long, UsageUser> mapMapEntity = restTmpl.postForEntity("http://star-zone-mobile-common/usage/getUserMapByIds", param, Map.class).getBody();
		System.out.println(mapEntity.toString() + mapMapEntity.toString());
		// 直接设置Object request(UsageUser usageUser)
		Map<Long, UsageUser> mapObj = restTmpl.postForObject("http://star-zone-mobile-common/usage/getUserMapByIds", usageUser, Map.class);
		// 设置Object request(Map<String, Object> param)
		Map<Long, UsageUser> mapMapObj = restTmpl.postForObject("http://star-zone-mobile-common/usage/getUserMapByIds", param, Map.class);
		System.out.println(mapObj.toString() + mapMapObj.toString());
		return restTmpl.postForEntity("http://star-zone-mobile-common/usage/getUserMapByIds", usageUser, Map.class).getBody();
	}
	
}
