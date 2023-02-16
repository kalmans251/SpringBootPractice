package com.mysite.sbb4.question;

import java.time.LocalDateTime;
import java.util.List;

import com.mysite.sbb4.answer.Answer;
import com.mysite.sbb4.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
 

@Getter
@Setter
@Entity	// 자바 클래스를 DataBase 의 테이블과 매핑된 클래스 : 테이블명 : question
public class Question {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//시퀀스할당
	private Integer id;
	
	@Column(length = 200)
	private String content;
	
	
	@Column(columnDefinition = "TEXT")	//문자 2Gb까지
	private String subject;
	
	private LocalDateTime createDate; //create_date
	
//	@Column(length=300)
//	private String addr;
	
	//Question 테이블에서 Answer 테이블을 참조하는 컬럼을 생성 @OnetoMany
	@OneToMany(mappedBy = "question",cascade = CascadeType.REMOVE ) //CascadeType.REMOVE는 부모를 삭제시 자식도 삭제되도록 설정.
	private List<Answer> answerList;
	
			//question.getAnswerList(); 로 리스트안의 값들을 가져올수 있다.
	@ManyToOne
	private SiteUser author;
	
	private LocalDateTime modifyDate;
	
}
