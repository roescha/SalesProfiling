package myshop.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		 http
		 .csrf().disable()
     .authorizeRequests()
         .antMatchers("/login").permitAll()
         .anyRequest().authenticated()
         .and()
     .formLogin()
         .loginPage("/login")
         .permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("gordon").password("GordonWaitrose999").roles("USER")
		.and().withUser("andy").password("AndyWaitrose999").roles("ADMIN")
		.and().withUser("alex").password("AlexWaitrose999").roles("ADMIN")
	  .and().withUser("nick").password("NickWaitrose999").roles("USER")
		.and().withUser("waitrose").password("W@1trose9!!").roles("USER");
	}
}