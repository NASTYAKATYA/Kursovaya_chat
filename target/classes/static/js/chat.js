var statusElement = document.querySelector('#status');
var messageInput = document.querySelector('#messageInput');
var sendMessageForm = document.querySelector('#sendMessageForm');
var chatUl = document.querySelector('#chat');
var files = document.querySelector('#files');
const inputElement = document.getElementById("fileElem");
inputElement.addEventListener("change", handleFiles, false);
var stompClient = null;
var username = null;

var stickerBtn = document.querySelector('#stickerBtn');
var stickerPack = document.querySelector('#stickerPack');
var btnClicked = false;

const els = document.getElementsByClassName('stickerId');

for(let i = 0 ; i < els.length; i++){
    function sendSticker(event) {
        var stickerId = els[i].value.trim();
        if(stickerId && stompClient) {
            var chatMessage = {
                username: username,
                sticker: stickerId,
                type: 'CHAT',
                chatId: getParameterByName('chatId')
            };
            stompClient.send("/app/chat.sticker", {}, JSON.stringify(chatMessage));
        }
        event.preventDefault();
    }
    els[i].addEventListener("click", sendSticker, false);
}

function getParameterByName(name, url = window.location.href) {
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

function connect() {
    username = document.querySelector('#username').innerText.trim();
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}
connect();

function onConnected() {
    stompClient.subscribe('/topic/chat/' + getParameterByName('chatId'), onMessageReceived);
    var headers = {};
    headers['chatId'] = getParameterByName('chatId');
    stompClient.send("/app/chat.addUser",
        headers,
        JSON.stringify({username: username, type: 'JOIN', chatId: getParameterByName('chatId')})
    )
    statusElement.classList.add('dnone');
}

function onError(error) {
    statusElement.textContent = 'Не удалось подключиться к Вебсокет серверу.';
    statusElement.style.color = 'red';
}

async function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            username: username,
            content: messageInput.value,
            type: 'CHAT',
            chatId: getParameterByName('chatId')
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    if (inputElement.files.length > 0 && stompClient){
        for (let i = 0; i < inputElement.files.length; i++) {
            (function(file) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    var chatMessage = {
                        username: username,
                        content: reader.result,
                        filename: file.name,
                        type: 'FILE',
                        chatId: getParameterByName('chatId')
                    };
                    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
                }
                reader.readAsDataURL(file);
            })(inputElement.files[i]);
        }
        files.innerHTML = '';
        for (let i = 0; i < inputElement.files.length; i++) {
            removeFileFromFileList(i);
        }
    }
    event.preventDefault();
}

function handleFiles() {
    let fileList = this.files;
    for (let i = 0; i < fileList.length; i++) {
        var linkedFileDiv = document.createElement('div');
        var fileNameA = document.createElement('a');
        fileNameA.text = fileList[i].name;
        fileNameA.download = fileList[i].name;
        fileNameA.href = URL.createObjectURL(fileList[i]);
        var deleteFileButton = document.createElement('button');
        deleteFileButton.classList.add('delete-file-button');
        deleteFileButton.name = fileList[i].name;
        deleteFileButton.addEventListener("click", deleteLinkedFile);
        var xMarkSvg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
        xMarkSvg.setAttribute('height', '7px');
        xMarkSvg.setAttribute('width', '7px');
        xMarkSvg.setAttribute('viewBox', '0 0 329.26933 329');
        var path = document.createElementNS(
            'http://www.w3.org/2000/svg',
            'path'
        );
        path.setAttribute(
            'd',
            'm194.800781 164.769531 128.210938-128.214843c8.34375-8.339844 8.34375-21.824219 0-30.164063-8.339844-8.339844-21.824219-8.339844-30.164063 0l-128.214844 128.214844-128.210937-128.214844c-8.34375-8.339844-21.824219-8.339844-30.164063 0-8.34375 8.339844-8.34375 21.824219 0 30.164063l128.210938 128.214843-128.210938 128.214844c-8.34375 8.339844-8.34375 21.824219 0 30.164063 4.15625 4.160156 9.621094 6.25 15.082032 6.25 5.460937 0 10.921875-2.089844 15.082031-6.25l128.210937-128.214844 128.214844 128.214844c4.160156 4.160156 9.621094 6.25 15.082032 6.25 5.460937 0 10.921874-2.089844 15.082031-6.25 8.34375-8.339844 8.34375-21.824219 0-30.164063zm0 0'
        );
        path.setAttribute('fill', 'red');
        xMarkSvg.appendChild(path);
        deleteFileButton.appendChild(xMarkSvg);
        linkedFileDiv.appendChild(fileNameA);
        linkedFileDiv.appendChild(deleteFileButton);
        files.appendChild(linkedFileDiv);
    }
}
function deleteLinkedFile(event){
    var filename = event.currentTarget.name;
    for (let i = 0; i < inputElement.files.length; i++) {
        if (inputElement.files[i].name == filename){
            removeFileFromFileList(i);
        }
    }
    event.currentTarget.parentNode.remove();
}
function removeFileFromFileList(index) {
    const dt = new DataTransfer()
    const { files } = inputElement

    for (let i = 0; i < files.length; i++) {
        const file = files[i]
        if (index !== i)
            dt.items.add(file) // here you exclude the file. thus removing it.
    }

    inputElement.files = dt.files // Assign the updates list
}

function dataURItoBlob(dataURI) {
    // convert base64/URLEncoded data component to raw binary data held in a string
    var byteString;
    if (dataURI.split(',')[0].indexOf('base64') >= 0)
        byteString = atob(dataURI.split(',')[1]);
    else
        byteString = unescape(dataURI.split(',')[1]);

    // separate out the mime component
    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

    // write the bytes of the string to a typed array
    var ia = new Uint8Array(byteString.length);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }

    return new Blob([ia], {type:mimeString});
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
        message.content = 'Присоединился к чату';
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
        message.content = 'Отсоединился от чата';
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
        if (message.sticker !== null) {
            var newSticker = document.createElement('img');
            newSticker.classList.add("sticker");
            newSticker.src = '/stickerpack/' + message.sticker + '.png';
        }
    }
    var messageTime  = new Date(message.date);
    var timeOptions = { hour: '2-digit', minute: '2-digit', year: 'numeric', month: 'long', day: 'numeric' };
    infoElementTime.innerText = messageTime.toLocaleDateString("ru-RU", timeOptions);
    infoElementUsername.innerText = message.username;
    if (message.type === 'FILE') {
        var fileNameA = document.createElement('a');
        fileNameA.text = message.filename;
        fileNameA.download = message.filename;
        fileNameA.href = URL.createObjectURL(dataURItoBlob(message.content));
        contentElement.appendChild(fileNameA);
    }
    else {
        contentElement.innerText = message.content;
    }
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
    if (message.content !== null) {
        messageElement.appendChild(triangleElement);
        messageElement.appendChild(contentElement);
    }
    if (message.sticker !== null) {
        messageElement.appendChild(newSticker);
    }
    chatUl.appendChild(messageElement);
}
function showStickers(event) {
    btnClicked = !btnClicked;
    if (btnClicked) {
        stickerPack.classList.remove("dnone");
        stickerPack.classList.add("dflex");
        messageInput.style.cssText = 'display: none;';
        chatUl.classList.add("smallheight");
        chatUl.classList.remove("bigheight");
    } else {
        stickerPack.classList.remove("dflex");
        stickerPack.classList.add("dnone");
        messageInput.style.cssText = '';
        chatUl.classList.add("bigheight");
        chatUl.classList.remove("smallheight");
    }
}

sendMessageForm.addEventListener('submit', sendMessage, true);
stickerBtn.addEventListener('click', showStickers, true); 