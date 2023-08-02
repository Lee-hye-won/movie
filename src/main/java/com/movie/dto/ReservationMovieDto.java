package com.movie.dto;

import com.movie.entity.Movie;
import com.movie.entity.Reservation;
import com.movie.entity.ReservationMovie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationMovieDto {

	// 엔티티 -> dto
	public ReservationMovieDto(ReservationMovie reservationMovie) {
		//this.title = reservationMovie.getMovie().getTitle();
		
		this.count = reservationMovie.getCount();
		this.movie = reservationMovie.getMovie();
		this.resNum = reservationMovie.getReservation();

	}
	
	private String title;	// 영화명
	private int count;
	private Movie movie;
	private Reservation resNum;
	private int orderPrice;
}
