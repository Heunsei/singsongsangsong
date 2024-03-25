import React, { useEffect, useRef, useState } from "react";
import { MdLibraryMusic } from "react-icons/md";
import { GrPowerReset } from "react-icons/gr";
import AudioPlayer from "react-h5-audio-player";
import "react-h5-audio-player/lib/styles.css";
import { current } from "@reduxjs/toolkit";

import styles from "./UploadPage.module.css";
import onDragImg from "./../../sources/imgs/inputimg.jpg";
import Button from "../../components/buttons/Button";

// 참고해야할 사이트
// https://hojung-testbench.tistory.com/entry/React-%ED%8C%8C%EC%9D%BC-%EC%97%85%EB%A1%9C%EB%93%9C-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84
// https://guiyomi.tistory.com/148

type audioType = {
  file: File;
  url: string;
  name: string;
  type: string;
};

const UploadPage = () => {
  const [uploadFile, setUploadFile] = useState<audioType | null>(null);
  const [isActive, setIsActive] = useState<boolean>(false);
  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleDragEnter = (event: React.DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    event.stopPropagation();
    console.log("handleDragEnter");
  };

  const handleDragOver = (event: React.DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    event.stopPropagation();
    setIsActive(true);
    console.log("handleDragOver");
  };

  const handleDragLeave = (event: React.DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    event.stopPropagation();
    if (!event.currentTarget.contains(event.relatedTarget as Node)) {
      // 마우스가 자식 요소에서 떠나면만 이벤트 처리
      setIsActive(false);
      console.log("handleDragLeave");
    }
  };

  const handleDrop = (event: React.DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    event.stopPropagation();
    const data = event.dataTransfer.files;
    console.log(data);
    if (data && data[0].type.startsWith("audio/")) {
      const url = URL.createObjectURL(data[0]);
      setUploadFile({
        file: data[0],
        url: url,
        name: data[0].name,
        type: data[0].type,
      });
    }
    setIsActive(false);
  };

  const handleClear = () => {
    setUploadFile(null);
  };

  const handleFileInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    event.preventDefault();
    event.stopPropagation();

    const file = event.target.files;
    console.log(file);
    if (file) {
      const url = URL.createObjectURL(file[0]);
      setUploadFile({
        file: file[0],
        url: url,
        name: file[0].name,
        type: file[0].type,
      });
    }
    setIsActive(false);
  };

  return (
    <div className={`w-100 py-main ${styles.container}`}>
      <div className={`${styles.header}`}>
        <h1>곡 분석하기</h1>
        <p>곡을 업로드하고 자세한 정보를 얻어보세요</p>
      </div>
      <div className={`w-100 ${styles.uploadSection}`}>
        <h1>곡 업로드</h1>
        {uploadFile ? (
          <div className={`bg-box flex-col-center ${styles.uploadBox}`}>
            <div className={styles.player}>
              <AudioPlayer src={uploadFile.url} volume={0.5}></AudioPlayer>
            </div>
            <h1 className="flex-row-center" style={{ gap: "15px" }}>
              {uploadFile.name}{" "}
              <GrPowerReset
                onClick={handleClear}
                style={{ cursor: "pointer" }}
              />
            </h1>
            <Button>분석하기</Button>
          </div>
        ) : (
          <label
            id="musicFileUploader"
            className={`bg-box flex-col-center ${styles.uploadBox} ${
              isActive ? styles.active : ""
            }`}
            onDragEnter={(event) => handleDragEnter(event)}
            onDragOver={(event) => handleDragOver(event)}
            onDragLeave={(event) => handleDragLeave(event)}
            onDrop={(event) => handleDrop(event)}
          >
            <input
              disabled={isActive}
              ref={fileInputRef}
              type="file"
              accept="audio/*"
              className={`${styles.file}`}
              onChange={handleFileInput}
            />
            <MdLibraryMusic
              style={{ pointerEvents: "none" }}
              size={96}
              className={`${isActive ? styles.iconActive : ""}`}
            />
            <h3>오디오 파일을 업로드 해주세요</h3>
          </label>
        )}
      </div>
      <div className={`${styles.checkSection}`}>
        <h1>업로드 현황</h1>
        <div className={`w-100 bg-box flex-col-center ${styles.checkBox}`}>
          여기에 업로드한 곡들의 현황이 표시됩니다
        </div>
      </div>
    </div>
  );
};

export default UploadPage;
