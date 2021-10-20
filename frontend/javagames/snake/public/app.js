var amqp = require('amqplib/callback_api');
document.addEventListener('DOMContentLoaded', () => {
    const squares = document.querySelectorAll(".grid div")
    const scoreDisplay = document.querySelector('span')
    const startBtn = document.querySelector('.start')

    const width = 10
    let currentIndex = 0;
    let appleIndex = 0;
    let currentSnake = [2,1,0]
    let direction = 1
    let score = 0
    let speed = 0.9
    let intervalTime = 0
    let interval = 0

    function startGame() {
        currentSnake.forEach(index => squares[index].classList.remove("snake"))
        squares[appleIndex].classList.remove("apple")
        clearInterval(interval)
        score = 0
        randomApple()
        direction = 1
        scoreDisplay.innerText = score
        intervalTime = 1000
        currentSnake = [2,1,0]
        currentIndex = 0
        currentSnake.forEach(index => squares[index].classList.add("snake"))
        interval = setInterval(moveOutcomes, intervalTime)
    }

    function moveOutcomes() {

        if(
            (currentSnake[0] + width >= (width * width) && direction === width ) || //if snake hits bottom
            (currentSnake[0] % width === width -1 && direction === 1) || //if snake hits right wall
            (currentSnake[0] % width === 0 && direction === -1) || //if snake hits left wall
            (currentSnake[0] - width < 0 && direction === -width) ||  //if snake hits top
            squares[currentSnake[0] + direction].classList.contains('snake') //if snake hits itself
        ) {
            return 
        }

        const tail = currentSnake.pop(); //removes last element of array
        squares[tail].classList.remove("snake");
        currentSnake.unshift(currentSnake[0] + direction) //gives direction to head of snake

        if(squares[currentSnake[0]].classList.contains("apple")) {
            squares[currentSnake[0]].classList.remove("apple")
            squares[tail].classList.add("snake")
            currentSnake.push(tail)
            randomApple()
            score++
            scoreDisplay.textContent = score
            clearInterval(interval)
            intervalTime = intervalTime * speed
            interval = setInterval(moveOutcomes, intervalTime)
        }
        squares[currentSnake[0]].classList.add("snake")

    }

    function randomApple() {
        do {
            appleIndex = Math.floor(Math.random() * squares.length)
        } while(squares[appleIndex].classList.contains("snake"))
        squares[appleIndex].classList.add("apple")
    }

    function gameOver() {
        clearInterval(interval);
        let user = localStorage.getItem("user");
        scoreDisplay.textContent = "grattis " + user.firstName;
        sendHighscore();
    }

    function sendHighscore(){
        amqp.connect('amqps://iaqmcoek:EuPEe_Au1nAuEa8DD8tWstWP3VwFwV1m@rattlesnake.rmq.cloudamqp.com/iaqmcoek', function(error0, connection) {
            if (error0) {
              throw error0;
            }
            connection.createChannel(function(error1, channel) {
              if (error1) {
                throw error1;
              }
              var queue = 'highscore';
              var msg = '"Hello" world';
          
              channel.assertQueue(queue, {
                durable: false
              });
          
              channel.sendToQueue(queue, Buffer.from(msg));
              console.log(" [x] Sent %s", msg);
            });
          });
    }
        

    function control(e) {
        squares[currentIndex].classList.remove('snake')

        if(e.keyCode === 39) {
            direction =  1 //Right arrow
        } else if(e.keyCode === 38) {
            direction = -width //Up arrow
        } else if (e.keyCode == 37) {
            direction = -1 //left arrow
        } else if (e.keyCode == 40) {
            direction = +width //down arrow
        }
    }

    document.addEventListener('keyup', control)
    startBtn.addEventListener('click', startGame)
})