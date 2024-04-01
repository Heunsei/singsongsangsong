package com.ssafy.singsongsangsong.dto;

import java.util.List;
import java.util.Map;

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
public class BpmChartDto {
	
	private Map<String, List<TrendSongDto>> bpms;

}