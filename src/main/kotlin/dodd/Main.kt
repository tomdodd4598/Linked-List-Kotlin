package dodd

import java.math.BigInteger
import java.util.regex.Pattern

val VALID_REGEX: Pattern = Pattern.compile("^(0|-?[1-9][0-9]*|[A-Za-z][0-9A-Z_a-z]*)$")
val NUMBER_REGEX: Pattern = Pattern.compile("^-?[0-9]+$")

fun isValidString(str: String) = VALID_REGEX.matcher(str).matches()

fun isNumberString(str: String) = NUMBER_REGEX.matcher(str).matches()

fun insertBefore(value: String, item: Item<String>): Boolean {
	return if (isNumberString(value) && isNumberString(item.value)) {
		BigInteger(value) <= BigInteger(item.value)
	}
	else {
		value <= item.value
	}
}

fun valueEquals(item: Item<String>, value: String) = item.value == value

fun main() {
	var start: Item<String>? = null
	
	var begin = true
	
	while (true) {
		if (!begin) {
			println()
		}
		else {
			begin = false
		}
		
		println("Awaiting input...")
		val read = readLine()
		if (read == null) {
			println("Failed to read line!\n")
		}
		var input = read!!
		
		if (input.isEmpty()) {
			println("\nProgram terminated!")
			//start = removeAll(start)
			return
		}
		else if (input[0] == '~') {
			if (input.length == 1) {
				println("\nDeleting list...")
				start = removeAll(start)
			}
			else {
				input = input.substring(1)
				if (isValidString(input)) {
					println("\nRemoving item...")
					start = removeItem(start, input, ::valueEquals)
				}
				else {
					println("\nCould not parse input!")
				}
			}
		}
		else if (input == "l") {
			println("\nLoop print...")
			printLoop(start)
		}
		else if (input == "i") {
			println("\nIterator print...")
			printIterator(start)
		}
		else if (input == "a") {
			println("\nArray print...")
			printArray(start)
		}
		else if (input == "r") {
			println("\nRecursive print...")
			printRecursive(start)
		}
		else if (input == "f") {
			println("\nFold print...")
			printFold(start)
		}
		else if (input == "b") {
			println("\nFoldback print...")
			printFoldback(start)
		}
		else if (isValidString(input)) {
			println("\nInserting item...")
			start = insertItem(start, input, ::insertBefore)
		}
		else {
			println("\nCould not parse input!")
		}
	}
}
