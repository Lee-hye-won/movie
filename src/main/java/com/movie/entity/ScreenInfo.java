package com.movie.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "screen_info")
@Getter
@Setter
@ToString
public class ScreenInfo {

	@Id
	@Column(name="scr_num")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long scrNum;
	
	@Column(name="scr_name")
	private String scrName;
	
	@Column(name="scr_seat_row")
	private Long scrSeatRow;
	
	@Column(name="scr_seat_line")
	private Long scrSeatLine;
}
