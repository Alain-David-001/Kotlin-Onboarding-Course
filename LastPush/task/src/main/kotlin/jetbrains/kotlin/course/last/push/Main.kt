package jetbrains.kotlin.course.last.push


// You will use this function later
fun getPattern(): String {
    println(
        "Do you want to use a pre-defined pattern or a custom one? " +
                "Please input 'yes' for a pre-defined pattern or 'no' for a custom one"
    )
    do {
        when (safeReadLine()) {
            "yes" -> {
                return choosePattern()
            }
            "no" -> {
                println("Please, input a custom picture")
                return safeReadLine()
            }
            else -> println("Please input 'yes' or 'no'")
        }
    } while (true)
}

// You will use this function later
fun choosePattern(): String {
    do {
        println("Please choose a pattern. The possible options: ${allPatterns().joinToString(", ")}")
        val name = safeReadLine()
        val pattern = getPatternByName(name)
        pattern?.let {
            return@choosePattern pattern
        }
    } while (true)
}

// You will use this function later
fun chooseGenerator(): String {
    var toContinue = true
    var generator = ""
    println("Please choose the generator: 'canvas' or 'canvasGaps'.")
    do {
        when (val input = safeReadLine()) {
            "canvas", "canvasGaps" -> {
                toContinue = false
                generator = input
            }
            else -> println("Please, input 'canvas' or 'canvasGaps'")
        }
    } while (toContinue)
    return generator
}

// You will use this function later
fun safeReadLine(): String = readlnOrNull() ?: error("Your input is incorrect, sorry")

fun getPatternHeight(pattern: String): Int = pattern.lines().size

fun fillPatternRow(patternRow : String, patternWidth : Int): String {
    check(patternRow.length <= patternWidth)
    val answer = patternRow + separator.toString().repeat(patternWidth-patternRow.length)
    return answer
}

fun repeatHorizontally(pattern: String, n : Int, patternWidth: Int): String {
    val result = StringBuilder()
    val lines = pattern.lines()
    for(line in lines){
        result.append(fillPatternRow(line,patternWidth).repeat(n))
        result.append(newLineSymbol)
    }
    result.deleteAt(result.length-1)
    result.deleteAt(result.length-1)
    if("\r\n" in result.toString()) {
        val newResult = result.toString().replace("\r\n","\n")
        return newResult
    }
    return result.toString()
}

fun dropTopFromLine(line: String, width:Int, patternHeight:Int, patternWidth:Int):String {
    if(patternHeight==1)return line
    return line.drop(width*patternWidth+1)
}

fun canvasGenerator(pattern: String, width: Int, height:Int):String {    val result = StringBuilder()
    result.append(repeatHorizontally(pattern,width, getPatternWidth(pattern)))
    val repeatedPattern = result.toString()
    for(i in 1 until height){
        result.append(newLineSymbol + dropTopFromLine(repeatedPattern,width, getPatternHeight(pattern), getPatternWidth(pattern)))
    }
    return result.toString()
}

fun repeatHorizontallyWithGaps(pattern: String, repeats: Int, parity: Boolean): String {
    val result = StringBuilder()
    if(repeats==1) {
        pattern.lines().forEachIndexed { index, x ->
            result.append(fillPatternRow(x, getPatternWidth(pattern)))
            if (index < pattern.lines().size - 1)
                result.append(newLineSymbol)
        }
        return result.toString()
    }
    pattern.lines().forEachIndexed { index, x ->
        for(i in 1..repeats){
            result.append(if((i%2==1)==parity) (x+"$separator".repeat(getPatternWidth(pattern)-x.count())) else "$separator".repeat(getPatternWidth(pattern)))
        }
        if (index < pattern.lines().size - 1)
            result.append(newLineSymbol)
    }
    return result.toString()
}

fun canvasWithGapsGenerator(pattern: String, width: Int, height: Int):String {
    val result = StringBuilder()
    for( i in 1..height){
        result.append(repeatHorizontallyWithGaps(pattern,width,i%2==1))
        if(i<height)result.append(newLineSymbol)
    }
    return result.toString()
}

fun applyGenerator(pattern: String, generatorName: String, width: Int, height: Int):String{
    val trimmed = pattern//.trimIndent()
    return when(generatorName){
        "canvas" -> {
            val answer = canvasGenerator(trimmed, width, height) + newLineSymbol
            if("\r\n" in answer) println("JAAAAAAAAAAAAA" + pattern)
            answer
        }
        "canvasGaps" -> canvasWithGapsGenerator(trimmed, width, height) + newLineSymbol
        else -> error("unexpected filter name")
    }
}

fun main() {
    // Uncomment this code on the last step of the game
    applyGenerator(" X\n" +
            "/ \\\n" +
            "\\ /\n" +
            " X","canvas",1,1)

     val pattern = getPattern()
     val generatorName = chooseGenerator()
     println("Please input the width of the resulting picture:")
     val width = safeReadLine().toInt()
     println("Please input the height of the resulting picture:")
     val height = safeReadLine().toInt()

     println("The pattern:$newLineSymbol${pattern.trimIndent()}")

     println("The generated image:")
     println(applyGenerator(pattern, generatorName, width, height))
}
