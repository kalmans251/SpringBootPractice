package com.mysite.sbb4;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb4.question.Question;
import com.mysite.sbb4.question.QuestionRepository;

@SpringBootTest
class Sbb4ApplicationTests {
	
	@Autowired
	private QuestionRepository questionRepository; 
	
	@Test
	void contextLoads() {
		
		Question q = null;
		List<Question> list_q=new ArrayList<Question>();
		for(int i = 0 ; i < 1000 ; i++) {
			q=new Question();
			q.setContent("컨텐츠"+(i+1));
			q.setCreateDate(LocalDateTime.now());
			q.setSubject("주제"+(i+1));
			list_q.add(q);
			q=null;
		}
		this.questionRepository.saveAll(list_q);
	}

}
