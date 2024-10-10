let stompClient = null;

// URL에서 chatRoomId를 추출하는 함수
function getChatRoomIdFromURL() {
    const pathParts = window.location.pathname.split('/');
    return pathParts[pathParts.length - 1];  // URL 마지막 부분에서 chatRoomId를 가져옴
}

// chatRoomId 변수 정의 및 초기화
const chatRoomId = getChatRoomIdFromURL();
function connect() {
    let socket = new SockJS('/ws');  // WebSocket 엔드포인트 설정
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);  // 연결 성공 메시지 확인
        stompClient.subscribe('/topic/chat/' + chatRoomId, function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}


function sendMessage() {
    const messageInput = document.getElementById('message');
    const messageContent = messageInput.value.trim();

    if (messageContent) {
        const chatMessage = {
            content: messageContent,
            sender: sessionStorage.getItem('userNm'),  // 메시지 발신자
            timestamp: new Date().toLocaleTimeString()
        };
        stompClient.send("/app/chat/" + chatRoomId, {}, JSON.stringify(chatMessage));
        messageInput.value = '';  // 메시지를 보낸 후 입력 필드를 비움
    }
}

function showMessage(message) {
    const chatWindow = document.getElementById('chat-box');
    const messageElement = document.createElement('div');
    const sessionUserNm = sessionStorage.getItem('userNm');

    // 발신자와 세션에 저장된 사용자가 동일한지 확인
    if (message.sender === sessionUserNm) {
        // 메시지가 현재 사용자가 보낸 것이라면 보낸 메시지 형식으로 표시
        messageElement.classList.add('outgoing_msg');
        messageElement.innerHTML = `
            <div class="sent_msg">
                <p>${message.content}</p>
                <span class="time_date">${message.timestamp} | You</span>
            </div>
        `;
    } else {
        // 메시지가 다른 사용자가 보낸 것이라면 받은 메시지 형식으로 표시
        messageElement.classList.add('incoming_msg');
        messageElement.innerHTML = `
            <div class="received_msg">
                <div class="received_width_msg">
                    <p>${message.content}</p>
                    <span class="time_date">${message.timestamp} | ${message.sender}</span>
                </div>
            </div>
        `;
    }
    chatWindow.appendChild(messageElement);
    scrollToBottom()
}

function scrollToBottom() {
    const chatWindow = document.getElementById('chat-box');
    console.log("scrollTop:", chatWindow.scrollTop, "scrollHeight:", chatWindow.scrollHeight);
    setTimeout(() => {
        chatWindow.scrollTop = chatWindow.scrollHeight;
    }, 0);  // DOM이 업데이트된 이후에 스크롤이 실행되도록 함
}

document.getElementById('send-button').addEventListener('click', sendMessage);
document.getElementById('message').addEventListener('keypress', function (event) {
    if (event.key === 'Enter') {
        event.preventDefault();  // Prevent default form submission if any
        sendMessage();           // sendMessage 함수 호출
    }
});


window.addEventListener('load', connect);
window.onload = function() {
    scrollToBottom();
};