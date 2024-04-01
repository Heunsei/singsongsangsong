package com.ssafy.singsongsangsong.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.singsongsangsong.dto.HotArtistResponseDto;
import com.ssafy.singsongsangsong.dto.LikedPageResponseDto;
import com.ssafy.singsongsangsong.dto.SearchResponseDto;
import com.ssafy.singsongsangsong.dto.SongBriefDto;
import com.ssafy.singsongsangsong.security.ArtistPrincipal;
import com.ssafy.singsongsangsong.service.song.SongPlayListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/music-playlist")
@RequiredArgsConstructor
public class SongPlayListController {
	private final SongPlayListService songPlayListService;

	@GetMapping("/liked-song/{pageNo}")
	public LikedPageResponseDto getLikedPagination(@AuthenticationPrincipal ArtistPrincipal artistPrincipal,
		@PathVariable("pageNo") int pageNo) {
		return songPlayListService.getLikedPagination(artistPrincipal.getUsername(), pageNo);
	}

	@GetMapping("/weekly-hitsong")
	public List<SongBriefDto> getWeeklyHitSongList() {
		return songPlayListService.getWeeklyHitSongList();
	}

	@GetMapping("/genre-hitsong/{genre}")
	public List<SongBriefDto> getGenreHitSongList(@PathVariable("genre") String genre) {
		return songPlayListService.getGenreHitSongList(genre);
	}

	@GetMapping("/atmosphere-hitsong/{atmosphere}")
	public List<SongBriefDto> getAtmosphereHitSongList(@PathVariable("atmosphere") String atmosphere) {
		return songPlayListService.getAtmosphereHitSongList(atmosphere);
	}

	@GetMapping("/hot-artist")
	public List<HotArtistResponseDto> getHotArtist() {
		return songPlayListService.getHotArtist();
	}

	@GetMapping("/search")
	public SearchResponseDto SearchArtistAndSong(@RequestParam(required = false) String keyword,
		@RequestParam(required = false) String genre,
		@RequestParam(required = false) String atmosphere,
		@RequestParam(required = false, defaultValue = "0") Integer bpm,
		@RequestParam(required = false) String sort) {
		return songPlayListService.searchArtistAndSong(keyword, genre, atmosphere, bpm, sort);
	}
}
