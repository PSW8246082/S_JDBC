package com.kh.jdbc.day01.basic;

//sql에 있는거 다 쓰기 위해 * 
import java.sql.*;

public class JDBCRun {

	public static void main(String[] args) {
		/**
		 * JDBC 코딩 절차
		 *  1. 드라이버 등록 ojdbc6.jar
		 *  2. DBMS 연결 생성
		 *  3. Statement 객체 생성(쿼리문 실행 준비)
		 *    - new Statement(); 가 아니라 연결을 통해 객체 생성함
		 *  4. SQL 전송(쿼리문 실행)
		 *  5. 결과받기(ResultSet으로 바로 받아버림)
		 *  6. 자원해제(close())
		 */

		
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "KH";
			String password = "KH";
			String query = "SELECT EMP_NAME, SALARY FROM EMPLOYEE"; //세미콜론 빼기
			
			// 1. 드라이버 등록 ojdbc6.jar
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. DBMS 연결 생성
			 //Connection는 인터페이스
			Connection conn =
			DriverManager.getConnection(url, user, password);
			
			// 3.쿼리문 실행 준비 (Statement 객체 생성) 
			Statement stmt = conn.createStatement();
			
			// 4. 쿼리문 실행( SQL 전송) / SELECT면 ResultSet
			// 5. 결과받기(ResultSet으로 바로 받아버림), 테이블 형태
			ResultSet rset = stmt.executeQuery(query);
			
			// 추가... 후처리 필요 - 디비에서 가져온 데이터 사용하기 위해서 위에 1~5까지 작성한것.
			while(rset.next()) {
				System.out.printf("직원명 :  %s, 급여 : %s\n"
						, rset.getString("EMP_NAME"), rset.getInt(2));
			}
			
			// 6. 자원해제(close())
			rset.close();
			stmt.close();
			conn.close();
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
