package com.ict.springboot.service;

import org.springframework.data.domain.Page;

public class PagingUtility {
	
	public static <T> String pagingStyle(Page<T> page,int blockPage,String url){
		
		String paging="<ul class=\"pagination justify-content-center\"  style=\"margin:20px 0\">";
		
		
		
		int totalPage= page.getTotalPages();
		//Pageable에서는 0번 페이지부터 시작함으로 page.getNumber()+1
		int intTemp = ((page.getNumber()+1 - 1) / blockPage) * blockPage + 1;

		//처음 및 이전을 위한 로직
		if(intTemp != 1){
			paging+="<li>\r\n" + 
					"<a  class=\"page-link\" href='"+url+"page=1'>\r\n" + 
					"<span><i class=\"fa-solid fa-backward-fast\"></i></span>\r\n" + 
					"</a>\r\n" + 
					"</li>\r\n" + 
					"<li >\r\n" + 
					"<a class=\"page-link\" href='"+url+"page="+(intTemp -blockPage)+"' >\r\n" + 
					"<span><i class=\"fa-solid fa-backward-step\"></i></span>\r\n" + 
					"</a>\r\n" + 
					"</li>";   
			
		}
		
		//페이지 표시 제어를 위한 변수
		int blockCount = 1;
		
		//페이지를 뿌려주는 로직
		//블락 페이지 수만큼 혹은 마지막 페이지가 될때까지 페이지를 표시한다1 
		while(blockCount <= blockPage && intTemp <= totalPage){  // 페이지 오버 를 체크
				//현재 페이지를 의미함
			if(intTemp == page.getNumber()+1){  
				paging+="<li class=\"page-item\"><a class=\"page-link\" href='#'><span class=\"text-danger\">"+intTemp+"</span></a></li>";
			}
		     else
		    	 paging+="<li class=\"page-item\"><a class=\"page-link\" href='"+url+"page="+intTemp+"'>"+intTemp+"</a></li>";
		       
			intTemp = intTemp + 1;
			blockCount = blockCount + 1;
		
		}

		//다음 및 마지막을 위한 로직
			
		if(intTemp <= totalPage){
			paging+="<li>\r\n" + 
					"<a class=\"page-link\" href='"+url+"page="+intTemp+"'>\r\n" + 
					"<span><i class=\"fa-solid fa-forward-step\"></i><span>\r\n" + 
					"</a>\r\n" + 
					"</li>\r\n" + 
					"<li class=\"page-item\">\r\n" + 
					"<a class=\"page-link\" href='"+url+"page="+totalPage+"' >\r\n" + 
					"<span><i class=\"fa-solid fa-forward-fast\"></i></span>\r\n" + 
					"</a>\r\n" + 
					"</li>";
							   
		}
		paging+="</ul>";
		return paging;
	}//////////////////////////////////////

}
