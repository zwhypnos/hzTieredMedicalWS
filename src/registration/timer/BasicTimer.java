package registration.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ��ʱ��
 **/
public class BasicTimer implements  ServletContextListener{

	public void contextInitialized(ServletContextEvent sce){
		//����ִ��ʱ��
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//ÿ��
        //����ÿ���21:09:00ִ�У�
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
