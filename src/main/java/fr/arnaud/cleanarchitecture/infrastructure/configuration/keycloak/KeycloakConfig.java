package fr.arnaud.cleanarchitecture.infrastructure.configuration.keycloak;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }
    
    @Bean
    public KeycloakConfigResolver keycloakConfigResolver(){
       return new KeycloakSpringBootConfigResolver();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
        	.authorizeRequests()
        
	        .antMatchers(HttpMethod.POST, "/auth**").permitAll()

	        .antMatchers(HttpMethod.DELETE, "/v1/championships/{championshipId}").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.DELETE, "/v1/leagues/{leagueId}").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.DELETE, "/v1/matchs/{matchId}").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.DELETE, "/v1/players/{playerId}").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.DELETE, "/v1/seasons/{seasonId}").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.DELETE, "/v1/teams/{teamId}").hasAnyRole(Role.ADMIN.name())
	        
	        .antMatchers(HttpMethod.PUT, "/v1/championships/{championshipId}").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.PUT, "/v1/leagues/{leagueId}").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.PUT, "/v1/matchs/{matchId}").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.PUT, "/v1/players/{playerId}").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.PUT, "/v1/seasons/{seasonId}").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.PUT, "/v1/teams/{teamId}").hasAnyRole(Role.ADMIN.name())
	        
	        .antMatchers(HttpMethod.POST, "/v1/championships").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.POST, "/v1/leagues").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.POST, "/v1/matchs").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.POST, "/v1/players").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.POST, "/v1/seasons").hasAnyRole(Role.ADMIN.name())
	        .antMatchers(HttpMethod.POST, "/v1/teams").hasAnyRole(Role.ADMIN.name())
        
	        .antMatchers(HttpMethod.GET, "/v1/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
	       

	        ;
        
        http.formLogin().disable();
        
        return http.build();
    }
}