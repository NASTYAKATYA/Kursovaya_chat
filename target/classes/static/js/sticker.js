var stickerButton = document.querySelector('#stickerButton');
var stickerPack = document.querySelector('#stickerPackk');

function clickStickerButton(event) {
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
    event.preventDefault();
}
