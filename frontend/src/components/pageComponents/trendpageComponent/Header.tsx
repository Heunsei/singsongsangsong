import React from 'react';

import styles from './Header.module.css'
import { DateType } from '../../../utils/types';

interface HeaderType {
    selectedDate: DateType
    selectedWeek: number
    onOpen: () => void
}

const Header = ({ selectedDate, selectedWeek, onOpen }: HeaderType) => {
    return (
        <div className={styles.header}>
            <div className={styles.dateSection} onClick={onOpen}>
                <h2>{selectedDate.year}</h2>
                <p>{selectedDate.month}월 {selectedDate.day}일</p>
                <p>{selectedWeek}주차</p>
            </div>
        </div>
    );
};

export default Header;