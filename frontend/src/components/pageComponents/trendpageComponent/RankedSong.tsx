import React from "react";
import styles from "./RankedSong.module.css";
import img from "./../../../sources/testimg/cover.png";
import crwon from "./../../../sources/imgs/crownicon.png";

const RankedSong = () => {
  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <span>
          <img src={crwon} alt="" style={{ width: "36px", height: "36px" }} />
        </span>
        <p>#{" 따뜻함"}</p>
      </div>
      <div className={`flex-row-center ${styles.content}`}>
        <div className={`${styles.albumCover}`}>
          <img src={img} alt="" />
        </div>
        <div className={`flex-col-center ${styles.songInfo}`}>
          <p style={{ fontSize: "24px", fontWeight: "700" }}>{"song name"}</p>
          <p>{"song author"}</p>
        </div>
        <div className={styles.playCount}>
          <p>
            <span style={{ fontSize: "36px", color: "#0085E5" }}>
              {"3211"}
            </span>
            회 재생
          </p>
        </div>
      </div>
    </div>
  );
};

export default RankedSong;
