package com.movie.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@ToString
public class Reservation {

	@Id
	@Column(name="res_num")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long resNum;	// 예약 고유번호
	
	@Column(name="res_movie_num")
	private Long resMovieNum;	// 예매 영화 고유번호
	
	@Column(name="res_movie_name")
	private String resMovieName;	// 예매 영화제목
	
	@Column(name="res_screen_num")
	private Long resScreenNum;	// 예매 영화 상영관번호
	
	@Column(name="res_screen_name")
	private String resScreenName;	// 예약 상영관명
	
	@Column(name="res_date")
	private String resDate;	// 예매 상영날짜
	
	@Column(name="res_time")
	private String resTime;	// 예매 상영시간
	
	@Column(name="res_seat")
	private String resSeat;	// 예매 상영좌석
	
	@Column(name="res_people")
	private int resPeople;	// 예매 인원수
	
	@Column(name="res_member_id")
	private String resMemberId;	//예매한 관객 아이디
	
	@Column(name="res_paytime")
	private String resPaytime;	// 예매 시간
	
	@Column(name="res_ pay")
	private Long resPay;	// 예매 결제금액
	
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	
}
