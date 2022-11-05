package fr.arnaud.cleanarchitecture.infrastructure.configuration.security;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
	  KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
	  SimpleAuthorityMapper authoritiesMapper = new SimpleAuthorityMapper();
	  	keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(authoritiesMapper);
	  	auth.authenticationProvider(keycloakAuthenticationProvider);
	}
	  
	  @Bean
	  @Override
	  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
	    return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	  }

	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
		  super.configure(http);
		  http
        	.csrf().disable()
        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	.and()
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
	        
	        .and()
	        .httpBasic().disable()
	        .oauth2Login().disable()
	        .formLogin().disable();
        
    }
}