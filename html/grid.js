function displayPuzzle(puzzleData) {
    const puzzleContainer = document.getElementById("wordSearchContainer");
    puzzleContainer.innerHTML = ""; 
    const puzzleGrid = puzzleData; 
    const rows = 50;
    const cols = 50;
    const table = document.createElement("table"); 
    table.style.borderCollapse = "collapse"; 
    for (let i = 0; i < rows; i++) {
        const row = document.createElement("tr");
        for (let j = 0; j < cols; j++) {
            const cell = document.createElement("td");
            cell.textContent = puzzleGrid[i][j]; 
            cell.style.border = "1px solid black"; 
            cell.style.width = "20px"; 
            cell.style.height = "20px"; 
            cell.style.textAlign = "center"; 
            row.appendChild(cell);
        }
        table.appendChild(row);
    }
    puzzleContainer.appendChild(table);
} 

function displayWordList(wordListData) { 
    const wordListContainer = document.getElementById("wordListContainer");
    wordListContainer.innerHTML = ""; 
    const wordList = wordListData;
    const ul = document.createElement("ul");
    ul.style.listStyleType = "none"; 
    ul.style.padding = "0"; 
    let count = 0;
    let row = document.createElement("div");
    row.className = "word-row"; 
    wordList.forEach(word => {
    const li = document.createElement("li");
    li.textContent = word;
    li.style.display = "inline-block"; 
    li.style.padding = "5px"; 
    row.appendChild(li);
    count++;
    if (count === 30) {
        ul.appendChild(row);
        row = document.createElement("div");
        row.className = "word-row"; 
        count = 0;
    }
    });
    if (count > 0) {
        ul.appendChild(row);
    }
    wordListContainer.appendChild(ul);
} 

function displayLeaderboard(leaderboardData) {
    const leaderboardContainer = document.getElementById("leaderboardContainer");
    leaderboardContainer.innerHTML = '';
    if (leaderboardData && leaderboardData.players) {
        leaderboardData.players.forEach((player, index) => {
            const playerElement = document.createElement('p');
            playerElement.textContent = (index + 1) + ". " + player.playerUsername + " - Score: " + player.score;
            leaderboardContainer.appendChild(playerElement);
        });
    } else {
        leaderboardContainer.textContent = "No leaderboard data available.";

    }
    
}
