package com.ssafy.singsongsangsong.repository.maria.artist;

import static com.ssafy.singsongsangsong.entity.QFollower_Following.*;
import static com.ssafy.singsongsangsong.entity.QLikes.*;
import static com.ssafy.singsongsangsong.entity.QSong.*;
import static com.ssafy.singsongsangsong.entity.QArtist.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.singsongsangsong.dto.HotArtistDto;
import com.ssafy.singsongsangsong.dto.HotArtistResponseDto;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.Song;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ArtistRepositoryCustomImpl implements ArtistRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public int getLikeCountByArtistId(Long artistId) {
		return jpaQueryFactory.selectFrom(likes).where(likes.artist.id.eq(artistId)).fetch().size();
	}

	@Override
	public int getSongCountByArtistId(Long artistId) {
		return jpaQueryFactory.selectFrom(song).where(song.artist.id.eq(artistId)).fetch().size();
	}

	@Override
	public int getFollowerCountByArtistId(Long artistId) {
		return jpaQueryFactory.selectFrom(follower_Following)
			.where(follower_Following.to.id.eq(artistId))
			.fetch()
			.size();
	}

	@Override
	public List<Song> getPublishedSongsByArtistId(Long artistId) {
		return jpaQueryFactory.selectFrom(song)
			.where(song.artist.id.eq(artistId), song.isPublished.eq(true))
			.fetch()
			.stream()
			.toList();
	}

	@Override
	public List<Artist> getFollowArtistByArtistId(Long artistId) {
		return jpaQueryFactory
			.select(artist)
			.from(follower_Following)
			.join(follower_Following.to,artist)
			.where(follower_Following.from.id.eq(artistId))
			.fetch();
	}

	@Override
	public List<Artist> findArtistBySearchParam(String keyword) {
		List<Artist> artistList = new ArrayList<>();
		if(keyword!= null) {
			artistList = jpaQueryFactory
				.selectFrom(artist)
				.where(artist.nickname.contains(keyword))
				.fetch();
		}
		return artistList;
	}

	@Override
	public List<HotArtistDto> findHotArtist() {
		return jpaQueryFactory
			.select(Projections.constructor(HotArtistDto.class,
				song.weeklyPlayCount.sum(),
				song.artist))
			.from(song)
			.groupBy(song.artist.id)
			.limit(10)
			.fetch();
	}

}
