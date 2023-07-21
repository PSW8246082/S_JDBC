package com.kh.jdbc.day02.student.controller;

import java.util.List;

import com.kh.jdbc.day02.student.model.dao.StudentDAO;
import com.kh.jdbc.day02.student.model.vo.Student;

public class StudentController {
	
	private StudentDAO StudentDao;
	
	public StudentController() {
		StudentDao = new StudentDAO();
	}

	public List<Student> printAllStudentList() {
		// cannot be resolved - 적혀있지 않은
		List<Student> sList = StudentDao.selectAll();
		return sList;
	}

	public List<Student> printStudentsByName(String studentName) {
		List<Student> sList = StudentDao.selectAllByName(studentName);
		return sList;
	}

	public Student printStrudentById(String studentId) {
		Student student = StudentDao.selectOneById(studentId);
		return student;
	}

	public int insertStudent(Student student) {
		int result = StudentDao.insertStudent(student);
		return result;
	}

	public int modifyStudent(Student student) {
		int result = StudentDao.updateStudent(student);
		return result;
	}

	public int deleteStudent(String studentId) {
		int result = StudentDao.deleteStudent(studentId);
		return result;
	}

}
