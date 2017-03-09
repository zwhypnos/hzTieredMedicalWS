package registration.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class ResourceInfo {
	@XmlElement(name="zzdm")
	private String zzdm;
	
	private String zylx;
	private String yyrq;
	private String yysd;
	private String kssj;
	private String jssj;
	private String zjdd;
	private String zjmh;
	private String yydm;
	private String yymc;
	private String ksdm;
	private String ksmc;
	private String zkdm;
	private String zkmc;
	private String ysgh;
	private String ysxm;
	private String yyzys;
	private String flag;
	private String zjbs;
	
	@XmlTransient
	public String getZydm() {
		return zzdm;
	}
	public void setZydm(String zzdm) {
		this.zzdm = zzdm;
	}
	public String getZylx() {
		return zylx;
	}
	public void setZylx(String zylx) {
		this.zylx = zylx;
	}
	public String getYyrq() {
		return yyrq;
	}
	public void setYyrq(String yyrq) {
		this.yyrq = yyrq;
	}
	public String getYysd() {
		return yysd;
	}
	public void setYysd(String yysd) {
		this.yysd = yysd;
	}
	public String getKssj() {
		return kssj;
	}
	public void setKssj(String kssj) {
		this.kssj = kssj;
	}
	public String getJssj() {
		return jssj;
	}
	public void setJssj(String jssj) {
		this.jssj = jssj;
	}
	public String getZjdd() {
		return zjdd;
	}
	public void setZjdd(String zjdd) {
		this.zjdd = zjdd;
	}
	public String getZjmh() {
		return zjmh;
	}
	public void setZjmh(String zjmh) {
		this.zjmh = zjmh;
	}
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
	public String getZkdm() {
		return zkdm;
	}
	public void setZkdm(String zkdm) {
		this.zkdm = zkdm;
	}
	public String getZkmc() {
		return zkmc;
	}
	public void setZkmc(String zkmc) {
		this.zkmc = zkmc;
	}
	public String getYsgh() {
		return ysgh;
	}
	public void setYsgh(String ysgh) {
		this.ysgh = ysgh;
	}
	public String getYsxm() {
		return ysxm;
	}
	public void setYsxm(String ysxm) {
		this.ysxm = ysxm;
	}
	public String getYyzys() {
		return yyzys;
	}
	public void setYyzys(String yyzys) {
		this.yyzys = yyzys;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getZjbs() {
		return zjbs;
	}
	public void setZjbs(String zjbs) {
		this.zjbs = zjbs;
	}
	
	
}
