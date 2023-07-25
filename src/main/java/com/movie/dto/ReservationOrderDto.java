package com.movie.dto;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationOrderDto {

	@Min(value = 1, message = "최소 예매좌석은 1개 입니다")
	@Max(value = 6, message = "최소 예매좌석은 6개 입니다")
	private int resPeople;	
	
//	private List<String> seatLine;	// 좌석 열 정보
//	private List<String> seatRow;	// 좌석 행 정보
	
	private List<String> selected;
	

}
