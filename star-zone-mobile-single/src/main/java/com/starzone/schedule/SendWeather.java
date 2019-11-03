package com.starzone.schedule;

import java.io.IOException;
//import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.starzone.pojo.SzUser;
import com.starzone.service.master.SzUserService;
import com.starzone.utils.EmailSendUtils;
import com.starzone.vo.EmailBean;

/**
 * Scheduled定时任务发送天气信息
 * @doc 说明
 * @FileName SendWeather.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年9月18日
 * @history 1.0.0.0 2019年9月18日 下午8:41:30 created by【qiu_hf】
 */
@Component
@Configuration
@EnableScheduling
//@PropertySource(value = "classpath:email-config.properties", encoding = "UTF-8")
public class SendWeather {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private EmailSendUtils emailSendUtils;
	
	@Autowired
	public SzUserService szUserServiceImpl;
	
	@Autowired
	RestTemplate restTmpl;
	
	@Value("${email.content.weather}")  
	private String content;
	
	@Value("${email.subject}")
	private String subject;
	
	@SuppressWarnings("unchecked")
	@Scheduled(cron="${cron.schedule}") // 0 * 21 * * ? 在每天21点到下午21:59期间的每1分钟触发，   0 0 6 * * ? 每天早上6点出发
	public void send() throws MessagingException, IOException, ServiceException {
		// 查询用户信息（存在邮箱、姓名跟城市的）
		List<SzUser> userList = szUserServiceImpl.querySzUserList(new SzUser());
		List<SzUser> emailList = new ArrayList<SzUser>();
		for (SzUser user : userList) {
			if (null != user.getExt1() && null != user.getExt2()) {
				emailList.add(user);
				// 获取天气信息然后发送邮件
				EmailBean emailBean = new EmailBean();
//				Map<String, Object> map = this.getCityWeather(user.getExt1());
				Map<String, Object> weatherInfo = restTmpl.getForEntity("http://star-zone-mobile-common/weather/getWeatherByCityName?cityName={1}", Map.class, user.getExt1()).getBody();
				emailBean.setTask(weatherInfo.get("today").toString() + weatherInfo.get("tomorrow") + weatherInfo.get("theDayAfterTomorrow"));
				List<String> to = new ArrayList<String>();
				to.add(user.getExt2());
				emailBean.setTo(to);
				String contents = MessageFormat.format(content, emailBean.getTask());
				emailBean.setContent(contents);
				emailBean.setSubject(subject);
				emailSendUtils.sendEmail(emailBean);
			}
		}
		logger.info("定时天气信息已发送");
	}
	
	/**
	 * 获取城市今、明、后天天气
	 * @doc 说明
	 * @param cityName 城市名 如：深圳
	 * @return 城市天气
	 * @throws ServiceException
	 * @throws RemoteException
	 * @author qiu_hf
	 * @history 2019年9月18日 下午10:47:51 Create by 【qiu_hf】
	 */
//	@SuppressWarnings("unused")
//	private Map<String, Object> getCityWeather(String cityName) throws ServiceException, RemoteException{
//		
//		WeatherWebServiceLocator weatherWebServiceLocator=new WeatherWebServiceLocator();
//	    WeatherWebServiceSoap weatherWebServiceSoap = weatherWebServiceLocator.getWeatherWebServiceSoap();
//	    String[] infos = weatherWebServiceSoap.getWeatherbyCityName(cityName);
//
//	    Map<String, Object> map = new HashMap<String, Object>();
//	    StringBuffer today = new StringBuffer();
//	    today.append(infos[1]).append("\r\n") // 厦门
//	    .append(infos[4]).append("\r\n")      // 2019/9/18 21:40:49
//	    .append(infos[5]).append("\r\n")      // 24℃/33℃
//	    .append(infos[6]).append("\r\n")      // 9月18日 晴转多云
//	    .append(infos[7]).append("\r\n")      // 东北风转北风4-5级
//	    .append(infos[10]).append("\r\n")     // 今日天气实况：气温：26℃；风向/风力：东北风 2级；湿度：48%；紫外线强度：强。空气质量：良。
//	    .append(infos[11]).append("\r\n");    // 紫外线指数：强，涂擦SPF大于15、PA+防晒护肤品。健臻·血糖指数：易波动，风力较大，血糖易波动，注意监测。穿衣指数：炎热，建议穿短衫、短裤等清凉夏季服装。洗车指数：适宜，天气较好，适合擦洗汽车。空气污染指数：良，气象条件有利于空气污染物扩散。
//	    
//	    StringBuffer tomorrow = new StringBuffer();
//	    tomorrow.append(infos[12]).append("\r\n") // 22℃/31℃
//	    .append(infos[13]).append("\r\n")         // 9月19日 多云转晴
//	    .append(infos[14]).append("\r\n");        // 东北风4-5级转3-4级
//	    
//	    StringBuffer theDayAfterTomorrow = new StringBuffer();
//	    theDayAfterTomorrow.append(infos[17]).append("\r\n") // 22℃/32℃
//	    .append(infos[18]).append("\r\n")                    // 9月20日 多云
//	    .append(infos[19]).append("\r\n");                   // 东风转西风3-4级
//	    
//	    map.put("today", today.toString());
//	    map.put("tomorrow", tomorrow.toString());
//	    map.put("theDayAfterTomorrow", theDayAfterTomorrow.toString());
//	    
//		return map;
//	}
}
