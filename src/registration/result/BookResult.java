package registration.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "results")
public class BookResult {
	@XmlElement(name = "Result",nillable=true)
	private BookResultInfo resultInfo;

	@XmlTransient
	public BookResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(BookResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
}
