package com.movie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	String uploadPath = "file:///C:/movie";	// 업로드할 경로

	// 웹 브라우저에서 url이 img로 시작하는 경우 uploadPath에 설정한 폴더를 가져온다.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/img/**")
				.addResourceLocations(uploadPath);
	}
	
	
}
