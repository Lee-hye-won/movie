package com.movie.dto;

import org.modelmapper.ModelMapper;

import com.movie.entity.Movie;
import com.movie.entity.MovieImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieImgDto {
	private Long id;
	private String imgName;
	private String oriImgName;
	private String imgUrl;
	private String repimgYn;
	private Movie movie;
	private static ModelMapper modelMapper = new ModelMapper();
	
	// entity -> dto
	public static MovieImgDto of(MovieImg movieImg) {
		return modelMapper.map(movieImg, MovieImgDto.class);
	}
}
