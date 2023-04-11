# README.md

## 0. 시작하기 앞서
- [전체 작업 내용](https://github.com/Mosquito0076/Nawa)

<br>

 해당 프로젝트는 운동하고 있거나 운동을 하고 싶어하는 사람들을 모아, 같이 운동할 수 있도록 하여 운동을 장려하는 어플리케이션 '나와'를 제작하는 프로젝트였습니다. 팀원간의 역할 분배는 아래와 같습니다.

<br>

김주원 - BE, jwt 토큰 및 자동 문자 발송 서비스 담당

이정재 - BE, 팀장, 파일 시스템 및 CD 담당

임진현 - FE, 부팀장, jwt 토큰 처리 및 매칭, 채팅 서비스를 포함한 FE 기능을 총괄하여 어플리케이션 제작

조호형 - FE, 채팅 서비스 및 마이페이지(캘린더 포함) 담당

한재혁 - FE, 서비스 소개 웹 페이지, 파일 시스템, CSS 담당

홍성목 - BE, 메이트 관련 서비스 및 Stomp 기반 채팅 시스템, 캘린더 담당

<br>

이 프로젝트에서 기술할 만한 내용은 STOMP를 통한 소켓 통신이라고 생각해서, 이를 따로 정리한 리포지토리를 생성하게 되었습니다. 특히 채팅 서비스를 완성할 때 쯤, FE에서 제작에 난항을 겪고 있는 것을 파악하여, 최대한 도움을 주기 위해 Spring-Boot 기반 및 Vue를 적용한 임시 FE를 직접 만들어서 테스트를 하였습니다. 

<br>

<br>

## 1. 메이트 서비스 및 Stomp 기반 Web Socket 채팅

 해당 프로젝트에서 나는 캘린더를 통한 **일정 추가** 서비스와, **메이트 추가 및 삭제, 차단, 신고**를 토대로 Stomp를 통해 **채팅**을 하거나 **알람**이 가도록 서비스를 만들었습니다. 캘린더 서비스는 기본적인 CRUD로 작성하였으므로, 메이트 서비스 및 채팅 서비스에 대해서만 추가적인 설명을 하고자 합니다.

<br>

### 1) 메이트 서비스

 '나와'는 다른 사용자와 메이트 관계가 될 수 있고, 메이트 신청 및 수락에 따라서 작동하는 기능은 아래와 같습니다.

- 상대에게 메이트 신청 시 푸시 알람
- 메이트 신청 수락 시 대화방 자동 생성
- 대화방에서 채팅을 통해 대화할 수 있음(읽음 처리 포함)
- 상대를 신고, 차단, 친구 삭제 할 수 있음

또한 메이트 서비스에서는 최대한 발생할 수 있는 오류를 처리하기 위해 노력하였으며, 채팅과는 다르게 소켓 서버를 하나 더 운영하도록 하였습니다.

<br>

```java
// AddMateService.java

            // 차단 당한 경우
            Optional<Block> isBlocked = blockRepository.findByBlockFromAndBlockTo(user2, user1);

            // 차단 한 경우
            Optional<Block> isBlock = blockRepository.findByBlockFromAndBlockTo(user1, user2);

            // 이미 친구인 경우
            Optional<Mate> isMated1 = mateRepository.findByMateUserId1AndMateUserId2(user1, user2);
            Optional<Mate> isMated2 = mateRepository.findByMateUserId1AndMateUserId2(user2, user1);

            // 이미 친구 신청이 와있는 경우
            Optional<AddMate> isAdded = addMateRepository.findByAddMateFromAndAddMateTo(user2, user1);

            // 이미 친구 신청을 한 경우
            Optional<AddMate> isAdd = addMateRepository.findByAddMateFromAndAddMateTo(user1, user2);


            if (isBlocked.isPresent()) {
                // 차단 당한 경우
                response = 402;
            } else if (isBlock.isPresent()) {
                // 차단한 경우
                response = 403;
            } else if (isMated1.isPresent() || isMated2.isPresent()) {
                // 이미 친구인 경우
                response = 406;
            } else if (isAdd.isPresent()) {
                // 이미 친구 신청을 한 경우
                response = 407;
            } else if (isAdded.isPresent()) {
                // 이미 친구 신청이 와있는 경우
                response = 409;
            } else if (addMateReqDto.getAddMateFrom().equals(addMateReqDto.getAddMateTo())) {
                // 나 자신에 대한 친구 요청인 경우
                response = 410;
            } else {
                Map<String, Map> data = new HashMap<>();
                Map<String, String> message = new HashMap<>();
                message.put("chatUserId", "mateRequest");
                message.put("detail", user1.getNickname() +  "님으로 부터 메이트 신청이 들어왔습니다.");
                message.put("addMateId", addMateRepository.save(addMateReqDto.addMate(user1, user2)).getAddMateId().toString());
                data.put("data", message);
                messaging.convertAndSend("/sub/chat/user/" + user2.getUserId(), data);
            }

```

<br>

또한 마지막 else문에서 친구 신청 시 STOMP를 통해 메세지가 송신되고 있음을 확인할 수 있습니다.

<br>

마찬가지로, 친구 요청이 수락 되었을 때 채팅방이 생성되도록 하였습니다.

<br>

```java
// AddMateService.java

			if (isMated1.isPresent() || isMated2.isPresent()) {
                // 이미 친구인 경우
                response = 406;
            } else if (isBlocked.isPresent()) {
                // 차단당한 경우
                response = 402;
            } else if (isBlock.isPresent()) {
                // 차단한 경우
                response = 403;
            } else {
                // 메이트 추가 후 채팅방 생성
                mateRepository.save(Mate.builder().mateUserId1(user1).mateUserId2(user2).build());
                roomService.createRoom(user1.getUserId(), user2.getUserId());
            }
            addMateRepository.delete(addMate.get());
        }
```

<br>

또한 신고 횟수 누적이 되면, 일정 기간 동안 계정을 정지시켜 로그인하지 못하도록 제작하였습니다. 또한 현재 어플리케이션에 로그인한 상태라면 실시간으로 로그아웃되어야 하기 때문에 소켓통신을 이용하여 로그아웃되도록 처리하였습니다.

<br>

```java
// ReportService.java

			// 유저 정보 업데이트
            int reportCount = user.getReportCount() + 1;
            Calendar date = Calendar.getInstance();

            // 신고 누적이 5회면 일주일 정지, 10회면 20년(영구) 정지
            if (reportCount == 5) {
                date.add(Calendar.DATE, 7);
            } else if (reportCount == 10) {
                date.add(Calendar.YEAR, 10);
            }

            // 유저 정보 업데이트

            Map<String, String> message = new HashMap<>();
            message.put("chatUserId", "reported");
            message.put("detail", "신고 회수 누적으로 임시 사용 정지 되었습니다.");
            Map<String, Map> data = new HashMap<>();
            data.put("data", message);
            messaging.convertAndSend("/sub/chat/user/" + user.getUserId(), data);


            response.put("result", 200);
```

<br>

### 2) 채팅 서비스 - Back End

메이트가 된 사람끼리는 채팅을 통해 서로 대화할 수가 있습니다. 메이트 관계가 되면 우선 채팅방이 생성되며, 이를 DB에 저장합니다. 이러한 소켓 통신을 연결하기 위해, SocketConfig를 통해 End Point를 설정하고, Broker로 소켓 통신 url을 규정하였습니다.

<br>

```java
// WebSocketConfig.java

	private final StompHandler stompHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/nawa").setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
```

<br>

모든 채팅 기록은 DB에 채팅방 ID와 함께 기록되고, 메이트 관계가 해제되면, 해제를 한 본인은 채팅방 자체가 보이지 않게 만들고, 상대는 채팅방은 남되 나에 대한 정보를 볼 수 없도록 만들었습니다. 즉 DB의 채팅방 정보에서 나에 대한 정보를 null로 수정하고 저장하였습니다.

<br>

```java
// RoomService.java

    @Transactional
    public void deleteRoom(String roomUser1, String roomUser2) {
        Room room1 = roomRepository.findByRoomUserId1AndRoomUserId2(roomUser1, roomUser2);
        Room room2 = roomRepository.findByRoomUserId1AndRoomUserId2(roomUser2, roomUser1);
        
        // 있는 쪽에서 roomUser1을 null로 초기화
        if (room1 != null) {
            room1.updateUser1();
        } else if (room2 != null) {
            room2.updateUser2();
        }
    }
```

<br>

또한 메시지의 읽음 처리를 구현하기 위해, 소켓 통신 연결 및 연결 해제 시 발송되는 Command Message와 생성되는 Session ID를 Handler를 통해 받고, 이를 토대로 해당 방에 몇 명이 있는지를 DB에 기록했습니다.

<br>

```java
// StompHandler.java

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);


        if (StompCommand.SUBSCRIBE == accessor.getCommand() && accessor.getDestination().startsWith("/sub/chat/room/")) {
            String[] splits = accessor.getDestination().split("/");
            long roomId = Long.parseLong(splits[splits.length-1]);
            String simpSessionId = (String) message.getHeaders().get("simpSessionId");
            
            // Session Id를 DB에 저장
            roomSessionService.saveSession(simpSessionId, roomId);
            
            // 해당 방의 미접속자 수를 1 감소
            roomService.roomCount(roomId, -1);
            
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String simpSessionId = (String) message.getHeaders().get("simpSessionId");
            
            // 접속이 종료되면, Session Id를 통해 DB에서 해당 세션을 제거
            Long roomId = roomSessionService.deleteSession(simpSessionId);
            
            // 제거되었으면, 해당 방의 미접속자 수를 1 증가
            if (roomId != 0l) {
                roomService.roomCount(roomId, 1);
            }
        }

        return message;

    }
```

<br>

<br>

### 3) 채팅 서비스 - Front End

프론트에서는, 기본적으로 친구 추가, 차단 등의 알림을 실시간으로 받아야하므로 항상 개인 소켓 통신이 연결되도록 했습니다.

<br>

```js
// main.js


// socket 연결할 url 주소
let url = 'http://localhost:8080'

// 소켓 JS 생성 후 이를 Stomp에 탑재
var sock = new SockJS(url + "/nawa");
var ws = Stomp.over(sock);
ws.debug = null;
var reconnect = 0;

// 채팅방에 연결하는 로직
function connect() {
    // 특정 채팅방을 구독함.
    ws.connect({}, function(frame) {
        ws.subscribe("/sub/chat/user/"+vm.$data.userId, function(message) {
            // 수신된 메시지 처리
            var recv = JSON.parse(message.body);
            vm.recvMessage(recv.data)
        });
    }, function(error) {
        // 연결이 종료되었을 때의 처리. 재연결
        if(reconnect++ <= 5) {
            setTimeout(function() {
                sock = new SockJS(url + "/nawa");
                ws = Stomp.over(sock);
                connect();
            },10 * 1000);
        }
    });
}
connect();
```

<br>

알림이나 메세지를 받는 로직은 다음과 같습니다.

<br>

```js
// main.js

var vm = new Vue({
    el: '#app',
    data: {
        userId: "",
    },
    mounted() {
        this.userId = localStorage.getItem("userId")
    },

    methods: {
        // 연결된 서버에 메세지가 왔을 때. 알림만 잘 띄우면 됨
        recvMessage: function(recv) {
            // 메이트 신청이 온 경우
            if (recv.chatUserId == 'mateRequest') {
                // mateId가 String 으로 같이 옴. 모달을 활용해서 바로 수락하거나, 푸시 알림으로 써도 괜찮을듯
                alert(recv.detail)
            // 신고 누적으로 정지 당했을 경우
            } else if (recv.chatUserId == "reported") {
                // 즉시 알림이 옴. 확인 후 바로 로그아웃 되도록 구현하면 좋을 것 같다.
                alert(recv.detail)

            } else {
                // 내가 보낸 메시지가 아닌 경우 알람 띄우기
                if (recv.chatUserId != this.userId) {
                    alert(recv.profileImg + recv.chatNickName + ': ' + recv.chatContent)
                }
            }
        },
    }
});
```

<br>

또한 채팅방 목록 페이지에 들어갈 경우, 자신의 채팅방과 채팅 기록을 전부 불러온 후, 이를 계산하여 안 읽은 채팅의 개수까지 표현할 수 있도록 하였습니다.

<br>

```js
// room.html


    var vm = new Vue({
        el: '#app',
        data: {
            allRoom: [],
            userId: "",
            chats: [],
        },
        mounted() {
            this.userId = localStorage.getItem("userId")
            this.findRoom();
            this.findChats();
        },
        computed: {
            // 방 전체 정보
            rooms: function() {
                return this.allRoom.map(room => {
                    // 1. 같은 방의 채팅, 2. 내가 보낸 채팅 이외, 3. isRead의 합
                    return {...room, "count": this.chats.filter((chat) => {return chat.roomId == room.roomId && chat.chatUserId != this.userId}).reduce((init, chat) => {return init + chat.isRead}, 0)
                        , "lastChat": this.chats.filter((chat) => {return chat.roomId == room.roomId}).at(-1)}
                })
            },
        },
        methods: {
            // 방 들어가는 로직. 편하신대로 구현하시면 됩니다.
            // enterRoom: function(roomId) {
            //     localStorage.setItem("roomId", roomId)
            //     location.href = '/chat/room/' + String(roomId) // 이건 무시하셔도 됩니다.
            // },

            // 모든 채팅방 정보 불러오기
            findRoom: function () {
                axios.get(url +'/chat/user/'+this.userId).then(response => {this.allRoom = response.data.allRooms})
            },

            // 모든 채팅 정보 불러오기
            findChats: function () {
                axios.get(url + '/chat/message/user/'+this.userId).then(response => { this.chats = response.data.allChats})
            },
```

<br>

마찬가지로 알림의 종류에 따라 서로 다른 알림을 띄울 수 있도록 구현하였습니다.

<br>

```js
            // 연결된 서버에 메세지가 왔을 때
            recvMessage: function(recv) {
                // 메이트 신청이 온 경우
                if (recv.chatUserId == 'mateRequest') {
                    // mateId가 String으로 같이 옴. 모달을 활용해서 바로 수락하거나, 푸시 알림으로 써도 괜찮을듯
                    alert(recv.detail)

                    // 신고 누적으로 정지 당했을 경우
                } else if (recv.chatUserId == "reported") {
                    // 즉시 알림이 옴. 확인 후 바로 로그아웃 되도록 구현하면 좋을 것 같다.
                    alert(recv.detail)

                } else {
                    // 채팅 기록들. 매번 받는건 좀 그렇고, 사실 localStorage에 저장하는 것이 가장 좋아보임.
                    // 맨 마지막 채팅 인덱스를 기반으로 그 이후 채팅 기록을 찾는 등

                    this.chats.push({
                        "userId":recv.chatUserId,
                        "chatId":recv.chatId,
                        "roomId":recv.roomId,
                        "chatContent":recv.chatContent,
                        "chatDate":recv.chatDate,
                        "isRead":recv.isRead
                    })

                    // 새로 방이 생성되어서 알림이 안 온 경우, 방을 추가해줌. 목록에 띄워야해서.
                    if (this.rooms.find(room => room.roomId == recv.roomId) == null) {
                        this.allRoom.push({
                            "roomId": recv.roomId,
                            "roomNickName": recv.chatNickName,
                            "roomUserId": recv.chatUserId,
                        })
                    }

                    // 내가 보낸 메시지가 아닌 경우 알람 띄우기
                    if (recv.chatUserId != this.userId) {
                        alert(recv.profileImg + recv.chatNickName + ': ' + recv.chatContent)
                    }
                }

            },
        }
    });

```

<br>

채팅방에 입장한 이후로는 채팅방의 소켓 서버 또한 연결하여 총 2개의 소켓 통신을 할 수 있도록 작성하였습니다.

<br>

```js
// roomdetail.html

        methods: {

            // 메시지 보내는 양식.
            sendMessage: function() {
                ws2.send("/pub/chat/message", {}, JSON.stringify({"roomId":this.roomId, "chatUserId":this.userId, "chatContent":this.message}));
                this.message = '';
            },

                
            connectRoom: function() {
                ws2.connect({}, function(frame) {
                    ws2.subscribe("/sub/chat/room/"+vm.$data.roomId, function(message) {
                        var recv = JSON.parse(message.body);
                        console.log(recv.data)
                        vm.recvRoomMessage(recv.data);
                    }, {"roomId":vm.$data.roomId});
                    ws2.send("/pub/chat/message", {}, JSON.stringify({"roomId":vm.$data.roomId, "chatUserId":'In', 'chatContent':vm.$data.userId}));
                });
            },
        }
```

<br>

<br>

## 2. UCC

이번 프로젝트에서 UCC 제작을 담당하게 되었다. 그래서 프로젝트 마지막 5일 동안의 일정은 다음과 같았습니다.

8/21 (일) : UCC 스크립트 작성

8/22 (월) : 도입부 작성 및 관련 오픈소스 영상 수집, 영상 편집 공부

8/23 (화) : 나레이션 녹음본, 그 외 효과음, BGM 및 오픈소스 아이콘 수집, 영상 편집 공부

8/24 (수) : 스크립트 수정 및 그에 따른 오픈소스 추가 수집, 영상 편집

8/25 (목) : 영상 편집 및 최종 수정

<br>

또한 이번 UCC의 기획 배경은 다음과 같습니다.

1. 목소리 좋은 팀원이 있었기에, TTS를 사용하지 않고 실제 목소리를 활용한다.
2. 촬영에 투자할 시간이 없기에, 오픈소스 영상들만을 활용한다.
3. 따라서 생동감이 떨어지기에, 차분한 느낌의 광고 UCC 컨셉으로 연출한다.
4. 또한 영상 편집은 처음이나 다름없기에, 아는 정보가 없어 최대한 부딪혀보면서 생각해본다.

결국 최종적인 컨셉트는 '유튜브에 있을법한 차분한, 다큐 느낌의 광고 UCC'가 되었다. 영상은 아래와 같습니다.

<br>

![썸네일](https://user-images.githubusercontent.com/95673624/188423788-ca05750d-36e0-42c1-a27d-63469dd904ce.png)

[나와 UCC](https://youtu.be/xIYFsRf1OSA)

<br>

UCC 수상은 프로젝트 수상과는 별개였기 때문에 UCC 수상을 노리고 자는 시간까지 줄여가며 열심히 만들었던 작품입니다. 하지만 이는 아래와 같은 이유로 입상하지 못하게 되었습니다.

1. 실제 목소리를 활용한 것은 좋았지만, 오픈소스 영상만을 활용했기 때문에 생동감이나 전달력이 크게 줄어들었다.
2. 게다가 초반 미사여구가 많아 전하고자 하는 목적이 더 많이 희석되었다. 기획 의도와 최소한 같은 길이 정도로 어플리케이션에 대한 설명이 있거나 기획 의도에 대한 설명 자체를 더 줄였어야 했다.
3. 영상 편집에 대해 미숙했기 때문에, 영상으로 표현하고자 하는 바를 다 표현하지 못했으며, 효과를 적용할 때도 디테일한 부분은 전혀 신경쓰지 못해 완성도가 크게 떨어졌다.
4. 무엇보다도 임팩트가 있는 UCC가 가장 메세지 전달력이 좋으며, 설득력이 있고, 기억에 남기 쉽다. 하지만 나와 UCC는 임팩트가 부족하였다.

<br>

모처럼 같은 팀원이 좋은 목소리로 나레이션을 맡아주었는데 이 기회를 망치게 된 것 같아 매우 아쉬웠습니다.

<br>

<br>

## 3. 마무리하며

첫 프로젝트라서 많이 헤매기도 하고, 결과물 또한 완벽하지 않았습니다. 하지만 시행착오를 통해 개발에서 제일 중요한 '정보를 찾는 방법' 및 '찾은 정보를 프로젝트에 적용하는 방법'에 대한 능력을 기르는 데에 많은 도움이 되었던 것 같습니다. 이를 통해 더 성장하고, 좋은 프로젝트를 만들 수 있도록 노력해야겠다는 생각이 들었습니다.

<br>

<br>

## 4. 참고자료

\<네이버 메세지 자동 송신 API\> - 해보고 싶었으나 다른 팀원이 직접 하게 됨

[Simple &amp; Easy Notification Service &#xC18C;&#xAC1C; - Simple &amp; Easy Notification Service](https://guide.ncloud-docs.com/docs/ko/sens-sens-1-1)

[https://jae04099.tistory.com/entry/Nodejs-네이버-문자-전송-API-사용하기feat-axios](https://jae04099.tistory.com/entry/Nodejs-%EB%84%A4%EC%9D%B4%EB%B2%84-%EB%AC%B8%EC%9E%90-%EC%A0%84%EC%86%A1-API-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0feat-axios)

[네이버 SENS API와 Node.js로 휴대전화 SMS 인증하기](https://g-song-ii.tistory.com/3)

[NAVER CLOUD PlATFORM[SENS API] - 네이버 문자 인증](https://giron.tistory.com/75)

<br>

\<Spring Boot 관련>

[STOMP로 채팅 만들기](https://sumin2.tistory.com/60)

[Spring Boot 에서 JPA 사용하기 (MySQL 사용)](https://memostack.tistory.com/155)

[웹소켓(websocket)으로 채팅서버 만들기](https://www.daddyprogrammer.org/post/4077/spring-websocket-chatting/)

[sockJS와 stompJS](https://fgh0296.tistory.com/24)

[STOMP.js Documentation](https://stomp-js.github.io/stomp-websocket/codo/class/Client.html)

[SpringBoot - 스프링부트에서 채팅프로그램(소켓통신) 만들기](https://myhappyman.tistory.com/100)

[[Spring] webSocket으로 채팅서버 구현하기](https://learnote-dev.com/java/Spring-%EA%B2%8C%EC%8B%9C%ED%8C%90-API-%EB%A7%8C%EB%93%A4%EA%B8%B0-webSocket%EC%9C%BC%EB%A1%9C-%EC%B1%84%ED%8C%85%EC%84%9C%EB%B2%84-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0/)
