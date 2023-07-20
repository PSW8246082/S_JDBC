package com.kh.jdbc.day01.student.controller;

import java.util.List;

import com.kh.jdbc.day01.student.model.dao.StudentDAO;
import com.kh.jdbc.day01.student.model.vo.Student;

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

}
