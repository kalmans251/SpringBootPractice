package com.mysite.sbb4.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb4.answer.AnswerForm;
import com.mysite.sbb4.question.Question;
import com.mysite.sbb4.question.QuestionForm;
import com.mysite.sbb4.service.QuestionService;
import com.mysite.sbb4.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final 필드의 생성자를 자동으로 만들어서 생성자를 통한 의존성주입
@Controller
public class QuestionController {
	
	/*  클래스를 객체로 생성 어노테이션
	 	@Component : 일반적인 클래스를 객체화
	 	@Controller : 클라이언트 요청을 받아서 처리 , Controller
	 		1. 클라이언트 요청을 받는다. @GetMapping() , @PostMapping(),
	 		2. 비즈니스 로직 처리, Service의 메소드 호출,
	 		3. View 페이지로 응답
	 
	 	@Service : DAO의 메소드를 인터페이스로 생성후 인터페이스의 메소드를 구현한 클래스
	 		- 유지보수를 쉽게하기 위해서 (약결합)
	 	@Repository : DAO 클래스를 Bean등록 해준다.
	 	@Entity : DTO클래스를 Bean등록 해주며 DB에 테이블 조작.
	 */
	
	/*  DI 주입 방법.
	  	(의존성주입)
	
	  	1.생성자로 주입 -> 권장 방식
	  	2.메소드 setter로 주입 : void setClass(객체타입 DAO()){}
	  	3.framework에 객체화된 클래스 주입 : @Autowired
	 */
	
	//생성자를 통한 의존성 주입을 권장
	/*
	@Autowired
	private final QuestionRepository questionrepository;
	*/
	//private final QuestionRepository questionrepository;
	
	private final QuestionService questionService;
	
	private final UserService userService;
	
	/*
	@GetMapping("/question/list")
	//@PostMapping("/question/list")
	public String list(Model model) { //모델 객체가 자동으로 주입
		//1. 클라이언트 요청정보
	
		//2. 비즈니스 로직을 처리
		List<Question> questionList = this.questionrepository.findAll();
	
		
		//3. 뷰페이지로 이동
			//Model : View페이지로 서버의 데이터를 담아서 전송하는 객체
		model.addAttribute("questionList",questionList);
		return "question_list.html";
	}
	*/
	 
	@GetMapping("/question/list")
	public String list(Model model,@RequestParam(value="page",defaultValue="0") int page) {
		
		
		Page<Question> paging = this.questionService.getList(page, 10);	
		
		model.addAttribute("paging", paging);
		
		return "question_list";
	}
	
	
	
	@GetMapping("/question/detail/{id}")
	public String detail(Model model,@PathVariable("id") Integer id, AnswerForm answerForm) {
		Question q = this.questionService.getQuestion(id);
		model.addAttribute("gd", q);
		return "question_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/question/create")
	public String questionCreate(QuestionForm ss) {
		System.out.println(ss);
		ss.setContent("으헝으헝");
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/question/create")
	public String questionCreate(//@RequestParam String subject,@RequestParam String content
			@Valid QuestionForm ssrms,BindingResult bindingResult,Principal principal) {
		System.out.println(ssrms);
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		//this.questionService.saveQuestion(subject, content);
		
		this.questionService.saveQuestion(ssrms.getSubject(), ssrms.getContent(),this.userService.getUser(principal.getName()));
		return "redirect:/question/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/question/modify/{id}")
	public String questionModify(QuestionForm questionForm,@PathVariable("id") Integer id,Principal principal){
		
		Question question = this.questionService.getQuestion(id);
		
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다");
		}
		
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/question/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm,BindingResult bindingResult,Principal principal,@PathVariable("id") Integer id) {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		Question question = this.questionService.getQuestion(id);
		
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
		}
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		
		return String.format("redirect:/question/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/question/delete/{id}")
	public String questionDelete(Principal principal,@PathVariable("id") Integer id) {
		
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다");
		}
		
		this.questionService.delete(question);
		
		return "redirect:/";
		
		
	}
	
}
