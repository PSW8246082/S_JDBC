package com.kh.jdbc.day04.student.view;

import java.util.*;

import com.kh.jdbc.day04.student.controller.StudentController;
import com.kh.jdbc.day04.student.model.vo.Student;



public class StudentView {
	
private StudentController controller;
	
	public StudentView() {
		controller = new StudentController();
	}

	public void startProgram() {
		
		List<Student> sList  = null;
		Student student = null;
		
		end:
		while(true) {
			int choice = printMenu();
			switch(choice) {
			case 1 : 
				//SELECT * FROM STUDENT_TBL
				sList = controller.selectAll();
				if(!sList.isEmpty()) {
					showAllStudent(sList);
				} else {
					displayError("해당 정보가 존재하지 않습니다.");
				}
				break;
			case 2 :
				//SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
				String studentId = inputStudentId("검색");
				student = controller.printStudenBytId(studentId);
				if(student != null) {
					showStudent(student);
				} else {
					displayError("해당 정보가 존재하지 않습니다.");
				}
				break;
			case 3 :
				String studentName = inputStudentName();
				//입력받은 이름을 매개변수로 연결하여 학생정보 출력하기(정보=>여러개=>리스트로 받기)
				sList = controller.selectAllByName(studentName);
				if(!sList.isEmpty()) {
					showAllStudent(sList);
				} else {
					displayError("해당 정보가 존재하지 않습니다.");
				}
				break;
			case 4 : 
				student = inputStudent(); 
				int result = controller.insertStudent(student);
				if(result > 0) {
					displaySuccess("데이터 등록 성공");
				} else {
					displayError("데이터 등록 실패.");
				}
				break;
			case 5 : 
				studentId = inputStudentId("수정");
				student = controller.printStudenBytId(studentId);
				if(student != null) {
					//있는거
					//수정할 정보 입력받기
					student = modifyStudent(student);
					//student.setStudentId(studentId);
					//입력받은 정보 객체에 넣어 변경해주기
					result = controller.updateStudent(student);
					if(result > 0) {
						displaySuccess("데이터 수정 성공");
					} else {
						displayError("데이터 수정 실패.");
					}
				} else {
					displayError("데이터 조회 실패.");
				}
				break;
			case 6 : 
				studentId = inputStudentId("삭제");  
				//dao에서 삭제하기
				result = controller.deleteStudent(studentId);
				if(result > 0) {
					displaySuccess("데이터 삭제 성공");
				} else {
					displayError("데이터 삭제 실패.");
				}
				break;
			case 0 : break end;
			}
		}
	}

	private Student modifyStudent(Student student) {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 정보 수정 =====");
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine();    //공백제거, 엔터제거
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.next();
		student.setStudentPwd(studentPw);
		student.setEmail(email);
		student.setPhone(phone);
		student.setAddress(address);
		student.setHobby(hobby);
		return student;
	}

	private String inputStudentName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 이름로 조회하기 =====");
		System.out.print("학생 이름 입력 : ");
		String studentName = sc.next();
		return studentName;
	}

	private int printMenu() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("===== 학생관리 프로그램 =====");
		System.out.println("1. 학생 전체 조회");
		System.out.println("2. 학생 아이디로 조회");
		System.out.println("3. 학생 이름으로 조회");
		System.out.println("4. 학생 정보 등록");
		System.out.println("5. 학생 정보 수정");
		System.out.println("6. 학생 정보 삭제");
		System.out.println("0. 프로그램 종료");
		System.out.print("메뉴 선택 : ");
		int input = sc.nextInt();
		return input;
	}
	
	private void showAllStudent(List<Student> sList) {
		System.out.println("===== 학생 전체 조회 =====");
		for(Student student : sList) {
			System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성별 : %s, "
					+ "이메일 : %s, 전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %s\n"
					, student.getStudentName()
					, student.getAge()
					, student.getStudentId()
					, student.getGender()
					, student.getEmail()
					, student.getPhone()
					, student.getAddress()
					, student.getHobby()
					, student.getEnrollDate());
		}
	}
	
	private void displaySuccess(String message) {
		System.out.println("[서비스 성공] : " + message );
	}

	private void displayError(String message) {
		System.out.println("[서비스 실패] : " + message );
	}
	
	private Student inputStudent() {
		Scanner sc = new Scanner(System.in); 
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		System.out.print("이름 : ");
		String studentName = sc.next();
		System.out.print("성별 : ");
		char gender = sc.next().charAt(0);
		System.out.print("나이 : ");
		int age = sc.nextInt();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine();    //공백제거, 엔터제거
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.next();
		
		Student student = new Student(studentId, studentPw, studentName, gender, age, email, phone, address, hobby);
		return student;
	}

	
	private void showStudent(Student student) {
		System.out.println("===== 학생 정보 출력 (아이디로 조회) =====");
		System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성별 : %s, "
				+ "이메일 : %s, 전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %s\n"
				, student.getStudentName()
				, student.getAge()
				, student.getStudentId()
				, student.getGender()
				, student.getEmail()
				, student.getPhone()
				, student.getAddress()
				, student.getHobby()
				, student.getEnrollDate());		
	}
	
	private String inputStudentId(String category) {
		Scanner sc = new Scanner(System.in);
		System.out.print(category + "할 학생 아이디 입력 : ");
		String studentId = sc.next();
		return studentId;
	}
	
	
}
