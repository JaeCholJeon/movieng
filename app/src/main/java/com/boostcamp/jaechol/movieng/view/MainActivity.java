package com.boostcamp.jaechol.movieng.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.boostcamp.jaechol.movieng.R;
import com.boostcamp.jaechol.movieng.databinding.MovieActivityBinding;
import com.boostcamp.jaechol.movieng.viewmodel.MovieViewModel;


/**
 * MainView , MainActivity
 *
 * 기본 기능
 *
 * 1. EditText를 통해 검색어를 입력받아 검색버튼으로 영화 검색
 * 2. 네이버 검색 API를 활용하여 검색어에 해당하는 결과 받아오기
 * 3. 검색결과를 RecyclerView에 표시하기
 * 4. 각 영화정보에는 아래 정보가 모두 표시 : 썸네일 이미지, 제목, 평점, 연도, 감독, 출연배우
 * 5. 목록에서 영화 선택시 해당 영화 정보 link페이지로 이동
 *
 * 추가 기능
 *
 * 1. 검색 시 네트워크 상태 체크
 * 2. 무한 스크롤 ( 한번에 모든 데이터를 로딩하지 않고 , 스크롤 인덱스에 따라 데이터 로딩 )
 * 3. 네이버 검색 API 1000 초과 쿼리 불가능함 - > 파라미터 조절하여 1000이하로 쿼리될 수 있게 조절
 * EX) REQUEST  : (https://openapi.naver.com/v1/search/movie.json?query=영화&start=1001)
 * RESPONSE : {
 * "errorMessage": "Invalid start value (부적절한 start 값입니다.)",
 * "errorCode": "SE03"
 * }
 * 4. 빈 검색어 , 빈 검색 결과 처리
 * 5. 데이터 바인딩 사용
 * 6. MVVM 패턴 적용
 */
public class MainActivity extends AppCompatActivity {

    MovieActivityBinding layoutBinding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.movie_activity);
        MovieViewModel movieViewModel = new MovieViewModel(MainActivity.this, layoutBinding.recyclerviewList);
        layoutBinding.setModelView(movieViewModel);
        movieViewModel.onCreate();
        /**
         * recyclerView 디바이더 설정
         */
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(MainActivity.this, new LinearLayoutManager(this).getOrientation());
        layoutBinding.recyclerviewList.addItemDecoration(dividerItemDecoration);
    }

}