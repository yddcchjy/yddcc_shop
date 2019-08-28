package com.ego.ms.task;
import java.util.Date;

import org.springframework.stereotype.Controller;

import com.ego.commons.utils.DateUtils;
import com.ego.commons.utils.HttpClientUtil;

@Controller
public class ProducehtmlTask
{
	public void printUserInfo()
    {
        System.out.println("***      start " + DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss:SSS") + "    *************");

        System.out.println("*");
        System.out.println("*        current username is " + System.getProperty("user.name"));
        System.out.println("*        current os name is " + System.getProperty("os.name"));
        System.out.println("*");

        System.out.println("*********current user information end******************");
    }

	
	public void producehtml(){
		String url = "http://127.0.0.1:8087/producehtml";
        try {
        	String entity = HttpClientUtil.doGet(url);
            System.out.println("ProducehtmlTask=="+entity);
            System.out.println("ProducehtmlTask-----定时任务----- ");
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
}
