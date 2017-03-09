package registration.pojo;

/**
 * 科室类
 **/
public class DepartmentInfo {
	private String yydm; //医院代码
	private String yymc; //医院名称
	private String ksdm; //科室代码
	private String ksmc; //科室名称
	private String ksdz; //科室地址
	private String zxdh; //咨询电话
	private String ksjj; //科室简介
	private String wsjksdm; //标准科室代码
	private String scbz; //删除标志
	private String bzw; //是否挂号科室
	private String zjbs; //专家标识
	
	public String getYydm() {
		return yydm;
	}
	public void setYydm(String yydm) {
		this.yydm = yydm;
	}
	
	public String getYymc() {
		return yymc;
	}
	public void setYymc(String yymc) {
		this.yymc = yymc;
	}
	
	public String getKsdm() {
		return ksdm;
	}
	public void setKsdm(String ksdm) {
		this.ksdm = ksdm;
	}
	
	public String getKsmc() {
		return ksmc;
	}
	public void setKsmc(String ksmc) {
		this.ksmc = ksmc;
	}
	
	public String getKsdz() {
		return ksdz;
	}
	public void setKsdz(String ksdz) {
		this.ksdz = ksdz;
	}
	
	public String getZxdh() {
		return zxdh;
	}
	public void setZxdh(String zxdh) {
		this.zxdh = zxdh;
	}
	
	public String getKsjj() {
		return ksjj;
	}
	public void setKsjj(String ksjj) {
		this.ksjj = ksjj;
	}
	
	public String getWsjksdm() {
		return wsjksdm;
	}
	public void setWsjksdm(String wsjksdm) {
		this.wsjksdm = wsjksdm;
	}
	
	public String getScbz() {
		return scbz;
	}
	public void setScbz(String scbz) {
		this.scbz = scbz;
	}
	
	public String getBzw() {
		return bzw;
	}
	public void setBzw(String bzw) {
		this.bzw = bzw;
	}
	
	public String getZjbs() {
		return zjbs;
	}
	public void setZjbs(String zjbs) {
		this.zjbs = zjbs;
	}
}
