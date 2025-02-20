package com.springbootmy.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootmy.todolist.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}