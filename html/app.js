const activeUsersList = document.getElementById("activeUsersList");
const chatMessages = document.getElementById('chatbox');

const socket = new WebSocket("ws://localhost:9880"); 

socket.onopen = function(event) {
    console.log("WebSocket connection established.");
};

socket.onmessage = function(event) {
    const data = JSON.parse(event.data);

    
    if (data.type === "puzzle") {
        displayPuzzle(data.data);
    } 
    else if (data.type === "wordList") {
        displayWordList(data.data);
    } 
    else if(data.type === "leaderboard") {
        const leaderboard = data.data;
        updateLeaderboardUI(leaderboard);
    }     
    else if (data.type === 'activeUsers') {
        updateActiveUsers(data.users);
    }
    else if (data.type === 'chatMessage') {
        displayChatMessage(data.username, data.message);    
        }
    };


    function updateLeaderboardUI(leaderboard) {
        // Assuming you have an HTML element with id "leaderboard" to display the leaderboard
        const leaderboardElement = document.getElementById('leaderboard');
        leaderboardElement.innerHTML = ''; // Clear previous data
    
        // Iterate over leaderboard data and create HTML elements
        leaderboard.forEach((player, index) => {
            const playerElement = document.createElement('div');
            playerElement.textContent = `${index + 1}. ${player.username} - Score: ${player.score}`;
            leaderboardElement.appendChild(playerElement);
        });
    }

function submitUsername() {
    
    const username = document.getElementById("username").value;
    if (username !== "") { // Check if username is not empty
        window.username = username;   
    socket.send(JSON.stringify({ type: "username", data: username }));
    
    document.getElementById("username").value = ""; 
    document.querySelector('.loginform').style.display = 'none';
    document.querySelector(".gameSection").style.display ="block";
    }
    else {
       
        alert("Please enter a username."); // Show an alert if username is empty
    }
    //console.log(username);
}

function fetchActiveUsers() {
    fetch('/active-users')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            updateActiveUsers(data.activeUsers);
        })
        .catch(error => {
            console.error('Error fetching active users:', error);
        });
}

// Function to update active users table
function updateActiveUsers(users) {
    activeUsersList.innerHTML = '';
    users.forEach(user => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${user}</td>
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
        messageInput.value = ''; // Clear input after sending
    }
}


function displayChatMessage(username, message) {
    const chatbox = document.getElementById('chatbox');
    const messageElement = document.createElement('div');
    messageElement.textContent = `${username}: ${message}`;
    chatMessages.appendChild(messageElement);
    chatMessages.scrollTop = chatMessages.scrollHeight; // Auto-scroll to the latest message
}






function StartGame(){
    document.querySelector(".mainGame").style.display ="block";
     // Call the method to broadcast the "Start Game" message
     broadcastStartGame();

}

function broadcastStartGame() {
    const startGameMessage = {
        type: 'startGame'
    };
    socket.send(JSON.stringify(startGameMessage));
}


