// 기본적으로, 채팅방 외부에서는 메시지 수신 기능만 있으면 된다.


// socket 연결할 url 주소
let url = 'http://localhost:8080'

// 소켓 JS 생성 후 이를 Stomp에 탑재
var sock = new SockJS(url + "/nawa");
var ws = Stomp.over(sock);
ws.debug = null;
var reconnect = 0;

// vue.js
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