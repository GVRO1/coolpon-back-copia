package br.com.coolpon.coolpon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

import static br.com.coolpon.coolpon.config.QueryBanco.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BasicAuthentication authEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers("/promotions/**",
//                "/rewards/**",
//                "/promotion-has-users/**",
//                "/users/**").hasAnyAuthority("BUSINESS","ADMIN").
//                and()
//                .authorizeRequests()
//                .antMatchers("/businesses/**").hasAuthority("ADMIN")
//                .and().authorizeRequests()
//                .antMatchers(HttpMethod.GET,"/businesses/**","/promotions").hasAuthority("/USER");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder,PasswordEncoder passwordEncoder, DataSource dataSource) throws Exception {
        builder
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery(USUARIO_POR_LOGIN)
                .authoritiesByUsernameQuery(PERMISSOES_POR_BUSINESS);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("321"));
    }
}
