import React, { useEffect, useState } from "react";
import { FaArrowLeft, FaArrowRight } from "react-icons/fa";

import styles from "./TrendWithOptions.module.css";
import BarChart from "../../public/chart/barChart/BarChart";
import RankedSong from "./RankedSong";

const AGES = [10, 20, 30, 40, 50, 60];
const TAB_OPTIONS = ["장르", "분위기", "곡", "아티스트"];

// type OptionType = {
//   gender: string;
//   age: number;
//   tabOption: string;
// };

/** 성별과 연령으로 알아보는 트렌드
 * @todo 나중에 주차별 데이터를 부모 page에서 내려받아야함
 * @todo props를 내리는건 주차로 관리하고 기본적으로 state는 여기서 관리하는게 좋을듯
 * @returns
 */
const TrendWithOptions = () => {
  // 선택된 option들을 관리할 state
  const [gender, setGender] = useState<boolean>(false);
  const [age, setAge] = useState<number>(10);
  const [option, setOption] = useState<string>("장르");

  const handleChangeGender = () => {
    setGender(!gender);
  };

  const handleAgeDecrease = (): void => {
    let index = AGES.indexOf(age);
    index = (index + AGES.length - 1) % AGES.length;
    setAge(AGES[index]);
  };
  const handleAgeIncrease = (): void => {
    let index = AGES.indexOf(age);
    index = (index + AGES.length + 1) % AGES.length;
    setAge(AGES[index]);
  };
  const handleOptionChange = (option: string): void => {
    setOption(option);
  };

  // options들이 변할 때 마다 새로운 데이터를 요청하는 fetch문 필요
  useEffect(() => {}, []);

  return (
    <div className={`flex-col-center gap-15 ${styles.container}`}>
      <h1>성별과 연령으로 알아보는 트렌드</h1>
      <div className={`shadow-box b-15 w-100 bg-box ${styles.content}`}>
        <div className={`w-100 ${styles.topSection}`}>
          <div className={styles.genderSelector} onClick={handleChangeGender}>
            <div
              className={`flex-row-center b-15 ${styles.toggleButton} 
              ${gender === false ? styles.active : ""}`}
            >
              남성
            </div>
            <div
              className={`flex-row-center b-15 ${styles.toggleButton}
             ${gender === true ? styles.active : ""}`}
            >
              여성
            </div>
          </div>
          <div className={styles.ageSelector}>
            <FaArrowLeft
              style={{ cursor: "pointer" }}
              onClick={handleAgeDecrease}
            />
            <p>{age}대</p>
            <FaArrowRight
              style={{ cursor: "pointer" }}
              onClick={handleAgeIncrease}
            />
          </div>
        </div>
        <div className={styles.bottomSection}>
          <div className={`flex-col-center ${styles.bottomLeftContainer}`}>
            <div>
              <ul className={styles.tabMenu}>
                {TAB_OPTIONS.map((element) => (
                  <li
                    key={element}
                    className={element === option ? styles.tabActive : ""}
                    onClick={() => handleOptionChange(element)}
                  >
                    {element}
                  </li>
                ))}
              </ul>
            </div>
            <div style={{ width: "100%", height: "500px" }}>
              <BarChart option={option} />
            </div>
          </div>
          <div className={`flex-col-center ${styles.bottomRightContainer}`}>
            <h1>{option} 1등 곡은??</h1>
            <RankedSong />
            <RankedSong />
            <RankedSong />
          </div>
        </div>
      </div>
    </div>
  );
};

export default TrendWithOptions;