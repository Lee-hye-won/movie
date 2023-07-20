package com.movie.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.movie.entity.MovieImg;
import com.movie.repository.MovieImgRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional

public class MovieImgService {
	private String movieImgLocation = "C:/movie/";
	private final MovieImgRepository movieImgRepository;
	private final FileService fileService;
	
	// 이미지 저장
	public void saveMovieImg(MovieImg movieImg, MultipartFile movieImgFile) throws Exception {
		String oriImgName = movieImgFile.getOriginalFilename();
		String imgName = "" ;
		String imgUrl = "";
		
		// 1. 파일을 movieImgLocation에 저장
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(movieImgLocation, oriImgName,movieImgFile.getBytes());
			imgUrl = "/img/movie/" + imgName;
		}
		
		// 2. movie_img 테이블에 저장
		movieImg.updateMovieImg(oriImgName, imgName, imgUrl);
		movieImgRepository.save(movieImg);
	}
	
	// 이미지 업데이트 메소드
	public void updateMovieImg(Long movieImgId, MultipartFile movieImgFile) throws Exception {
		if(!movieImgFile.isEmpty()) {
			// 해당 이미지 가져오기
			MovieImg savedMovieImg = movieImgRepository.findById(movieImgId)
													.orElseThrow(EntityNotFoundException::new);
			
			System.out.println(savedMovieImg);
			
			// 기존 이미지 파일폴더에서 삭제
			if(!StringUtils.isEmpty(savedMovieImg.getImgName())) {
				fileService.deleteFile(movieImgLocation + "/" + savedMovieImg.getImgName());
			}
			
			// 수정된 파일 경로에 업로드
			String oriImgName = movieImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(movieImgLocation, oriImgName, movieImgFile.getBytes());
			String imgUrl = "/img/movie/" + imgName;
			
			savedMovieImg.updateMovieImg(oriImgName, imgName, imgUrl);
		}
	}
	
}
