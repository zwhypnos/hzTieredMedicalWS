package registration.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "results")
public class DepResult {
	@XmlElement(name = "Result",nillable=true)
	private DepResultInfo resultInfo;

	@XmlTransient
	public DepResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(DepResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
	
}
