import React from "react";
import styles from "./Notice.module.css";

type NoticeProps = {
  pathname: string;
};

const Notice: React.FC<NoticeProps> = ({ pathname }): JSX.Element => {
  let authInfo = {
    index: "",
    notice: "",
  };

  switch (pathname) {
    case "/register":
      authInfo.index = "Create your account";
      authInfo.notice = "discover brilliant idea";
      break;
    case "/login":
      authInfo.index = "LOGIN";
      authInfo.notice = "Welecome back!!";
      break;
  }
  return (
    <div className={styles.container}>
      <p className={styles.index}>{authInfo.index}</p>
      <p className={styles.notice}>{authInfo.notice}</p>
    </div>
  );
};

export default Notice;
