package com.ssafy.singsongsangsong.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.singsongsangsong.dto.ArtistInfoDto;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.Image;

@SpringBootTest
public class ModelMapperTest {

	@Autowired
	ModelMapper modelMapper;

	@Nested
	class ArtistInfoDto에_대해 {
		Artist artist = Artist.builder()
			.id(1L)
			.nickname("nickname")
			.username("username")
			.password("password")
			.profileImage(new Image(2L, "imgLocation", "orgFileName"))
			.introduction("introduction")
			.age(1)
			.sex('M')
			.build();

		@Test
		void it_returns_valid_artistInfoDto() {
			ArtistInfoDto result = ArtistInfoDto.from(artist);
			assertThat(result.getArtistId()).isEqualTo(artist.getId());
			assertThat(result.getNickname()).isEqualTo(artist.getNickname());
			assertThat(result.getUsername()).isEqualTo(artist.getUsername());
			assertThat(result.getIntroduction()).isEqualTo(artist.getIntroduction());
			assertThat(result.getProfileImageUrl()).isEqualTo(artist.getProfileImage().getImageLocation());
		}

	}

}
