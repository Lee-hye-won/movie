package com.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.entity.ScreenInfo;

public interface ScreenInfoRepository extends JpaRepository<ScreenInfo, Long>{
	
	ScreenInfo findByScrNum(Long scrNum);
}
