package registration.result;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import registration.pojo.BookingInfo;
@XmlRootElement(name = "results")
public class QueryResult {
	@XmlElement
	private String result;
	
	@XmlElement
	private String error;
	
	@XmlElementWrapper(name="BookingInfos")
	@XmlElements(@XmlElement(type=BookingInfo.class,name="BookingInfo"))
	private List<BookingInfo> datas;
	
	@XmlTransient
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
