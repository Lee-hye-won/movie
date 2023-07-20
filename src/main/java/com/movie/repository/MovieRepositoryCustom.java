package com.movie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.movie.dto.MainMovieDto;
import com.movie.dto.MovieSearchDto;
import com.movie.entity.Movie;

public interface MovieRepositoryCustom  {
	
	Page<Movie> getAdminMoviePage(MovieSearchDto movieSearchDto, Pageable pageable);
	
	Page<MainMovieDto> getMainMoviePage(MovieSearchDto movieSearchDto, Pageable pageable); 
}
