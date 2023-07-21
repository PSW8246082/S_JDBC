package com.kh.jdbc.day02.student.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day01.student.model.vo.Student;

public class StudentDAO {  //dao는 오라클에 있는 전체 정보를 가져와서 객체화 하는것

	/* 
	 *  1. 드라이버 등록
	 *  2. DB 연결 생성
	 *  3. 쿼리문 실행 준비
	 *  4. 쿼리문 실행 및 5. 결과받기
	 *  6. 자원해제(close())
	 */
	
	
	//필드 - 멤버변수로 올려줘서 전역변수로 씀.(여러 메소드에서 쓸 수 있음)
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER = "STUDENT";
	private final String PASSWORD = "STUDENT";
	
	
	
	
	// must return a result of type List<Student> - 메소드에 리턴 써줘라!
	public List<Student> selectAll() {
		
	
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = null;
		
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVER_NAME);
			
			// 2. DB 연결 생성(DriverManager)
			Connection conn = 
			DriverManager.getConnection(URL, USER, PASSWORD);
			
			// 3. 쿼리문 실행 준비 (Statement)
			Statement stmt =
			conn.createStatement();
			
			// 4. 쿼리문 실행 및 5. 결과받기    (SELECT 이면 ResultSet)
			ResultSet rset =            //결과받기
			stmt.executeQuery(query);  //실행
			
			//후처리
			//db에 있는 데이터들을 객체화 해준다. getter메소드로 가져와서..
			sList = new ArrayList<Student>();
			
			while(rset.next()) {
				Student student = rsetToStudent(rset);
				sList.add(student);
			
//				System.out.printf("아이디 :  %s, 이름 : %s\n"
//						, rset.getString("STUDENT_ID")
//						, rset.getString("STUDENT_NAME"));
			}
			rset.close();
			stmt.close();
			conn.close();
			
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// must return a result of type List<Student> - 리턴 써줘라!
		return sList;
		
		
		}

	public List<Student> selectAllByName(String studentName) {
		
		//SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '일용자';
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME ='" + studentName + "'";
		List<Student> sList = new ArrayList<Student>();
		Student student = null;
		
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVER_NAME);
			
			// 2. DB연결 생성
			Connection conn = 
					DriverManager.getConnection(URL, USER, PASSWORD);
			
			// 3. 쿼리문 실행 준비
			Statement stmt = 
					conn.createStatement();
			
			// 4. 쿼리문 실행 및 5. 결과받기  SELECT면 ResultSet
			ResultSet rset =
					stmt.executeQuery(query);
			
			//후처리 결과값
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
			
			
			rset.close();
			conn.close();
			stmt.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sList;
	}

	public Student selectOneById(String studentId) {
		
		
		//SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = '" +studentId+"'";   //이거 대체 뭐지
		Student student = null; 
		
		
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVER_NAME);
			
			// 2. DB연결 생성
			Connection conn =
					DriverManager.getConnection(URL, USER, PASSWORD);
			
			// 3. 쿼리문 실행 준비
			Statement stmt =
					conn.createStatement();
	
			// 4. 쿼리문 실행 및 5. 결과받기
			ResultSet rset =   //(SELECT 이면 ResultSet)
			stmt.executeQuery(query);
			
			//후처리 결과값이 한개면 if문으로 써준다 , 여러개면 while
			if(rset.next()) {
				student = rsetToStudent(rset);
			}
			rset.close();
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return student;
	}

	public int insertStudent(Student student) {
		/*
		 *  1. 드라이버 등록
		 *  2. DB연결 생성
		 *  3. 쿼리문 실행 준비
		 *  4. 쿼리문 실행 및 5. 결과받기
		 *  6. 자원해제
		 */
		
		//홑따옴표 잘 보기.. + (문자열 쌍따옴표를 감싼다)
		//INSERT INTO STUDENT_TBL VALUES ('khuser01', 'pass01', '일용자', 'M', 11, 'khuser01@kh.com', '01012345678'
		//,'서울시 중구 남대문로 120', '독서, 수영', SYSDATE);
		String query = "INSERT INTO STUDENT_TBL VALUES("
				+ "'"+student.getStudentId()+"', "
						+ "'"+student.getStudentPwd()+"',"
								+ " '"+student.getStudentName()+"',"
										+ " '"+student.getGender()+"',"
												     +student.getAge()+","
														+ " '"+student.getEmail()+"',"
																+ " '"+student.getPhone()+"',"
																		+ "'"+student.getAddress()+"',"
																				+ " '"+student.getHobby()+
																				"', SYSDATE)";
		int result = -1;
		
		
		try {
			
		     // 1. 드라이버 등록
			Class.forName(DRIVER_NAME);
			
			// 2. DB연결 생성
			Connection conn = 
			DriverManager.getConnection(URL, USER, PASSWORD);
			
			// 3. 쿼리문 실행 준비
			Statement stmt = 
					conn.createStatement();
			
			// 4. 쿼리문 실행 및 5. 결과받기
			result =                       // 결과 받기
			stmt.executeUpdate(query);     // 이거는 DML용 (INSERT, UPDATE, DELETE)
			// stmt.executeQuery(query);   //이거는 SELECT용 
			

			stmt.close();
			conn.close();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public int updateStudent(Student student) {
		
		//UPDATE STUDENT_TBL 
		//SET STUDENT_PWD = 'pass011' ,EMAIL = 'khuser011@kh.com' ,PHONE = '01101234567'
		// ,ADDRESS = '서울시 중구 남대문로 1120' ,HOBBY = '독서1, 수영1'
		//WHERE STUDENT_ID = 'khuser01';
		String query = "UPDATE STUDENT_TBL SET "
				+ "STUDENT_PWD = '"+student.getStudentPwd()+"', "
						+ "EMAIL = '"+student.getEmail()+"', "
								+ "PHONE = '"+student.getPhone()+"', "
										+ "ADDRESS = '"+student.getAddress()+"', "
												+ "HOBBY = '"+student.getHobby()+"' "
														+ "WHERE STUDENT_ID = '"+student.getStudentId()+"'";
		int result = 0; // -1도 괜찮음
		
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			
			stmt.close();
			conn.close();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	public int deleteStudent(String studentId) {
		//DELETE FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01';
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = '"+studentId+"'" ;
		int result = 0; // -1도 괜찮음
		
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student();
		
		student.setStudentId(rset.getString("STUDENT_ID"));
		student.setStudentPwd(rset.getString("STUDENT_PWD"));
		student.setStudentName(rset.getString("STUDENT_NAME"));
		student.setAge(rset.getInt("AGE"));
		student.setEmail(rset.getString("EMAIL"));
		student.setPhone(rset.getString("PHONE"));
		//문자는 문자열에 문자로 잘라서 사용, charAt() 메소드 사용
		student.setGender(rset.getString("GENDER").charAt(0));
		student.setAddress(rset.getString("ADDRESS"));
		student.setHobby(rset.getString("HOBBY"));
		student.setEnrollDate(rset.getDate("ENROLL_DATE"));
		
		return student;
	}


}
