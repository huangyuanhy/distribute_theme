package com.taotao.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
@Component
@ComponentScan()
public class GlobalException implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		//日志写入文件
		System.out.println(ex.getMessage());
		ex.printStackTrace();
		//通知开发人员 短信、邮件
			
		//给出友好提示
		ModelAndView modelAndView=new ModelAndView();
		//设置返回的页面
		modelAndView.setViewName("error/exception");
		//返回视图中所需要的数据
		modelAndView.addObject("message","系统出错，请重试");
		return modelAndView;
	}

}
