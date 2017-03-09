package registration.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "results")
public class DocResult {
	@XmlElement(name = "Result",nillable=true)
	private DocResultInfo resultInfo;

	@XmlTransient
	public DocResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(DocResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
	
}
