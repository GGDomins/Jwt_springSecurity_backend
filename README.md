

# Jwt_SpringSecurity_Backend
JWT, springSecurity,redis..etc 를 공부하고 실습해보는 Repo입니다.


<!-- TABLE OF CONTENTS -->
## 목차

* [About the Project](#about-the-project)
  * [Tech Stack](#tech-stack)
  * [File Structure](#file-structure)
* [Getting Started](#getting-started)
  * [Installation](#installation)
* [Results](#results)
* [Description](#description)
* [Future Work](#future-work)
* [Contributors](#contributors)
* [License](#license)


<!-- ABOUT THE PROJECT -->
## About The Project
**API 명세서 및 블로그 작업중**

### Tech Stack
프로그램에서 사용하는 라이브러리 및 종속성 입니다. 
* [Oauth2](https://oauth.net/2/)
* [SpringSecurity](https://docs.spring.io/spring-security/reference/index.html)
* [lombok](https://projectlombok.org/setup/)
* [jwt](https://jwt.io/)
* [redis](https://redis.io/) 
* [servlet](https://www.ros.org/) 
* [h2](https://www.h2database.com/html/main.html)

### File Structure
    .
    ├── .gradle                
    ├── .idea                  
    ├── . build                
    ├── . build               
    ├── . out                   
    ├── . src                   
    │   ├── main                
    │   ├── test               
    ├── .README.md             
    ├── .build.gradle           
    ├── .gradlew               
    ├── .gradlew.bat         
    └── .settings.gradle     
    

<!-- GETTING STARTED -->
## Getting Started

### Installation
1. Clone the repo
```
git clone https://github.com/My-Rolling-paper/Jwt_springSecurity_backend.git
```

<!-- RESULTS -->
## Results
ResponseEntity 반환값입니다. 
### localhost:8080/signup

<details>
    <summary> Success </summary>
 
**RequestBody**
```
{
    "email":"kevin0928@naver.com",
    "name" : "kevin",
    "password" : "1234"
}
```
**ResponseBody**
```
{
    "code": 200,
    "message": "회원 가입 성공",
    "data": {
        "id": 1,
        "name": null,
        "email": "kevin0928@naver.com",
        "roles": [
            "ROLE_USER"
        ],
        "enabled": true,
        "password": "$2a$10$zedB.8n3nTeGDm/AOr6gUOw2y.29IbPM4QfteJHsosriKPJUwCMH2",
        "username": "kevin0928@naver.com",
        "authorities": [
            {
                "authority": "ROLE_USER"
            }
        ],
        "accountNonExpired": true,
        "credentialsNonExpired": true,
        "accountNonLocked": true
    }
}
```
</details>


<details>
    <summary> Fail </summary>
 
**RequestBody**
```
{
    "email":"kevin0928@naver.com", -> 중복된 이메일 
    "name" : "kevin",
    "password" : "1234"
}
```
**ResponseBody**
```
{
    "code": 400,
    "message": "이미 사용 중인 이메일입니다.",
    "data": null
}
```
</details>

---
### localhost:8080/login

<details>
    <summary> Success </summary>
 
**RequestBody**
```
{
    "email":"kevin0928@naver.com",
    "password" : "1234"
}
```
**ResponseBody**
```
{
    "code": 200,
    "message": "로그인 성공",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrZXZpbjA5MjhAbmF2ZXIuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sIkFVVEhPUklUSUVTX0tFWSI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjg0MzA4OTQzLCJleHAiOjE2ODQzMTI1NDN9.j7kc9oXi87ET4yH9X2pmOhSwIMu391S3zmYE3e7T-qU"
}
```
</details>


<details>
    <summary> Fail (Email-Error) </summary>
 
**RequestBody**
```
{
    "email":"kevin0928@nver.com", -> 틀린 이메일
    "password" : "1234"
}
```
**ResponseBody**
```
{
    "code": 400,
    "message": "이메일을 잘못 입력하셨습니다.",
    "token": null
}
```
</details>

<details>
    <summary> Fail (Password-Error) </summary>
 
**RequestBody**
```
{
    "email":"kevin0928@nver.com", 
    "password" : "1234" -> 틀린 비밀번호 
}
```
**ResponseBody**
```
{
    "code": 400,
    "message": "비밀번호를 잘못 입력하셨습니다.",
    "token": null
}
```
</details>

---
### localhost:8080/my-page

<details>
    <summary> Success </summary>
 
**Authentication HttpServletRequest**
**ResponseBody**
```
{
    "code": 200,
    "message": "회원 인증 성공",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrZXZpbjA5MjhAbmF2ZXIuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sIkFVVEhPUklUSUVTX0tFWSI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjg0MzA4OTQzLCJleHAiOjE2ODQzMTI1NDN9.j7kc9oXi87ET4yH9X2pmOhSwIMu391S3zmYE3e7T-qU"
}
```
</details>

<details>
    <summary> Fail </summary>
 
**Non-authentication HttpServletRequest**
**ResponseBody**
```
{
    "code": 401,
    "message": "회원 인증 실패",
    "token": null
}
```
</details>

---
### localhost:8080/refresh-token

<details>
    <summary> Success </summary>
 
**member has a refresh token**
**RequestBody**
```
{

    "refreshToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrZXZpbjA5MjhAbmF2ZXIuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sIkFVVEhPUklUSUVTX0tFWSI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjg0MzA5MzY5LCJleHAiOjE2ODQ1MjUzNjl9.g34oe9qIMlU-mA4Obosr2LioezBwoKXMc9OPhM00GpM"
}
```
**ResponseBody**
```
{
    "code": 200,
    "message": "토큰 재발급 성공",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrZXZpbjA5MjhAbmF2ZXIuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sIkFVVEhPUklUSUVTX0tFWSI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjg0MzA5Mzg4LCJleHAiOjE2ODQzMTI5ODh9.oIM572DqIUTWY3FLWEs9OfRrQZqXExvO0aBu2CoiW7U",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrZXZpbjA5MjhAbmF2ZXIuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sIkFVVEhPUklUSUVTX0tFWSI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjg0MzA5MzY5LCJleHAiOjE2ODQ1MjUzNjl9.g34oe9qIMlU-mA4Obosr2LioezBwoKXMc9OPhM00GpM"
}
```
</details>

<details>
    <summary> Fail </summary>
 
**Member does not have a refresh token**
**RequestBody**
```
{

    "refreshToken":""
}
```
**ResponseBody**
```
{
    "timestamp": "2023-05-17T07:47:39.382+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "path": "/refresh-token"
}
```
</details>

---
<!-- Description -->
## Description
### 작업중

<!-- FUTURE WORK -->
## Future Work
* TDL
- [x] Task 1 RefreshToken 구현
- [x] Task 2 AccessToken을 RefreshToken로 재발급
- [ ] Task 3 API 명세서
- [ ] Task 4 프론트와 서버 통신
- [ ] Task 5 Blog에 글 쓰기



<!-- CONTRIBUTORS -->
## Contributors
~~* [Member Name]~~




<!-- LICENSE -->
## License
License 작
