# ONE Click
<div align="center">
	<img src="https://img.shields.io/badge/Java-FF160B?style=for-the-badge&logo=Conda-Forge&logoColor=white" />
	<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white" />
 	<img src="https://img.shields.io/badge/Javascript-F7DF1E?style=for-the-badge&logo=Javascript&logoColor=white" />
 	<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white" />
	<br />
	<img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white" />
 	<img src="https://img.shields.io/badge/SASS-CC6699?style=for-the-badge&logo=Sass&logoColor=white" />
	<img src="https://img.shields.io/badge/SCSS-CC6699?style=for-the-badge&logo=Sass&logoColor=white" />
	<img src="https://img.shields.io/badge/CSS-3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white" />
	<br />
 	<img src="https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=white" />
	<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white" />
	<img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white" />
</div>  



  

## 팀원
윤지희  
박예빈  
이동현  
김정민



## 프로젝트 소개
기획서비스 : 원데이 클래스 사이트  
기획배경 : 사용자가 원하는 원데이 클래스를 자유롭게 수강할 수 있는 사이트의 필요성  
기획목적 : 다양한 원데이 클래스 제공  
기대효과 : 일회성 클래스에 관심 있는 사용자의 유입과 광고 수수료로 인한 수익 창출  
주요기능 : 일반 결제와 구독권 중 한 가지 방법을 사용해 원데이 클래스 결제  
주요고객 : 자기 발전에 투자하는 성향이 강한 전 연령대  
서비스 채널 : 웹사이트

#### 프로젝트 기간 : 2023.05.04 ~ 2023.05.26


    
## 설계의 주안점
1. 직관적인 ui/ux와 지도를 제공하여 사이트 이용자가 클래스 정보를 쉽게 얻을 수 있도록 구현  
2. 구독권과 일반 결제로 나눠 사용자에게 보다 다양한 선택권 부여  

  

## 구현 기능
* 홈
  * TOP10 클래스, 특정 카테고리 클래스 추천
  * 사이트 통합 기능 제공
* 클래스
  * 클래스 메인 페이지
  * 카테고리 별 렌더링
  * 클래스 상세 페이지에서 프로젝트 소개, 크리에이터 정보 확인, 리뷰 확인 기능 구현
  * 구독권 및 일반 결제 기능 구현
  * 장바구니 담기 구현
* 마이페이지
  * 메인 보드에서 마이페이지 전체 목록 볼 수 있도록 구현 
  * 일반 회원 : 수강 중인 클래스, 위시리스트, 내 후기, 장바구니, 결제내역, 내 구독권, 회원정보 수정, 로그아웃 
  * 강사 회원 : 일반 회원과 같은 기능 가능하도록 구현하고 수강생 확인 기능 추가
* 로그인/회원가입
  * 일반 회원, 강사 회원으로 나누어 가입
* 결제 및 구독권
  * 구독권 및 일반 결제 모두 아임포트로 구현
  * 구독권 페이지에서 기간 별 선택이 가능하도록 구현
 

  

## 개발 환경
* OS : Window 10, Mac OS
* IDE : VS Code, IntelliJ
* Language : Java, Javascript
* FrontEnd : HTML/CSS
* Library : ReactJS
* DB : Oracle
* Server : Tomcat


    

## 사용 라이브러리
```
yarn add react-naver-maps
yarn add react-router-dom
yarn add add axios
yarn add styled-components
yarn add react-slick
```
