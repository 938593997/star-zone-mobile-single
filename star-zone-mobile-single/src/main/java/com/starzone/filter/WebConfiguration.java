package com.starzone.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starzone.config.IpConfig;
import com.starzone.pojo.SzUser;
import com.starzone.utils.RedisUtil;

/**
 * Title: WebConfiguration
 * Description: 
 * 自定义过滤器 
 * Version:1.0.0  
 * @author qiu_hf
 * @date 2019年4月13日
 */
@Configuration
public class WebConfiguration {
    
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private IpConfig ipconfig; 
	
	@Autowired
	private RedisUtil redisUtil;
	
    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        //过滤掉 /getUser 和/hello 的请求
        registration.addUrlPatterns("/api/*");
        //过滤掉所有请求
//        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }
    
    
     class MyFilter implements Filter {
		@Override
		public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain) throws IOException, ServletException {
			HttpServletRequest req = (HttpServletRequest) srequest;
			HttpServletResponse resp = (HttpServletResponse) sresponse;
			//执行过滤操作...
//			logger.info("请求的url :" + request.getRequestURI());
			//检查是否是白名单的IP
//			if(!checkIP(request, response)){
//				return;
//			}
			if(!isLogin(req, resp)){
				return;
			}

            filterChain.doFilter(req, sresponse); // 发现了Spring有一个问题就是： ServletRequest中getReader()和getInputStream()只能调用一次。而又由于@RequestBody注解获取输出参数的方式也是根据流的方式获取的。所以我们前面使用流获取后，后面的@RequestBody就获取不到对应的输入流了
		}

		private boolean isLogin(HttpServletRequest request, HttpServletResponse response) {
//			HttpSession session = request.getSession();
//			SzUser user = (SzUser) session.getAttribute("LoginUser");
			
			String method = request.getMethod();
			String token = null;
			if ("GET".equals(method) || "DELETE".equals(method)) {
				token = request.getParameter("__token__");
			} else if ("POST".equals(method) || "PUT".equals(method)) {
				token = request.getHeader("__token__");
			}
			SzUser user = (SzUser) redisUtil.get(token);
			if (null == user) {
				try {
		              response.setCharacterEncoding("UTF-8");
		              response.setContentType("application/json; charset=utf-8");
		              // 消息
		              Map<String, Object> messageMap = new HashMap<>();
		              messageMap.put("code", -600);
		              ObjectMapper objectMapper = new ObjectMapper();
		              String writeValueAsString;
					  writeValueAsString = objectMapper.writeValueAsString(messageMap);
		              response.getWriter().write(writeValueAsString);
		        	 } catch (Exception e) {
						e.printStackTrace();
					 }
				return false;
			} else { // 如果登入了，用户每请求一次更新其过期时间
				redisUtil.expire(user.getId(), 60 * 30);
			}
			return true;
		}

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
			logger.info("参数初始化:"+filterConfig);
		}
		
		@Override
		public void destroy() {
			logger.info("开始销毁...");
		}
    }
    
     @SuppressWarnings("unused")
	private boolean checkIP(HttpServletRequest request,HttpServletResponse response){
    	  String ip = getIpAddr(request);
          // 获取可以访问系统的白名单
          String ipStr = ipconfig.getIpWhiteList();
          String[] ipArr = ipStr.split("\\|");
          List<String> ipList = Arrays.asList(ipArr);
          if (ipList.contains(ip)) {
//          	 System.out.println("该IP: " + ip+"通过!");
              return true;
          } else {
//              System.out.println("该IP: " + ip+"不通过!");
          	try {
              response.setCharacterEncoding("UTF-8");
              response.setContentType("application/json; charset=utf-8");
              // 消息
              Map<String, Object> messageMap = new HashMap<>();
              messageMap.put("status", "1");
              messageMap.put("message", "您好，ip为" + ip + ",暂时没有访问权限，请联系管理员开通访问权限。");
              ObjectMapper objectMapper=new ObjectMapper();
              String writeValueAsString;
			  writeValueAsString = objectMapper.writeValueAsString(messageMap);
              response.getWriter().write(writeValueAsString);
        	 } catch (Exception e) {
				e.printStackTrace();
			 }
              return false;
          }
     }
     

     /**
      * 获取访问的ip地址
      * 
      * @param request
      * @return
      */
     private  String getIpAddr(HttpServletRequest request) {
         String ip = request.getHeader("X-Forwarded-For");
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getHeader("Proxy-Client-IP");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getHeader("WL-Proxy-Client-IP");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getHeader("HTTP_CLIENT_IP");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getHeader("HTTP_X_FORWARDED_FOR");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getRemoteAddr();
         }
         return ip;
     }
}