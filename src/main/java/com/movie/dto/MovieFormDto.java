package com.movie.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.movie.entity.Movie;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieFormDto {
	
	private Long id;

	@NotBlank(message = "제목은 필수 입력값입니다.")
	private String title;
	
	@NotBlank(message = "제목은 필수 입력값입니다.")
	private String director;
	
	@NotBlank(message = "제목은 필수 입력값입니다.")
	private String actors;
	
	@NotBlank(message = "제목은 필수 입력값입니다.")
	private String genre;
	
	private String runtime;
	
	private String openDate;
	
	@NotBlank(message = "제목은 필수 입력값입니다.")
	private String intro;
	
	private Long scrNum; 
	
	// 영화 이미지 정보를 저장
	private List<MovieImgDto> movieImgDtoList = new ArrayList<>();
	
	// 영화이미지 아이디 저장 -> 수정시에 불러올 때 필요
	private List<Long> movieImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	// dto -> entity
	public Movie createMovie() {
		return modelMapper.map(this, Movie.class);
	}
	
	// entity -> dto
	public static MovieFormDto of(Movie movie) {
		return modelMapper.map(movie, MovieFormDto.class);
	}
	
}
