package com.starzone.utils;

import javax.servlet.http.HttpServletRequest;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 拦击器中获取请求参数（token）
 * @doc 说明
 * @FileName RequestHandleUtil.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年9月25日
 * @history 1.0.0.0 2019年9月25日 下午9:34:43 created by【qiu_hf】
 */
public class RequestHandleUtil {
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_GET = "GET";
    public static final String CONTENT_TYPE_JSON = "application/json";

    /**
     * 获取请求参数
     * @param req
     * @return 请求参数格式key-value
     */
    public static Map<String, String> getReqParam(HttpServletRequest req){
        String method = req.getMethod();
        Map<String, String> reqMap = new HashMap<String, String>();
        if(METHOD_GET.equals(method) || METHOD_DELETE.equals(method)){
            reqMap = doGet(req);
        }else if(METHOD_POST.equals(method) || METHOD_PUT.equals(method)){
            reqMap = doPost(req);
        }else{
            return null;//其他请求方式暂不处理
        }
        return reqMap;
    }

    private static Map<String, String> doGet(HttpServletRequest req) {
        String param = req.getQueryString();
        return paramsToMap(param);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String, String> doPost(HttpServletRequest req){
        String contentType = req.getContentType();
        try {
            if (CONTENT_TYPE_JSON.equals(contentType)) {
                StringBuffer sb = new StringBuffer();
                InputStream is = req.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String s = "";
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                }
                String str = sb.toString();
                Map json = (Map)com.alibaba.fastjson.JSON.parse(str);
//                return paramsToMap(str);
                return json;
            } else {
                //其他内容格式的请求暂不处理
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> paramsToMap(String params) {
        Map<String, String> map = new LinkedHashMap<>();
        if (null != params && !"".equals(params)) {
            String[] array = params.split("&");
            for (String pair : array) {
                if ("=".equals(pair.trim())) {
                    continue;
                }
                String[] entity = pair.split("=");
                if (entity.length == 1) {
                    map.put(decode(entity[0]), null);
                } else {
                    map.put(decode(entity[0]), decode(entity[1]));
                }
            }
        }
        return map;
    }

    /**
     * 编码格式转换
     * @param content
     * @return
     */
    public static String decode(String content) {
        try {
            return URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
