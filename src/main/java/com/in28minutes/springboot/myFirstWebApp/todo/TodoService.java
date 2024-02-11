package com.in28minutes.springboot.myFirstWebApp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.print.DocFlavor.INPUT_STREAM;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class TodoService {
	private static List<Todo> todos = new ArrayList<>();
	private static int todoCount = 0;
	
	static {
		todos.add(new Todo(++todoCount, "in28minutes", "Learn AWS Cloud", LocalDate.now().plusYears(1), false));
		todos.add(new Todo(++todoCount, "in28minutes", "Learn DevOps", LocalDate.now().plusYears(2), false));
		todos.add(new Todo(++todoCount, "in28minutes", "Learn Full Stack Development", LocalDate.now().plusYears(3), false));
	}
	
	public List<Todo> findByUser(String username){
		Predicate <? super Todo> predicate = todo -> todo.getUserName().equalsIgnoreCase(username);
		return todos.stream().filter(predicate).toList();
	}
	
	public void addTodo(String userName, String description, LocalDate targetDate, boolean done) {
		todos.add(new Todo(++todoCount, userName, description, targetDate, done));
	}
	
	public void deleteById(int id) {
		Predicate<? super Todo > predicate = todo -> todo.getId()==id;
		todos.removeIf(predicate);
	}
	
	public Todo findById(int id) {
		Predicate<? super Todo > predicate = todo -> todo.getId()==id;
		Todo todo = todos.stream().filter(predicate).findFirst().get();
		return todo;
	}

	public void updateTodo(@Valid Todo todo) {
		deleteById(todo.getId());
		todos.add(todo);
	}
}
