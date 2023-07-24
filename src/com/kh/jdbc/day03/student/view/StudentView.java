package com.kh.jdbc.day03.student.view;

import java.util.List;
import java.util.Scanner;

import com.kh.jdbc.day03.student.controller.StudentController;
import com.kh.jdbc.day03.student.vo.Student;

public class StudentView {   //화면 출력
	
	private StudentController controller;
	
	public StudentView() {
		controller = new StudentController();
	}
	
	public void studentProgram() {
		List<Student> sList  = null;
		Student student = null;
		end:
		while(true) {
			int input = printMenu();
			switch(input) {
				case 1 : 
					sList = controller.selectAllStudent();
					showAllStudent(sList);
					break;
				case 2 :
					//아이디로 학생정보 조회하기  //SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
					//아이디 입력받기
					String studentId = inputStudentId("검색");
					//입력받은 아이디를 매개변수로 연결하여 학생정보 출력하기(정보=>한개=>객체 자체로 받기)
					student = controller.printStudenBytId(studentId);
					showStudent(student);
					break;
				case 3 : 
					//이름으로 학생정보 조회하기  //SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '일용자'
					//이름 입력받기
					String studentName = inputStudentName();
					//입력받은 이름을 매개변수로 연결하여 학생정보 출력하기(정보=>여러개=>리스트로 받기)
					sList = controller.selectAllByName(studentName);
					showAllStudent(sList);
					break;
				case 4 : 
					//학생 정보 등록하기 INSERT INTO STUDENT_TBL VALUES('khuser01', 'pass01', '일용자', 'M', 11, 'khuser01@kh.com', '01012345678'
					//,'서울시 중구 남대문로 120', '독서, 수영', SYSDATE);    
					// -> ~행이 삽입되었습니다.  숫자로 리턴됨
					//학생 정보 입력받기
					student = inputStudent(); 
					//입력받은 정보 넣어주기.
					int result = controller.insertStudent(student);
					break;
				case 5 : 
					//학생 정보 수정 
					//UPDATE STUDENT_TBL 
					//SET STUDENT_PWD = 'pass011' ,EMAIL = 'khuser011@kh.com' ,PHONE = '01101234567'
					// ,ADDRESS = '서울시 중구 남대문로 1120' ,HOBBY = '독서1, 수영1'
					//WHERE STUDENT_ID = 'khuser01';
					
					
					//아이디로 검색해서 존재 여부 확인하기
					//SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'; 
					studentId = inputStudentId("수정");
					student = controller.printStudenBytId(studentId);
					if(student != null) {
						//있는거
						//수정할 정보 입력받기
						student = modifyStudent();
						student.setStudentId(studentId);
						//입력받은 정보 객체에 넣어 변경해주기
						result = controller.updateStudent(student);
					} else {
						//없는거
					}
					break;
				case 6 : 
					//학생 정보 삭제 DELETE FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01';
					//삭제할 아이디 입력받기 -> int 값으로 리턴됨
					studentId = inputStudentId("삭제");  
					//dao에서 삭제하기
					result = controller.deleteStudent(studentId);
					
					break;
				case 9 :
					//SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01' AND STUDENT_PWD = '1234'
					student = inputLoginInfo();
					student = controller.studentLogin(student);
					if(student != null) {
						//로그인 성공
						displaySuccess("로그인 성공");
					} else {
						//로그인 실패
						displayError("해당 정보가 존재하지 않습니다.");
					}
					break;
				case 0 : 
					break end;
			}
		}
	}

	
	


	

	private void displaySuccess(String message) {
		System.out.println("[서비스 성공] : " + message );
	}

	private void displayError(String message) {
		System.out.println("[서비스 실패] : " + message );
	}
	
	private Student inputLoginInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 로그인 =====");
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		Student student = new Student(studentId, studentPw);
		return student;
	}

	private Student modifyStudent() {
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
		Student student = new Student(studentPw, email, phone, address, hobby);
		return student;
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

	private String inputStudentName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 이름로 조회하기 =====");
		System.out.print("학생 이름 입력 : ");
		String studentName = sc.next();
		return studentName;
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

	private int printMenu() {
		Scanner sc = new Scanner(System.in);		
		System.out.println("===== 학생관리 프로그램 =====");
		System.out.println("9. 학생 로그인");
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
	
	
	
	
	
}
