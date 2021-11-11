var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('#connecting');

var stompClient = null;

function connect() {

    var socket = new SockJS('/webs');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}


connect();

function onConnected(options) {

    stompClient.subscribe('/topic/chatRoom', onMessageReceived);
    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Не получилось подкючиться к WebSocket серверу.';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            timestamp: new Date(),
            sender: username,
            content: messageInput.value

        };

        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);



    var messageElement = document.createElement('li');

    messageElement.classList.add('chat-message');


    var usernameElement = document.createElement('strong');
    usernameElement.classList.add('nickname');
    var usernameText = document.createTextNode(message.sender);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);
    var textElement = document.createElement('span');

    var messageText = document.createTextNode(message.content);

    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

messageForm.addEventListener('submit', sendMessage, true);