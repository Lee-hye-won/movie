package com.movie.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "show_info")
@Getter
@Setter
@ToString
public class ShowInfo {

	@Id
	@Column(name="shw_num")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long shwNum;
	
	@Column(name="shw_movie_num")
	private int shwMovieNum;	// 상영관리 해당 영화 고유번호
	
	@Column(name="shw_movie_name")
	private String shwMovieName;
	
	@Column(name="shw_screen_num")
	private int shwScreenNum;	// 상영관리 해당 상영관 고유번호
	
	@Column(name="shw_screen_name")
	private String shwScreenName;	// 상영관 명
	
	@Column(name="shw_date")
	private String shwDate; // 상영일자
	
	@Column(name="shw_seatcnt")
	private Long shwSeatCnt; 	// 남는 좌석 수 
	
	@Column(name="shw_expire")
	private String shwExpire;	// 상영 만료
	
	@Column(name="shw_time")
	private String shwTime;	// 상영시작 시간
	
	@ManyToOne
	@JoinColumn(name = "scr_num")
	private ScreenInfo screenNum;
	
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
}
