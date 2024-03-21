package com.ssafy.singsongsangsong.service.jwt;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {
	@Value("${jwt.secretKey}")
	private String secretKey;

	@Value("${jwt.access.expiration}")
	private Long accessTokenExpirationPeriod;

	@Value("${jwt.access.header}")
	private String accessHeader;

	private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	private static final String EMAIL_CLAIM = "email";
	private static final String BEARER = "Bearer_";

	private final ArtistRepository artistRepository;
	public String createAccessToken(String email) {
		System.out.println("accessToken을 만듭니다.");
		Date now = new Date();
		return JWT.create()
			.withSubject(ACCESS_TOKEN_SUBJECT)
			.withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
			.withClaim(EMAIL_CLAIM,email)
			.sign(Algorithm.HMAC256(secretKey));
	}

	public void sendAccessToken(HttpServletResponse response, String accessToken) {
		response.setStatus(HttpServletResponse.SC_OK);

		response.setHeader(accessHeader, accessToken);
		log.info("재발급된 Access Token : {}", accessToken);
	}

	public Optional<String> extractAccessToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String accessCookie = null;
		for(int i = 0; cookies!=null&&i<cookies.length; i++) {
			if(cookies[i].getName().equals("accessToken")) {
				accessCookie = cookies[i].getValue();
			}
		}
		return Optional.ofNullable(accessCookie);
	}

	public Optional<String> extractEmail(String accessToken) {
		try {
			System.out.println(accessToken);
			return Optional.ofNullable(JWT.require(Algorithm.HMAC256(secretKey))
				.build()
				.verify(accessToken)
				.getClaim(EMAIL_CLAIM)
				.asString());
		} catch (Exception e) {
			log.error("액세스 토큰이 유효하지 않습니다.");
			return Optional.empty();
		}
	}

	public boolean isTokenValid(String token) {
		try {
			System.out.println(token);
			JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
			return true;
		} catch (Exception e) {
			log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
			return false;
		}
	}
}
