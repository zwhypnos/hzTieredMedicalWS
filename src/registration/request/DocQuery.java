package registration.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="Query")
public class DocQuery {
	@XmlElement
	private String yydm;
	@XmlElement
	private String yymc;
	@XmlElement
	private String ksdm;
	@XmlElement
	private String ksmc;
	@XmlElement
	private String zkdm;
	@XmlElement
	private String zkmc;
	@XmlElement
	private String ysgh;
	
	@XmlTransient
	public String getYydm() {
		return yydm;
	}
	public void setYydm(String yydm) {
		this.yydm = yydm;
	}
	@XmlTransient
	public String getYymc() {
		return yymc;
	}
	public void setYymc(String yymc) {
		this.yymc = yymc;
	}
	@XmlTransient
	public String getKsdm() {
		return ksdm;
	}
	public void setKsdm(String ksdm) {
		this.ksdm = ksdm;
	}
	@XmlTransient
	public String getKsmc() {
		return ksmc;
	}
	public void setKsmc(String ksmc) {
		this.ksmc = ksmc;
	}
	@XmlTransient
	public String getZkdm() {
		return zkdm;
	}
	public void setZkdm(String zkdm) {
		this.zkdm = zkdm;
	}
	@XmlTransient
	public String getZkmc() {
		return zkmc;
	}
	public void setZkmc(String zkmc) {
		this.zkmc = zkmc;
	}
	@XmlTransient
	public String getYsgh() {
		return ysgh;
	}
	public void setYsgh(String ysgh) {
		this.ysgh = ysgh;
	}
	
	
}
