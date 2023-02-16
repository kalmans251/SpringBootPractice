package com.mysite.sbb4.answer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.sbb4.question.Question;



public interface AnswerRepository extends JpaRepository<Answer, Integer> {
	// JPA 에서 사용가능한 메소드 상속됨.
		//findAll()
		//findById()
		//save()
		//delete()
	public List<Answer> findAllByQuestion(Question question);
	
	
}
