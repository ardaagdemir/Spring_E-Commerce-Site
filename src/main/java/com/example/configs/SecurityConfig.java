package com.example.configs;

import com.example.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final JwtFilter jwtFilter;
    final UserService jwtUserService;
    public SecurityConfig(JwtFilter jwtFilter, UserService jwtUserService) {
        this.jwtFilter = jwtFilter;
        this.jwtUserService = jwtUserService;
    }

    //veritabanında yönetim, kullanıcı varlık denetimi
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserService).passwordEncoder( jwtUserService.encoder() );
    }

    //role ve yönetim
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .deny()
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                //user list

                //product roles
                .antMatchers(USER_ROLE_LIST).hasAnyRole("user", "admin")
                .antMatchers("/product/**").hasRole("admin")

                //category roles
                .antMatchers("/category/getCategories").hasAnyRole("user", "admin")
                .antMatchers("/category/**").hasRole("admin")

                //order roles
                .antMatchers("/order/getOrders", "/order/getOrderById/{uid}").hasAnyRole("admin", "user")
                .antMatchers("/order/**").hasRole("user")

                //image roles
                .antMatchers("/image/getImages/{pid}").hasAnyRole("user", "admin")
                .antMatchers("/image/**").hasRole("admin")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class );
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    private static final String[] USER_ROLE_LIST = {
            "/product/getProducts",
            "/product/searchProduct",
            "/product/getProductById",
            "/product/getImagesByProduct",
            "/product/getImageByProductId/{pid}",
            "/product/getProductByCategoryId/{cid}"
    };
    private static final String[] AUTH_WHITELIST = {

            "/auth",

            "/register",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };
}