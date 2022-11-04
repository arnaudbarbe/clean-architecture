package fr.arnaud.cleanarchitecture.infrastructure.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import fr.arnaud.cleanarchitecture.core.exception.BadCredentialsException;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.TokenDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.UserDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthService {

	@Value("${keycloak.realm}")
	private String realm;

	@Value("${keycloak.resource}")
	private String clientId;
	
	@Value("${keycloak.auth-server-url}")
	private String authServerUrl;
	
	public TokenDto authenticate(UserDto user) {

		try(Keycloak keycloak = Keycloak.getInstance(authServerUrl, realm, user.username(), user.password(), clientId)) {
			
			TokenManager tokenmanager = keycloak.tokenManager();

			AccessTokenResponse accessToken = tokenmanager.getAccessToken();
			
			log.debug("Login to keycloak server");
			
			return new TokenDto(accessToken.getToken(), accessToken.getRefreshToken(), accessToken.getExpiresIn(),
					accessToken.getRefreshExpiresIn());
		} catch (Exception e) {
			throw new BadCredentialsException(e.getMessage());
		}

	}

	public TokenDto refresh(String refreshToken) {

		return null;
	}

	/**
	 * {authServerUrl}/realms/{realmId}/protocol/openid-connect/logout
	 * 
	 * @param currentToken
	 */
	public void logout(TokenDto token) {

		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);     

			MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
			body.add("client_id", clientId);
			body.add("refresh_token", token.refreshToken());
			
			restTemplate.postForLocation(authServerUrl + "/realms/" +  realm + "/protocol/openid-connect/logout", body, headers);
			
			log.debug("Logout from keycloak server");
			
		} catch (Exception e) {
			throw new BadCredentialsException(e.getMessage());
		}
	}
}
