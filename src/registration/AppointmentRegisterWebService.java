package registration;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.headers.Header;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import registration.db.GET_HZJKUtils;
import registration.pojo.BookingInfo;
import registration.pojo.DepartmentInfo;
import registration.pojo.DoctorInfo;
import registration.pojo.ResourceInfo;
import registration.pojo.HZJK.Hospital;
import registration.pojo.HZJK.User;
import registration.request.BookQuery;
import registration.request.BookingForm;
import registration.request.Cancel;
import registration.request.DepQuery;
import registration.request.DocQuery;
import registration.request.ResQuery;
import registration.result.BookResult;
import registration.result.BookResultInfo;
import registration.result.DepResult;
import registration.result.DepResultInfo;
import registration.result.DocResult;
import registration.result.DocResultInfo;
import registration.result.HosResult;
import registration.result.QueryResult;
import registration.result.ResResult;
import registration.result.HosResultInfo;
import registration.result.ResResultInfo;

@WebService(targetNamespace="yypt")
public class AppointmentRegisterWebService {
	@Resource
	private WebServiceContext context;
	
	private String userid = "";
	/**
	 * 医院字典
	 **/
	@WebMethod
	@WebResult(name="Root")
	public HosResult getHospitals(){
		HosResult rs = new HosResult();
		if(isPass()){
			List<Hospital> hospitalList = new ArrayList<Hospital>();
			try {
				hospitalList = GET_HZJKUtils.getHospital();
				HosResultInfo result = new HosResultInfo();
				result.setSuccess("true");
				result.setError("");
				result.setDatas(hospitalList);
				rs.setResultInfo(result);
			} catch (SQLException e) {
				HosResultInfo result = new HosResultInfo();
				result.setSuccess("false");
				result.setError("数据获取失败");
				rs.setResultInfo(result);
				e.printStackTrace();
			}
		}else{
			HosResultInfo result = new HosResultInfo();
			result.setSuccess("false");
			result.setError("头信息不正确,请传入正确的用户名和密码");
			rs.setResultInfo(result);
		}
		return rs;
	}

	
	/**
	 * 科室字典
	 **/
	@WebMethod
	@WebResult(name="Root")
	public DepResult getDepartmentInfo(@WebParam(name = "Query")DepQuery query){
		DepResult rs = new DepResult();
		if(isPass()){
			if(StringUtils.isNotEmpty(query.getYydm())){
				List<DepartmentInfo> departmentInfoList = new ArrayList<DepartmentInfo>();
				try {
					departmentInfoList = GET_HZJKUtils.getDepartmentInfo(query.getYydm());
					DepResultInfo result = new DepResultInfo();
					result.setSuccess("true");
					result.setError("");
					result.setDatas(departmentInfoList);
					rs.setResultInfo(result);
				} catch (Exception e) {
					DepResultInfo result = new DepResultInfo();
					result.setSuccess("false");
					result.setError("数据获取失败");
					rs.setResultInfo(result);
					e.printStackTrace();
				}
			}else{
				DepResultInfo result = new DepResultInfo();
				result.setSuccess("false");
				result.setError("字段yydm不可为空");
				rs.setResultInfo(result);
			}
		}else{
			DepResultInfo result = new DepResultInfo();
			result.setSuccess("false");
			result.setError("头信息不正确,请传入正确的用户名和密码");
			rs.setResultInfo(result);
		}
		return rs;
	}
	
	/**
	 * 医生字典
	 **/
	@WebMethod
	@WebResult(name="Root")
	public DocResult getDoctorInfo(@WebParam(name = "Query")DocQuery query){
		DocResult rs = new DocResult();
		if(isPass()){
			if(StringUtils.isNotEmpty(query.getYydm())){
				List<DoctorInfo> doctorInfoList = new ArrayList<DoctorInfo>();
				try {
					doctorInfoList = GET_HZJKUtils.getDoctorInfo(query);
					DocResultInfo result = new DocResultInfo();
					result.setSuccess("true");
					result.setError("");
					result.setDatas(doctorInfoList);
					rs.setResultInfo(result);
				} catch (Exception e) {
					DocResultInfo result = new DocResultInfo();
					result.setSuccess("false");
					result.setError("数据获取失败");
					rs.setResultInfo(result);
					e.printStackTrace();
				}
			}else{
				DocResultInfo result = new DocResultInfo();
				result.setSuccess("false");
				result.setError("字段yydm不可为空");
				rs.setResultInfo(result);
			}
		}else{
			DocResultInfo result = new DocResultInfo();
			result.setSuccess("false");
			result.setError("头信息不正确,请传入正确的用户名和密码");
			rs.setResultInfo(result);
		}
		return rs;
	}
	
	/**
	 * 获取排班信息
	 **/
	@WebMethod
	@WebResult(name="Root")
	public ResResult getResource(@WebParam(name = "Query")ResQuery query){
		String yydm = query.getYydm();
		String ksdm = query.getKsdm();
		String ysgh = query.getYsgh();
		String yyrq = query.getYyrq();
		String zjbs = query.getZjbs();
		ResResult rs = new ResResult();
		if(isPass()){
			List<ResourceInfo> resourceInfoList = new ArrayList<ResourceInfo>();
			try {
				if(StringUtils.isNotEmpty(yydm)&&(StringUtils.isNotEmpty(ksdm)||StringUtils.isNotEmpty(ysgh))){
					Boolean isTimeOk = true;
					if(StringUtils.isNotEmpty(yyrq)){
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
							System.out.println(begin);
							calendar.add(Calendar.DAY_OF_MONTH, 6);
							Date end = calendar.getTime();
							System.out.println(end);
							Date yyrq_short = df_short.parse(yyrq);
							if(begin.getTime() > yyrq_short.getTime() || yyrq_short.getTime() > end.getTime()){
								isTimeOk = false;
							}
						}else{
							calendar.add(Calendar.DAY_OF_MONTH, 2);
							Date begin = calendar.getTime();
							System.out.println(begin);
							calendar.add(Calendar.DAY_OF_MONTH, 6);
							Date end = calendar.getTime();
							System.out.println(end);
							Date yyrq_short = df_short.parse(yyrq);
							if(begin.getTime() > yyrq_short.getTime() || yyrq_short.getTime() > end.getTime()){
								isTimeOk = false;
							}
						}
						if(isTimeOk){
							if(StringUtils.isNotEmpty(ksdm)){
									if(StringUtils.isNotEmpty(zjbs)){
										if(zjbs.equals("0")){
											if(StringUtils.isEmpty(ysgh)){
												ysgh = "";
											}
											resourceInfoList = GET_HZJKUtils.getNumberSourceInfo(yydm, yyrq, ksdm, ysgh);
											ResResultInfo result = new ResResultInfo();
											result.setSuccess("true");
											result.setError("");
											result.setDatas(resourceInfoList);
											rs.setResultInfo(result);
										}else if(zjbs.equals("1")){
											if(StringUtils.isNotEmpty(ysgh)){
												resourceInfoList = GET_HZJKUtils.getNumberSourceInfo(yydm, yyrq, ksdm, ysgh);
												ResResultInfo result = new ResResultInfo();
												result.setSuccess("true");
												result.setError("");
												result.setDatas(resourceInfoList);
												rs.setResultInfo(result);
											}else{
												ResResultInfo result = new ResResultInfo();
												result.setSuccess("false");
												result.setError("传入参数ysgh错误，专家门诊时不能为空");
												rs.setResultInfo(result);
											}
										}
									}else{
										ResResultInfo result = new ResResultInfo();
										result.setSuccess("false");
										result.setError("传入参数zjbs错误，当yyrq不为空时zjbs必须传入“0”或者“1”");
										rs.setResultInfo(result);
									}
							}else{
								ResResultInfo result = new ResResultInfo();
								result.setSuccess("false");
								result.setError("传入参数ksdm错误，当yyrq不为空时ksdm不能为空");
								rs.setResultInfo(result);
							}
							
						}else{
							ResResultInfo result = new ResResultInfo();
							result.setSuccess("false");
							result.setError("参数yyrq日期传入有误，若当前时间在16:00:00之前，则可传入未来7天内中的一天；若当前时间在16:00:00之后，则可传入后天开始的7天内中的一天");
							rs.setResultInfo(result);
						}
					}else{
						resourceInfoList = GET_HZJKUtils.getResourceInfo(yydm, ksdm, ysgh, yyrq, zjbs);
						ResResultInfo result = new ResResultInfo();
						result.setSuccess("true");
						result.setError("");
						result.setDatas(resourceInfoList);
						rs.setResultInfo(result);
					}
					
				}else{
					ResResultInfo result = new ResResultInfo();
					result.setSuccess("false");
					result.setError("字段yydm,(ksdm或者ysgh)不可为空");
					rs.setResultInfo(result);
				}
			} catch (Exception e) {
				ResResultInfo result = new ResResultInfo();
				result.setSuccess("false");
				result.setError("数据获取失败");
				rs.setResultInfo(result);
				e.printStackTrace();
			}
		}else{
			ResResultInfo result = new ResResultInfo();
			result.setSuccess("false");
			result.setError("头信息不正确,请传入正确的用户名和密码");
			rs.setResultInfo(result);
		}
		return rs;
	}
	
	/**
	 * 预约单下达
	 **/
	@WebMethod
	@WebResult(name="Root")
	public BookResult  doBooking(@WebParam(name = "BookingForm")BookingForm form){
		String[] infos = form.getZydm().split("\\|");
		String yydm = infos[0];
		String yyrq = infos[3];
		String yysd = infos[4];
		String ksdm = infos[1];
		String ysgh = infos[2];
		String zydm = infos[6];
		String kssj = infos[5];
		String zjhm = form.getZjhm();
		String xm = form.getXm();
		String sex = Integer.parseInt(zjhm.substring(16, 17))%2==0?"1":"2";
		String csrq = form.getCsrq();
		String lxdh = form.getLxdh();
		BookResult rs = new BookResult();
		if(isPass()){
			List<BookingInfo> bookingInfoList = new ArrayList<BookingInfo>();
			try {
				Boolean validate = false;
				if(StringUtils.isNotEmpty(yydm)&&StringUtils.isNotEmpty(yyrq)&&StringUtils.isNotEmpty(yysd)&&StringUtils.isNotEmpty(ksdm)&&StringUtils.isNotEmpty(zydm)&&StringUtils.isNotEmpty(zjhm)&&StringUtils.isNotEmpty(xm)&&StringUtils.isNotEmpty(sex)&&StringUtils.isNotEmpty(lxdh)){
					if(yysd.equals("1")||yysd.equals("2")){
						validate = true;
					}
				}
				if(validate){
					User user = new User();
					user.setSfzh(zjhm);
					user.setXm(xm);
					user.setSex(sex);
					user.setYddh(lxdh);
					user.setRegisterdate(new Date());
					if(StringUtils.isNotEmpty(csrq)){
						SimpleDateFormat df_short = new SimpleDateFormat("yyyy-MM-dd");
						user.setBirthday(df_short.parse(csrq));
					}
					BookingInfo bookingInfo = new BookingInfo();
					ysgh = StringUtils.isNotEmpty(ysgh)?ysgh:"";
					bookingInfo = GET_HZJKUtils.getBookingInfo(yydm,yyrq, yysd, ksdm, ysgh, zydm,kssj, user,this.userid);
					bookingInfoList.add(bookingInfo);
					BookResultInfo result = new BookResultInfo();
					result.setSuccess("true");
					result.setError("");
					result.setDatas(bookingInfoList);
					rs.setResultInfo(result);
				}else{
					BookResultInfo result = new BookResultInfo();
					result.setSuccess("false");
					result.setError("传入参数有误，请求失败");
					rs.setResultInfo(result);
				}
			} catch (Exception e) {
				BookResultInfo result = new BookResultInfo();
				result.setSuccess("false");
				result.setError("预约单下达失败");
				rs.setResultInfo(result);
				e.printStackTrace();
			}
		}else{
			BookResultInfo result = new BookResultInfo();
			result.setSuccess("false");
			result.setError("头信息不正确,请传入正确的用户名和密码");
			rs.setResultInfo(result);
		}
		return rs;
	}
	
	/**
	 * 取消预约单
	 **/
	@WebMethod
	@WebResult(name="Root")
	public BookResult cancelBooking(@WebParam(name = "Cancel")Cancel cancel){
		String yydm = cancel.getYydm();
		String ghdh = cancel.getGhdh();
		BookResult rs = new BookResult();
		if(isPass()){
			try {
				if(StringUtils.isNotEmpty(yydm)&&StringUtils.isNotEmpty(ghdh)){
					if(GET_HZJKUtils.cancelBooking(yydm, ghdh)){
						BookResultInfo result = new BookResultInfo();
						result.setSuccess("true");
						result.setError("");
						rs.setResultInfo(result);
					}else{
						BookResultInfo result = new BookResultInfo();
						result.setSuccess("false");
						result.setError("取消预约单失败");
						rs.setResultInfo(result);
					}
					
				}else{
					BookResultInfo result = new BookResultInfo();
					result.setSuccess("false");
					result.setError("传入参数有误，请求失败");
					rs.setResultInfo(result);
				}
			} catch (Exception e) {
				BookResultInfo result = new BookResultInfo();
				result.setSuccess("false");
				result.setError("取消预约单失败");
				rs.setResultInfo(result);
				e.printStackTrace();
			}
		}else{
			BookResultInfo result = new BookResultInfo();
			result.setSuccess("false");
			result.setError("头信息不正确,请传入正确的用户名和密码");
			rs.setResultInfo(result);
		}
		return rs;
	}
	
	/**
	 * 预约单查询
	 **/
	@WebMethod
	@WebResult(name="Root")
	public BookResult queryBooking(@WebParam(name = "Query")BookQuery query){
		String zjhm = query.getZjhm();
		String zjlx = query.getZjlx();
		String kssj = query.getKsrq();
		String jssj = query.getJsrq();
		BookResult rs = new BookResult();
		if(isPass()){
			try {
				if(StringUtils.isNotEmpty(zjhm)&&StringUtils.isNotEmpty(zjlx)){
					if(zjlx.equals("01")){
						List<BookingInfo> bookingInfoList = new ArrayList<BookingInfo>();
						bookingInfoList = GET_HZJKUtils.queryBooking(zjhm, kssj, jssj);
						BookResultInfo result = new BookResultInfo();
						result.setSuccess("true");
						result.setError("");
						result.setDatas(bookingInfoList);
						rs.setResultInfo(result);
					}else{
						BookResultInfo result = new BookResultInfo();
						result.setSuccess("false");
						result.setError("证件类型不正确，请传入身份证01");
						rs.setResultInfo(result);
					}
				}else{
					BookResultInfo result = new BookResultInfo();
					result.setSuccess("false");
					result.setError("字段zjhm和zjlx不能为空");
					rs.setResultInfo(result);
				}
			} catch (Exception e) {
				BookResultInfo result = new BookResultInfo();
				result.setSuccess("false");
				result.setError("获取数据失败");
				rs.setResultInfo(result);
				e.printStackTrace();
			}
		}else{
			BookResultInfo result = new BookResultInfo();
			result.setSuccess("false");
			result.setError("头信息不正确,请传入正确的用户名和密码");
			rs.setResultInfo(result);
		}
		return rs;
	}
	
	private Boolean isPass(){
		Boolean result = false;
		Map<String, Object> headers = context.getMessageContext();
		@SuppressWarnings("unchecked")
		List<Header> headerList = (List<Header>)headers.get(Header.HEADER_LIST);
		for (Header header : headerList) { 
			Node root = (Node) header.getObject();
			if("Authorization".equals(root.getNodeName())){
				NodeList list = root.getChildNodes();
				int size = list.getLength();
				String userid = "";
				String password = "";
				for(int i=0;i<size;i++){
					Node node = (Node) list.item(i);
					if("userid".equals(node.getNodeName())){
						userid = node.getTextContent();
					}
					if("password".equals(node.getNodeName())){
						password = node.getTextContent();
					}
				}
				if(GET_HZJKUtils.validateS_User(userid, password)){
					result = true;
					this.userid = userid;
				}
			}
		}
		return result;
	}
	
}
