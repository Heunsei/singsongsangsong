package com.ssafy.singsongsangsong.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.singsongsangsong.constants.EmotionsConstants;
import com.ssafy.singsongsangsong.dto.ArtistInfoDto;
import com.ssafy.singsongsangsong.dto.CommentsInfoDto;
import com.ssafy.singsongsangsong.dto.CommentsResponseDto;
import com.ssafy.singsongsangsong.dto.CommentsResponseDto.CommentsResponse;
import com.ssafy.singsongsangsong.dto.SimpleSongDto;
import com.ssafy.singsongsangsong.dto.SongInfoResponse;
import com.ssafy.singsongsangsong.dto.SongInfoResponse.SongInfoResponseBuilder;
import com.ssafy.singsongsangsong.dto.SongListByThemeResponseDto;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.Comments;
import com.ssafy.singsongsangsong.entity.Emotions;
import com.ssafy.singsongsangsong.entity.Song;
import com.ssafy.singsongsangsong.exception.artist.ArtistNotFoundException;
import com.ssafy.singsongsangsong.exception.song.NotFoundSongException;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.repository.maria.comments.CommentsRepository;
import com.ssafy.singsongsangsong.repository.maria.song.EmotionRepository;
import com.ssafy.singsongsangsong.repository.maria.song.SongRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

	private final SongRepository songRepository;

	private final EmotionRepository emotionRepository;

	private final ArtistRepository artistRepository;

	private final CommentsRepository commentsRepository;

	@Override
	@Transactional
	public void updateEmotionType(Long artistId, Long songId, EmotionsConstants emotionType) throws
		NoSuchFieldException {
		// 기존 사용자가 해당 노래에 대해서 emotion을 남긴 경우, 해당 노래의 emotion을 삭제하고 count down 시킨 다음,
		// 새로운 감정을 추가한다. count up++

		Song song = songRepository.getSongByArtistIdAndSongId(songId, artistId)
			.orElseThrow(() -> new IllegalArgumentException("해당 노래가 존재하지 않습니다."));
		Optional<java.lang.String> targetEmotion = emotionRepository.checkIfEmotionExists(song.getId(), artistId);
		if (targetEmotion.isPresent()) {
			java.lang.String previousEmotionName = targetEmotion.get();
			// 기존 emotion 업데이트 및 반정규화된 table, count 조정
			emotionRepository.updateEmotionType(song.getId(), artistId, emotionType);
			songRepository.decrementEmotionCount(song.getId(), artistId, previousEmotionName);
			songRepository.incrementEmotionCount(song.getId(), artistId, emotionType.getName());
		} else {
			// emotion 추가 및 반정규화된 table, count++
			emotionRepository.save(Emotions.builder()
				.song(song)
				.artist(song.getArtist())
				.emotionType(emotionType)
				.build());
			songRepository.incrementEmotionCount(song.getId(), artistId, emotionType.getName());
		}
	}

	@Override
	@Transactional
	public void postComment(Long artistId, Long songId, String content) {
		// 댓글을 남긴다
		Artist artist = artistRepository.findById(artistId).orElseThrow(ArtistNotFoundException::new);
		Song song = songRepository.findById(songId).orElseThrow(NotFoundSongException::new);
		commentsRepository.save(Comments.builder().artist(artist).song(song).content(content).build());
	}

	@Override
	public CommentsResponseDto getComments(Long songId) {
		List<CommentsResponse> comments = commentsRepository.findBySongId(songId)
			.stream()
			.map(CommentsResponse::from)
			.toList();
		return CommentsResponseDto.builder()
			.comments(comments)
			.build();
	}

	@Override
	public SongListByThemeResponseDto getSongListByTheme(String themeName, int size) {
		List<Song> songs = songRepository.findByThemeName(themeName, size);
		return SongListByThemeResponseDto.builder()
			.size(songs.size())
			.songList(songs.stream().map(SimpleSongDto::from).toList())
			.build();
	}

	@Override
	public SongInfoResponse getSong(Long songId) {
		Song song = songRepository.findById(songId).orElseThrow(NotFoundSongException::new);
		Artist artist = song.getArtist();
		List<Comments> commentsList = commentsRepository.findBySongId(songId);

		SongInfoResponseBuilder builder = SongInfoResponse.builder();

		builder = builder.songTitle(song.getTitle()).artist(ArtistInfoDto.from(artist))
			.lyrics(song.getLyrics()).chord(song.getChord()).bpm(song.getBpm())
			.songFileName(song.getMusicFileName()).albumImageFileName(song.getAlbumImage().getOriginalFileName())
			.songDescription(song.getSongDescription());

		builder = builder.movedEmotionCount(song.getMovedEmotionCount())
			.likeEmotionCount(song.getLikeEmotionCount()).energizedEmotionCount(song.getEnergizedEmotionCount())
			.excitedEmotionCount(song.getExcitedEmotionCount())
			.funnyEmotionCount(song.getFunnyEmotionCount()).sadEmotionCount(song.getSadEmotionCount());

		List<CommentsInfoDto> comments = commentsList.stream()
			.map(CommentsInfoDto::from)
			.toList();

		builder = builder.likeCount(song.getLikeCount())
			.downloadCount(song.getDownloadCount())
			.playCount(song.getPlayCount());
		builder = builder.comments(comments);

		return builder.build();
	}

}
