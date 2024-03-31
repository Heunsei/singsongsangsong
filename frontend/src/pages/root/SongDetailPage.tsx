import React from "react";
import { useParams } from "react-router";

import testArtist from "./../../sources/testimg/artistProfile.jpg";
import Album from "../../components/public/Album";
import SongDetails from "../../components/pageComponents/songDetailpageComponent/SongDetails";
import SongInfo from "../../components/public/analysis/SongInfo";
import EmotionBox from "../../components/public/emotionBox/EmotionBox";
import styles from "./SongDetailPage.module.css";
import CommentForm from "../../components/public/comment/CommentForm";
import SongHeader from "../../components/pageComponents/songDetailpageComponent/SongHeader";
import { useAxios } from "../../hooks/api/useAxios";

const DUMMY_DATA = {
  songTitle: "test1",
  artist: {
    artistId: "1",
    nickname: "testName1",
    userName: "testName1",
    profileImageFileName: "test1",
    introduction: "소개입니당",
  },
  songFileName: "filename1",
  albumImageFileName: "albumImageFilename",
  songDescription: "곡 소개 입니당.",
  movedEmotionCount: 13,
  likeEmotionCount: 15,
  excitedEmotionCount: 16,
  energizedEmotionCount: 72,
  funnyEmotionCount: 65,
  sadEmotionCount: 34,

  lyrics: "가사입니다",
  chord: "C#",
  bpm: 160,

  likeCount: 53,
  downloadCount: 53,
  playCount: 3213,

  comments: {
    artistId: 23,
    nickname: "commentNick",
    content: "댓글내용인데용",
    profileImageName: "응애",
    createdAt: "2024-04-01",
  },
};

const SongDetailPage = () => {
  const { songId } = useParams();

  // getSong -> 아래로 내려줄 기본적인 페이지 로드 시 실행할 요청
  // response =>
  const { response, isLoading, refetch } = useAxios({
    method: "GET",
    url: `/song/${songId}`,
  });

  return (
    <div className={`w-100 flex-col-center gap-30 ${styles.container}`}>
      <SongHeader
      // songtitle={DUMMY_DATA.songTitle}
      // artistName={DUMMY_DATA.artist.nickname}
      // likeCount={DUMMY_DATA.likeCount}
      // playCount={DUMMY_DATA.playCount}
      // downloadCOunt={DUMMY_DATA.downloadCount}
      />
      <div className={`flex-col-center gap-30 ${styles.content}`}>
        <SongInfo
        // songDescription={DUMMY_DATA.songDescription}
        // bpm={DUMMY_DATA.bpm}
        // chord={DUMMY_DATA.chord}
        />
        <SongDetails
        // lyrics={DUMMY_DATA.lyrics}
        />
        <div className={`flex-col-center  ${styles.emotionBox}`}>
          <p>사람들은 이 곡에서</p>
          <p>이러한 느낌을 받았어요</p>
          <EmotionBox />
        </div>
        <CommentForm />
      </div>
    </div>
  );
};

export default SongDetailPage;
