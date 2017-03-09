package registration.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="BookingForm")
public class BookingForm {
	@XmlElement
	private String zydm;
	@XmlElement
	private String zjhm;
	@XmlElement
	private String xm;
	@XmlElement
	private String csrq;
	@XmlElement
	private String lxdh;
	@XmlElement
	private String zjlx;
	@XmlElement
	private String jkkh;
	@XmlElement
	private String klx;
	
	@XmlTransient
	public String getZydm() {
		return zydm;
	}
	public void setZydm(String zydm) {
		this.zydm = zydm;
	}
	@XmlTransient
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	@XmlTransient
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	@XmlTransient
	public String getCsrq() {
		return csrq;
	}
	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}
	@XmlTransient
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	@XmlTransient
	public String getZjlx() {
		return zjlx;
	}
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	@XmlTransient
	public String getJkkh() {
		return jkkh;
	}
	public void setJkkh(String jkkh) {
		this.jkkh = jkkh;
	}
	@XmlTransient
	public String getKlx() {
		return klx;
	}
	public void setKlx(String klx) {
		this.klx = klx;
	}
	
	
}
