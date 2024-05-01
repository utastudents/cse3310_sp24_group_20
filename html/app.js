const activeUsersList = document.getElementById("activeUsersList");
const chatMessages = document.getElementById('chatbox');

var serverUrl;

serverUrl = "ws://" + window.location.hostname +":"+ (parseInt(location.port) + 100);

socket = new WebSocket(serverUrl); 

socket.onopen = function(event) {
    console.log("WebSocket connection established.");
};

socket.onmessage = function(event) {
    console.log("Message received from server:", event.data); // Log received message
    const data = JSON.parse(event.data); 


     
    if (data.type === 'version') {
        document.getElementById("title").innerHTML = "TWSG GP20 GitHash: " + data.username; //Display commit hash to title. 
        console.log(data);
    }
    else if (data.type === "puzzle") {
        displayPuzzle(data.data);
    } 
    else if (data.type === "wordList") {
        displayWordList(data.data);
    } 
    else if(data.type === "leaderboard") {
        displayLeaderboard(data.data); // Update the leaderboard UI
    }     
    else if (data.type === 'activeUsersUpdate') {
         displayActiveUser(data.activeUsers)
    }
    else if (data.type === 'chatMessage') {
        displayChatMessage(data.username, data.message);    
        }
    else if (data.type === 'startGame') {
        console.log("Received startGame message from server");
        document.getElementById("joinGameButton").style.display = "block";
        displayJoinGameButton(); // Display the "Join Game" button
        }
    else if (data.type === 'joinGame') {
        console.log("Received joinGame message from server");
            // Display the puzzle when the user joins the game
        displayPuzzle(data.data);
        }
    else if (data.type === "startTimer") {
            startTimer(data.data);
        } 
    else if (data.type === "updateTimer") {
            updateTimer(data.data);
        }
    else if (data.type === "gameEnd") {
        displayGameEndMessage(data.username);
    }

    else if (data.type === "gameType") { // Handle the usernames message type
        console.log("Received username:", data.username);
        // Update the UI to display the list of usernames
        displayUsernames(data.userlist);
        
    }
    };


    function displayGameEndMessage(message) {

        console.log(message);
       
            // Extract the winner's username from the message
            const winnerUsername = message.split(":")[1].trim();
        
            // Display the game end message along with the winner's username on the frontend
            const gameEndMessageElement = document.getElementById("gameEndMessage");
            gameEndMessageElement.textContent = `Game ended! Winner:  ${winnerUsername}`;

        
    }

    let timerInterval;

    function startTimer(remainingTime) {
        clearInterval(timerInterval); // Clear any existing timer interval
        updateTimer(remainingTime); // Update the timer immediately
    
        // Start a new interval to update the timer every second
        timerInterval = setInterval(() => {
            updateTimer(--remainingTime);
            if (remainingTime <= 0) {
                clearInterval(timerInterval); // Stop the timer when it reaches 0
            }
        }, 1000);
    }
    
    function updateTimer(remainingTime) {
        // Display the remaining time in a timer element on the page
        const timerElement = document.getElementById("timer");
        timerElement.textContent = formatTime(remainingTime); // Format time as HH:MM:SS
    }
    
    function formatTime(seconds) {
        const hours = Math.floor(seconds / 3600);
        const minutes = Math.floor((seconds % 3600) / 60);
        const secs = seconds % 60;
        return `${pad(hours)}:${pad(minutes)}:${pad(secs)}`;
    }
    
    function pad(num) {
        return num.toString().padStart(2, "0");
    }


    function displayJoinGameButton() {
        const joinGameButton = document.createElement('button');
        joinGameButton.textContent = 'Join Game';
        joinGameButton.id = 'joinGameButton'; // Assign an ID for easy access
        joinGameButton.addEventListener('click', joinGame);
        document.querySelector('.gameSection div:last-child').appendChild(joinGameButton); // Append to the last div inside .gameSection
    }

    function joinGame() {
        // Send a message to the server indicating the user's intention to join the game
        const joinGameMessage = {
            type: 'joinGame'
        };
        socket.send(JSON.stringify(joinGameMessage));
    }

function submitUsername() {
    
    const username = document.getElementById("username").value;
    console.log("Username submitted:", username);
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

function displayActiveUser(activeUsers) {
    const activeUsersList = document.getElementById("activeUsersList");
    activeUsersList.innerHTML = ''; // Clear the previous list
    
    activeUsers.forEach(username => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${username}</td>
            <td><span class="badge bg-success">Online</span></td>
        `;
        activeUsersList.appendChild(row);
    });
}


function StartGame(){
    document.querySelector(".mainGame").style.display ="block";
     // Broadcast the "Start Game" event to all connected clients
     const startGameMessage = {
        type: 'startGame'
    };
    socket.send(JSON.stringify(startGameMessage));
    console.log("Sent startGame message to server");
}

function broadcastStartGame() {
    const startGameMessage = {
        type: 'startGame'
    };
    socket.send(JSON.stringify(startGameMessage));
}
