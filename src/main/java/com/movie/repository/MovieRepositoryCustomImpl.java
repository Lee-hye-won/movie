package com.movie.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.movie.dto.MainMovieDto;
import com.movie.dto.MovieSearchDto;
import com.movie.dto.QMainMovieDto;
import com.movie.entity.Movie;
import com.movie.entity.QMovie;
import com.movie.entity.QMovieImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class MovieRepositoryCustomImpl implements MovieRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public MovieRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		
		if(StringUtils.equals("title", searchBy)) {
			return QMovie.movie.title.like("%" + searchQuery + "%");
		}
		return null;
			
	}
	
	
	
	@Override
	public Page<Movie> getAdminMoviePage(MovieSearchDto movieSearchDto, Pageable pageable) {
		List<Movie> content = queryFactory.selectFrom(QMovie.movie)
				.where(searchByLike(movieSearchDto.getSearchBy(), movieSearchDto.getSearchQuery()))
				.orderBy(QMovie.movie.id.asc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = queryFactory.select(Wildcard.count).from(QMovie.movie)
				.where(searchByLike(movieSearchDto.getSearchBy(), movieSearchDto.getSearchQuery()))
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}

	
	
	private BooleanExpression titleLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery)?
				null : QMovie.movie.title.like("%" + searchQuery + "%");
	}
	
	@Override
	public Page<MainMovieDto> getMainMoviePage(MovieSearchDto movieSearchDto, Pageable pageable) {
		
		/* select movie.id, movie.title, movie_img.imgUrl
		 * from movie, movie_img
		 * where movie_img.id = movie_img.movie_id
		 * and movie_img.repimg_yn = "Y" 
		 * and movie.title like '%검색어%'
		 * */
		
		QMovie movie = QMovie.movie;
		QMovieImg movieImg = QMovieImg.movieImg;
		
		
		List<MainMovieDto> content = queryFactory
				.select(
						new QMainMovieDto(
								movie.id,
								movie.title,
								movieImg.imgUrl)
								)
						.from(movieImg)
						.join(movieImg.movie, movie)
						.where(movieImg.repimgYn.eq("Y"))
						.where(titleLike(movieSearchDto.getSearchQuery()))
						.orderBy(movie.id.asc())
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize())
						.fetch();
						
		long total = queryFactory
				.select(Wildcard.count)
				.from(movieImg.movie, movie)
				.where(movieImg.repimgYn.eq("Y"))
				.where(titleLike(movieSearchDto.getSearchQuery()))
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
						
	}
}
