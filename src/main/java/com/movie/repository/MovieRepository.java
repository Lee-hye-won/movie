package com.movie.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movie.dto.MainMovieDto;
import com.movie.dto.MainMovieListDto;
import com.movie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>, MovieRepositoryCustom{
	
	// select * from movie where title = ?
	List<Movie> findByTitle(String title);
	

	@Query(value="select\r\n"
			+ "        m1_0.movie_id id,\r\n"
			+ "            m2_0.title title,\r\n"
			+ "            m1_0.img_url imgUrl \r\n"
			+ "        from\r\n"
			+ "            movie_img m1_0 \r\n"
			+ "        join\r\n"
			+ "            movie m2_0 \r\n"
			+ "                on m2_0.movie_id=m1_0.movie_id \r\n"
			+ "        where\r\n"
			+ "            m1_0.repimg_yn=:repimgYn \r\n"
			+ "        order by\r\n"
			+ "            m1_0.movie_id", 
			countQuery = "select\r\n"
					+ "        count(*)\r\n"
					+ "        from\r\n"
					+ "            movie_img m1_0 \r\n"
					+ "        join\r\n"
					+ "            movie m2_0 \r\n"
					+ "                on m2_0.movie_id=m1_0.movie_id \r\n"
					+ "        where\r\n"
					+ "            m1_0.repimg_yn=:repimgYn \r\n"
					+ "        order by\r\n"
					+ "            m1_0.movie_id",
			nativeQuery = true)
	
	PageImpl<MainMovieListDto> getMainMoviePageList(@Param("repimgYn") String repimgYn, Pageable pageable);
	
	
}
