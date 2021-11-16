var statusElement = document.querySelector('#status');
var messageInput = document.querySelector('#messageInput');
var sendMessageForm = document.querySelector('#sendMessageForm');
var chatUl = document.querySelector('#chat');
var stompClient = null;
var username = null;


function connect() {
    username = document.querySelector('#username').innerText.trim();
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}
connect();

function onConnected() {
    stompClient.subscribe('/topic/chat', onMessageReceived);
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({username: username, type: 'JOIN'})
    )
    statusElement.classList.add('dnone');
}

function onError(error) {
    statusElement.textContent = 'Не удалось подключиться к Вебсокет серверу.';
    statusElement.style.color = 'red';
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            username: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    var infoElement = document.createElement('div');
    infoElement.classList.add('info');
    var infoElementTime = document.createElement('h3');
    var infoElementUsername = document.createElement('h2');
    var infoElementStatus = document.createElement('span');
    infoElementStatus.classList.add('status');
    var triangleElement = document.createElement('div');
    triangleElement.classList.add('triangle');
    var contentElement = document.createElement('div');
    contentElement.classList.add('message');

    if(message.type === 'JOIN') {
        infoElementStatus.classList.add('green');
        triangleElement.classList.add('transparent-green');
        contentElement.classList.add('green');
        if (message.username == username) {
            messageElement.classList.add('me');
            infoElementStatus.classList.add('mr0-ml7');
        }
        else {
            messageElement.classList.add('other');
        }
        message.content = 'Присоединился к чату.';
    } else if (message.type === 'LEAVE') {
        infoElementStatus.classList.add('orange');
        triangleElement.classList.add('transparent-orange');
        contentElement.classList.add('orange');
        if (message.username == username) {
            messageElement.classList.add('me');
            infoElementStatus.classList.add('mr0-ml7');
        }
        else {
            messageElement.classList.add('other');
        }
        message.content = 'Отсоединился от чата.';
    } else {
        if (message.username == username) {
            messageElement.classList.add('me');
            infoElementStatus.classList.add('light-blue', 'mr0-ml7');
            triangleElement.classList.add('transparent-light-blue');
            contentElement.classList.add('light-blue');
        }
        else {
            messageElement.classList.add('other');
            infoElementStatus.classList.add('dark-blue');
            triangleElement.classList.add('transparent-dark-blue');
            contentElement.classList.add('dark-blue');
        }
    }
    infoElementTime.innerText = '10:12AM, Today';
    infoElementUsername.innerText = message.username;
    contentElement.innerText = message.content;
    if (message.username == username) {
        infoElement.appendChild(infoElementTime);
        infoElement.appendChild(infoElementUsername);
        infoElement.appendChild(infoElementStatus);
    }
    else {
        infoElement.appendChild(infoElementStatus);
        infoElement.appendChild(infoElementUsername);
        infoElement.appendChild(infoElementTime);
    }
    messageElement.appendChild(infoElement);
    messageElement.appendChild(triangleElement);
    messageElement.appendChild(contentElement);
    chatUl.appendChild(messageElement);
}


sendMessageForm.addEventListener('submit', sendMessage, true);