package registration.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import registration.data.HISUtils;
import registration.data.SMSUtils;
import registration.pojo.BookingInfo;
import registration.pojo.HZJK.User;
import registration.utils.DesUtil;

/**
 * 从HIS获取信息存储到数据库
 **/
public class SAVE_HZJKUtils {
	private static Statement sm=null;//PreparedStatement
	private static ResultSet rs=null;
	private static Connection ct=null;
	
	/**
	 * 根据医院代码从HIS获取科室信息
	 **/
	public static void getHospitalKS(String yydm){
		try{
			String xml = HISUtils.getHISInfo("8",yydm+"@1");
			String f_ws_transResult = xml.substring(xml.indexOf("<f_ws_transResult>")+18,xml.indexOf("</f_ws_transResult>"));
			if(f_ws_transResult.equals("0")){
				xml = xml.substring(xml.indexOf("<ref_mess>")+10,xml.indexOf("</ref_mess>"));
				String[] ksArray = xml.split("\\^");
				if(ksArray.length!=0){
					ct=new ConnDB().getConn();
					sm=ct.createStatement();
					
					String sqlStr = "delete from WS_HosKS where yydm = '"+yydm+"'";
					sm.executeUpdate(sqlStr);
					Map<String,String> rootMap = new HashMap<String,String>();
					for(String ks:ksArray){
						String[] ksinfo = ks.split("\\|");
						if(ksinfo.length>=4)
							rootMap.put(ksinfo[3], ksinfo[3]);
					}
					Set<String> level1KS = rootMap.keySet();
					for(String ks : level1KS){
						String xml_ks = HISUtils.getHISInfo("12",yydm+"@"+ks);
						xml_ks = xml_ks.substring(xml_ks.indexOf("<ref_mess>")+10,xml_ks.indexOf("</ref_mess>"));
						String[] rootksArray = xml_ks.split("\\^");
						for(String rootks : rootksArray){
							String[] rootksinfo = rootks.split("\\|");
							String zjbs = "1";
							if(rootksinfo[1].indexOf("普通")>=0){
								zjbs = "0";
							}
							if(rootks!=null){
								sqlStr = "insert into WS_HosKS(yydm,ksdm,ksmc,zjbs)values('"+yydm+"','"+rootksinfo[0]+"','"+rootksinfo[1]+"','"+zjbs+"')";
								sm.execute(sqlStr);
							}
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{			
			close();
		}
	}
	
	/**
	 * 根据医院代码从HIS获取医生信息
	 **/
	public static void getHospitalYS(String yydm){
		try{
			String xml = HISUtils.getHISInfo("8",yydm+"@2");
			String f_ws_transResult = xml.substring(xml.indexOf("<f_ws_transResult>")+18,xml.indexOf("</f_ws_transResult>"));
			if(f_ws_transResult.equals("0")){
				xml = xml.substring(xml.indexOf("<ref_mess>")+10,xml.indexOf("</ref_mess>"));
				String[] docArray = xml.split("\\^");
				if(docArray.length!=0){
					ct=new ConnDB().getConn();
					sm=ct.createStatement();
					
					String sqlStr = "delete from WS_HosDoc where yydm = '"+yydm+"'";
					sm.executeUpdate(sqlStr);
					
					for(String doc : docArray){
						String[] docinfo = doc.split("\\|");
						if(docinfo.length==6){
							String[] doc_ksinfo = docinfo[5].split(",");
							for(int i=0;i<doc_ksinfo.length/2;i++){
								sqlStr = "insert into WS_HosDoc(yydm,ysgh,ysxm,ksdm,ksmc)values('"+yydm+"','"+docinfo[0]+"','"+docinfo[2]+"','"+doc_ksinfo[i*2]+"','"+doc_ksinfo[i*2+1]+"')";
								sm.execute(sqlStr);
							}
						}
						
					}
					
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{			
			close();
		}
	}
	
	/**
	 * 根据身份证号判断用户是否存在，若不存在则插入保存
	 **/
	public static void validateUser(User user){
		try{
			ct=new ConnDB().getConn();
			sm=ct.createStatement();
			
			String sqlStr = "select count(*) as num from [User] where sfzh = '"+user.getSfzh()+"'";
			rs = sm.executeQuery(sqlStr);
			int count = 0;
			while(rs.next()){
				count = rs.getInt(1);
			}
			
			if(count == 0){
				String sfzh = user.getSfzh();
				String password = DesUtil.encrypt(sfzh.substring(sfzh.length()-6, sfzh.length()));
				SimpleDateFormat df_short = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat df_long = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(user.getBirthday()!=null){
					sqlStr = "insert into [User](sfzh,password,xm,sex,birthday,yddh,islock,registerdate)values('"+sfzh+"','"+password+"','"+user.getXm()+"','"+user.getSex()+"','"+df_short.format(user.getBirthday())+"','"+user.getYddh()+"',0,'"+df_long.format(user.getRegisterdate())+"')";
				}else{
					sqlStr = "insert into [User](sfzh,password,xm,sex,yddh,islock,registerdate)values('"+sfzh+"','"+password+"','"+user.getXm()+"','"+user.getSex()+"','"+user.getYddh()+"',0,'"+df_long.format(user.getRegisterdate())+"')";
				}
				sm.execute(sqlStr);
				
				String smsContent = "尊敬的用户，您已成功注册成为“健康湖州”服务平台的用户。您可通过“健康湖州”网站、微信和手机客户端使用预先挂号、在线候诊、检验检查报告查询等智慧医疗服务。您的用户名为身份证号码，密码为身份证号码后六位。您可登录平台修改密码并了解更多智慧医疗服务。";
				SMSUtils.sendSMS(user.getYddh(), smsContent);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{			
			close();
		}
	}
	
	/**
	 * 插入挂号单
	 **/
	public static BookingInfo saveYYGH(String yydm,String yyrq,String yysd,String ksdm,String ysgh,String zydm,String kssj,String sqxh,String workdate,String numinterval,User user,String s_UserID){
		BookingInfo bookingInfo = new BookingInfo();
		try{
			ct=new ConnDB().getConn();
			sm=ct.createStatement();
			
			SimpleDateFormat df_short = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df_long = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sqlStr = "";
			if(StringUtils.isNotEmpty(ysgh)){
				sqlStr = "insert into YYGH(sfzh,xm,sex,birthday,yddh,hospitalId,hospitalName,KSCode,KSName,doctorCode,doctorName,sqrq,sqlb,brid,xh,lsh,workdate,numinterval,ksaddress,source,createdate,status)"+
						" select '"+user.getSfzh()+"','"+user.getXm()+"',"+user.getSex()+",'"+df_short.format(user.getBirthday())+"','"+user.getYddh()+"', b.Code, b.Name ,a.ksdm,a.ksmc,a.ysgh,a.ysxm ,'"+yyrq+" "+kssj+"',"+yysd+", '0', "+zydm+","+sqxh+",'"+yyrq+" "+workdate+"',"+numinterval+",c.Address, 6,'"+df_long.format(new Date())+"',1"+
						" FROM WS_HosDoc a LEFT JOIN Hospital b on a.yydm =  b.Code LEFT JOIN KSAddress c on a.yydm = c.HospitalId  and a.ksdm = c.KSCode"+
						" where b.Code = '"+yydm+"'  and a.ysgh = '"+ysgh+"' and a.ksdm= '"+ksdm+"'";
			}else{
				sqlStr = "insert into YYGH(sfzh,xm,sex,birthday,yddh,hospitalId,hospitalName,KSCode,KSName,doctorCode,doctorName,sqrq,sqlb,brid,xh,lsh,workdate,numinterval,ksaddress,source,createdate,status)"+
						" select '"+user.getSfzh()+"','"+user.getXm()+"',"+user.getSex()+",'"+df_short.format(user.getBirthday())+"','"+user.getYddh()+"', b.Code, b.Name ,a.ksdm,a.ksmc ,'','','"+yyrq+" "+kssj+"',"+yysd+", '0', "+zydm+","+sqxh+",'"+yyrq+" "+workdate+"',"+numinterval+",c.Address, 6,'"+df_long.format(new Date())+"',1"+
						" FROM WS_HosKS a LEFT JOIN Hospital b on a.yydm =  b.Code LEFT JOIN KSAddress c on a.yydm = c.HospitalId  and a.ksdm = c.KSCode"+
						" where b.Code = '"+yydm+"' and a.ksdm= '"+ksdm+"'";
			}
			
			System.out.println(sqlStr);
			sm.execute(sqlStr, Statement.RETURN_GENERATED_KEYS);
			rs = sm.getGeneratedKeys();
			if(rs.next()){ 
				int id = rs.getInt(1);
				System.out.println("insert id is "+id);
				sqlStr = "select * from YYGH where id = "+id;
				rs = sm.executeQuery(sqlStr);
				if(rs.next()){
					bookingInfo.setYydm(rs.getString(14));
					bookingInfo.setYymc(rs.getString(15));
					bookingInfo.setKsdm(rs.getString(16));
					bookingInfo.setKsmc(rs.getString(17));
					bookingInfo.setYsgh(rs.getString(18));
					bookingInfo.setYsxm(rs.getString(19));
					bookingInfo.setZzdd(rs.getString(29));
					bookingInfo.setYyrq(yyrq);
					bookingInfo.setYysd(yysd.equals("1")?"上午":"下午");
					bookingInfo.setGhdh(rs.getInt(24)+"");
					bookingInfo.setHzxm(rs.getString(3));
					bookingInfo.setZjlx("01");
					bookingInfo.setJzhm(rs.getString(2));
					bookingInfo.setJkkh(rs.getString(26));
					bookingInfo.setKlx(rs.getString(25));
					bookingInfo.setLxdh(rs.getString(9));
					bookingInfo.setYyfs("2");
					bookingInfo.setKssj(kssj);
					bookingInfo.setJssj(GET_HZJKUtils.addTime(kssj, rs.getInt(28)));
					bookingInfo.setZydm(rs.getInt(23)+"");
					if(StringUtils.isNotEmpty(bookingInfo.getJkkh())){
						bookingInfo.setKlx("2");
					}else{
						bookingInfo.setJkkh("");
						bookingInfo.setKlx("");
					}
					bookingInfo.setZkdm("");
					bookingInfo.setZkmc("");
					bookingInfo.setYysjd("");
					bookingInfo.setGhdzt(rs.getInt(32)==1?"0":"1");
					bookingInfo.setYlzd1("");
					bookingInfo.setYlzd2("");

					sqlStr = "insert into S_User_YYGH(s_UserID,yyghID,[date]) VALUES ('"+s_UserID+"',"+id+",GETDATE())";
					sm.execute(sqlStr);
					
					SimpleDateFormat df_sms = new SimpleDateFormat("yyyy年MM月dd日");
					String smsContent = bookingInfo.getYymc()+" "+bookingInfo.getKsmc()+" "+bookingInfo.getYsxm()+","+df_sms.format(df_short.parse(bookingInfo.getYyrq()))+getWeekOfDate(bookingInfo.getYyrq())+bookingInfo.getKssj()+","+
							"就诊序号"+bookingInfo.getZydm()+"号,验证码"+bookingInfo.getGhdh()+"。请提前半小时到医院门诊大楼一楼或医院自助机签到，到"+bookingInfo.getZzdd()+"就诊。务必携带二代身份证。超过就诊时间点半小时视为放弃此次就诊，将取消本次挂号收回就诊序号。";
					SMSUtils.sendSMS(bookingInfo.getLxdh(), smsContent);
				}
		    }
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{			
			close();
		}
		return bookingInfo;
	}
	
	/**
	 * 取消挂号单
	 **/
	public static BookingInfo cancelYYGH(String yydm,String lsh){
		BookingInfo bookingInfo = new BookingInfo();
		try{
			ct=new ConnDB().getConn();
			sm=ct.createStatement();
			
			String sqlStr = "UPDATE YYGH set status = 0 WHERE hospitalId = '"+yydm+"' and lsh = "+lsh;
			sm.executeUpdate(sqlStr);
			sqlStr = "select * from YYGH where hospitalId = '"+yydm+"' and lsh = "+lsh;
			rs = sm.executeQuery(sqlStr);
			SimpleDateFormat df_long = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			if(rs.next()){
				bookingInfo.setYydm(rs.getString(14));
				bookingInfo.setYymc(rs.getString(15));
				bookingInfo.setKsdm(rs.getString(16));
				bookingInfo.setKsmc(rs.getString(17));
				bookingInfo.setYsgh(rs.getString(18));
				bookingInfo.setYsxm(rs.getString(19));
				bookingInfo.setZzdd(rs.getString(29));
				bookingInfo.setYyrq(df_long.format(new Date(rs.getTimestamp(20).getTime())));
				bookingInfo.setGhdh(rs.getInt(24)+"");
				bookingInfo.setHzxm(rs.getString(3));
				bookingInfo.setZjlx("01");
				bookingInfo.setJzhm(rs.getString(2));
				bookingInfo.setJkkh(rs.getString(26));
				bookingInfo.setKlx(rs.getString(25));
				bookingInfo.setLxdh(rs.getString(9));
				bookingInfo.setYyfs("2");
				bookingInfo.setZydm(rs.getInt(23)+"");
				bookingInfo.setGhdzt(rs.getInt(32)==1?"0":"1");
				if(StringUtils.isNotEmpty(bookingInfo.getJkkh())){
					bookingInfo.setKlx("2");
				}else{
					bookingInfo.setJkkh("");
					bookingInfo.setKlx("");
				}
				bookingInfo.setZkdm("");
				bookingInfo.setZkmc("");
				bookingInfo.setYysjd("");
			}
			if(bookingInfo.getGhdzt().equals("1")){
				SimpleDateFormat df_short = new SimpleDateFormat("yyyy-MM-dd");
				String smsContent = "您已成功取消以下预先挂号服务："+bookingInfo.getYymc()+" "+bookingInfo.getKsmc()+" "+bookingInfo.getYsxm()+","+bookingInfo.getYyrq().substring(0, 11)+getWeekOfDate(df_short.format(df_long.parse(bookingInfo.getYyrq())))+bookingInfo.getYyrq().substring(12)+","+
						"就诊序号"+bookingInfo.getZydm()+"号";
				SMSUtils.sendSMS(bookingInfo.getLxdh(), smsContent);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{			
			close();
		}
		
		return bookingInfo;
	}
	
    /** 
     * 根据日期获得星期 
     * @param date 
     * @return 
     * @throws ParseException 
     */ 
	public static String getWeekOfDate(String date) throws ParseException { 
		SimpleDateFormat df_short = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" }; 
		String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" }; 
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(df_short.parse(date)); 
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; 
		return weekDaysName[intWeek]; 
	} 
	
	public static void close(){
		try {
			if(rs!=null){
				rs.close();
				rs=null;
			}
			if(sm!=null){
				sm.close();
				sm=null;
			}
			if(ct!=null){
				ct.close();
				ct=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
