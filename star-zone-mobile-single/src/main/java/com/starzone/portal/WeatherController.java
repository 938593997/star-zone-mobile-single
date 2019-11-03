//package com.starzone.portal;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import com.starzone.utils.JsonResult;
//
///**
// * 调用其他工程接口（weather）
// * @doc 说明
// * @FileName WeatherController.java
// * @author qiu_hf
// * @version 1.0.0
// * @since 2019年9月19日
// * @history 1.0.0.0 2019年9月19日 下午9:36:27 created by【qiu_hf】
// */
//@RestController
//@RequestMapping("/weather")
//public class WeatherController {
//
//	@Autowired
//	RestTemplate restTmpl;
//	
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value="/getWeatherByCityName", method=RequestMethod.GET)
//	public JsonResult<Object> getWeatherByCityName(String cityName) {
//		JsonResult<Object> result = new JsonResult<Object>();
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("cityName", cityName);
//		Map<String, Object> weatherInfo = restTmpl.getForEntity("http://star-zone-mobile-common/weather/getWeatherByCityName?cityName={1}", Map.class, cityName).getBody();
//		result.setMessage("success");
//		result.setCode(1);
//		result.setData(weatherInfo.get("today").toString() + weatherInfo.get("tomorrow") + weatherInfo.get("theDayAfterTomorrow"));
//		return result;
//	}
//	
//	@RequestMapping(value="/first", method=RequestMethod.GET)
//	public String firstService() {
//		return restTmpl.getForEntity("http://star-zone-mobile-common/weather/first", String.class).getBody();
//	}
//}
