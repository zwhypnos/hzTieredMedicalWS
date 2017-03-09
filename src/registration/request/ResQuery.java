package registration.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class ResQuery {
	@XmlElement
	private String yydm;
	@XmlElement
	private String ksdm;
	@XmlElement
	private String ysgh;
	@XmlElement
	private String yyrq;
	@XmlElement
	private String zjbs;
	@XmlElement
	private String zzbs;
	@XmlElement
	private String zkdm;
	
	@XmlTransient
	public String getYydm() {
		return yydm;
	}
	public void setYydm(String yydm) {
		this.yydm = yydm;
	}
	@XmlTransient
	public String getKsdm() {
		return ksdm;
	}
	public void setKsdm(String ksdm) {
		this.ksdm = ksdm;
	}
	@XmlTransient
	public String getYsgh() {
		return ysgh;
	}
	public void setYsgh(String ysgh) {
		this.ysgh = ysgh;
	}
	@XmlTransient
	public String getYyrq() {
		return yyrq;
	}
	public void setYyrq(String yyrq) {
		this.yyrq = yyrq;
	}
	@XmlTransient
	public String getZjbs() {
		return zjbs;
	}
	public void setZjbs(String zjbs) {
		this.zjbs = zjbs;
	}
	@XmlTransient
	public String getZzbs() {
		return zzbs;
	}
	public void setZzbs(String zzbs) {
		this.zzbs = zzbs;
	}
	@XmlTransient
	public String getZkdm() {
		return zkdm;
	}
	public void setZkdm(String zkdm) {
		this.zkdm = zkdm;
	}
	
	
}
