const square = document.querySelectorAll('.square')
const mole = document.querySelectorAll('.mole')
const timeLeft = document.querySelector('#time-left')
let score = document.querySelector('#score')

let result = 0
let currentTime = timeLeft.textContent
let timerInterval = null;
let moleInterval = null;
function randomSquare() {
    square.forEach( className => {
        className.classList.remove('mole')
    })
    let randomPosition = square[Math.floor(Math.random() * 9)]
    randomPosition.classList.add('mole')

    hitPosition = randomPosition.id
}

square.forEach(id => {
    id.addEventListener('mouseup', () => {
        if(id.id === hitPosition){
            result = result + 1
            score.textContent = result
            randomSquare()
        }
    })
})

function start(){
    clearInterval(timerInterval)
    clearInterval(moleInterval)
    currentTime = 60
    timeLeft.textContent = currentTime
    result = 0
    score.textContent = result
    moveMole()
}

function moveMole() {
    moleInterval = setInterval(countDown, 1000)
    timerInterval = setInterval(randomSquare, 1000)
}

function countDown(){
    currentTime--
    timeLeft.textContent = currentTime
    if(currentTime === 0) {
        clearInterval(timerInterval)
        clearInterval(moleInterval)
        alert("Game over! your final score is " + result)
    }
}

