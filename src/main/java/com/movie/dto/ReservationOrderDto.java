package com.movie.dto;

import java.util.List;

import com.movie.entity.Member;
import com.movie.entity.Reservation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationOrderDto {

	private Long movieId;
	
	private String resMovieName;

	private String resDate;
	
	@Min(value = 1, message = "최소 예매수량은 1개 입니다")
	@Max(value = 6, message = "최대 예매수량은6개 입니다")
	private int resPeople;
	
	private List<String> seatLine;	// 예매좌석 열
	private List<String> seatRow;	// 예매좌석 행

	
}
