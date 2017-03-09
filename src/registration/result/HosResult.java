package registration.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
@XmlRootElement(name = "results")
public class HosResult {
	@XmlElement(name = "Result",nillable=true)
	private HosResultInfo resultInfo;

	@XmlTransient
	public HosResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(HosResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
}
