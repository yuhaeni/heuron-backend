# Heuron 백엔드 과제 
## 환자 정보 생성/조회/삭제 API 구현  🏥

<br>

### 문제 및 해결 방안 💡
<br>

1. 저장 1단계, 2단계 구분 필요
- 환자의 이미지 업로드 유무로 저장 단계를 구분해야 함
- 환자 정보와 이미지 업로드 시, 이미지가 업로드 된 경우에만 DB에 이미지 경로를 저장
- 상세 조회 시, 이미지 경로가 있는 경우에만 조회 가능하도록 함
   <br><br>
2. 저장 API Controller에서 MultipartFile, Dto를 함께 요청

- `@RequestBody`와 `@RequestParam` 어노테이션을 사용하여 json 형태의 데이터와 이미지 파일을 요청값으로 받으려고 했지만 `"Unsupported Media Type"` 오류 발생함
- multipart/form-data에 특화되어 여러 복잡한 값을 처리할 때 사용할 수 있는 `@RequestPart` 어노테이션을 사용하여 해결

<br>

3. 프로젝트 경로 문제로 인한 이미지 업로드 실패
- 실제 프로젝트가 위치한 경로(K드라이브)와 임시파일 저장 디렉토리(C 드라이브)가 상이해서 업로드 시 `java.io.IOException: The temporary upload location` 오류가 발생함
- `System Property`를 이용하여 현재 프로젝트의 위치를 얻어 저장 위치를 핸들링하여 해결함

<br>

4. 이미지 조회
- 웹 브라우저에 이미지 파일을 직접 보여주기 위해 `Resource` 인터페이스를 이용하여 이미지 URL 생성함 
- 이미지 확장자에 따라 `Content-Type`을 설정함
<br><br><br><br>

### API 정의 📖

1) 저장 API 
- 요청 url
  : [POST] http://localhost:8080/heuron/v1/patients
- json 샘플 
```
{
   data:{
       "name": "김옥순"
       ,"age": 38
       ,"gender": "W"
       ,"diseaseFlag": "Y"
   },
   img: 이미지파일.jpg
}
```

![image](https://github.com/yuhaeni/heuron-backend/assets/55648249/6965a637-ab7c-4207-943c-e73f1945e62a)

2) 상세조회 API
- 요청 url
  : [GET] http://localhost:8080/heuron/v1/patients/{id}
![image](https://github.com/yuhaeni/heuron-backend/assets/55648249/f0d7671c-68c3-4cce-8d06-4c29518b199e)


3) 이미지 조회 API
- 요청 url
  : [GET] http://localhost:8080/heuron/v1/patients/images/{id}
![image](https://github.com/yuhaeni/heuron-backend/assets/55648249/0934cd4a-7978-4814-b13e-12b2a1d8802d)


4) 삭제 API
- 요청 url
  : [DELETE] http://localhost:8080/heuron/v1/patients/{id}
![image](https://github.com/yuhaeni/heuron-backend/assets/55648249/e62b5e18-0947-4f62-be61-02471bef313f)




