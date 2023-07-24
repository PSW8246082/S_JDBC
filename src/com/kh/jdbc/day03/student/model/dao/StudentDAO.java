package com.kh.jdbc.day03.student.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day03.student.vo.Student;

public class StudentDAO {   //데이터 접근 객체
	
	private final String DRIVERNAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	private final String USER = "STUDENT";
	private final String PASSWORD = "STUDENT";
	Student student = null;
	int result = 0;

	public List<Student> selectAll() {
		
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = new ArrayList<Student>();
	
		//finally 에서 쓰기 위해 전역변수로 올려줌
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		try {
			getClass().forName(DRIVERNAME);
			conn = 
			DriverManager.getConnection(URL, USER, PASSWORD);
			stmt =
			conn.createStatement();
			rset =
			stmt.executeQuery(query);
			
			while(rset.next()) {
				Student student = rsetToStudent(rset);
				sList.add(student);
			
//				System.out.printf("아이디 :  %s, 이름 : %s\n"
//						, rset.getString("STUDENT_ID")
//						, rset.getString("STUDENT_NAME"));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return sList;
	}
	
	
	

	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student();
		//student.setStudentId(rset.getString(1));  //컬럼의 순서로도 가져올 수 있음
		
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




	public Student selectOneById(String studentId) {
		
		//1.위치홀더 셋팅
		//2.PreparedStatement 객체 생성 with query
		//3.입력값 셋팅
		//4.쿼리문 실행 및 결과 받기(feat. method())
		
		Student student = new Student();
		//SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName(DRIVERNAME);
			conn =
			DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			rset = pstmt.executeQuery();
//			Statement stmt = 
//			conn.createStatement();
//			ResultSet rset = 
//			stmt.executeQuery(query);
			
			//후처리...기억안남.
			//반환되는 결과값이 1개이기 때문에 if문을 써준다. 여러개면 while
			if(rset.next()) {
				student = rsetToStudent(rset);
			}		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				rset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return student;
	}




	public List<Student> selectAllByName(String studentName) {
		
		//SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '일용자'
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = ?";
		Student student = new Student();
		List<Student> sList = new ArrayList<Student>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		
		try {
			Class.forName(DRIVERNAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentName);
			rset = pstmt.executeQuery();
//			Statement stmt = 
//			conn.createStatement();
//			ResultSet rset = 
//			stmt.executeQuery(query);
			
			//후처리 결과값 -> 이름으로 조회한 결과가 여러개일 수 있으니까 while(rset.next())
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
			
			
			
			} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				rset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		return sList;
	}




	public int insertStudent(Student student) {
		
		//INSERT INTO STUDENT_TBL VALUES('khuser01', 'pass01', '일용자', 'M', 11, 'khuser01@kh.com', '01012345678','서울시 중구 남대문로 120', '독서, 수영', SYSDATE);  
		String query = "INSERT INTO STUDENT_TBL VALUES(?,?,?,?,?,?,?,?,?,SYSDATE)";
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVERNAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentId());
			pstmt.setString(2, student.getStudentPwd());
			pstmt.setString(3, student.getStudentName());
			pstmt.setString(4, student.getGender()+"");    //String.valueOf(student.getGender());
			pstmt.setInt(5, student.getAge());
			pstmt.setString(6, student.getEmail());
			pstmt.setString(7, student.getPhone());
			pstmt.setString(8, student.getAddress());
			pstmt.setString(9, student.getHobby());
			result = pstmt.executeUpdate();    //쿼리문 실행 빼먹지 않기
			
//			Statement stmt = conn.createStatement();
//			result = stmt.executeUpdate(query);
		
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return result;
	}




	public int deleteStudent(String studentId) {
		
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
	
		try {
			Class.forName(DRIVERNAME);
		    conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			result = pstmt.executeUpdate();
//			Statement stmt = conn.createStatement();
//			result = stmt.executeUpdate(query);
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
	}




	public int updateStudent(Student student) {
		
		//UPDATE STUDENT_TBL 
		//SET STUDENT_PWD = 'pass011' ,EMAIL = 'khuser011@kh.com' ,PHONE = '01101234567'
		// ,ADDRESS = '서울시 중구 남대문로 1120' ,HOBBY = '독서1, 수영1'
		//WHERE STUDENT_ID = 'khuser01';
		
//		String query = "UPDATE STUDENT_TBL SET STUDENT_PWD = "
//				+ "'"+student.getStudentPwd()+"' ,"
//						+ "EMAIL = '"+student.getEmail()+"' ,"
//								+ "PHONE = '"+student.getPhone()+"',"
//										+ "ADDRESS = '"+student.getAddress()+"' ,"
//												+ "HOBBY = '"+student.getHobby()+"'"
//														+ "WHERE STUDENT_ID = '"+student.getStudentId()+"'";
	
		String query = "UPDATE STUDENT_TBL SET STUDENT_PWD = ? , EMAIL = ? , PHONE = ?, ADDRESS = ? , HOBBY = ? WHERE STUDENT_ID = ?";
		
		int result = 0; // -1도 괜찮음
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVERNAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentPwd());
			pstmt.setString(2, student.getEmail());
			pstmt.setString(3, student.getPhone());
			pstmt.setString(4, student.getAddress());
			pstmt.setString(5, student.getHobby());
			pstmt.setString(6, student.getStudentId());
			result = pstmt.executeUpdate();
			
//			Statement stmt = conn.createStatement();
//			result = stmt.executeUpdate(query);
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}




	public Student selectLoginInfo(Student student) {
		
		Student result = null;
		//SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ? AND STUDENT_PWD = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName(DRIVERNAME);
		    conn =DriverManager.getConnection(URL, USER, PASSWORD);
			
			//입력값이 있을 때는 PreparedStatement 사용하기를 권장한다.
			pstmt = conn.prepareStatement(query);  //컴파일
			pstmt.setString(1, student.getStudentId());     //시작은 1로 시작함
			pstmt.setString(2, student.getStudentPwd());   //마지막 숫자는 물음표 갯수와 같아야 함 (물음표 = 위치홀더)
			rset = pstmt.executeQuery();  //실행만
			
			//아래 4줄을 PreparedStatement 변경
//			Statement stmt = 
//			conn.createStatement();
//			ResultSet rset = 
//			stmt.executeQuery(query);
			
			//후처리
			//반환되는 결과값이 1개이기 때문에 if문을 써준다. 여러개면 while
			if(rset.next()) {
				result = rsetToStudent(rset);
			}
					
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				rset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}


	

	
	
	
	
	
	
	
}
