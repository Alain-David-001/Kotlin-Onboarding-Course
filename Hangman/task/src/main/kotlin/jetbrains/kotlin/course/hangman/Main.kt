package jetbrains.kotlin.course.hangman

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int) = "Welcome to the game!$newLineSymbol$newLineSymbol" +
        "In this game, you need to guess the word made by the computer.$newLineSymbol" +
        "The hidden word will appear as a sequence of underscores, one underscore means one letter.$newLineSymbol" +
        "You have $maxAttemptsCount attempts to guess the word.$newLineSymbol" +
        "All words are English words, consisting of $wordLength letters.$newLineSymbol" +
        "Each attempt you should enter any one letter,$newLineSymbol" +
        "if it is in the hidden word, all matches will be guessed.$newLineSymbol$newLineSymbol" +
        "" +
        "For example, if the word \"CAT\" was guessed, \"_ _ _\" will be displayed first, " +
        "since the word has 3 letters.$newLineSymbol" +
        "If you enter the letter A, you will see \"_ A _\" and so on.$newLineSymbol$newLineSymbol" +
        "" +
        "Good luck in the game!"

// You will use this function later
fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = complete && attempts <= maxAttemptsCount

// You will use this function later
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = !complete && attempts > maxAttemptsCount

fun isComplete(secret: String, currentGuess: String): Boolean = secret == currentGuess.replace(separator,"")

fun generateNewUserWord(secret: String,guess: Char, currentUserWord: String): String {
    return currentUserWord
        .replace(separator,"")
        .mapIndexed{ index, letter -> (if (secret[index] == guess) guess else letter)}
        .joinToString(separator)
}

fun getHiddenSecret(wordLength: Int): String = List(wordLength){'_'}.joinToString(separator)

fun generateSecret():String = words.random()

fun isCorrectInput(userInput:String):Boolean {
    if(userInput.count()!=1){
        println("The length of your guess should be 1! Try again!")
        return false
    }
    if(!userInput[0].isLetter()){
        println("You should input only English letters! Try again!")
        return false
    }
    return true
}

fun safeUserInput(): Char {
    println("Please input your guess.")
    val userInput = safeReadLine()
    if(isCorrectInput(userInput)){
        return userInput.uppercase()[0]
    }
    return safeUserInput()
}

fun getRoundResults(secret: String, guess: Char, currentUserWord: String):String {
    return if(guess in secret){
        val nextUserWord =  generateNewUserWord(secret,guess,currentUserWord)
        println("Great! This letter is in the word! The current word is $nextUserWord")
        nextUserWord
    }
    else {
        println("Sorry, the secret does not contain the symbol: $guess. The current word is $currentUserWord")
        currentUserWord
    }
}

fun playGame(secret: String, maxAttemptsCount: Int): Unit {
    var attempts = 0
    var currentUserWord = getHiddenSecret(wordLength)
    do {
        val userInput = safeUserInput()
        attempts++
        currentUserWord = getRoundResults(secret,userInput,currentUserWord)
        if(isLost(isComplete(secret,currentUserWord),attempts,maxAttemptsCount)){
            println("Sorry, you lost! My word is $secret")
            break
        }
        if(isWon(isComplete(secret,currentUserWord),attempts,maxAttemptsCount)){
            println("Congratulations! You guessed it!")
            break
        }
    } while(attempts<=maxAttemptsCount)
}

fun main() {
    // Uncomment this code on the last step of the game

    println(getGameRules(wordLength, maxAttemptsCount))
    playGame(generateSecret(), maxAttemptsCount)
}
