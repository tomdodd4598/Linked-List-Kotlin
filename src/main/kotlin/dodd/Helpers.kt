package dodd

fun <T> insertItem(start: Item<T>?, value: T, insertBefore: (T, Item<T>) -> Boolean): Item<T>? {
	println("Creating item: $value")
	var head = start
	
	var current = head
	var previous: Item<T>? = null
	
	while (current != null && !insertBefore(value, current)) {
		previous = current
		current = current.next
	}
	val item = Item(value, current)
	
	if (previous == null) {
		head = item
	}
	else {
		previous.next = item
	}
	
	return head
}

fun <T> removeItem(start: Item<T>?, value: T, valueEquals: (Item<T>, T) -> Boolean): Item<T>? {
	var head = start
	var current = head
	var previous: Item<T>? = null
	
	while (current != null && !valueEquals(current, value)) {
		previous = current
		current = current.next
	}
	
	if (current == null) {
		println("Item $value does not exist!")
	}
	else {
		if (previous == null) {
			head = current.next
		}
		else {
			previous.next = current.next
		}
		println("Removed item: $value")
	}
	
	return head
}

fun <T> removeAll(@Suppress("UNUSED_PARAMETER") start: Item<T>?): Item<T>? {
	return null
}

fun <T> printList(start: Item<T>?) {
	var item = start
	while (item != null) {
		item = item.printGetNext()
	}
}

fun <T> printIterator(start: Item<T>?) {
	if (start != null) {
		for (item in start) {
			item.printGetNext()
		}
	}
}

fun <T> printArray(start: Item<T>?) {
	if (start != null) {
		var item = start
		var i = 0
		while (item != null) {
			item = start[i]?.printGetNext()
			i += 1
		}
	}
}

tailrec fun <T> printRecursive(start: Item<T>?) {
	if (start != null) {
		printRecursive(start.printGetNext())
	}
}

fun <T> printFold(start: Item<T>?) {
	val fSome = { current: Item<T>, _: Item<T>, accumulator: String -> "$accumulator${current.value}, " }
	val fLast = { current: Item<T>, accumulator: String -> "$accumulator${current.value}\n" }
	val fEmpty = { accumulator: String -> accumulator }
	print(itemFold(fSome, fLast, fEmpty, "", start))
}

fun <T> printFoldback(start: Item<T>?) {
	val fSome = { current: Item<T>, _: Item<T>, innerVal: String -> "${current.value}, $innerVal" }
	val fLast = { current: Item<T> -> "${current.value}\n" }
	val fEmpty = { "" }
	print(itemFoldback(fSome, fLast, fEmpty, { x -> x }, start))
}
