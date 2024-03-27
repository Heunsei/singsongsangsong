package com.ssafy.singsongsangsong.entity;

import org.springframework.beans.factory.annotation.Value;

import com.ssafy.singsongsangsong.util.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nickname;

	private String username;
	private String password;

	@OneToOne
	@JoinColumn(name = "profileImageId")
	private Image profileImage;
	private int age;
	private char sex;
	private String introduction;
	@Enumerated(value = EnumType.STRING)
	private Role role;
}