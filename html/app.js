const activeUsersList = document.getElementById("activeUsersList");

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
        displayLeaderboard(data.data)
    }     
    else if (data.type === 'activeUsers') {
        updateActiveUsers(data.users);
    } 
};

function submitUsername() {
    
    const username = document.getElementById("username").value;
    if (username !== "") { // Check if username is not empty
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

}

function StartGame(){
    document.querySelector(".mainGame").style.display ="block";

}


