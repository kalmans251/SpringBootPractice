package com.mysite.sbb4.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


//사용자 정보를 폼에서 넘겨 받아서 인증을 처리함.


@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<SiteUser> _siteUser=this.userRepository.findByUsername(username);
		if(_siteUser.isEmpty()) {
			
			throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
			
		}
		
		SiteUser siteUser=_siteUser.get();
		
		// Authentication (인증) : Identity (id) + password를 확인 하는것
		// Authorization  (허가) : 인증된 사용자에게 사이트의 서비스를 쓸수 있도록 권한을 부여하는것.
			// 계정이 admin 이라면 ADMIN Role 을 적용
			// 계정이 admin 이 아니라면 USER Role 을 적용
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		if("admin".equals(username)) {
			
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
			
		}else {
			
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
			
		}
		
		return new User(siteUser.getUsername(),siteUser.getPassword(),authorities);
	}

	
}
