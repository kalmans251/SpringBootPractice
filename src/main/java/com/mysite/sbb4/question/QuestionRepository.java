package com.mysite.sbb4.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	//JPA에서 Question 테이블을 Select , Insert , Update , Delete
	// JPA의 CRUD 메소드 :
			// save() : insert , update , delete
			// findAll()
			// findById()
			// 사용자 정의해서 사용 : findBySubject()
	
	// Question 테이블을 SQL 쿼리를 사용하지 않고 JPA 메소드를 사용해서 CRUD 하는 Repository
		//JpaRepository<Question ,Integer>
					// Question : JPA 메소드에서 쿼리할 엔티티 클래스
					// Integer : 엔티티 클래스의 Primary key 컬럼의 데이터 타입.
	
	// 하나의 컬럼을 조건으로 처리된 값 가져오기.
	// findById() : Question 테이블의 Primary Key 컬럼이므로 기본 제공됨.
	
	
	//select * from question where subject like '%ssb%' 검색 결과가 여러개일 경우 List<Question>
	List<Question> findBySubjectLike(String subject);
	
	//select * from where content = ? 검색 결과가 1개일때 Question
	Question findByContent(String content);
	
	//content 컬럼을 조건으로 검색
	// select * from question where content like '%내용%'
	List<Question> findByContentLike(String content);
	
	//select * from question where subject like '%sbb%' and content like '%내용%'
	List<Question> findBySubjectLikeAndContentLike(String subject,String content);
	
	//select * from question where subject = ? order by createDate asc
	//select * from question where subject = ? order by createDate desc
	List<Question> findBySubjectLikeOrderByCreateDateAsc(String subject); //오름차순
	
	List<Question> findAllByOrderByCreateDateAsc();
	List<Question> findAllByOrderByCreateDateDesc();
	
	//Update : save()
	
	//Delete : delete()
	
	//페이징을 처리하기 위한 메소드 생성
	//select * from question : pageable 변수에 : page , 레코드수를 넣어주면 자동으로 페이징 처리해줌.
	//출력할 레코드수를 JPA에 알려주면 내부에서 JPA가 전체 레코드(1000)/10 개의 페이지가 나온다.
	Page<Question> findAll(Pageable pageable);
	
}
