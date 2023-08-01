package com.movie.dto;

import com.movie.entity.ReservationMovie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationMovieDto {

	// 엔티티 -> dto
	public ReservationMovieDto(ReservationMovie reservationMovie, String string) {
		this.title = reservationMovie.getMovie().getTitle();
		this.count = reservationMovie.getCount();
		
	}
	
	private String title;	// 영화명
	private int count;
}
