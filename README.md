# 영화정보 검색 어플리케이션
## 설명
- 네이버 검색 API를 활용하여 영화정보를 검색하는 어플리케이션 제작<br>
- 사용자로부터 검색어를 입력받아 검색결과 목록을 표시<br><br>

# 기본 기능 완료<br><br>

 -1. EditText를 통해 검색어를 입력받아 검색버튼으로 영화 검색<br>
 -2. 네이버 검색 API를 활용하여 검색어에 해당하는 결과 받아오기<br>
 -3. 검색결과를 RecyclerView에 표시하기<br>
 -4. 각 영화정보에는 아래 정보가 모두 표시 : 썸네일 이미지, 제목, 평점, 연도, 감독, 출연배우<br>
 -5. 목록에서 영화 선택시 해당 영화 정보 link페이지로 이동<br><br>

# 추가 기능<br><br>

 -1. 검색 시 네트워크 상태 체크<br>
 -2. 무한 스크롤 ( 한번에 모든 데이터를 로딩하지 않고 , 스크롤 인덱스에 따라 데이터 로딩 )<br>
 -3. 네이버 검색 API start 파라미터 1000 초과 쿼리 불가능함 - > 파라미터 조절하여 1000이하로 쿼리될 수 있게 조절<br>
   EX) REQUEST  : (https://openapi.naver.com/v1/search/movie.json?query=영화&start=1001)<br>
  RESPONSE :
  <br>{<br>
  "errorMessage": "Invalid start value (부적절한 start 값입니다.)",<br>
  "errorCode": "SE03"<br>
  }<br>
 -4. 빈 검색어 , 빈 검색 결과 처리<br><br>
 -5. 데이터바인딩 사용
 
 # 결과영상<br>
  ![Screenshot](https://github.com/JaeCholJeon/movieng/blob/master/Screenshot.gif)
 
 

