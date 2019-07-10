package com.yhxc.utils;

import java.util.Calendar;

public class DateCont {
	/** 
	 * 取得当月天数 
	 * */  
	public int getCurrentMonthLastDay()  
	{  
	    Calendar a = Calendar.getInstance();  
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
	    int maxDate = a.get(Calendar.DATE);  
	    return maxDate;  
	}  

	/** 
	 * 取得上个月天数 
	 * */  
	public int getlastMonthDay()  
	{  
	    Calendar a = Calendar.getInstance(); 
	    a.set(Calendar.DAY_OF_MONTH, 1); 
	    a.add(Calendar.DAY_OF_MONTH, -1); 
	    int maxDate = a.getActualMaximum(Calendar.DAY_OF_MONTH);   
	    return maxDate;  
	} 
	/** 
	 * 根据年月查询天数 
	 * */
	 public  int getDaysByYearMonth(int year, int month) {
		  
		         Calendar a = Calendar.getInstance();
		          a.set(Calendar.YEAR, year);
		          a.set(Calendar.MONTH, month - 1);
		          a.set(Calendar.DATE, 1);
		         a.roll(Calendar.DATE, -1);
		          int maxDate = a.get(Calendar.DATE);
		         return maxDate;
		     }
	 
  public static void main(String[] args) {
	  String wtime="2018-02"; 
	  int year = Integer.parseInt( wtime.substring(0,4));
	 System.out.println(year);
	  String months =wtime.substring(5,7);
	  if (months.substring(0, 1).equals("0")) {
		  months = months.substring(1, 2);
		}
	  int month = Integer.parseInt( months);
	  System.out.println(month);
	  DateCont dateCont=new DateCont();
	  System.out.println(dateCont.getDaysByYearMonth(year, month));
}
  
  
  

  
  
}
