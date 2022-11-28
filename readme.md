# README.md

## 0. 시작하기 앞서

 해당 프로젝트는 운동하고 있거나 운동을 하고 싶어하는 사람들을 모아 같이 운동할 수 있도록 운동을 장려하는 어플리케이션 '나와'를 제작하는 프로젝트였다. 팀원간의 역할 분배는 아래와 같다.

김주원 - BE, jwt 토큰 및 자동 문자 발송 서비스 담당

이정재 - BE, 팀장, 파일 시스템 및 CD 담당

임진현 - FE, 부팀장, jwt 토큰 처리 및 매칭, 채팅 서비스를 포함한 FE 기능을 총괄하여 어플리케이션 제작

조호형 - FE, 채팅 서비스 및 마이페이지(캘린더 포함) 담당

한재혁 - FE, 서비스 소개 웹 페이지, 파일 시스템, CSS 담당

홍성목 - BE, 메이트 관련 서비스 및 Stomp 기반 채팅 시스템, 캘린더 담당

<br>

팀 프로젝트라면 전체적인 프로젝트에 대한 readme를 작성하고 이를 포트폴리오로 활용하는 것이 일반적이나, 이번 프로젝트에서는 혼자서 프로젝트에 참가한 내용을 따로 정리하게 되었다. 그 이유는 아래와 같다.

1. 해당 프로젝트가 미완성으로 끝나, 전체적인 프로젝트에 대해 정리를 해서 보여줄 수 있는 것이 없다.
   - BE에서는 기획과 같은 방향으로 제작이 완료되었으나, FE는 제작에 실패하였다. 실패한 이유는 아래와 같다
     - 팀원간의 적절한 소통의 부재로 인해
     - 역량에 비해 과한 난이도의 프로젝트, 
     - FE와 BE 간의 인원 분배가 적절하지 못했음
2. 위와 같은 이유로 내가 담당했던 메이트 관련 서비스와 채팅 서비스는 FE에서 거의 활용되지 않았기 때문에, 프로젝트 전체에서는 내 작업물을 보여줄 수 있는 것이 없었다. 하지만 혼자서 두 대의 서버를 구축하여 연결했다는 것 자체를 활용할 수 있다고 생각했다.
   - 채팅 서비스를 완성할 때 쯤, FE에서 제작에 난항을 겪고 있는 것을 파악하여, 최대한 도움을 주기 위해 Spring-Boot 기반 및 Vue를 적용한 임시 FE를 직접 만들어서 테스트를 하였다. 해당 코드를 react native로 이식만 할 수 있었다면 적용할 수 있었으나, 이식을 성공하지 못해 결국 활용되지 못 하였다.
   - 하지만 혼자서 서로 다른 서버간에 stomp 기반의 Web Socket 연결에 성공했기 때문에 정리해서 활용하고자 마음먹게 되었다.

<br>

따라서 여기에 이번 프로젝트에서 내가 작성했던 코드에 대해 간략히 기록해두고자 한다.

<br>

<br>

## 1. 메이트 서비스 및 Stomp 기반 Web Socket 채팅

 해당 프로젝트에서 나는 캘린더를 통한 일정 추가 서비스와, 메이트 추가 및 삭제, 차단, 신고를 토대로 Stomp를 통해 채팅을 하거나 알림이 가도록 서비스를 만들었다. 캘린더 서비스는 기본적인 CRUD로 작동했으므로, 메이트 서비스 및 채팅 서비스에 대해서만 추가적인 설명을 하고자 한다.

<br>

### 1) 메이트 서비스

 '나와'는 다른 사용자와 메이트 관계가 될 수 있고, 메이트 신청 및 수락에 따라서 작동하는 기능은 아래와 같다.

- 상대에게 메이트 신청시 푸시 알람
- 







[sockJS와 stompJS](https://fgh0296.tistory.com/24)

https://daddyprogrammer.org/post/4077/spring-websocket-chatting/

https://stomp-js.github.io/stomp-websocket/codo/class/Client.html

https://memo-the-day.tistory.com/73?category=925491

위 기반으로 제작





위 



















<br>

<br>

## 2. UCC

이번 프로젝트에서 UCC 제작을 담당하게 되었다. 그래서 프로젝트 마지막 5일 동안의 일정은 다음과 같았다.

8/21 (일) : UCC 스크립트 작성

8/22 (월) : 도입부 작성 및 관련 오픈소스 영상 수집, 영상 편집 공부

8/23 (화) : 나레이션 녹음본, 그 외 효과음, BGM 및 오픈소스 아이콘 수집, 영상 편집 공부

8/24 (수) : 스크립트 수정 및 그에 따른 오픈소스 추가 수집, 영상 편집

8/25 (목) : 영상 편집 및 최종 수정

<br>

또한 이번 UCC의 기획 배경은 다음과 같다.

1. 목소리 좋은 팀원이 있었기에, TTS를 사용하지 않고 실제 목소리를 활용한다.
2. 촬영에 투자할 시간이 없기에, 오픈소스 영상들만을 활용한다.
3. 따라서 생동감이 떨어지기에, 차분한 느낌의 광고 UCC 컨셉으로 연출한다.
4. 또한 영상 편집은 처음이나 다름없기에, 아는 정보가 없어 최대한 부딪혀보면서 생각해본다.
   - 간단하게 2분짜리 영상을 짜집어 본 적은 있으나, UCC와 같은 영상을 만드는 것은 처음이었다.

결국 최종적인 컨셉트는 '유튜브에 있을법한 차분한, 다큐 느낌의 광고 UCC'가 되었다. 영상은 아래와 같다.

<br>

![썸네일](https://user-images.githubusercontent.com/95673624/188423788-ca05750d-36e0-42c1-a27d-63469dd904ce.png)

https://youtu.be/xIYFsRf1OSA

<br>

UCC 부문에서의 수상은 프로젝트 수상과는 별개로 이루어져서, UCC 수상을 노리고 자는 시간까지 줄여가며 열심히 만들었던 작품이다. 하지만 이는 아래와 같은 이유로 입상하지 못하게 되었다.

1. 실제 목소리를 활용한 것은 좋았지만, 오픈소스 영상만을 활용했기 때문에 생동감이나 전달력이 크게 줄어들었다.
2. 게다가 초반 미사여구가 많아 전하고자 하는 목적이 더 많이 희석되었다. 기획 의도와 최소한 같은 길이 정도로 어플리케이션에 대한 설명이 있거나 기획 의도에 대한 설명 자체를 더 줄였어야 했다.
3. 영상 편집에 대해 미숙했기 때문에, 영상으로 표현하고자 하는 바를 다 표현하지 못했으며, 효과를 적용할 때도 디테일한 부분은 전혀 신경쓰지 못해 완성도가 크게 떨어졌다.
4. 무엇보다도 임팩트가 있는 UCC가 가장 메세지 전달력이 좋으며, 설득력이 있고, 기억에 남기 쉽다. 실제로 입상한 UCC들 또한 그러했다. 즉 UCC의 방향 자체를 차분한 광고로 설정한 시점에서 UCC의 본래 목적을 잃어버린 것이다.

<br>

모처럼 같은 팀원이 좋은 목소리로 나레이션을 맡아주었는데 이 기회를 망치게 된 것 같아 매우 아쉬웠다. UCC라고 하기엔 퀄리티가 매우 낮지만, 처음 제작한 UCC인 만큼 기록으로 남기기 위해 이를 저장하고 첨부한다.

<br>

<br>

## 3. 마무리하며























그 외 참고자료

---

\<네이버 메세지 자동 송신 API\> - 해보고 싶었으나 다른 팀원이 직접 하게 됨

[Simple &amp; Easy Notification Service &#xC18C;&#xAC1C; - Simple &amp; Easy Notification Service](https://guide.ncloud-docs.com/docs/ko/sens-sens-1-1)

[https://jae04099.tistory.com/entry/Nodejs-네이버-문자-전송-API-사용하기feat-axios](https://jae04099.tistory.com/entry/Nodejs-%EB%84%A4%EC%9D%B4%EB%B2%84-%EB%AC%B8%EC%9E%90-%EC%A0%84%EC%86%A1-API-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0feat-axios)

[네이버 SENS API와 Node.js로 휴대전화 SMS 인증하기](https://g-song-ii.tistory.com/3)

[NAVER CLOUD PlATFORM[SENS API] - 네이버 문자 인증](https://giron.tistory.com/75)



\<Spring Boot 관련>

https://velog.io/@jsb100800/%EA%B0%9C%EB%B0%9C-WebRTC-SpringBoot-Vue.js%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-Group-Video-Cal

https://sumin2.tistory.com/60

https://github.com/seojiwon0702/keep_My_Receipt/tree/main/

https://memostack.tistory.com/155


