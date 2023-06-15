

# Jwt_SpringSecurity_Backend
JWT, springSecurity,redis..etc 를 공부하고 정리해보는 Repo입니다.


<!-- TABLE OF CONTENTS -->
## 목차

* [schema](#schema)
* [About the Project](#about-the-project)
  * [Blog](#blog)
  * [Tech Stack](#tech-stack)
  * [File Structure](#file-structure)
* [Getting Started](#getting-started)
  * [Installation](#installation)
* [Results](#results)
* [Future Work](#future-work)
* [Contributors](#contributors)
* [License](#license)


<!-- Description -->
## schema
![프로젝트 스키마 drawio](https://github.com/My-Rolling-paper/Jwt_springSecurity_backend/assets/104314593/26d7df14-7aca-458d-8eb4-2db6f8d5ae98)


<!-- ABOUT THE PROJECT -->
## About The Project
### Blog
1. [redis로 refreshToken 저장, 조회, 삭제 하기](https://solution-is-here.tistory.com/172)
2. [JWT를 이용해 AccessToken 발급,검사,정보 추출](https://solution-is-here.tistory.com/173)
3. [RefreshToken을 이용한 Logout 구현하기](https://solution-is-here.tistory.com/177)

### Tech Stack
프로그램에서 사용하는 라이브러리 및 종속성된 파일들입니다. 
* [Oauth2](https://oauth.net/2/)
* [SpringSecurity](https://docs.spring.io/spring-security/reference/index.html)
* [lombok](https://projectlombok.org/setup/)
* [jwt](https://jwt.io/)
* [redis](https://redis.io/) 
* [servlet](https://www.ros.org/) 
* [mySql](https://www.mysql.com/)
* [HTTPONLY](https://developer.mozilla.org/ko/docs/Web/HTTP/Cookies)
* [Cookie](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-web-cookies)

### File Structure
    .
    ├── .gradle                
    ├── .idea                  
    ├──  build
    ├──  gradle    
    ├──  out                   
    ├──  src                   
    │   ├── main                
    │   ├── test   
    ├──  LICENSE  
    ├──  README.md             
    ├──  build.gradle           
    ├──  gradlew               
    ├──  gradlew.bat         
    └──  settings.gradle     
    

<!-- GETTING STARTED -->
## Getting Started

### Installation
1. Clone the repo
```
git clone https://github.com/yongjun-hong/Jwt_springSecurity.git
```

<!-- RESULTS -->
## Results

### localhost:8080/signup (POST)

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
        "id": 3,
        "name": "kevin",
        "email": "kevin0928@naver.com",
        "roles": [
            "ROLE_USER"
        ],
        "enabled": true,
        "password": "$2a$10$HRHe9./bnjCH6Aby3o/.MOEcOJnC7BDjsmPbyJ4yE9TIl5B5jzDBy",
        "username": "kevin0928@naver.com",
        "authorities": [
            {
                "authority": "ROLE_USER"
            }
        ],
        "accountNonLocked": true,
        "credentialsNonExpired": true,
        "accountNonExpired": true
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
    "code": 409,
    "message": "이미 사용 중인 이메일입니다.",
    "data": null
}
```
</details>


---
### localhost:8080/login (POST)

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
### header
 ```
refreshToken=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrZXZpbjA5MjhAbmF2ZXIuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sIkFVVEhPUklUSUVTX0tFWSI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjg2MjMwMDU3LCJleHAiOjE2ODY0NDYwNTd9.ZceFy6-XgStt5B8xI1Gz258KTAaSOrNyqFrtDtjEVD0;
 Path=/; Max-Age=3600000; Expires=Thu, 20 Jul 2023 05:14:19 GMT; Secure; HttpOnly; SameSite=None
 ```
 ### body
```
{
    "code": 200,
    "message": "로그인 성공",
    "token": null,
    "expireTimeMs": null
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
    "code": 401,
    "message": "이메일을 잘못 입력하셨습니다.",
    "token": null,
    "expireTimeMs": null
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
    "code": 403,
    "message": "비밀번호를 잘못 입력하셨습니다.",
    "token": null,
    "expireTimeMs": null
}
```
</details>

---
### localhost:8080/logout (POST)

<details>
    <summary> Success </summary>

### member has a refresh token!

**ResponseBody**

### body
```
{
    "code": 200,
    "message": "로그아웃 성공"
}
```
</details>



---
### localhost:8080/my-page (GET)

<details>
    <summary> Success </summary>
 
### Authentication HttpServletRequest

**ResponseBody**
```
{
    "code": 200,
    "message": "회원 인증 성공",
    "token": null,
    "name": "kevin",
    "email": "kevin0928@naver.com"
}
```
</details>

<details>
    <summary> Fail </summary>
 
### Non-authentication HttpServletRequest

**ResponseBody**
```
{
    "code": 401,
    "message": "회원 인증 실패",
    "token": null,
    "name": null,
    "email": null
}
```
</details>

---
### localhost:8080/refresh-token (POST)

<details>
    <summary> Success </summary>
 
### member has a refresh token

**ResponseBody**
```
{
    "code": 200,
    "message": "토큰 재발급 성공",
    "token": null,
    "expireTimeMs": null
}
```
</details>

<details>
    <summary> Fail </summary>
 
### Member does not have a refresh token

**ResponseBody**
```
{
    "code": 400,
    "message": "토큰 재발급 실패",
    "token": null,
    "expireTimeMs": null
}
```
</details>

---
### localhost:8080/passwordChange/{id} (POST)

<details>
    <summary> Success </summary>

**RequestBody**
```
{
    "currentPassword" : "124",
    "newPassword" : "1234"
}
```
**ResponseBody**
```
{
    "code": 200,
    "message": "비밀번호 변경 완료",
    "data": "124"
}
```
</details>

<details>
    <summary> Fail </summary>


### Wrong Password

**RequestBody**
```
{
    "currentPassword" : "123", -> 틀린 비밀번호
    "newPassword" : "1234"
}
```
**ResponseBody**
```
{
    "code": 600,
    "message": "비밀번호를 잘못 입력하셨습니다.",
    "data": null
}
```

### Enter the same password twice

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
    "currentPassword" : "1234", // 똑같은 비밀번호
    "newPassword" : "1234"
}
```
</details>

---


<!-- FUTURE WORK -->
## Future Work
* TDL
- [x] Task 1 RefreshToken 구현
- [x] Task 2 AccessToken을 RefreshToken로 재발급
- [ ] Task 4 Service - impl 분리
- [x] Task 3 API 명세서
- [x] Task 4 프론트와 서버 통신

* [수정내용](https://shining-fish-553.notion.site/Checklist-to-finish-off-5b5197d4b9e140058c6e4b84df71af78)


<!-- CONTRIBUTORS -->
## Contributors
[Yongjun Hong](https://github.com/yongjun-hong)




<!-- LICENSE -->
## License
MIT License

Copyright (c) 2023 My-Rolling-paper

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
