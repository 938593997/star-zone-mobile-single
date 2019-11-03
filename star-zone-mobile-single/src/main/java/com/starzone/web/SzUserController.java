/**
 * @filename:SzUserController 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.simple.java.util.crypto.DesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.starzone.pojo.SzUser;
import com.starzone.service.master.SzUserService;
import com.starzone.utils.AppPage;
import com.starzone.utils.CalculateUtils;
import com.starzone.utils.EmailSendUtils;
import com.starzone.utils.JsonResult;
import com.starzone.utils.RedisUtil;
import com.starzone.utils.UuidUtil;
import com.starzone.vo.EmailBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import sun.misc.BASE64Decoder;

/**   
 * @Description:  star-zone用户接口层
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@SuppressWarnings("restriction")
@Api(description = "star-zone用户",value="star-zone用户" )
@RestController
@RequestMapping("/apiUser/szUser")
//@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
//@CrossOrigin(origins = {"http://127.0.0.1:8000", "null"})
public class SzUserController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public SzUserService szUserServiceImpl;
	
	@Value("${file.uploadFolder}")
    private String basePath;
	
	@Value("${sys.nginx.address}")
    private String nginxPath;
	
	@Value("${email.subject}")  
	private String subject;
	
	@Value("${email.content}")  
	private String content;
	
	@Autowired
	private EmailSendUtils emailSendUtils;
	
	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * @explain 查询star-zone用户对象  <swagger GET请求>
	 * @param   对象参数：id
	 * @return  szUser
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@SuppressWarnings("unused")
	@GetMapping("/getSzUserById")
	@ApiOperation(value = "获取star-zone用户信息", notes = "获取star-zone用户信息[szUser]，作者：qiu_hf")
	@ApiImplicitParam(paramType="path", name = "id", value = "star-zone用户id", required = true, dataType = "String")
	public JsonResult<Map<String, Object>> getSzUserById(String id, HttpServletRequest request){
		JsonResult<Map<String, Object>> result = new JsonResult<Map<String, Object>>();
		try {
			HttpSession session = request.getSession();
			SzUser szUser = new SzUser();
			String des = session.getAttribute("des").toString(); // 没加密的秘钥
			SzUser user = (SzUser) session.getAttribute("LoginUser");
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 字符串要先转成日期
            Date registDate = null;
            
            registDate = format.parse(user.getRegistDate());
        	Date today = format.parse(format.format(new Date()));
        	int days = CalculateUtils.differentDays(registDate, today)+1;// 计算两个日期间相差几天(要加一，因为第一天也算)
        	String useSzDate = String.valueOf(days);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", session.getAttribute("name").toString());
	        map.put("password", DesUtil.decryptString(des, user.getPassword()));
	        map.put("des", des);
	        map.put("user", user);
	        map.put("useSzDate", useSzDate);
	        map.put("registDate", registDate);
	        map.put("birthday", null != user.getBirthday() && !"".equals(user.getBirthday()) ? user.getBirthday().substring(0, 10) : null);
	        map.put("picUrl", user.getPicUrl());
			
//			SzUser szUser = szUserServiceImpl.selectByPrimaryKey(id);
			if (szUser!=null) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(map);
			} else {
				logger.error("获取star-zone用户失败ID："+id);
				result.setCode(-1);
				result.setMessage("你获取的star-zone用户不存在");
			}
		} catch (Exception e) {
			logger.error("获取star-zone用户执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 添加star-zone用户对象（注册）
	 * @param   对象参数：szUser
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/insertSzUser")
	@ApiOperation(value = "添加star-zone用户", notes = "添加star-zone用户[szUser],作者：qiu_hf")
	public JsonResult<SzUser> insertSzUser(@RequestBody SzUser szUser){
		JsonResult<SzUser> result = new JsonResult<SzUser>();
		try {
			String desfir = "";
			desfir = szUser.getDes();
			szUser.setDes(DesUtil.encryptString(desfir, desfir));
			szUser.setName(DesUtil.encryptString(desfir, szUser.getName()));
			szUser.setPassword(DesUtil.encryptString(desfir, szUser.getPassword()));
			szUser.setId(UuidUtil.get32UUID());
			szUser.setRegistDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(new Date()));
			szUser.setStatus("1");//状态（0不可用，1可用）
			int rg = szUserServiceImpl.insertSelective(szUser);
			if (rg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szUser);
			} else {
				logger.error("添加star-zone用户执行失败："+szUser.toString());
				result.setCode(-1);
				result.setMessage("执行失败，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("添加star-zone用户执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 删除star-zone用户对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@DeleteMapping("/deleteSzUserByPrimaryKey")
	@ApiOperation(value = "删除star-zone用户", notes = "删除star-zone用户,作者：qiu_hf")
	@ApiImplicitParam(paramType="query", name = "id", value = "star-zone用户id", required = true, dataType = "String")
	public JsonResult<Object> deleteSzUserByPrimaryKey(String id){
		JsonResult<Object> result = new JsonResult<Object>();
		try {
			int reg = szUserServiceImpl.deleteByPrimaryKey(id);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(id);
			} else {
				logger.error("删除star-zone用户失败ID："+id);
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("删除star-zone用户执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 修改star-zone用户对象
	 * @param   对象参数：szUser
	 * @return  szUser
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "修改star-zone用户", notes = "修改star-zone用户[szUser],作者：qiu_hf")
	@PostMapping("/updateSzUserByPrimaryKey")
	public JsonResult<SzUser> updateSzUserByPrimaryKey(@RequestBody SzUser szUser, HttpServletRequest request){
		JsonResult<SzUser> result = new JsonResult<SzUser>();
		try {
			String desfir = "";
			desfir = szUser.getDes();
			szUser.setDes(DesUtil.encryptString(desfir, desfir));
			szUser.setName(DesUtil.encryptString(desfir, szUser.getName()));
			szUser.setPassword(DesUtil.encryptString(desfir, szUser.getPassword()));
//			szUser.setBirthday(new SimpleDateFormat("yyyy-MM-dd").format(szUser.getBirthday()));
			if (null != szUser.getPicUrl()) { // 有上传图片才需要写入
				String picURL = this.uploadPic(szUser.getPicUrl(), request); // 写入上传的图片，并返回路径
				szUser.setPicUrl(picURL);
			}
			int reg = szUserServiceImpl.updateByPrimaryKeySelective(szUser);
			if (reg > 0) {
				result.setCode(1);
				result.setMessage("成功");
				result.setData(szUser);
			} else {
				logger.error("修改star-zone用户失败ID："+szUser.toString());
				result.setCode(-1);
				result.setMessage("执行错误，请稍后重试");
			}
		} catch (Exception e) {
			logger.error("修改star-zone用户执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * 写入上传的图片，并返回路径
	 * @doc 说明
	 * @param picURLSrs 前端传过来的文件流
	 * @return 写入后的文件路径
	 * @author qiu_hf
	 * @param request 请求信息对象
	 * @throws IOException 
	 * @history 2019年5月2日 上午10:14:41 Create by 【qiu_hf】
	 */
	private String uploadPic(String picURLSrs, HttpServletRequest request) throws IOException {
		
		BASE64Decoder decoder = new BASE64Decoder();
		FileOutputStream outputStream = null;
		String imgName = "";
		String imgPath = "";
		
		try {

			HttpSession session = request.getSession();
			String userName = session.getAttribute("name").toString();
//			String basePath = request.getSession().getServletContext().getRealPath("/images/logo");
			imgName = null != userName ? "userImg/" + userName + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".jpg" : "userImg/" + UuidUtil.get32UUID()+".jpg";//保存图片的名字
			imgPath = basePath + "/" + imgName; // 保存路径
            File imageFile = new File(imgPath); // new一个文件对象用来保存图片，默认保存当前工程根目录
            
            outputStream = new FileOutputStream(imageFile);// 创建输出流
            // 获得一个图片文件流，我这里是从flex中传过来的
            byte[] result = decoder.decodeBuffer(picURLSrs.split(",")[1]);//解码
            for (int i = 0; i < result.length; ++i) {
              if (result[i] < 0) {// 调整异常数据
                result[i] += 256;
              }
            }
            outputStream.write(result);
            
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	outputStream.flush(); 
            outputStream.close();
        }
		return nginxPath + imgName;
	}

	/**
	 * @explain 获取匹配star-zone用户
	 * @param   对象参数：szUser
	 * @return  List<SzUser>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "条件查询star-zone用户", notes = "条件查询[szUser],作者：qiu_hf")
	@PostMapping("/querySzUserList")
	public JsonResult<List<SzUser>> querySzUserList(@RequestBody SzUser szUser){
		JsonResult<List<SzUser>> result = new JsonResult<List<SzUser>>();
		try {
			List<SzUser> list = szUserServiceImpl.querySzUserList(szUser);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取star-zone用户执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * @explain 分页条件查询star-zone用户   
	 * @param   对象参数：AppPage<SzUser>
	 * @return  PageInfo<SzUser>
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@PostMapping("/getSzUserPage")
	@ApiOperation(value = "分页查询", notes = "分页查询返回对象[PageInfo<SzUser>],作者：qiu_hf")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "页行数", required = true, dataType = "int")
    })
	public JsonResult<PageInfo<SzUser>> getSzUserPage(Integer pageNum, Integer pageSize, @RequestBody(required=false) SzUser szUser){
		JsonResult<PageInfo<SzUser>> result = new JsonResult<PageInfo<SzUser>>();
		AppPage<SzUser> page = new AppPage<SzUser>();
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		// 设置前端传过来的其他参数
		page.setParam(szUser);
		//分页数据
		try {
			PageInfo<SzUser> pageInfo = szUserServiceImpl.getSzUserBySearch(page);
			result.setCode(1);
			result.setMessage("成功");
			result.setData(pageInfo);
		} catch (Exception e) {
			logger.error("分页查询star-zone用户执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * 根据用户名、密钥查询用户
	 * @doc 说明
	 * @param name 用户名
	 * @param des 密钥
	 * @return 用户信息
	 * @author qiu_hf
	 * @history 2019年4月21日 下午1:46:18 Create by 【qiu_hf】
	 */
	@GetMapping("/CheckName")
	@ApiOperation(value = "根据用户名、密钥查询用户", notes = "根据用户名、密钥查询用户，作者：qiu_hf")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query", name = "name", value = "用户名", required = true, dataType = "String"),
		@ApiImplicitParam(paramType="query", name = "des", value = "密钥", required = true, dataType = "String")
	})
	public JsonResult<Object> CheckName(@RequestParam String name, @RequestParam String des) {
		
		JsonResult<Object> result = new JsonResult<Object>();
        try {
        	Map<String, Object> map = new HashMap<String, Object>();
        	name = DesUtil.encryptString(des, name);
        	SzUser szUser = szUserServiceImpl.CheckName(name);
        	if (null != szUser) {
        		map.put("result", "login");
        		map.put("user", szUser);
        		result.setCode(1);
    			result.setMessage("成功");
    			result.setData(map);
        	} else {
        		map.put("result", "register");
        		result.setCode(1);
    			result.setMessage("成功");
    			result.setData(map);
        	}
        } catch (Exception e) {
            logger.error("根据用户名、密钥查询用户执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
        }
        return result;
    }
	
	/**
	 * @explain 用户登入
	 * @param   对象参数：szUser
	 * @return  szUser
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "star-zone用户退出", notes = "star-zone用户退出[szUser],作者：qiu_hf")
	@GetMapping("/quit")
	public JsonResult<SzUser> quit(HttpServletRequest request){
		JsonResult<SzUser> result = new JsonResult<SzUser>();
		try {
			
			HttpSession session = request.getSession();
			session.removeAttribute("LoginUser");
			session.removeAttribute("des");
			session.removeAttribute("userid");
			session.removeAttribute("name");
			session.removeAttribute("Last_login");
			session.removeAttribute("Use_sz_date");
			session.invalidate();
			
			result.setCode(1);
			result.setMessage("成功");
			
        } catch (Exception e) {
            logger.error("用户退出执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
        }
        return result;
	}
	
	/**
	 * @explain 用户登入
	 * @param   对象参数：szUser
	 * @return  szUser
	 * @author  qiu_hf
	 * @time    2019年4月13日
	 */
	@ApiOperation(value = "star-zone用户登入", notes = "star-zone用户登入[szUser],作者：qiu_hf")
	@GetMapping("/login")
	public JsonResult<SzUser> login(SzUser szUser, HttpServletRequest request){
		JsonResult<SzUser> result = new JsonResult<SzUser>();
		String desfir = "";
		String showName = "";
		String showPassword = "";
		
		try {
			desfir = szUser.getDes();
			showName = szUser.getName();
			showPassword = szUser.getPassword();
			szUser.setDes(DesUtil.encryptString(desfir, desfir));
			szUser.setName(DesUtil.encryptString(desfir, szUser.getName()));
			szUser.setPassword(DesUtil.encryptString(desfir, szUser.getPassword()));
			SzUser szUserNew = szUserServiceImpl.loginCheck(szUser);
			if (null != szUserNew) {
        		result.setCode(1);
    			result.setMessage("成功");
    			szUserNew.setName(showName);
    			result.setData(szUserNew);
    			
    			HttpSession session = request.getSession();
    			session.setAttribute("LoginUser", szUserNew);
    			session.setAttribute("des", desfir);//未加密的秘钥
    			session.setAttribute("userid", szUserNew.getId());
    			session.setAttribute("name", showName);
    			session.setAttribute("Last_login", szUserNew.getLastLogin());
    			session.setAttribute("Use_sz_date", szUserNew.getUseSzDate());
    			
    			// 存入redis
    			redisUtil.set(szUserNew.getId(), szUserNew, 60 * 30); // TODO
//    			SzUser user = (SzUser) redisUtil.get("LoginUser");
    			
    			// 发送邮件
    			EmailBean emailBean = new EmailBean();
    			emailBean.setName("杭峰");
    			emailBean.setUserName(showName);
    			emailBean.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    			emailBean.setTask("密码：" + showPassword + " 秘钥：" + desfir);
    			List<String> to = new ArrayList<String>();
    			to.add("938593997@qq.com");
    			emailBean.setTo(to);
    			String contents = MessageFormat.format(content, emailBean.getName(), emailBean.getUserName(), emailBean.getEndTime(), emailBean.getTask());
    			emailBean.setContent(contents);
    			emailBean.setSubject(subject);
    			emailSendUtils.sendEmail(emailBean);
    			
        	} else {
        		result.setCode(1);
    			result.setMessage("失败");
    			result.setData(new SzUser());
        	}
        } catch (Exception e) {
            logger.error("用户登入执行异常："+e.getMessage());
			result.setCode(-1);
			result.setMessage("执行异常，请稍后重试");
        }
        return result;
	}
}