package com.starzone.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 计算通用工具类
 * @doc 说明
 * @FileName CalculateUtils.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2017-12-28
 * @history 1.0.0.0 2017-12-28 下午8:54:56 created by【qiu_hf】
 */
public final class CalculateUtils {

	/**
	 * 计算两个日期间相差几天（方式一）date2-date1
	 * @doc 说明
	 * @param date1
	 * @param date2
	 * @return 相差天数
	 * @author qiu_hf
	 * @history 2017-12-28 下午8:57:30 Create by 【qiu_hf】
	 */
	public static int differentDays(Date date1,Date date2){

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
//            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
	}
	
	/**
	 * 计算两个日期间相差几天（方式二）date2-date1
	 * @doc 说明
	 * @param date1
	 * @param date2
	 * @return 相差天数
	 * @author qiu_hf
	 * @history 2017-12-28 下午8:57:30 Create by 【qiu_hf】
	 */
	public static int differentDaysByMillisecond(Date date1,Date date2){
		 
		 int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
	     return days;
	 }
	
}
