package registration.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 定时类
 **/
public class BasicTimer implements  ServletContextListener{

	public void contextInitialized(ServletContextEvent sce){
		//设置执行时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
        //定制每天的21:09:00执行，
        calendar.set(year, month, day, 03, 00, 00);
        Date date = calendar.getTime();
        Timer timer = new Timer();
        System.out.println(date);
        
        HISTask hisTask = new HISTask();
        //timer.schedule(hisTask, date);
	}
	
	public void contextDestroyed(ServletContextEvent sce) {

	 }
}
