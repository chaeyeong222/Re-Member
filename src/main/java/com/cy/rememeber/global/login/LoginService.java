package com.cy.rememeber.global.login;

import com.cy.rememeber.business.Entity.User;
import com.cy.rememeber.business.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(phone)
            .orElseThrow(() -> new UsernameNotFoundException("해당 휴대폰 번호가 존재하지 않습니다."));

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getPhone())
            .roles(user.getRole().name())
            .build();
    }
}