package com.kh.jdbc.day03.student.controller;

import java.util.List;

import com.kh.jdbc.day03.student.model.dao.StudentDAO;
import com.kh.jdbc.day03.student.vo.Student;

public class StudentController {    //반환값 처리
	
	private StudentDAO sDao;

	
	
	public StudentController() {
		sDao = new StudentDAO();
	}
	
	

	//SELECT * FROM STUDENT_TBL   -> 다 가져오니까 List
	public List<Student> selectAllStudent() {  
		//쿼리문을 보고 리턴값 예상해보고 void 수정 
		List<Student> sList = sDao.selectAll();
		return sList;
	}



	public Student printStudenBytId(String studentId) {
		Student student = sDao.selectOneById(studentId);
		return student;
	}



	public List<Student> selectAllByName(String studentName) {
		List<Student> sList = sDao.selectAllByName(studentName);
		return sList;
	}



	public int insertStudent(Student student) {
		int result = sDao.insertStudent(student);
		return result;
	}



	public int deleteStudent(String studentId) {
		int result = sDao.deleteStudent(studentId);
		return result;
	}



	public int updateStudent(Student student) {
		int result = sDao.updateStudent(student);
		return result;
	}



	public Student studentLogin(Student student) {
		Student result = sDao.selectLoginInfo(student);
		return result;
	}









}
