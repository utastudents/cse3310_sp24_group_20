const activeUsersList = document.getElementById("activeUsersList");
const chatMessages = document.getElementById('chatbox');

const socket = new WebSocket("ws://localhost:9880"); 

socket.onopen = function(event) {
    console.log("WebSocket connection established.");
};

socket.onmessage = function(event) {
    console.log("Message received from server:", event.data); // Log received message
    const data = JSON.parse(event.data);

    
    if (data.type === "puzzle") {
        displayPuzzle(data.data);
    } 
    else if (data.type === "wordList") {
        displayWordList(data.data);
    } 
    else if(data.type === "leaderboard") {
        updateLeaderboardUI(data.data);
    }     
    else if (data.type === 'activeUsersUpdate') {
        console.log("Message received from server:",data.user);
         displayActiveUser(data.user)
    }
    else if (data.type === 'chatMessage') {
        displayChatMessage(data.username, data.message);    
        }
    };


    function updateLeaderboardUI(leaderboard) {
    console.log("Leaderboard data:", leaderboard); // Log the leaderboard data
    leaderboardData = leaderboard;

    const leaderboardElement = document.getElementById('leaderboardContainer');
    leaderboardElement.innerHTML = ''; 

    const players = leaderboard.players; // Access the players array from the leaderboard object

    players.forEach((player, index) => { // Change `leaderboard.forEach` to `players.forEach`
        const playerElement = document.createElement('div');
        playerElement.textContent = `${index + 1}. ${player.username} - Score: ${player.score}`;
        leaderboardElement.appendChild(playerElement);
    });
}

function submitUsername() {
    
    const username = document.getElementById("username").value;
    if (username !== "") { 
        window.username = username;   
    socket.send(JSON.stringify({ type: "username", data: username }));
    
    document.getElementById("username").value = ""; 
    document.querySelector('.loginform').style.display = 'none';
    document.querySelector(".gameSection").style.display ="block";
    }
    else {
       
        alert("Please enter a username."); 
    }
    //console.log(username);
}


function updateActiveUsers(activeUsers) {
    console.log("Updating active users:", activeUsers);
    activeUsersList.innerHTML = '';
    activeUsers.forEach(user => {
        const row = document.createElement('tr');
        row.innerHTML = `
        <td>${user.username}</td>
        <td><span class="badge bg-success">Online</span></td>
    `;
    activeUsersList.appendChild(row);
    });
}



function sendMessage() {
    const messageInput = document.getElementById('message');
    const message = messageInput.value.trim();
    if (message !== "") {
        const chatMessage = {
            type: 'chatMessage',
            username: window.username,
            message: message
        };
        socket.send(JSON.stringify(chatMessage));
        messageInput.value = ''; 
    }
}

function displayChatMessage(username, message) {
    const chatbox = document.getElementById('chatbox');
    const messageElement = document.createElement('div');
    messageElement.textContent = `${username}: ${message}`;
    chatMessages.appendChild(messageElement);
    chatMessages.scrollTop = chatMessages.scrollHeight; 
}

function displayActiveUser(username){
    const activeUsersList = document.getElementById("activeUsersList");
    messageElement.textContent = `${username}`;
    Activeuser.appendChild(messageElement);
}



function StartGame(){
    document.querySelector(".mainGame").style.display ="block";
     broadcastStartGame();

}

function broadcastStartGame() {
    const startGameMessage = {
        type: 'startGame'
    };
    socket.send(JSON.stringify(startGameMessage));
}


