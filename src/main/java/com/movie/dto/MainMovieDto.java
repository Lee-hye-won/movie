package com.movie.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainMovieDto {
	private Long id;
	private String title;
	private String imgUrl;


	@QueryProjection
	public MainMovieDto(Long id, String title, String imgUrl) {
		this.id = id;
		this.title = title;
		this.imgUrl = imgUrl;
	}
}
