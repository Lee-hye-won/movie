package com.movie.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movie.entity.Reservation;

public interface OrderRepository extends JpaRepository<Reservation, Long>{

	// 현재 로그인한 사용자의 주문데이터를 페이징조건에 맞춰서 조회
	@Query("select o from Reservation o where o.member.email = :email")
	List<Reservation>findOrders(@Param("email")String email, Pageable pageable);
	
	// 현재 로그인한 회원의 주문건이 몇건인지 조회
	@Query("select count(o) from Reservation o where o.member.email= :email")
	Long countOrder(@Param("email")String email);
}
