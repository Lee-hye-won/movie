package com.movie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.movie.dto.ReservationHistDto;
import com.movie.dto.ReservationOrderDto;
import com.movie.entity.Member;
import com.movie.entity.Movie;
import com.movie.entity.Reservation;
import com.movie.entity.ReservationMovie;
import com.movie.repository.MemberRepository;
import com.movie.repository.MovieImgRepository;
import com.movie.repository.MovieRepository;
import com.movie.repository.OrderRepository;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

	// 의존성주입
	private final MovieRepository movieRepository;
	private final MemberRepository memberRepository;
	private final MovieImgRepository movieImgRepository;
	private final OrderRepository orderRepository;
	
	// 주문할 영화를 조회
	public Long reservation(ReservationOrderDto reservationOrderDto, String email) {
		Movie movie = movieRepository.findById(reservationOrderDto.getMovieId())
									 .orElseThrow(EntityNotFoundException::new);
		
		// 현재 로그인한 회원의 이메일 사용해서 회원 정보 조회
		Member member = memberRepository.findByEmail(email);
		
		List<String> seatLine = new ArrayList<>();
		seatLine = reservationOrderDto.getSeatLine();
		
		List<String> RowLine = new ArrayList<>();
		RowLine = reservationOrderDto.getSeatRow();
		
		List<ReservationMovie> reservationMovieList = new ArrayList<>();
		ReservationMovie reservationMovie = ReservationMovie.createOrderMovie(movie, reservationOrderDto.getResPeople());
		reservationMovieList.add(reservationMovie);
		
		Reservation reservation = Reservation.createOrder(member, reservationMovieList);
		orderRepository.save(reservation);
		
		return reservation.getResMovieNum();
	}
	
	//주문 목록을 가져오는 서비스
	@Transactional(readOnly = true)
	public Page<ReservationHistDto> getOrderList(String email, Pageable pageable){
		//1. 유저 아이디와 페이징 조건을 이용하여 주문 목록을 조회
		List<Reservation> reservations = orderRepository.findOrders(email, pageable); //매개변수 2개 넣어줌. 
		//orders에 주문목록에 대한 모든 정보가 들어있음. 
		
		//2. 유저의 주문 중 총 개수를 구한다. countOrder 이용.
		Long totalCount = orderRepository.countOrder(email);
		
		List<ReservationHistDto> ReservationHistDto = new ArrayList<>();
		
		//3. 주문리스트를 순회하면서 구매 이력 페이지에 전달할 DTO(OrderHistDto)를 생성
		for(Reservation reservation : reservations) {
			ReservationHistDto reservationHistDto = new ReservationHistDto(reservation);
			List<ReservationMovie> reservationMovie = reservation.getReservationMovies();
			

			ReservationHistDto.add(reservationHistDto);
			
		}
		
		return new PageImpl<>(ReservationHistDto, pageable,totalCount); //4. 페이지 구현 객체를 생성하여 return
	}
	
	
	// 본인확인
	@Transactional(readOnly = true)
	public boolean validateOrder(Long orderId,String email) {
		Member curMember = memberRepository.findByEmail(email); //로그인한 사용자 찾기. 
		Reservation reservation = orderRepository.findById(orderId)
									 .orElseThrow(EntityNotFoundException ::new); //주문내역 찾기
		
	//주문한 사용자 찾기 
	Member saveMember = reservation.getMember(); //주문한 사용자 찾기. 
	
	//로그인한 사용자의 이메일과 주문한 사용자 이메일이 같은지 최종비교
	if(!StringUtils.equals(curMember.getEmail(),saveMember.getEmail())) {
		//같지 않으면
		return false;
	}
		return true;
	}
	
	
	
	
	
}
