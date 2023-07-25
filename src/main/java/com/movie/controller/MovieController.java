package com.movie.controller;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.movie.dto.MainMovieDto;
import com.movie.dto.MainMovieListDto;
import com.movie.dto.MovieFormDto;
import com.movie.dto.MovieSearchDto;
import com.movie.entity.Movie;
import com.movie.service.MovieService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MovieController {

	private final MovieService movieService;
	
	// 상영중인 영화 리스트 불러오기 
	@GetMapping(value= "/movie/shop")
	public String movieShopList(Model model, MovieSearchDto movieSearchDto, Optional<Integer> page) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 9);
		Page<MainMovieListDto> movies = movieService.getMainMoviePage(movieSearchDto, pageable);
	
		model.addAttribute("movies", movies);
		model.addAttribute("movieSearchDto", movieSearchDto);
		model.addAttribute("maxPage", 7);
		
		return "movie/movieList";
	}
	// 곧 개봉하는 영화 리스트 불러오기 
	
	
	// 영화 상세페이지
	@GetMapping(value = "/movie/{movieId}")
	public String movieDtl(Model model, @PathVariable("movieId") Long movieId) {
		
		MovieFormDto movieFormDto = movieService.getMovieDtl(movieId);
		model.addAttribute("movie", movieFormDto);
		return "movie/movieDtl";
	}
	
	
	
	// 영화 등록 화면 페이지
	@GetMapping(value="/admin/movie/new")
	public String itemForm(Model model) {
		
		
		model.addAttribute("movieFormDto", new MovieFormDto());
		return "/movie/movieForm";
	}
	
	// 영화, 영화이미지 등록 (insert)
	@PostMapping(value = "/admin/movie/new")
	public String movieNew(@Valid MovieFormDto movieFormDto, BindingResult bindingResult,
			Model model, @RequestParam("movieImgFile") List<MultipartFile> movieImgFileList) {
		
		
		if(bindingResult.hasErrors()) {
			return "movie/movieForm";
		}
		
		// 상품등록전에 첫번재 이미지가 있는지 없는지 검사
		if(movieImgFileList.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "대표 이미지는 필수입니다.");
			return "movie/movieForm";
		}
		
		System.out.println(movieImgFileList.get(0));
		try {
			movieService.saveMovie(movieFormDto, movieImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "영화등록 중 에러가 발생했습니다.");
			return "movie/movieForm";
		}
		return "redirect:/";
	}
	
	// 영화 관리페이지
	@GetMapping(value= {"admin/movies", "admin/movies/{page}"})
	public String movieManage(MovieSearchDto movieSearchDto,
			@PathVariable("page") Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent()?page.get():0, 5);
		
		Page<Movie> movies = movieService.getAdminMoviePage(movieSearchDto, pageable);
		
		model.addAttribute("movies", movies);
		model.addAttribute("movieSearchDto", movieSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "movie/movieMng";
	}
	
	
	// 영화 수정페이지 보기 
	@GetMapping(value = "/admin/movie/{movieId}")
	public String movieDtl(@PathVariable("movieId") Long movieId, Model model) {		
		try {
			MovieFormDto movieFormDto = movieService.getMovieDtl(movieId);
			model.addAttribute("movieFormDto", movieFormDto);
			
			System.out.println(movieFormDto.getScrNum()+"fffffffffffffff");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "영화정보를 가져오는 중 에러가 발생했습니다.");
			model.addAttribute("movieFormDto", new MovieFormDto());
			return "movie/movieForm";
		}
		
		return "movie/movieModify";
	}
	
	// 영화 수정기능
	@PostMapping(value = "/admin/movie/{movieId}")
	public String movieUpdate(@Valid MovieFormDto movieFormDto, Model model,
			BindingResult bindingResult, @RequestParam("movieImgFile") List<MultipartFile> movieImgFileList) {
		
		if(bindingResult.hasErrors()) {
			return "movie/movieForm";
		}
		
		// 대표 포스터가 들어있는지 검사 
		if(movieImgFileList.get(0).isEmpty() && movieFormDto.getId()==null) {
			model.addAttribute("errorMessage", "대표 이미지는 필수입니다.");
			return "movie/movieForm";
		}
		
		try {
			movieService.updateMovie(movieFormDto, movieImgFileList);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "영화 수정 중 에러가 발생했습니다.");
			return "movie/movieForm";
		}
		
		return "redirect:/";
		
	}
	
	
	
	// 영화 삭제
	@DeleteMapping("/movie/{movieId}/delete")
	public @ResponseBody ResponseEntity deletemovie(@RequestBody @PathVariable("movieId") Long movieId,
			Principal principal) {
		movieService.deleteMovie(movieId);
		return new ResponseEntity<Long>(movieId, HttpStatus.OK);
	}
	
	
	
	
	
}
