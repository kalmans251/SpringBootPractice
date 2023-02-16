package com.mysite.sbb4.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mysite.sbb4.answer.AnswerForm;
import com.mysite.sbb4.question.Question;
import com.mysite.sbb4.service.AnswerService;
import com.mysite.sbb4.service.QuestionService;
import com.mysite.sbb4.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class AnswerController {
	
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;
	
	//http://localhost:9292/answer/create/1 요청에 대한 답변글 등록 처리
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/answer/create/{id}")
	public String createAnswer(
			//<<Validation 전 구성>>
			//Model model,@PathVariable("id") Integer id, @RequestParam String content
			
			//content의 유효성 검사
			Model model,@PathVariable("id") Integer id,
			@Valid AnswerForm af,BindingResult bindingResult,Principal principal
			) {
		
		Question question = this.questionService.getQuestion(id);
		// content 의 값이 비어 있을때,
		if(bindingResult.hasErrors()) {
			model.addAttribute("gd",question);
			return "question_detail";
		}
		
		System.out.println("값 존재");
			
		this.answerService.create(question, af.getContent(),this.userService.getUser(principal.getName()));
			
		return String.format("redirect:/question/detail/%s", id);
		
		
	}
	
}
