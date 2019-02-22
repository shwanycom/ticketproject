package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {
	public static Connection getConnection() {
		Connection con=null;
		try {
			// 1. MySql database 클래스를 로드한다.
			Class.forName("com.mysql.jdbc.Driver");
			
			// 2. 주소, ID, password를 통해서 접속요청한다.
			con = DriverManager.getConnection("jdbc:mysql://localhost/performance", "root", "123456");
//			MainController.callAlert("연결성공 : 데이터베이스 연결 성공");
		}catch(Exception e) {
			MainController.callAlert("연결실패 : 데이터베이스 연결 실패, 점검 요망");
			e.printStackTrace();
			return null;
		}
		return con;
	}
	
}
