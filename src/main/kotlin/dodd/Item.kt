package dodd

class Item<T>(val value: T, var next: Item<T>?) : Iterable<Item<T>?> {
	
	fun printGetNext(): Item<T>? {
		print(value)
		print(if (next == null) "\n" else ", ")
		return next
	}
	
	operator fun get(n: Int): Item<T>? {
		var item: Item<T>? = this
		for (i in 0 until n) {
			item = item?.next
		}
		return item
	}
	
	override fun iterator(): Iterator<Item<T>> {
		return object : Iterator<Item<T>> {
			
			var item: Item<T>? = this@Item
			
			override fun hasNext(): Boolean {
				return item != null
			}
			
			override fun next(): Item<T> {
				val next = item
				item = item?.next
				return next!!
			}
		}
	}
}

tailrec fun <T, A, R> itemFold(fSome: (Item<T>, Item<T>, A) -> A, fLast: (Item<T>, A) -> R, fEmpty: (A) -> R, accumulator: A, item: Item<T>?): R {
	return if (item != null) {
		val next = item.next
		if (next != null) {
			itemFold(fSome, fLast, fEmpty, fSome(item, next, accumulator), next)
		} else {
			fLast(item, accumulator)
		}
	} else {
		fEmpty(accumulator)
	}
}

tailrec fun <T, A, R> itemFoldback(fSome: (Item<T>, Item<T>, A) -> A, fLast: (Item<T>) -> A, fEmpty: () -> A, generator: (A) -> R, item: Item<T>?): R {
	return if (item != null) {
		val next = item.next
		if (next != null) {
			itemFoldback(fSome, fLast, fEmpty, { innerVal -> generator(fSome(item, next, innerVal)) }, next)
		} else {
			generator(fLast(item))
		}
	} else {
		generator(fEmpty())
	}
}
