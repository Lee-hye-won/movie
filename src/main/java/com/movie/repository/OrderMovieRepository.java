package com.movie.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movie.entity.Reservation;
import com.movie.entity.ReservationMovie;

public interface OrderMovieRepository extends JpaRepository<ReservationMovie, Long>{

}
