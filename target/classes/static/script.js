var stompClient = null;

function connect() {
    var socket = new sockjs('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);
    var headers = {
        Authorization: 'Bearer ' + localStorage.getItem('jwtToken') // Asegúrate de que el token JWT esté almacenado en localStorage
    };
    stompClient.connect(headers, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/mensajes', function (mensaje) {
            showMessage(JSON.parse(mensaje.body));
        });
    });
}

function login() {
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ correo: username, contrasenia: password })
    })
    .then(response => response.json())
    .then(data => {
        localStorage.setItem('jwtToken', data.jwt); // Almacena el token JWT en localStorage
        console.log('Login successful:', data);
        connect(); // Conéctate al WebSocket después de iniciar sesión
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

function sendMessage() {
    var chatId = document.getElementById('chatId').value;
    var userId = document.getElementById('userId').value;
    var body = document.getElementById('body').value;

    var message = {
        chatId: chatId,
        userId: userId,
        body: body
    };

    stompClient.send("/app/enviar", {}, JSON.stringify(message));
}

function showMessage(message) {
    var messagesDiv = document.getElementById('messages');
    var p = document.createElement('p');
    p.appendChild(document.createTextNode('Chat ID: ' + message.chatId + ' User ID: ' + message.userId + ' Message: ' + message.body));
    messagesDiv.appendChild(p);
}

function createGroupChat() {
    var groupName = document.getElementById('groupName').value;
    var groupUsers = document.getElementById('groupUsers').value.split(',').map(id => id.trim());

    fetch('http://localhost:8080/chats/group', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
        },
        body: JSON.stringify({
            nameChat: groupName,
            userIds: groupUsers
        })
    })
    .then(response => response.json())
    .then(data => {
        console.log('Group Chat Created:', data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

function createSingleChat() {
    var userId1 = document.getElementById('singleUser1').value;
    var userId2 = document.getElementById('singleUser2').value;

    fetch('http://localhost:8080/chats/single', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
        },
        body: JSON.stringify({
            userId1: userId1,
            userId2: userId2
        })
    })
    .then(response => response.json())
    .then(data => {
        console.log('Single Chat Created:', data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

window.onload = function() {
    // No conectamos directamente, esperemos al login
};
