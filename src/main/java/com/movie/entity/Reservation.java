package com.movie.entity;

import java.util.ArrayList;
import java.util.List;

import com.movie.dto.ReservationMovieDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@ToString
public class Reservation {

	@Id
	@Column(name = "res_num")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long resNum; // 예약 고유번호
	// --------------------------
	@Column(name = "res_movie_num")
	private Long resMovieNum; // 예매 영화 고유번호

	@Column(name = "res_movie_name")
	private String resMovieName; // 예매 영화제목

	@Column(name = "res_screen_num")
	private Long resScreenNum; // 예매 영화 상영관번호

	@Column(name = "res_screen_name")
	private String resScreenName; // 예약 상영관명
	// --------------------------

	@Column(name = "res_date")
	private String resDate; // 예매 상영날짜

	@Column(name = "res_time")
	private String resTime; // 예매 상영시간

	@Column(name = "res_seat")
	private String resSeat; // 예매 상영좌석

	@Column(name = "res_people")
	private int resPeople; // 예매 인원수

	@Column(name = "res_member_id")
	private String resMemberId; // 예매한 관객 아이디

	@Column(name = "res_paytime")
	private String resPaytime; // 예매 시간

	@Column(name = "res_ pay")
	private Long resPay; // 예매 결제금액

//	@Column(name="seatLine")
//	private String seatLine;	// 예매좌석 열
//	
//	@Column(name="seatRow")
//	private String seatRow;	// 예매좌석 행

	@Column(name = "seat")
	private String seat;

	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

//	@OneToMany(mappedBy = "reservation",
//			cascade = CascadeType.ALL,
//			orphanRemoval = true, fetch = FetchType.LAZY)
//	private ReservationMovie reservationMovie;
//	private List<ReservationMovie> reservationMovies = new ArrayList<>();

//	public void addOrderItem(ReservationMovie reservationMovie) {
//		reservationMovie.setReservation(this);
//	}

	// Reservation 객체생성
	public static Reservation createOrder(Member member, ReservationMovie reservationMovie,
			ReservationMovieDto reservationMovieDto, List<String> seatLine, List<String> seatRow) {
		Reservation reservation = new Reservation();
		reservation.setMember(member);

		String totalSeat = null;
//
//		List<String> seatList = seat;
//
//		int size = 0;
//
//		for (String seats : seatList) {
//			if (size == 0) {
//				totalSeat = seats;
//			} else {
//				totalSeat += ", " + seats;
//			}
//			++size;
//		}
		//reservationMovie.setSeat(totalSeat);

		reservation.setSeat(totalSeat);
//		reservation.setReservationMovie(reservationMovie);

		reservation.setMovie(reservationMovieDto.getMovie());

		return reservation;
	}

}
