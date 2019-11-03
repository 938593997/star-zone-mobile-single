package com.starzone.utils;

import java.io.IOException;
import java.security.Security;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.starzone.vo.EmailBean;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

/**
 * 邮件发送工具类
 * @doc 说明
 * @FileName EmailSendUtils.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年9月17日
 * @history 1.0.0.0 2019年9月17日 下午8:12:11 created by【qiu_hf】
 */
@Component
@PropertySource(value = "classpath:email-config.properties", encoding = "UTF-8")
public class EmailSendUtils {

	@Value("${email.host}")  
	private String host;
	
	@Value("${email.username}")  
	private String username;
	
	@Value("${email.password}")  
	private String password;
	
	@Value("${email.from}")  
	private String from;
	
	/**
	 * 发送邮件
	 * @doc 说明  传过来的收件人邮箱是List<String>
	 * @param emailBean 有用户名、List<String> to 收件人、任务及截止时间
	 * @throws MessagingException
	 * @throws IOException
	 * @author qiu_hf
	 * @history 2019年9月17日 下午8:55:15 Create by 【qiu_hf】
	 */
	public void sendEmail(EmailBean emailBean) throws MessagingException, IOException {
		
		Session session = this.createSession();
//		String contents = MessageFormat.format(content, emailBean.getName(), emailBean.getUserName(), emailBean.getEndTime(), emailBean.getTask());
		Mail mail = new Mail();
		if (null != emailBean.getTo() && emailBean.getTo().size() > 0) {
			for (String tos : emailBean.getTo()) {
				mail.addToAddress(tos);
			}
		}
		mail.setFrom(from);
		mail.setSubject(emailBean.getSubject());
		mail.setContent(emailBean.getContent());
		MailUtils.send(session, mail); // 发送邮件
	}
	
	/**
	 * 创建发送邮件session信息
	 * @doc 说明
	 * @param host 主机名
	 * @param username 用户名
	 * @param password 密码
	 * @return session信息
	 * @author qiu_hf
	 * @history 2019年9月17日 下午8:25:17 Create by 【qiu_hf】
	 */
	@SuppressWarnings("restriction")
	private Session createSession() {
		
		Properties prop = new Properties();
		prop.setProperty("mail.host", host);// 设置主机名
		prop.setProperty("mail.smtp.auth", "true");//是否需要身份验证   
		
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        //阿里云服务器吧25端口禁了，端口不改成465项目部署到服务器就发不了邮件
        prop.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.smtp.socketFactory.port", "465");
//        prop.setProperty("mail.smtp.port", "587");
//        prop.setProperty("mail.smtp.socketFactory.port", "587");

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		return Session.getInstance(prop, auth);
	}
}
