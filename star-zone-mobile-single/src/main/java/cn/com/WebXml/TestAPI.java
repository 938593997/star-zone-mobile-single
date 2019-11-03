package cn.com.WebXml;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

/**
 * 天气测试类
 * @doc 说明
 * @FileName TestAPI.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年9月18日
 * @history 1.0.0.0 2019年9月18日 下午9:31:29 created by【qiu_hf】
 */
public class TestAPI {
	
	public static void main(String[] args) throws ServiceException, RemoteException {
		   WeatherWebServiceLocator weatherWebServiceLocator=new WeatherWebServiceLocator();
		   WeatherWebServiceSoap weatherWebServiceSoap = weatherWebServiceLocator.getWeatherWebServiceSoap();
		   String[] infos = weatherWebServiceSoap.getWeatherbyCityName("乌鲁木齐");
		   int i = 0;
		   for(String str:infos){
		       System.out.println(i++ + ": "+ str);
		   }
		    
		   System.exit(0);
		}
}
