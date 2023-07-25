package com.kh.jdbc.day04.student.common;

import java.sql.*;

public class JDBCTemplate_old {

	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
//	private final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	private final String USER = "STUDENT";
	private final String PASSWORD = "STUDENT";
	
	//패턴 정리
	//디자인 패턴 : 각기 다른 소프트웨어 모듈이나 기능을 가진 응용 SW
	//개발할 때 공통되는 설계 문제를 해결하기 위하여 사용되는 패턴
	//==> 효율적인 방식을 위함
	//패턴의 종류 : 생성패턴, 구조패턴, 행위패턴, ...
	//1. 생성패턴 : 싱글톤 패턴, 추상팩토리, 팩토리 메서드, ...
	//2. 구조패턴 : 컴포지트, 데코레이트, ...
	//3. 행위패턴 : 옵저버, 스테이트, 절략, 템플릿 메서드, ...
	
	/*
	 * public class Singletone {
	 * 		private static Singletone instance;
	 * 
	 * 		private Singletone() {}
	 * 
	 * 		public static Singletone getInstance() {
	 * 			if(instance == null) {
	 * 				instance = new Singletone();
	 * 			}
	 * 		}
	 * }
	 * 
	 */
	
	//싱글톤 패턴
	//무조건 딱 한번만 생성되고 없을 때에만 생성한다.
	//이미 존재하면 존재하는 객체를 사용함
	private static JDBCTemplate_old instance;
	private static Connection conn;
	
	private JDBCTemplate_old() {
		
	}
	
    //무조건 딱 한번만 생성되고 없을 때에만 생성한다.
	public static JDBCTemplate_old getInstance() {
		// 이미 만들어져 있는지 체크하고
		// JDBC객체 /  이 객체가 존재하는지 확인하기
		if(instance == null) {
			// 안만들어져 있으면 생성해서 사용해
			instance = new JDBCTemplate_old();
		}
		    // 만들어져 있으면 그거 사용해
			return instance;
	}
	
	
	
	
	public Connection createConnection() {
		
		try {
			if(conn == null || conn.isClosed()) {
				Class.forName(DRIVER_NAME);
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				//DBCP(DataBase Connection Pool)
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	public void close() {
		if(conn != null) {
			try {
				if(!conn.isClosed()) {
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
 	
}
