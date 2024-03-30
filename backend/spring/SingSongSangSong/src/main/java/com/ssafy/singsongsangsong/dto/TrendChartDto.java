package com.ssafy.singsongsangsong.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trend")
public class TrendChartDto {
	
	private List<TrendSongDto> weekly;
	private List<TrendSongDto> world;
	private List<TrendSongDto> korean;
	private EmotionSongsDto emotions;
	
}