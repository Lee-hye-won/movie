package com.movie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.movie.dto.MainMovieDto;
import com.movie.dto.MainMovieListDto;
import com.movie.dto.MovieFormDto;
import com.movie.dto.MovieImgDto;
import com.movie.dto.MovieSearchDto;
import com.movie.entity.Member;
import com.movie.entity.Movie;
import com.movie.entity.MovieImg;
import com.movie.entity.ScreenInfo;
import com.movie.repository.MemberRepository;
import com.movie.repository.MovieImgRepository;
import com.movie.repository.MovieRepository;
import com.movie.repository.ScreenInfoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {
	
	private final MovieRepository movieRepository;
	private final MovieImgService movieImgService;
	private final MovieImgRepository movieImgRepository;
	private final ScreenInfoRepository screenInfoRepository;

	private final MemberRepository memberRepository;

	// movie 테이블에 영화등록 (insert)
	public Long saveMovie(MovieFormDto movieFormDto, List<MultipartFile> movieImgFileList) throws Exception {
		// 영화 등록
		Movie movie = movieFormDto.createMovie();
		
		
		// ScreenInfo 가져오기 
		
		System.out.println("movieFormDto.getScrNum(): "+ movieFormDto.getScrNum());    
		 ScreenInfo	screenInfo = screenInfoRepository.findById(movieFormDto.getScrNum())
				                                     .orElseThrow(EntityNotFoundException::new);

		 System.out.println("screenInfo: " + screenInfo.toString());    
		movie.setScreenInfo(screenInfo);
		
		movieRepository.save(movie);
		
		// 이미지 등록
		for(int i=0; i< movieImgFileList.size(); i++) {
			MovieImg movieImg = new MovieImg();
			movieImg.setMovie(movie);
			
			// 첫번째 이미지를 대표 이미지로 저장
			if(i==0) {
				movieImg.setRepimgYn("Y");
			} else {
				movieImg.setRepimgYn("N");
			}
			movieImgService.saveMovieImg(movieImg, movieImgFileList.get(i));
		}
		return movie.getId();
		
	}
		
	// 영화목록 가져오기
	@Transactional(readOnly = true)
	public MovieFormDto getMovieDtl(Long movieId) {
		List<MovieImg> movieImgList = movieImgRepository.findByMovieIdOrderByIdAsc(movieId);
		
		// entity -> dto
		List<MovieImgDto> movieImgDtoList = new ArrayList<>();
		for(MovieImg movieImg : movieImgList) {
			MovieImgDto movieImgDto = MovieImgDto.of(movieImg);
			movieImgDtoList.add(movieImgDto);
		}
		
		// movie테이블의 데이터 가져오기
		Movie movie = movieRepository.findById(movieId).orElseThrow(EntityNotFoundException::new);
		
		// movie엔티티 -> dto
		MovieFormDto movieFormDto = MovieFormDto.of(movie);
		
		// MovieFormDto에 이미지 정보 넣어주기
		movieFormDto.setMovieImgDtoList(movieImgDtoList);
		
		return movieFormDto;
	}
	
	// 수정하기
	public Long updateMovie(MovieFormDto movieFormDto, List<MultipartFile> movieImgFileList) throws Exception {
		
		// 엔티티 가져오기
		Movie movie = movieRepository.findById(movieFormDto.getId()).orElseThrow(EntityNotFoundException::new);
		
		movie.updateMovie(movieFormDto);
		
		List<Long> movieImgIds = movieFormDto.getMovieImgIds();
		
		for(int i=0; i<movieImgFileList.size(); i++) {
			movieImgService.updateMovieImg(movieImgIds.get(i), movieImgFileList.get(i));
		}
		return movie.getId();
	}
	
	@Transactional(readOnly = true)
	public Page<Movie> getAdminMoviePage(MovieSearchDto movieSearchDto, Pageable pageable) {
		Page<Movie> moviePage = movieRepository.getAdminMoviePage(movieSearchDto, pageable);
		return moviePage;
	}
	
	@Transactional(readOnly = true)
	public Page<MainMovieListDto> getMainMoviePage(MovieSearchDto movieSearchDto, Pageable pageable) {
		//Page<MainMovieDto> mainMoviePage = movieRepository.getMainMoviePage(movieSearchDto, pageable);
		Page<MainMovieListDto> mainMoviePage = movieRepository.getMainMoviePageList("Y", pageable);
		return mainMoviePage;
	}
	
	// 영화 삭제
	public void deleteMovie(Long movieId) {
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(EntityNotFoundException::new);
		
		movieRepository.delete(movie);
	}
}
