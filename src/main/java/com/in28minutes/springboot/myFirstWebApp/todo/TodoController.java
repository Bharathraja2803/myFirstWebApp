package com.in28minutes.springboot.myFirstWebApp.todo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class TodoController {
	private TodoService todoService;
	
	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}


	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		List<Todo> todos = todoService.findByUser("in28minutes");
//		model.put("todos", todos);
		model.addAttribute("todos", todos);
		return "list-todos";
	}
}
