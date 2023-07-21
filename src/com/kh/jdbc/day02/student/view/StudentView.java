package com.kh.jdbc.day02.student.view;

import java.util.*;

import com.kh.jdbc.day02.student.controller.StudentController;
import com.kh.jdbc.day02.student.model.vo.Student;

public class StudentView {
	
	private StudentController controller;
	
	public StudentView() {
		controller = new StudentController();
	}

	
	
	
	public void startProgram() {
	
		Student student = null;
		List<Student> sList = null;
		
		
		finish :
		while(true) {
			
			int choice = this.printMenu();
			
			switch(choice) {
			case 1 :
				//쿼리문을 보고 리턴값을 예상할 수 있음
				// SELECT * FROM STUDENT_TBL
				sList = 
				controller.printAllStudentList();
				//데이터가 있을때만 출력
				if(!sList.isEmpty()) {
					showAllStudents(sList);
				} else {
					displayError("학생 정보가 조회되지 않습니다.");
				}
				break;
			case 2 : 
				//아이디로 조회하는 쿼리문 (리턴형(List아니고 Student -> 값 하나이기때문에), 매개변수 생각해보기)
				//SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
				String studentId = inputStudentId();
				//printStrudentById() 메소드가 학생 정보를 조회, dao의 메소드는 selectOneById()로 명명
				student = controller.printStrudentById(studentId);
				//showStudent() 메소드로 학생 정보 출력
				if(student != null) {
					showStudent(student);
				} else {
					displayError("학생 정보가 존재하지 않습니다.");
				}
			
				break;
			case 3 : 
				// 이름으로 조회하는 쿼리문 생각해보기(매개변수 유무, 리턴형)
				String studentName = inputStudentName();
				//printStudentByName, printStudentsByName (?)   -> 이름 중복 가능하므로 sList로 받기
				sList = controller.printStudentsByName(studentName);
				//selectOneByName, selectAllByName (?)
				//showStudent, showAllStudents (?)
				if(!sList.isEmpty()) {
					showAllStudents(sList);     
				} else {
					displayError("학생 정보가 조회되지 않습니다.");
				}
				        
				break;
			case 4 : 
				//--데이터 넣기
				//INSERT INTO STUDENT_TBL VALUES ('khuser01', 'pass01', '일용자', 'M', 11, 'khuser01@kh.com', '01012345678'
				//,'서울시 중구 남대문로 120', '독서, 수영', SYSDATE);
				
				student = inputStudent();  //입력받는 메소드
				int result = controller.insertStudent(student);  //입력하는 메소드  -  int값으로 dao에서 넘어옴
				if(result > 0) {
					//성공메세지 출력
					displaySuccess("학생 정보 등록 성공");
				} else {
					//실패메세지 출력
					displayError("학생 정보 등록 실패");
				}
				break;
			case 5 : 
				//UPDATE STUDENT_TBL 
				//SET STUDENT_PWD = 'pass011' ,EMAIL = 'khuser011@kh.com' ,PHONE = '01101234567'
				// ,ADDRESS = '서울시 중구 남대문로 1120' ,HOBBY = '독서1, 수영1'
				//WHERE STUDENT_ID = 'khuser01';
				student = modifyStudent();
				result = controller.modifyStudent(student);
				if(result > 0) {
					//성공메세지 출력
					displaySuccess("학생 정보 수정 성공");
				} else {
					//실패메세지 출력
					displayError("학생 정보 수정 실패");
				}
				break;
			case 6 : 
				//삭제 쿼리문 생각해보기(매개변수 필요 유무, 반환형?)
				//DELETE FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01';
				studentId = inputStudentId();    //입력하는 메소드  -  int값으로 dao에서 넘어옴
				result = controller.deleteStudent(studentId);
				if(result > 0) {
					//성공메세지 출력
					displaySuccess("학생 정보 삭제 성공");
				} else {
					//실패메세지 출력
					displayError("학생 정보 삭제 실패");
				}
				break;
			case 0 : break finish;
			}
		}
	}
	
	
	
	
	
	
	public int printMenu() {
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




	private String inputStudentId() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 아이디로 조회하기 =====");
		System.out.print("학생 아이디 입력 : ");
		String studentId = sc.next();
		return studentId;
	}




	private String inputStudentName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 이름으로 조회하기 =====");
		System.out.print("학생 이름 입력 : ");
		String studentName = sc.next();
		return studentName;
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




	private Student modifyStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 정보 수정 =====");
		System.out.println("아이디 : ");
		String studentId = sc.next();
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
		Student student = new Student(studentId, studentPw, email, phone, address, hobby);
		return student;
	}




	private void displaySuccess(String message) {
		System.out.println("[서비스 성공] : " + message);
		
	}




	private void displayError(String message) {
		System.out.println("[서비스 실패] : " + message);
		
	}




	private void showAllStudents(List<Student> sList) {
		System.out.println("===== 학생 전체 정보 출력 =====");
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
	
	
}
