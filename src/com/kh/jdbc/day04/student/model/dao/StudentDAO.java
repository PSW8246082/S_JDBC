package com.kh.jdbc.day04.student.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.jdbc.day04.student.common.JDBCTemplate;
import com.kh.jdbc.day04.student.model.vo.Student;

public class StudentDAO {
	
	/*
	 * 1.Checked Exception 과 Unchecked Exception
	 * 2.예외의 종류 Throwable - Exception(checked exception 한정)
	 * 3.예외처리 방법 : throws, try ~ catch
	 */
	
//	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
//	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
//	private final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
//	private final String USER = "STUDENT";
//	private final String PASSWORD = "STUDENT";
	
	Student student = null;
	int result = 0;
	private Properties prop;
	
	public StudentDAO() {
		prop = new Properties();
		Reader reader;
		try {
			reader = new FileReader("resources/query.properties");
			prop.load(reader);//해당파일을 읽어줌
		} catch(Exception e) {
			e.printStackTrace();
		} 
//		catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	

	public List<Student> selectAll(Connection conn) throws SQLException {

		String query = prop.getProperty("selectAll");
		List<Student> sList = new ArrayList<Student>();
		
		//finally 에서 쓰기 위해 전역변수로 올려줌
		Statement stmt = null;
		ResultSet rset = null;
		
		stmt = conn.createStatement();
		rset = stmt.executeQuery(query);
		
		while(rset.next()) {
			Student student = rsetToStudent(rset);
			sList.add(student);
		}
		rset.close();
		stmt.close();
		
//		try {
////			prop = new Properties();
////			Reader reader = new FileReader("resources/query.properties");
////			prop.load(reader);//해당파일을 읽어줌
////			String query = prop.getProperty("selectAll");
//			
//			
//			
//			
//			
//		} catch(Exception e) {
//			e.printStackTrace();
//		} 
////		catch (SQLException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (FileNotFoundException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} 
//		finally {
//			try {
//				rset.close();
//				stmt.close();
//				
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
//		}
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



	public Student selectOneById(Connection conn, String studentId) {
		//1.위치홀더 셋팅
		//2.PreparedStatement 객체 생성 with query
		//3.입력값 셋팅
		//4.쿼리문 실행 및 결과 받기(feat. method())
		
		Student student = new Student();
		//SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
		
		
		
		String query = prop.getProperty("selectOneById");
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
//			prop = new Properties();
//			Reader reader = new FileReader("resources/query.properties");
//			prop.load(reader);//해당파일을 읽어줌
//			String query = prop.getProperty("selectOneById");
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			rset = pstmt.executeQuery();
//					Statement stmt = 
//					conn.createStatement();
//					ResultSet rset = 
//					stmt.executeQuery(query);
			
			//후처리...기억안남.
			//반환되는 결과값이 1개이기 때문에 if문을 써준다. 여러개면 while
			if(rset.next()) {
				student = rsetToStudent(rset);
			}		
		} catch(Exception e) {
			e.printStackTrace();
		} 
//		catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		finally {
			try {
				
				pstmt.close();
				rset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return student;
	}



	public List<Student> selectAllByName(Connection conn, String studentName) {
		
		//SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '일용자'
		String query = prop.getProperty("selectAllByName");
		Student student = new Student();
		List<Student> sList = new ArrayList<Student>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		
		try {
//			prop = new Properties();
//			Reader reader = new FileReader("resources/query.properties");
//			prop.load(reader);//해당파일을 읽어줌
//			String query = prop.getProperty("selectAllByName");
			
			
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
			
			
			
			} catch(Exception e) {
				e.printStackTrace();
			} 
//		catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		finally {
			try {
				
				pstmt.close();
				rset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		return sList;
	}



	public int deleteStudent(Connection conn, String studentId) {

		String query = prop.getProperty("deleteStudent");
		PreparedStatement pstmt = null;
	
		try {
//			prop = new Properties();
//			Reader reader = new FileReader("resources/query.properties");
//			prop.load(reader);//해당파일을 읽어줌
//			String query = prop.getProperty("deleteStudent");
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			result = pstmt.executeUpdate();
//			Statement stmt = conn.createStatement();
//			result = stmt.executeUpdate(query);
		
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
//		catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		finally {
			try {
				pstmt.close();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
	}



	public int insertStudent(Connection conn, Student student) {
		//INSERT INTO STUDENT_TBL VALUES('khuser01', 'pass01', '일용자', 'M', 11, 'khuser01@kh.com', '01012345678','서울시 중구 남대문로 120', '독서, 수영', SYSDATE);  
				
		int result = 0;
		String query = prop.getProperty("insertStudent");
		PreparedStatement pstmt = null;
		
		try {
//			prop = new Properties();
//			Reader reader = new FileReader("resources/query.properties");
//			prop.load(reader);//해당파일을 읽어줌
//			String query = prop.getProperty("insertStudent");
			
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
			
//					Statement stmt = conn.createStatement();
//					result = stmt.executeUpdate(query);
		
		}  catch(Exception e) {
			e.printStackTrace();
		} 
//		catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		finally {
			try {
				pstmt.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return result;
	}



	public int updateStudent(Connection conn, Student student) {
		
		
		int result = 0; // -1도 괜찮음
		String query = prop.getProperty("updateStudent");
		PreparedStatement pstmt = null;
		
		try {
//			prop = new Properties();
//			Reader reader = new FileReader("resources/query.properties");
//			prop.load(reader);//해당파일을 읽어줌
//			String query = prop.getProperty("updateStudent");
			
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
		
		} catch(Exception e) {
			e.printStackTrace();
		} 
//		catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		finally {
			try {
				pstmt.close();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	
	

}
