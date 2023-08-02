package com.movie.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;

import com.movie.dto.ReservationMovieDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="reservation_movie")
@Getter
@Setter
@ToString
public class ReservationMovie extends BaseEntity{

	@Id
	@Column(name="order_movie_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "res_num")
	private Reservation reservation;
	
	private int count;
	
	private int orderPrice; //주문가격
	
	public static ReservationMovie createOrderMovie(Movie movie, int count) {
		ReservationMovie reservationMovie = new ReservationMovie();

		reservationMovie.setMovie(movie);
		
		reservationMovie.setCount(count);
		reservationMovie.setRegTime(LocalDateTime.now());
		
		
		return reservationMovie;
	}
	
}
