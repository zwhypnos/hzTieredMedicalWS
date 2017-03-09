package registration.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import registration.data.HISUtils;
import registration.pojo.BookingInfo;
import registration.pojo.DepartmentInfo;
import registration.pojo.DoctorInfo;
import registration.pojo.NumberSourceInfo;
import registration.pojo.ResourceInfo;
import registration.pojo.HZJK.Hospital;
import registration.pojo.HZJK.User;
import registration.request.DocQuery;
import registration.utils.DesUtil;
import registration.utils.HtmlUtil;

/**
 * 接口数据获取
 **/
public class GET_HZJKUtils {
	
	private static Statement sm=null;//PreparedStatement
	private static ResultSet rs=null;
	private static Connection ct=null;
	
	/**
	 * 获取医院信息
	 **/
	public static List<Hospital> getHospital() throws SQLException{
		List<Hospital> hospitalList =new ArrayList<Hospital>();
		try {
			ct=new ConnDB().getConn();
			sm=ct.createStatement();
			
			String sqlStr="SELECT * FROM Hospital A WHERE A.IsOpen = 1";
			rs=sm.executeQuery(sqlStr);
			
			while(rs.next()){
				Hospital hospital = new Hospital();
				hospital.setCode(rs.getString(1));
				hospital.setName(rs.getString(2));
				hospital.setAddress(rs.getString(5));
				hospital.setPhone1(rs.getString(6));
				hospital.setPhone2(rs.getString(7));
				hospital.setIntroduce(rs.getString(8));
				hospital.setShortname(rs.getString(12));
				hospitalList.add(hospital);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
			close();
		}
		return hospitalList;
	}
	
	/**
	 * 获取科室信息
	 **/
	public static List<DepartmentInfo> getDepartmentInfo(String yydm){
		List<DepartmentInfo> departmentInfoList =new ArrayList<DepartmentInfo>();
		try {
			ct=new ConnDB().getConn();
			sm=ct.createStatement();
			String sqlStr="select b.Code,b.Name,a.ksdm,a.ksmc,c.Address,c.Introduce, a.zjbs from WS_HosKS a left join Hospital b on a.yydm = b.code left join KSAddress c on a.ksdm = c.kscode and a.yydm = c.HospitalId where b.Code='"+yydm+"'";
			rs=sm.executeQuery(sqlStr);
			while(rs.next()){
				DepartmentInfo departmentInfo = new DepartmentInfo();
				//System.out.println(rs.getString(3)+rs.getString(5));
				departmentInfo.setYydm(rs.getString(1));
				departmentInfo.setYymc(rs.getString(2));
				departmentInfo.setKsdm(rs.getString(3));
				departmentInfo.setKsmc(rs.getString(4));
				departmentInfo.setKsdz(rs.getString(5));
				departmentInfo.setZxdh("");
				departmentInfo.setKsjj(rs.getString(6));
				departmentInfo.setWsjksdm("");
				departmentInfo.setScbz("0");
				departmentInfo.setBzw("1");
				departmentInfo.setZjbs(rs.getString(7));
				if(departmentInfo.getKsdz()==null){
					departmentInfo.setKsdz("");
				}
				departmentInfoList.add(departmentInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
			close();
		}
		return departmentInfoList;
	}
	
	/**
	 * 获取医生信息
	 **/
	public static List<DoctorInfo> getDoctorInfo(DocQuery query){
		List<DoctorInfo> doctorInfoList = new ArrayList<DoctorInfo>();
		try {
			ct=new ConnDB().getConn();
			sm=ct.createStatement();
			String sqlStr="select a.ysgh,a.ysxm,c.Sex,a.yydm,b.Name,a.ksdm,a.ksmc,c.Positional,c.Face,c.Introduce from WS_HosDoc a LEFT JOIN Hospital b on a.yydm = b.Code LEFT JOIN Doctor c on a.yydm = c.HospitalId and a.ysgh = c.Code where b.Code='"+query.getYydm()+"'";
			if(StringUtils.isNotEmpty(query.getYsgh())&&StringUtils.isEmpty(query.getKsdm()))
				sqlStr += "and a.ysgh = '"+query.getYsgh()+"'";
			if(StringUtils.isEmpty(query.getYsgh())&&StringUtils.isNotEmpty(query.getKsdm()))
				sqlStr += "and a.ksdm = '"+query.getKsdm()+"'";
			if(StringUtils.isNotEmpty(query.getYsgh())&&StringUtils.isNotEmpty(query.getKsdm()))
				sqlStr += "and a.ysgh = '"+query.getYsgh()+"' and a.ksdm = '"+query.getKsdm()+"'";
			rs=sm.executeQuery(sqlStr);
			
			while(rs.next()){
				DoctorInfo doctorInfo = new DoctorInfo();
				doctorInfo.setYsgh(rs.getString(1));
				doctorInfo.setYsxm(rs.getString(2));
				doctorInfo.setXb(rs.getString(3));
				doctorInfo.setCsrq("");
				doctorInfo.setKsgznf("");
				doctorInfo.setYydm(rs.getString(4));
				doctorInfo.setYymc(rs.getString(5));
				doctorInfo.setKsdm(rs.getString(6));
				doctorInfo.setKsmc(rs.getString(7));
				doctorInfo.setZkdm("");
				doctorInfo.setZkmc("");
				String zc = rs.getString(8);
				doctorInfo.setZc(zc);
				if(zc==null){
					doctorInfo.setZc("");
				}else{
					if(zc.equals("主任医师")){doctorInfo.setZcdm("23231");}
					else if(zc.equals("副主任医师")){doctorInfo.setZcdm("23232");}
					else if(zc.equals("主治医师")){doctorInfo.setZcdm("23233");}
					else if(zc.equals("医师")){doctorInfo.setZcdm("23234");}
					else{doctorInfo.setZcdm("23235");}
				}
				doctorInfo.setZw("");
				doctorInfo.setXldm("");
				doctorInfo.setXl("");
				doctorInfo.setZplj(rs.getString(9));
				doctorInfo.setYsjj(HtmlUtil.trimHtml2Txt(rs.getString(10), null));
				doctorInfo.setGhf("");
				doctorInfo.setTc("");
				
				if(doctorInfo.getXb()==null){
					doctorInfo.setXb("");
				}else if(doctorInfo.getXb().equals("男")){
					doctorInfo.setXb("1");
				}else{
					doctorInfo.setXb("2");
				}
				
				if(doctorInfo.getZplj()==null||doctorInfo.getZplj().equals("")){
					doctorInfo.setZplj("");
				}else{
					doctorInfo.setZplj("http://www.hzjk.com"+doctorInfo.getZplj());
				}
				
				if(doctorInfo.getYsjj()==null){
					doctorInfo.setYsjj("");
				}
				
				doctorInfoList.add(doctorInfo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
			close();
		}
		return doctorInfoList;
	}
	
	/**
	 * 获取排班信息
	 **/
	public static List<ResourceInfo> getResourceInfo(String yydm,String ksdm,String ysgh,String yyrq,String zjbs){
		List<ResourceInfo> resourceInfoList = new ArrayList<ResourceInfo>();
		try {
			ct=new ConnDB().getConn();
			sm=ct.createStatement();
			String sqlStr="select Name from Hospital where Code = '"+yydm+"'";
			rs=sm.executeQuery(sqlStr);
			String yymc = "";
			while(rs.next()){
				yymc = rs.getString(1);
			}
			String mess = "";
			if(StringUtils.isNotEmpty(yyrq)){
				if(StringUtils.isNotEmpty(ksdm)){
					mess = yydm+"@"+yyrq+"@"+yyrq+"@1@"+ksdm;
				}else if(StringUtils.isEmpty(ksdm)&&StringUtils.isNotEmpty(ysgh)){
					mess = yydm+"@"+yyrq+"@"+yyrq+"@2@"+ysgh;
				}else{
					mess = yydm+"@"+yyrq+"@"+yyrq+"@4@0";
				}
			}else{
				Date now = new Date();
				SimpleDateFormat df_long = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat df_short = new SimpleDateFormat("yyyy-MM-dd");
				String now_short_str = df_short.format(now);
				Date _4clock = df_long.parse(now_short_str+" 16:00:00");
				Date now_short = df_short.parse(now_short_str);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(now_short);
				if(now.getTime()<=_4clock.getTime()){
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					Date begin = calendar.getTime();
					String begin_str = df_short.format(begin);
					System.out.println(begin_str);
					calendar.add(Calendar.DAY_OF_MONTH, 6);
					Date end = calendar.getTime();
					String end_str = df_short.format(end);
					System.out.println(end_str);
					
					if(StringUtils.isNotEmpty(ksdm)){
						mess = yydm+"@"+begin_str+"@"+end_str+"@1@"+ksdm;
					}else if(StringUtils.isEmpty(ksdm)&&StringUtils.isNotEmpty(ysgh)){
						mess = yydm+"@"+begin_str+"@"+end_str+"@2@"+ysgh;
					}else{
						mess = yydm+"@"+begin_str+"@"+end_str+"@4@0";
					}
				}else{
					calendar.add(Calendar.DAY_OF_MONTH, 2);
					Date begin = calendar.getTime();
					String begin_str = df_short.format(begin);
					System.out.println(begin_str);
					calendar.add(Calendar.DAY_OF_MONTH, 6);
					Date end = calendar.getTime();
					String end_str = df_short.format(end);
					System.out.println(end_str);
					if(StringUtils.isNotEmpty(ksdm)){
						mess = yydm+"@"+begin_str+"@"+end_str+"@1@"+ksdm;
					}else if(StringUtils.isEmpty(ksdm)&&StringUtils.isNotEmpty(ysgh)){
						mess = yydm+"@"+begin_str+"@"+end_str+"@2@"+ysgh;
					}else{
						mess = yydm+"@"+begin_str+"@"+end_str+"@4@0";
					}
				}
			}
			
			String xml = HISUtils.getHISInfo("6",mess);
			String f_ws_transResult = xml.substring(xml.indexOf("<f_ws_transResult>")+18,xml.indexOf("</f_ws_transResult>"));
			if(f_ws_transResult.equals("0")){
				xml = xml.substring(xml.indexOf("<ref_mess>")+10,xml.indexOf("</ref_mess>"));
				String[] schArray = xml.split("\\^");
				if(schArray.length!=0){
					for(String sch : schArray){
						String[] schinfo = sch.split("\\|");
						ResourceInfo resourceInfo = new ResourceInfo();
						resourceInfo.setYydm(yydm);
						resourceInfo.setYymc(yymc);
						resourceInfo.setYyrq(schinfo[0]);
						resourceInfo.setKsdm(schinfo[2]);
						resourceInfo.setKsmc(schinfo[3]);
						resourceInfo.setZjbs(schinfo[4].equals("1")?"0":"1");
						resourceInfo.setYsgh(schinfo[5]);
						resourceInfo.setYsxm(schinfo[6]);
						resourceInfo.setZylx("");
						resourceInfo.setJssj("");
						resourceInfo.setZjdd("");
						resourceInfo.setZjmh("");
						resourceInfo.setZkdm("");
						resourceInfo.setZkmc("");
						resourceInfo.setYyzys("");
						resourceInfo.setFlag("0");
						if(StringUtils.isNotEmpty(zjbs)){
							if(zjbs.equals("1")){
								if(!schinfo[4].equals("1")){
									setYysdToAdd(resourceInfoList,resourceInfo,schinfo[7]);
								}
							}else if(zjbs.equals("0")){
								if(schinfo[4].equals("1")){
									setYysdToAdd(resourceInfoList,resourceInfo,schinfo[7]);
								}
							}else{
								setYysdToAdd(resourceInfoList,resourceInfo,schinfo[7]);
							}
						}else{
							setYysdToAdd(resourceInfoList,resourceInfo,schinfo[7]);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
			close();
		}
		List<ResourceInfo> resourceInfoList2 = new ArrayList<ResourceInfo>();
		for(ResourceInfo resource : resourceInfoList){
			List<ResourceInfo> resourceInfoList3 = getNumberSourceInfo(yydm, resource.getYyrq(), resource.getKsdm(), resource.getYsgh());
			resourceInfoList2.addAll(resourceInfoList3);
		}
		
		return resourceInfoList2;
	}
	
	public static void setYysdToAdd(List<ResourceInfo> resourceInfoList,ResourceInfo resourceInfo,String sqlb){
		if(sqlb.equals("0")){
			ResourceInfo resourceInfo2 = new ResourceInfo();
			BeanUtils.copyProperties(resourceInfo, resourceInfo2);
			resourceInfo.setYysd("上午");
			String zzdm = resourceInfo.getYyrq().replaceAll("-", "")+"1"+resourceInfo.getKsdm()+resourceInfo.getYsgh();
			resourceInfo.setZydm(zzdm);
			resourceInfo.setKssj("08:30");
			resourceInfoList.add(resourceInfo);
			resourceInfo2.setYysd("下午");
			zzdm = resourceInfo2.getYyrq().replaceAll("-", "")+"2"+resourceInfo2.getKsdm()+resourceInfo2.getYsgh();
			resourceInfo2.setZydm(zzdm);
			resourceInfo2.setKssj("13:30");
			resourceInfoList.add(resourceInfo2);
		}else{
			resourceInfo.setYysd(sqlb.equals("1")?"上午":"下午");
			String zzdm = resourceInfo.getYyrq().replaceAll("-", "")+sqlb+resourceInfo.getKsdm()+resourceInfo.getYsgh();
			resourceInfo.setZydm(zzdm);
			resourceInfo.setKssj("08:30");
			resourceInfoList.add(resourceInfo);
		}
	}
	
	/**
	 * 申请号源信息
	 **/
	public static List<ResourceInfo> getNumberSourceInfo(String yydm,String yyrq,String ksdm,String ysgh){
		List<ResourceInfo> resourceInfoList = new ArrayList<ResourceInfo>();
		//List<NumberSourceInfo> numberSourceInfoList = new ArrayList<NumberSourceInfo>();
		try {
			ct=new ConnDB().getConn();
			sm=ct.createStatement();
			String yymc = "";
			String ksmc = "";
			String ysxm = "";
			if(StringUtils.isNotEmpty(ysgh)){
				String sqlStr="select a.Name,c.ksmc,b.ysxm from Hospital a LEFT JOIN WS_HosDoc b ON a.Code = b.yydm LEFT JOIN WS_HosKS c ON b.ksdm = c.ksdm where a.Code = '"+yydm+"' and b.ksdm = '"+ksdm+"' and b.ysgh = '"+ysgh+"'";
				rs=sm.executeQuery(sqlStr);
				if(rs.next()){
					yymc = rs.getString(1);
					ksmc = rs.getString(2);
					ysxm = rs.getString(3);
				}
			}else{
				String sqlStr="select a.Name,c.ksmc from Hospital a LEFT JOIN WS_HosKS b ON a.Code = b.yydm LEFT JOIN WS_HosKS c ON b.ksdm = c.ksdm where a.Code = '"+yydm+"' and b.ksdm = '"+ksdm+"'";
				rs=sm.executeQuery(sqlStr);
				if(rs.next()){
					yymc = rs.getString(1);
					ksmc = rs.getString(2);
				}
			}
			
			//上午
			String xml = HISUtils.getHISInfo("7",yydm+"@"+yyrq+"@1@"+ksdm+"@"+ysgh+"@0@1");
			String f_ws_transResult = xml.substring(xml.indexOf("<f_ws_transResult>")+18,xml.indexOf("</f_ws_transResult>"));
			if(f_ws_transResult.equals("0")){
				xml = xml.substring(xml.indexOf("<ref_mess>")+10,xml.indexOf("</ref_mess>"));
				String[] schArray = xml.split("@");
				Integer totalCount = Integer.valueOf(schArray[2]);
				String start_str = schArray[4];
				Integer interval = Integer.valueOf(schArray[5]);
				Map<String,String> totalMap = new HashMap<String,String>();
				for(int i=0;i<totalCount;i++){
					totalMap.put(i+1+"", addTime(start_str,i*interval));
				}
				String[] usedNum = schArray[3].split("\\|");
				List<String> numlist=Arrays.asList(usedNum);
				for(String key : totalMap.keySet()){
					if(!numlist.contains(key)){
						ResourceInfo  resourceInfo = new ResourceInfo();
						resourceInfo.setYydm(yydm);
						resourceInfo.setYymc(yymc);
						resourceInfo.setYyrq(yyrq);
						resourceInfo.setKsdm(ksdm);
						resourceInfo.setKsmc(ksmc);
						resourceInfo.setYsgh(ysgh);
						resourceInfo.setYsxm(ysxm);
						resourceInfo.setYysd("上午");
						resourceInfo.setZydm(yydm+"|"+ksdm+"|"+ysgh+"|"+yyrq+"|1|"+totalMap.get(key)+"|"+key);
						resourceInfo.setKssj(totalMap.get(key));
						resourceInfo.setJssj(addTime(totalMap.get(key), interval));
						resourceInfo.setYyzys("1");
						resourceInfo.setFlag("");
						resourceInfo.setZylx("");
						resourceInfo.setZjdd("");
						resourceInfo.setZjmh("");
						resourceInfo.setZkdm("");
						resourceInfo.setZkmc("");
						resourceInfo.setZjbs("");
						resourceInfoList.add(resourceInfo);
					}
				}
			}
			
			//下午
			xml = HISUtils.getHISInfo("7",yydm+"@"+yyrq+"@2@"+ksdm+"@"+ysgh+"@0@1");
			f_ws_transResult = xml.substring(xml.indexOf("<f_ws_transResult>")+18,xml.indexOf("</f_ws_transResult>"));
			if(f_ws_transResult.equals("0")){
				xml = xml.substring(xml.indexOf("<ref_mess>")+10,xml.indexOf("</ref_mess>"));
				String[] schArray = xml.split("@");
				Integer totalCount = Integer.valueOf(schArray[2]);
				String start_str = schArray[4];
				Integer interval = Integer.valueOf(schArray[5]);
				Map<String,String> totalMap = new HashMap<String,String>();
				for(int i=0;i<totalCount;i++){
					totalMap.put(i+1+"", addTime(start_str,i*interval));
				}
				String[] usedNum = schArray[3].split("\\|");
				List<String> numlist=Arrays.asList(usedNum);
				for(String key : totalMap.keySet()){
					if(!numlist.contains(key)){
						ResourceInfo  resourceInfo = new ResourceInfo();
						resourceInfo.setYydm(yydm);
						resourceInfo.setYymc(yymc);
						resourceInfo.setYyrq(yyrq);
						resourceInfo.setKsdm(ksdm);
						resourceInfo.setKsmc(ksmc);
						resourceInfo.setYsgh(ysgh);
						resourceInfo.setYsxm(ysxm);
						resourceInfo.setYysd("下午");
						resourceInfo.setZydm(yydm+"|"+ksdm+"|"+ysgh+"|"+yyrq+"|2|"+totalMap.get(key)+"|"+key);
						resourceInfo.setKssj(totalMap.get(key));
						resourceInfo.setJssj(addTime(totalMap.get(key), interval));
						resourceInfo.setYyzys("1");
						resourceInfo.setFlag("");
						resourceInfo.setZylx("");
						resourceInfo.setZjdd("");
						resourceInfo.setZjmh("");
						resourceInfo.setZkdm("");
						resourceInfo.setZkmc("");
						resourceInfo.setZjbs("");
						resourceInfoList.add(resourceInfo);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
			close();
		}
		
		return resourceInfoList;
	}
	
	public static String addTime(String start_str,Integer interval) throws ParseException{
		SimpleDateFormat df_Hm = new SimpleDateFormat("HH:mm");
		Date start = df_Hm.parse(start_str);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.add(Calendar.MINUTE, interval);
		return df_Hm.format(calendar.getTime());
	}
	
	/**
	 * 确认号源
	 **/
	public static BookingInfo getBookingInfo(String yydm,String yyrq,String yysd,String ksdm,String ysgh,String zydm,String kssj,User user,String s_UserID){
		BookingInfo bookingInfo = new BookingInfo();
		try {
			SAVE_HZJKUtils.validateUser(user);
			SimpleDateFormat df_short = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(yydm+"@"+yyrq+"@"+yysd+"@"+ksdm+"@"+ysgh+"@"+zydm+"@0@"+user.getXm()+"|"+(user.getSex().equals("1")?"男":"女")+"|"+df_short.format(user.getBirthday())+"||||"+user.getYddh()+"|||@1|身份证|"+user.getSfzh());
			String xml = HISUtils.getHISInfo("9",yydm+"@"+yyrq+"@"+yysd+"@"+ksdm+"@"+ysgh+"@"+zydm+"@0@"+user.getXm()+"|"+(user.getSex().equals("1")?"男":"女")+"|"+df_short.format(user.getBirthday())+"||||"+user.getYddh()+"|||@1|身份证|"+user.getSfzh());
			String f_ws_transResult = xml.substring(xml.indexOf("<f_ws_transResult>")+18,xml.indexOf("</f_ws_transResult>"));
			if(f_ws_transResult.equals("0")){
				String sqxh = xml.substring(xml.indexOf("<ref_mess>")+10,xml.indexOf("</ref_mess>"));
				System.out.println(sqxh);
				String ref_mess[] = sqxh.split("@");
				String lsh = ref_mess[0];
				String workdate = ref_mess[1];
				String numinterval = ref_mess[2];
				bookingInfo =SAVE_HZJKUtils.saveYYGH(yydm, yyrq, yysd, ksdm, ysgh, zydm, kssj, lsh, workdate, numinterval, user,s_UserID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookingInfo;
	}
	
	/**
	 * 预约号源退号
	 **/
	public static Boolean cancelBooking(String yydm,String ghdh){
		Boolean result = false;
		try {
			String xml = HISUtils.getHISInfo("11",yydm+"@"+ghdh+"@");
			String f_ws_transResult = xml.substring(xml.indexOf("<f_ws_transResult>")+18,xml.indexOf("</f_ws_transResult>"));
			if(f_ws_transResult.equals("0")){
				BookingInfo bookingInfo = new BookingInfo();
				bookingInfo = SAVE_HZJKUtils.cancelYYGH(yydm, ghdh);
				if(StringUtils.isNotEmpty(bookingInfo.getGhdh())){
					System.out.println(bookingInfo.getGhdh());
				}
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 预约单查询
	 **/
	public static List<BookingInfo> queryBooking(String sfzh,String kssj,String jssj){
		List<BookingInfo> BookingInfoList = new ArrayList<BookingInfo>();
		try {
			ct=new ConnDB().getConn();
			sm=ct.createStatement();
			
			String sqlStr="";
			if(StringUtils.isNotEmpty(kssj)&&StringUtils.isNotEmpty(jssj)){
				sqlStr="SELECT * FROM YYGH WHERE sfzh = '"+sfzh+"' and CONVERT(varchar(100), sqrq, 23) >= '"+kssj+"' and CONVERT(varchar(100), sqrq, 23)<= '"+jssj+"' ORDER BY sqrq DESC";
			}else{
				sqlStr="SELECT * FROM YYGH WHERE sfzh = '"+sfzh+"' ORDER BY sqrq DESC";
			}
			rs=sm.executeQuery(sqlStr);
			SimpleDateFormat df_Yyrq = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df_Kssj = new SimpleDateFormat("HH:mm");
			while(rs.next()){
				BookingInfo bookingInfo = new BookingInfo();
				bookingInfo.setYydm(rs.getString(14));
				bookingInfo.setYymc(rs.getString(15));
				bookingInfo.setKsdm(rs.getString(16));
				bookingInfo.setKsmc(rs.getString(17));
				bookingInfo.setYsgh(rs.getString(18));
				bookingInfo.setYsxm(rs.getString(19));
				bookingInfo.setZzdd(rs.getString(29));
				bookingInfo.setYyrq(df_Yyrq.format(new Date(rs.getTimestamp(20).getTime())));
				bookingInfo.setYysd(rs.getInt(21)==1?"上午":"下午");
				bookingInfo.setGhdh(rs.getInt(24)+"");
				bookingInfo.setHzxm(rs.getString(3));
				bookingInfo.setZjlx("01");
				bookingInfo.setJzhm(rs.getString(2));
				bookingInfo.setJkkh(rs.getString(26));
				bookingInfo.setKlx(rs.getString(25));
				bookingInfo.setLxdh(rs.getString(9));
				bookingInfo.setYyfs("2");
				bookingInfo.setKssj(df_Kssj.format(new Date(rs.getTimestamp(20).getTime())));
				bookingInfo.setJssj(addTime(bookingInfo.getKssj(), rs.getInt(28)));
				bookingInfo.setGhdzt(rs.getInt(32)==1?"0":"1");
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
				bookingInfo.setZYBZ("");
				bookingInfo.setYlzd1("");
				bookingInfo.setYlzd2("");
				BookingInfoList.add(bookingInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
			close();
		}
		return BookingInfoList;
	}
	
	/**
	 * 验证组织用户
	 **/
	public static Boolean validateS_User(String userid,String password){
		Boolean result = false;
		try{
			ct=new ConnDB().getConn();
			sm=ct.createStatement();
			
			String sqlStr = "select count(*) as num from S_User where userCode  = '"+userid+"' and password = '"+DesUtil.encrypt(password)+"'";
			rs = sm.executeQuery(sqlStr);
			int count = 0;
			if(rs.next()){
				count = rs.getInt(1);
			}
			if(count!=0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
			close();
		}
		return result;
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
