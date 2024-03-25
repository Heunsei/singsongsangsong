package com.ssafy.singsongsangsong.service.jwt;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.util.CustomOAuth2User;
import com.ssafy.singsongsangsong.util.OAuthAttributes;
import com.ssafy.singsongsangsong.util.SocialType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final ArtistRepository userRepository;

	private static final String NAVER = "naver";
	private static final String KAKAO = "kakao";
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		SocialType socialType = getSocialType(registrationId);
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		Map<String, Object> attributes = oAuth2User.getAttributes();

		OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

		Artist createdUser = getUser(socialType,extractAttributes);
		return new CustomOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getKey())),
			attributes,
			extractAttributes.getNameAttributeKey(),
			createdUser.getUsername(),
			createdUser.getRole()
		);
	}

	private SocialType getSocialType(String registrationId) {
		if(NAVER.equals(registrationId)){
			return SocialType.NAVER;
		}
		if(KAKAO.equals(registrationId)){
			return SocialType.KAKAO;
		}
		return SocialType.GOOGLE;
	}

	private Artist getUser(SocialType socialType, OAuthAttributes oAuthAttributes) {
		Artist user = userRepository.findByUsername(socialType.toString() + "_" + oAuthAttributes.getOAuth2UserInfo().getId())
			.orElse(null);

		if(user == null) {
			user = saveUser(socialType,oAuthAttributes);
		}
		return user;
	}

	private Artist saveUser(SocialType socialType, OAuthAttributes oAuthAttributes){
		Artist user = oAuthAttributes.toEntity(socialType,oAuthAttributes.getOAuth2UserInfo());
		System.out.println("유저 저장, save user");
		userRepository.save(user);
		return user;
	}


}
