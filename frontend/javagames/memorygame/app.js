document.addEventListener('DOMContentLoaded', () => {
    //Card options
    const cardArray = [
        {
            name: 'fries',
            img: 'dist/javagames/memorygame/images/fries.png'
        },
        {
            name: 'fries',
            img: 'dist/javagames/memorygame/images/fries.png'
        },
        {
            name: 'cheeseburger',
            img: 'dist/javagames/memorygame/images/cheeseburger.png'
        },
        {
            name: 'cheeseburger',
            img: 'dist/javagames/memorygame/images/cheeseburger.png'
        },
        {
            name: 'hotdog',
            img: 'dist/javagames/memorygame/images/hotdog.png'
        },
        {
            name: 'hotdog',
            img: 'dist/javagames/memorygame/images/hotdog.png'
        },
        {
            name: 'ice-cream',
            img: 'dist/javagames/memorygame/images/ice-cream.png'
        },
        {
            name: 'ice-cream',
            img: 'dist/javagames/memorygame/images/ice-cream.png'
        },
        {
            name: 'milkshake',
            img: 'dist/javagames/memorygame/images/milkshake.png'
        },
        {
            name: 'milkshake',
            img: 'dist/javagames/memorygame/images/milkshake.png'
        },
        {
            name: 'pizza',
            img: 'dist/javagames/memorygame/images/pizza.png'
        },
        {
            name: 'pizza',
            img: 'dist/javagames/memorygame/images/pizza.png'
        }
    ]

    cardArray.sort(() => 0.5 - Math.random());

    var cardsChosen = [];
    var cardsChosenId = [];
    var cardsWon = [];

    const grid = document.querySelector('.grid');
    const resultDisplay = document.querySelector('#result');

    function createBoard() {
        for (let i = 0; i < cardArray.length; i++) {
            var card = document.createElement('img');
            card.setAttribute('src', 'dist/javagames/memorygame/images/blank.png');
            card.setAttribute('data-id', i);
            card.addEventListener('click', flipcard);
            grid.appendChild(card);
        }
    }

    function flipcard() {
        var cardId = this.getAttribute('data-id');
        cardsChosen.push(cardArray[cardId].name);
        cardsChosenId.push(cardId);
        this.setAttribute('src', cardArray[cardId].img);
        if (cardsChosen.length === 2) {
            setTimeout(checkForMatch, 500);
        }
    }

    function checkForMatch(){
        var  cards = document.querySelectorAll('img');
        const optionOneId = cardsChosenId[0];
        const optionTwoId = cardsChosenId[1];
        if (cardsChosen[0] === cardsChosen[1]){
            alert('You found a match');
            cards[optionOneId].setAttribute('src', 'dist/javagames/memorygame/images/white.png');
            cards[optionTwoId].setAttribute('src', 'dist/javagames/memorygame/images/white.png');
            cardsWon.push(cardsChosen);
        } else {
            cards[optionOneId].setAttribute('src', 'dist/javagames/memorygame/images/blank.png');
            cards[optionTwoId].setAttribute('src', 'dist/javagames/memorygame/images/blank.png');
            alert('Sorry, try again!');
        }
        resultDisplay.textContent = cardsWon.length;
        if(cardsWon.length === cardArray.length/2){
            resultDisplay.textContent = "Congratulations! You have found them all!"
        }
        cardsChosen = [];
        cardsChosenId = [];

    }

    createBoard();

})