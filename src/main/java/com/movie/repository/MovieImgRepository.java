package com.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.entity.MovieImg;

public interface MovieImgRepository extends JpaRepository<MovieImg, Long>{

	//select * from movie_img where movie_id = ? order by movie_id asc;
	List<MovieImg> findByMovieIdOrderByIdAsc (Long movieId);
	
}
