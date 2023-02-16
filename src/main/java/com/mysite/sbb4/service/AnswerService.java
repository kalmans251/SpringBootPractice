package com.mysite.sbb4.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mysite.sbb4.answer.Answer;
import com.mysite.sbb4.answer.AnswerRepository;
import com.mysite.sbb4.question.Question;
import com.mysite.sbb4.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor		//DI
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	//답변글을 저장하는 메소드 , Controller 에서 Question 생성해서 아규먼트로 인풋
	public void create(Question question, String content,SiteUser author) {
		
		//Answer 객체를 
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		
		this.answerRepository.save(answer);
		
	}
	
}
