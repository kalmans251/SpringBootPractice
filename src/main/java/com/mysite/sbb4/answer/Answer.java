package com.mysite.sbb4.answer;

import java.time.LocalDateTime;

import com.mysite.sbb4.question.Question;
import com.mysite.sbb4.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // JPA 에서 자바객체를 데이터 DB의 테이블에 맵핑설정.
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;		//Primary Key , 자동 증가 ( 1 , 1) (초기값1,증가값1)
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	@ManyToOne // Answer이 many Question이 one,,  Foreign key : 부모테이블의 PK , UK 컬럼의 값을 참조해서 값을 할당.
	private Question question;	//부모테이블이 Question 테이블의 Primary Key 를 참조 (id)
	
	@ManyToOne
	private SiteUser author;
	
	private LocalDateTime modifyDate;
	
}
