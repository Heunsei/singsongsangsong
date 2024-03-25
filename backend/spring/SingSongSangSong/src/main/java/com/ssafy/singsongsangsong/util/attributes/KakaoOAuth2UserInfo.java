package com.ssafy.singsongsangsong.util.attributes;

import java.util.Map;
import java.util.Objects;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo{
	public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}
	@Override
	public String getId() {
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public String getNickname() {
		Map<String,Object> profile = (Map<String, Object>) attributes.get("profile");
		if(profile == null) {
			return null;
		}
		return (String)profile.get("nickname");
	}

	@Override
	public String getImageUrl() {
		Map<String,Object> profile = (Map<String, Object>) attributes.get("profile");
		if(profile == null) {
			return null;
		}
		return (String) profile.get("profile_image_url");
	}
}
