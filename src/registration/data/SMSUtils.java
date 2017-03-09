package registration.data;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

/**
 * ��������
 **/
public class SMSUtils {
	/**
	 * ���Ͷ���
	 * 
	 **/
	public static void sendSMS(String mobile,String content){
		try {			
			//String soapBindingAddress = "http://10.172.1.183:9090/SmsService.asmx?wsdl";  
			String soapBindingAddress = "http://www.hzjk.com:9090/SmsService.asmx?wsdl";
	        ServiceClient sender = new ServiceClient();  
	        EndpointReference endpointReference = new EndpointReference(  
	                soapBindingAddress);  
	        Options options = new Options();  
	        options.setAction("http://tempuri.org/Send");  
	        options.setTo(endpointReference);  
	        sender.setOptions(options);  
	        OMFactory fac = OMAbstractFactory.getOMFactory();  
	        // �����qname��࣬���������ռ�  
	        OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "Send");  
	        OMElement data = fac.createOMElement("Send", omNs);  

	        // ��Ӧ�����Ľڵ�  
	        String[] strs = new String[] { "_openMasUrl","Content","ExtendCode","ApplicationID","Password" };  
	        // ����ֵ  
	        String[] val = new String[] { "http://211.140.120.216:9080/OpenMasService", content, "9", "asdqwe", "asdqwe" };  
	        
	        for (int i = 0; i < strs.length; i++) {  
	            OMElement inner = fac.createOMElement(strs[i], omNs);  
	            inner.setText(val[i]); 
	            data.addChild(inner);  
	        }  
	        
	        //
	        OMElement inner = fac.createOMElement("MobileList", omNs);
	        OMElement child = fac.createOMElement("string", omNs);
	        child.setText(mobile);
	        inner.addChild(child);
	        data.addChild(inner);
	        
	        // �������ݣ����ؽ��  
	        OMElement result = sender.sendReceive(data); 
	        
	        System.out.println(result.toString());
		} catch (AxisFault e) {
			e.printStackTrace();
		}
	}
		
}
