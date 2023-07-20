package com.kh.jdbc.day01.student.model.dao;

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
	
	
	// must return a result of type List<Student> - 메소드에 리턴 써줘라!
	public List<Student> selectAll() {
		
		String driverName = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "STUDENT";
		String password = "STUDENT";
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = null;
		
		try {
			// 1. 드라이버 등록
			Class.forName(driverName);
			
			// 2. DB 연결 생성(DriverManager)
			Connection conn = 
			DriverManager.getConnection(url, user, password);
			
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
				sList.add(student);
			
				
				
				System.out.printf("아이디 :  %s, 이름 : %s\n"
						, rset.getString("STUDENT_ID")
						, rset.getString("STUDENT_NAME"));
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


}
