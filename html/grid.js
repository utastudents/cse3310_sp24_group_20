function displayPuzzle(puzzleData) {
    const puzzleContainer = document.getElementById("wordSearchContainer");
    puzzleContainer.innerHTML = ""; 
    const puzzleGrid = puzzleData; 
    const rows = 50;
    const cols = 50;
    const table = document.createElement("table");
    for (let i = 0; i < rows; i++) {
        const row = document.createElement("tr");
        for (let j = 0; j < cols; j++) {
            const cell = document.createElement("td");
            cell.textContent = puzzleGrid[i][j];
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
    if (count === 10) {
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