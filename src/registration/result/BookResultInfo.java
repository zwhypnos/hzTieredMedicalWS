package registration.result;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import registration.pojo.BookingInfo;

public class BookResultInfo {
	@XmlAttribute(name="success")
	private String success;
	@XmlAttribute(name="error")
	private String error;
	@XmlElement(name="BookingInfo")
	private List<BookingInfo> datas;
	
	@XmlTransient
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	@XmlTransient
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	@XmlTransient
	public List<BookingInfo> getDatas() {
		return datas;
	}
	public void setDatas(List<BookingInfo> datas) {
		this.datas = datas;
	}
}
