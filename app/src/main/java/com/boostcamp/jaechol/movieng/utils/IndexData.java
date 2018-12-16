package com.boostcamp.jaechol.movieng.utils;

/**
 * 영화 리스트 인덱스 및 카운트 데이터 클래스
 */

public class IndexData {
    public int itemCount = 0;
    public int totalCount = 0;

    public IndexData() {
        this.itemCount = 0;
        this.totalCount = 0;
    }

    public void set(int itemCount, int totalCount) {
        this.itemCount = itemCount;
        this.totalCount = totalCount;
    }
}
