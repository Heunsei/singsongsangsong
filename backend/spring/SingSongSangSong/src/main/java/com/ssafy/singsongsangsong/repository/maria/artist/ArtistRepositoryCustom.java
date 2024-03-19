package com.ssafy.singsongsangsong.repository.maria.artist;

import java.util.List;

import com.ssafy.singsongsangsong.dto.SimpleSongDto;
import com.ssafy.singsongsangsong.entity.Song;

public interface ArtistRepositoryCustom {
	int getLikeCountByArtistId(Long artistId);

	int getSongCountByArtistId(Long artistId);

	int getFollowerCountByArtistId(Long artistId);

	List<Song> getPublishedSongsByArtistId(Long artistId);
}
