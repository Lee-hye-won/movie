package com.movie.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.modelmapper.ModelMapper;

import com.movie.dto.MovieImgDto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "movie_img")
public class MovieImg extends BaseEntity{
	
	@Id
	@Column(name = "movie_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repimgYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action= OnDeleteAction.CASCADE)
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	// 이미지 정보 업데이트 메소드
	public void updateMovieImg(String oriImgName, String imgName, String imgUrl) {
		this.oriImgName = oriImgName;
		this.imgName = imgName;
		this.imgUrl = imgUrl; 
	}
	
	
	
	
}
