package registration.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HIS����
 **/
public class HISUtils {
	/**
	 * ����HIS�ӿ�
	 **/
	public static String getHISInfo(String code,String mess) throws Exception{
		//����ĵ�ַ
		URL wsUrl = new URL("http://www.hzjk.com/ws_hisinterface/n_ws_trans.asmx?wsdl");
		//URL wsUrl = new URL("http://10.172.1.183/ws_hisinterface/n_ws_trans.asmx?wsdl");
        
        HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();
        
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        
        OutputStream os = conn.getOutputStream();
        
        //������
        String soap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempurl.org\">"+
        				"<soapenv:Header/>"+
					    "<soapenv:Body>"+
					      "<tem:f_ws_trans>"+
					         "<tem:code>"+code+"</tem:code>"+
					         "<tem:mess>"+mess+"</tem:mess>"+
					         "<tem:ref_mess></tem:ref_mess>"+
					      "</tem:f_ws_trans>"+
					    "</soapenv:Body>"+
					  "</soapenv:Envelope>";
        
        os.write(soap.getBytes("UTF-8"));
        
        InputStream is = conn.getInputStream();
        
        BufferedReader breader =new BufferedReader(new InputStreamReader(is , "utf-8"));
		String str=breader.readLine();
		String result = "";
		while(str != null){
		  result += str;
		  str=breader.readLine();
		}
        System.out.println(result);
        
        is.close();
        os.close();
        conn.disconnect();
        return result;
	}
}
