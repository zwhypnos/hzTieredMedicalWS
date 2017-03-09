package registration.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "results")
public class ResResult {
	@XmlElement(name = "Result",nillable=true)
	private ResResultInfo resultInfo;

	@XmlTransient
	public ResResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(ResResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
	
}
