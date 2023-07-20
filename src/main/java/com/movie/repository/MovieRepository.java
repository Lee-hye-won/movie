package com.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>, MovieRepositoryCustom{
	
	// select * from movie where title = ?
	List<Movie> findByTitle(String title);
	
	
}
