package registration.timer;

import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import registration.db.GET_HZJKUtils;
import registration.db.SAVE_HZJKUtils;
import registration.pojo.HZJK.Hospital;

/**
 * 定时执行的任务
 **/
public class HISTask extends TimerTask{
	@SuppressWarnings("unused")
	private ServletContext sce=null;

	 public void setServletContext(ServletContext sce){
	      this.sce = sce;
	 }
	 
	 public HISTask(){
		 
	 }

	public void run() {
		try {
			List<Hospital> hospitalList = GET_HZJKUtils.getHospital();
			System.out.println("=============STRAT GET DATA FROM HIS=============");
			for(Hospital hospital : hospitalList){
				SAVE_HZJKUtils.getHospitalKS(hospital.getCode());
				SAVE_HZJKUtils.getHospitalYS(hospital.getCode());
			}
			System.out.println("=============END GET DATA FROM HIS=============");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
