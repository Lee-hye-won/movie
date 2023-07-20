package com.movie.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.movie.constant.Role;
import com.movie.dto.MemberFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member {

	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private String email;
	
	@Column 
	private String name;
	
	@Column 
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	// 멤버 생성 
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		
		// 패스워드 암호화
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		
		// MemberFormDto를 Member 엔티티로 변환
		Member member = new Member();
		
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setPassword(password);
		member.setRole(Role.ADMIN);
		
		return member;
	}
	
}
