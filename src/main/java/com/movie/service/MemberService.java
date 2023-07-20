package com.movie.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.entity.Member;
import com.movie.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{

	private final MemberRepository memberRepository;
	
	// 회원가입 데이터를 DB에 저장
	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		
		Member savedMember = memberRepository.save(member);
		return savedMember;
	}

	// 이메일 중복체크
	public void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByEmail(member.getEmail());
		
		if(findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}


	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		// 사용자가 입력한 email이 DB에 존재하는지 쿼리문을 사용한다.
		Member member = memberRepository.findByEmail(email);
		
		if(member == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return User.builder()
				.username(member.getEmail())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}
	
	

}
