package com.example.services;
import com.example.entities.Role;
import com.example.entities.User;
import com.example.props.JWTLogin;
import com.example.repositories.UserRepository;
import com.example.services.utils.JwtUtil;
import com.example.services.utils.REnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class UserService implements UserDetailsService {

    final UserRepository userRepository;
    final JwtUtil jwtUtil;
    final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalJWTUser = userRepository.findByEmailEqualsIgnoreCase(username);
        if (optionalJWTUser.isPresent()) {
            User u = optionalJWTUser.get();
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    u.getEmail(),
                    u.getPassword(),
                    u.isEnabled(),
                    u.isTokenExpired(),
                    true,
                    true,
                    roles(u.getRoles())
            );
            return userDetails;
        }else {
            throw new UsernameNotFoundException("Böyle bir kullanıcı yok");
        }

    }

    public Collection roles(List<Role> rolex ) {
        List<GrantedAuthority> ls = new ArrayList<>();
        for ( Role role : rolex ) {
            ls.add( new SimpleGrantedAuthority( role.getName() ));
        }
        return ls;
    }

    public ResponseEntity register(User user){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        Optional<User> optionalUser = userRepository.findByEmailEqualsIgnoreCase(user.getEmail());
        if (!optionalUser.isPresent()){
            user.setPassword(encoder().encode(user.getPassword()));
            User registerUser = userRepository.save(user);
            hm.put(REnum.status, true);
            hm.put(REnum.result, registerUser);
            return new ResponseEntity(hm, HttpStatus.OK);
        }
        else {
            hm.put(REnum.status,false);
            hm.put(REnum.message, "Email zaten kayıtlı.");
            hm.put(REnum.result, user);
            return new ResponseEntity(hm, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity auth(JWTLogin jwtLogin) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jwtLogin.getUsername(), jwtLogin.getPassword()
            ));
            UserDetails userDetails = loadUserByUsername(jwtLogin.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);
            Optional<User> oUser = userRepository.findByEmailEqualsIgnoreCase(jwtLogin.getUsername());
            if (oUser.isPresent()){
                User u = oUser.get();
                hm.put(REnum.status, true);
                hm.put(REnum.jwt, jwt);
                hm.put(REnum.result, userDetails);
                hm.put(REnum.userId, u.getUid());
                hm.put(REnum.userName, u.getFirstName() + " " + u.getLastName());
            }
            return new ResponseEntity(hm, HttpStatus.OK);

        } catch (Exception e) {
            hm.put(REnum.status, false);
            hm.put(REnum.error, e.getMessage());
            return new ResponseEntity(hm, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //---JsonIgnore---
    public ResponseEntity getUsers(){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        hm.put(REnum.result, userRepository.findAll());

        return new ResponseEntity(hm, HttpStatus.OK);
    }

    public ResponseEntity getUserById(int uid){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        hm.put(REnum.result, userRepository.findById(uid));

        return new ResponseEntity(hm, HttpStatus.OK);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
