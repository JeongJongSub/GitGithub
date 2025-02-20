package com.ict.springboot.basic.handler.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MultiMethodController {
	
	@GetMapping("/controller/multimethod/list.do")
	public String list(ModelMap model) {
		//데이터 저장
		model.addAttribute("message", "IT REQUESTS LIST");
		//뷰 정보 반환(html 페이지 응답)
		return "controller02/controller";
	}/////list
	
	@GetMapping("/controller/multimethod/edit.do")
	public String edit(Map model) {
		//데이터 저장
		model.put("message", "IT REQUESTS UPDATE");
		//뷰 정보 반환(html 페이지 응답)
		return "controller02/controller";
	}/////list
	@GetMapping("/controller/multimethod/view.do")
	public String view(Model model) {
		//데이터 저장
		model.addAttribute("message", "IT REQUESTS VIEW");
		//뷰 정보 반환(html 페이지 응답)
		return "controller02/controller";
	}/////list
	@GetMapping("/controller/multimethod/delete.do")
	public String delete(Model model) {
		//데이터 저장
		model.addAttribute("message", "IT REQUESTS DELETE");
		//뷰 정보 반환(html 페이지 응답)
		return "controller02/controller";
	}/////list
}
