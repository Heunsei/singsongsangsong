import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import { json } from "react-router-dom";
import { RiMusic2Fill } from "react-icons/ri";
import { TbMoodNerd } from "react-icons/tb";

import { useAxios } from "../../hooks/api/useAxios";
import {
  getArtist,
  getEmotions,
  getFollowerCount,
  getSongList,
} from "../../utils/api/artistApi";
import styles from "./ArtistPage.module.css";
import ArtistHeader from "../../components/pageComponents/artistpageComponent/ArtistHeader";
import EmotionBox from "../../components/public/emotionBox/EmotionBox";
import BarChart from "../../components/public/chart/barChart/BarChart";
import MusicTable from "../../components/public/music/MusicTable";
import nodataImg from "./../../sources/imgs/nodataimg.webp";
import { getPresignedUrl } from "../../utils/api/minioApi";
import axios from "axios";
import ArtistEmotionBox from "../../components/public/emotionBox/ArtistEmotionBox";
import { axiosInstance } from "../../hooks/api";

const ArtistPage = () => {
  // const { artistId } = useParams();
  const [followerCount, setFollowerCount] = useState<number | null>(null);
  const [songList, setSongList] = useState(null);
  const artistId = 1;
  // 아티스트 정보를 요청하는 페이지 로드시 실행할 함수
  const { response, isLoading, refetch, error } = useAxios({
    url: `/artist/${artistId}`,
    method: "GET",
  });

  const {
    response: emotions,
    isLoading: emotionLoading,
    refetch: emotionRefetch,
    error: emotinEror,
  } = useAxios({
    url: `/artist/emotions/${artistId}`,
    method: "GET",
  });

  // console.log(response);
  // console.log(emotions);

  useEffect(() => {
    const getItems = async () => {
      const emotions = await getEmotions(1);
      console.log(emotions);
    };
    // console.log("djasiofgnioasgnaegniae", getArtist(1));
    // setFollowerCount(getFollowerCount(artistId))
    // getSongList(artistId)
    getItems();
  }, []);

  if (!response) {
    return (
      <div className={`w-100 flex-col-center ${styles.nonePage}`}>
        <img src={nodataImg} alt="" />
        <p>해당 아티스트에 대한 정보가 없습니다</p>
      </div>
    );
  }
  return (
    <div className={styles.container}>
      <div className={styles.headerSection}>
        <ArtistHeader
          artistId={response.artistId}
          nickname={response.nickname}
          profileImageFileName={response.profileImageFileName}
          introduction={response.introduction}
        />
      </div>
      <div className={styles.content}>
        <div className={`flex-row-center w-100`}>
          <div className={"w-50"}>
            <h2 className={`flex-row`}>
              주요 장르&nbsp;
              <RiMusic2Fill />
            </h2>
            <div style={{ height: "300px" }} className={`w-100`}>
              <BarChart option="장르" />
            </div>
          </div>
          <div className={"w-50"}>
            <h2 className={`flex-row`}>
              주요 분위기&nbsp;
              <RiMusic2Fill />
            </h2>
            <div style={{ height: "300px" }} className={`w-100`}>
              <BarChart option="분위기" />
            </div>
          </div>
        </div>
        <div className={`flex-col-center bg-box py-15 ${styles.emotionBox}`}>
          <h1>사람들은 {response.nickname} 님에게서</h1>
          <h1>이런 느낌을 받았어요</h1>
          <ArtistEmotionBox />
        </div>
        <div className={`w-100 py-15`}>
          <MusicTable />
        </div>
      </div>
    </div>
  );
};

export default ArtistPage;