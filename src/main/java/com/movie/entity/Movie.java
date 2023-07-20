package com.movie.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.movie.dto.MovieFormDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movie")
@Getter
@Setter
@ToString
public class Movie extends BaseEntity{

	@Id
	@Column(name = "movie_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "director", nullable = false)
	private String director;
	
	@Column(name = "actors", nullable = false)
	private String actors;
	
	@Column(name = "genre", nullable = false)
	private String genre;
	
	@Column(name = "runtime", nullable = false)
	private String runtime;
	
	@Column(name = "open_date", nullable = false)
	private String openDate;
	
	@Lob
	@Column(name = "intro", nullable = false, columnDefinition = "longtext")
	private String intro;
	

	@ManyToOne(fetch = FetchType.LAZY)
//	@PrimaryKeyJoinColumn(name = "scr_num")
	@JoinColumn(name = "scr_num")
	private ScreenInfo screenInfo;
	
	@Column(name = "create_by")
	private String createBy;
	
	// 엔티티 수정
	public void updateMovie(MovieFormDto movieFormDto) {
		this.title = movieFormDto.getTitle();
		this.genre = movieFormDto.getGenre();
		this.director = movieFormDto.getDirector();
		this.actors = movieFormDto.getActors();
		this.intro = movieFormDto.getIntro();
		
	}
	
	
}
