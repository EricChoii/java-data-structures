// --== CS400 File Header Information ==--
// Name: 				Eric Choi
// Email: 				hchoi256@wisc.edu
// Team: 				ID
// TA:		 			Mu Cai
// Lecturer: 			Gary Dahl
// Notes to Grader: 	<optional extra notes>

import java.util.LinkedList;
import java.util.NoSuchElementException;

class KeyValueClass {
	private Object key;
	private Object value;

	public KeyValueClass(Object key2, Object value2) {
		this.key = key2;
		this.value = value2;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

public class HashTableMap implements MapADT {
	public static int arr_Capacity;
	private static int size = 0;
	private LinkedList<KeyValueClass>[] linkedList;

	public HashTableMap() // with default capacity = 10
	{
		arr_Capacity = 10;
		linkedList = new LinkedList[arr_Capacity];
	}

	public HashTableMap(int capacity) {
		arr_Capacity = capacity;
		linkedList = new LinkedList[arr_Capacity];
	}

	// GrowCapacity : Grow by doubling and rehashing, whenever its load capacity
	// becomes greater than or equal to 80%
	private boolean GrowCapacity(Object key, Object value) {
		LinkedList<KeyValueClass>[] temp = linkedList;

		size = 0; // reset the size
		arr_Capacity *= 2; // double the capacity
		linkedList = new LinkedList[arr_Capacity];

		// rehashing
		for (LinkedList<KeyValueClass> iterate : temp) {
			if (iterate != null) {
				for (KeyValueClass node : iterate) {
					put(node.getKey(), node.getValue());
				}
			}
		}

		return put(key, value); // hash the last input
	}

	@Override
	public boolean put(Object key, Object value) {
		int hashCode = Math.abs(key.hashCode()) % arr_Capacity; // create a hash code

		KeyValueClass keyValuePair = new KeyValueClass(key, value);

		if (linkedList[hashCode] == null) {
			linkedList[hashCode] = new LinkedList(); // first time to put at a index
		} else {
			for (KeyValueClass iterate : linkedList[hashCode]) {
				if (iterate.getKey().equals(key))
					return false; // the same key already exists
			}
		}

		// before adding an input, check the load capacity of the array
		// if the load capacity exceeds 80% after adding the current input, grow by
		// doubling and rehashing
		if (size + 1 >= arr_Capacity * 8 / 10)
			return GrowCapacity(key, value);

		size++;
		linkedList[hashCode].addLast(keyValuePair);

		return true;
	}

	@Override
	public Object get(Object key) throws NoSuchElementException {
		// Find head of chain for given key
		int hashCode = Math.abs(key.hashCode()) % arr_Capacity;

		if (linkedList[hashCode] != null) {
			if (linkedList[hashCode].size() <= 1) { // only one input has been saved at an index
				if (linkedList[hashCode].getFirst().getKey().equals(key))
					return linkedList[hashCode].getFirst().getValue();
			} else { // multiple inputs have been saved at an index
				for (int index = 0; index < linkedList[hashCode].size(); index++) {
					if (linkedList[hashCode].get(index).getKey().equals(key))
						return linkedList[hashCode].get(index).getValue();
				}
			}
		}

		// If key not found
		throw new NoSuchElementException();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean containsKey(Object key) {
		int hashCode = Math.abs(key.hashCode()) % arr_Capacity;

		if (linkedList[hashCode] != null) {
			if (linkedList[hashCode].size() < 2) {
				if (linkedList[hashCode].getFirst().getKey().equals(key))
					return true;
			} else {
				for (int index = 0; index < linkedList[hashCode].size(); index++) {
					if (linkedList[hashCode].get(index).getKey().equals(key))
						return true;
				}
			}
		}

		return false;
	}

	@Override
	public Object remove(Object key) {
		int hashCode = Math.abs(key.hashCode()) % arr_Capacity;
		Object res = null;

		if (linkedList[hashCode] != null) {
			if (linkedList[hashCode].size() <= 1) {
				res = linkedList[hashCode].getFirst().getValue();
				size--;
				linkedList[hashCode] = null;
			} else {
				for (int index = 0; index < linkedList[hashCode].size(); index++) {
					if (linkedList[hashCode].get(index).getKey().equals(key)) {
						res = linkedList[hashCode].get(index).getValue();
						size--;
						linkedList[hashCode].remove(index);
					}
				}
			}
		}

		return res;
	}

	@Override
	public void clear() {
		size = 0;
		linkedList = new LinkedList[arr_Capacity];
	}

}
