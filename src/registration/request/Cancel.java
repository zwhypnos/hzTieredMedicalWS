package registration.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="Cancel")
public class Cancel {
	@XmlElement
	private String yydm;
	@XmlElement
	private String yyrq;
	@XmlElement
	private String ghdh;
	@XmlElement
	private String message;
	@XmlTransient
	public String getYydm() {
		return yydm;
	}
	public void setYydm(String yydm) {
		this.yydm = yydm;
	}
	@XmlTransient
	public String getYyrq() {
		return yyrq;
	}
	public void setYyrq(String yyrq) {
		this.yyrq = yyrq;
	}
	@XmlTransient
	public String getGhdh() {
		return ghdh;
	}
	public void setGhdh(String ghdh) {
		this.ghdh = ghdh;
	}
	@XmlTransient
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
