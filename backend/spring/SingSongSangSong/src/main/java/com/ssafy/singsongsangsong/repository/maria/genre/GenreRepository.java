package com.ssafy.singsongsangsong.repository.maria.genre;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.singsongsangsong.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long>, GenreRepositoryCustom {
}