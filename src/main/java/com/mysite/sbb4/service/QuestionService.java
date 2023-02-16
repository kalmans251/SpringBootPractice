package com.mysite.sbb4.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb4.DataNotFoundException;
import com.mysite.sbb4.question.Question;
import com.mysite.sbb4.question.QuestionRepository;
import com.mysite.sbb4.user.SiteUser;
import com.mysite.sbb4.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	private final UserRepository userRepository;
//	public List<Question> getList(){ //2월 14일 수정됨 : Paging 처리를 위해
//		return this.questionRepository.findAll();
//	}
	
	
	
	
	public Page<Question> getList(int page,int count){
		//Pageable 변수에 2개의 값을 담아서 매개변수로 던짐
		List<Sort.Order> sorts = new ArrayList();
		sorts.add(Sort.Order.desc("createDate"));
		
		
		Pageable pageable = PageRequest.of(page, count ,Sort.by(sorts));
		return this.questionRepository.findAll(pageable);
	}
	

	
	
	
	public Question getQuestion(Integer id) {
		
		Optional<Question> op = this.questionRepository.findById(id);
		if(op.isPresent()) {
			return op.get();	//Question 객체를 리턴
		}else {
			//사용자 정의 예외:
			//throw : 예외를 강제로 발생
			//throws : 예외를 요청한 곳에서 처리하도록 미루는것
			throw new DataNotFoundException("요청한 파일을 찾지 못했습니다.");
		}
		
	}
	
	public void saveQuestion(String subject,String content,SiteUser siteuser ) {
		Question q= new Question();
		q.setContent(content);
		q.setSubject(subject);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(siteuser);
		
		this.questionRepository.save(q);
	}
	
	public void modify(Question question,String subject,String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
		
	}
	
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
}
