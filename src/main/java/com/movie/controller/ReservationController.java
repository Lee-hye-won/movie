package com.movie.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.dto.MovieFormDto;
import com.movie.dto.ReservationOrderDto;
import com.movie.service.MovieService;
import com.movie.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReservationController {

	private final MovieService movieService;
	private final ReservationService reservationService;
	
	// 예매하기로 넘어가기
		@GetMapping(value = {"/movie/{movieId}/reservation", "/movie/reservation/{movieId}"}) 
		public String movieRes(Model model, @PathVariable("movieId") Long movieId) {
			
			MovieFormDto movieFormDto = movieService.getMovieDtl(movieId);
			model.addAttribute("movie", movieFormDto);
			
			return "screenInfo/screen";
		}
		
		
		@PostMapping(value = "/order")
		public @ResponseBody ResponseEntity order(@RequestBody @Valid ReservationOrderDto reservationOrderDto,
					BindingResult bindingResult, Principal principal) {
			
			//바인딩 에러 처리. 
			if(bindingResult.hasErrors()) {
				StringBuilder sb = new StringBuilder();
				List<FieldError> fieldErrors = bindingResult.getFieldErrors();
				
				for(FieldError fieldError : fieldErrors) {
					sb.append(fieldError.getDefaultMessage()); //에러메시지를 합친다.
				}
				
				return new ResponseEntity<String>(sb.toString(),HttpStatus.BAD_REQUEST);
				
				// List<Object> selected = new ArrayList<>();
				
				
			}
			
			
			
			
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		// 주문 기능 
		
}
