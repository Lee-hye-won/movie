package com.movie.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.movie.entity.Reservation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationHistDto {

	public ReservationHistDto(Reservation reservation) {
		this.orderId = reservation.getResMovieNum();
		
	}
	
	private Long orderId;
	

	
	private List<ReservationMovieDto> reservationMovieDtoList = new ArrayList<>();
	
	public void addReservationMovieDto (ReservationMovieDto reservationMovieDto) {
		this.reservationMovieDtoList.add(reservationMovieDto);
	}
	
}
