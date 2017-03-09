package registration.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 连接数据库
 **/
public class ConnDB {

	private Connection ct=null;
	
	public Connection getConn(){
		try {
	    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    	ct=DriverManager.getConnection("jdbc:sqlserver://10.172.1.184;databaseName=jkfw","sa","shenlan@123");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ct;
	}
}
