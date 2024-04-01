package com.ssafy.singsongsangsong.webclient;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Service
public class WebClientRequestService {

	private final WebClient webClient;

	public static class RequestAnalyzeSongDto {
		private Long songId;
		private String savedFileName;

		public RequestAnalyzeSongDto(Long songId, String savedFileName) {
			this.songId = songId;
			this.savedFileName = savedFileName;
		}
	}

	public void requestAnalyzeSong(Long songId, String savedFileName) {
		RequestAnalyzeSongDto requestAnalyzeSongDto = new RequestAnalyzeSongDto(songId, savedFileName);
		webClient.post()
			.uri("/song/{songId}", songId)
			.bodyValue(requestAnalyzeSongDto)
			.retrieve()
			.bodyToMono(Void.class)
			.block();
	}

	public void requestSaveSimilarity(Long songId) throws WebClientRequestException {
		ResponseEntity<Void> response = webClient.post()
			.uri("/similarity/{songId}", songId)
			.retrieve()
			.toBodilessEntity()
			.block();

		Objects.requireNonNull(response, "응답을 받아오는 것에 문제가 생김");
		if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
			throw new WebClientResponseException("Failed to save similarity", response.getStatusCode().value(),
				response.getStatusCode().toString(), null, null, null);
		}
	}

	public List<SimilarityResponse.SimilarityInfo> requestSelectSimilarity(Long songId) throws
		WebClientRequestException {
		ResponseEntity<SimilarityResponse> response = webClient.get()
			.uri("/similarity/{songId}", songId)
			.retrieve()
			.toEntity(SimilarityResponse.class)
			.block();

		Objects.requireNonNull(response, "응답을 받아오는 것에 문제가 생김");

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new WebClientResponseException("Failed to select similarity", response.getStatusCode().value(),
				response.getStatusCode().toString(), null, null, null);
		}

		return Objects.requireNonNull(response.getBody()).data;
	}

	@Builder
	@Getter
	@Setter
	public static class SimilarityResponse {
		@Builder
		@Getter
		@Setter
		public static class SimilarityInfo implements Comparable<SimilarityInfo> {
			private Long similarSongId;
			private Float distance;

			@Override
			public int compareTo(@NotNull SimilarityInfo o) {
				return Float.compare(o.distance, this.distance);
			}
		}

		List<SimilarityInfo> data;
	}

}