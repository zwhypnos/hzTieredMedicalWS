package registration.result;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import registration.pojo.NumberSourceInfo;
@XmlRootElement(name = "results")
public class NumResult {
	@XmlElement
	private String result;
	
	@XmlElement
	private String error;
	
	@XmlElementWrapper(name="NumberSourceInfos")
	@XmlElements(@XmlElement(type=NumberSourceInfo.class,name="NumberSourceInfo"))
	private List<NumberSourceInfo> datas;
	
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
	public List<NumberSourceInfo> getDatas() {
		return datas;
	}
	public void setDatas(List<NumberSourceInfo> datas) {
		this.datas = datas;
	}
	
}
