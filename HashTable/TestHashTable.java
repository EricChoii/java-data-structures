// --== CS400 File Header Information ==--
// Name: 				Eric Choi
// Email: 				hchoi256@wisc.edu
// Team: 				ID
// TA:		 			Mu Cai
// Lecturer: 			Gary Dahl
// Notes to Grader: 	<optional extra notes>

public class TestHashTable {

	public static void main(String[] args) {
		System.out.println("Main Method : " + test1());
		System.out.println("Main Method : " + test2());
		System.out.println("Main Method : " + test3());
		System.out.println("Main Method : " + test4());
		System.out.println("Main Method : " + test5());
	}

	// Try putting different types of key and values and tests the specific load factor that triggers growth in private internal array 
	public static boolean test1() {
		try {
			HashTableMap hashTable = new HashTableMap();
			
			System.out.println("Start test1(put)");
			System.out.println("Put fruits :"
					+ "\n apple: 1,000,"
					+ "\n pear: 2,000,"
					+ "\n watermelon: 5,000,"
					+ "\n plum: 1,500,"
					+ "\n orange: 700,"
					+ "\n grape: null,"
					+ "\n strawberry: sold out"
					+ "\n strawberry: sold out");

			System.out.println(hashTable.put("apple", 1000));
			System.out.println(hashTable.put("pear", 2000));
			System.out.println(hashTable.put("watermelon", 5000));
			System.out.println(hashTable.put("plum", 1500));
			System.out.println(hashTable.put("orange", 700));
			System.out.println(hashTable.put("grape", null));
			System.out.println(hashTable.put("strawberry", "sold out"));
			System.out.println("size() : " + hashTable.size() + "\tcapacity : " + hashTable.arr_Capacity);
			System.out.println(hashTable.put("strawberry", "sold out"));
			System.out.println("size() : " + hashTable.size() + "\tcapacity : " + hashTable.arr_Capacity);
			System.out.println(hashTable.put("cherry", 1000));
			System.out.println("size() : " + hashTable.size() + "\tcapacity : " + hashTable.arr_Capacity);
			
			return true;
		} catch (Exception e) {
			System.out.println("Error: " + e);

			return false;
		}
	}

	// Use conatiansKey() and Try passing a null key to cause an error deliberately so that we check the application will return false
	public static boolean test2() {
		try {
			HashTableMap hashTable = new HashTableMap();
			
			hashTable.put("Eric", "Computer Science");
			
			System.out.println("Is Sara in the hash table?\t" + hashTable.containsKey("Sara"));
			System.out.println("Is Eric in the hash table?\t" + hashTable.containsKey("Eric"));

			System.out.println(hashTable.put(null, "Programming is fun!"));
			
			return true;
		} catch (Exception e) {
			System.out.println("Error: " + e);

			return false;
		}
	}

	// grow by doubling and rehashing multiple times by using a loop
	public static boolean test3() {
		try {
			HashTableMap hashTable = new HashTableMap(2);
			
			for(int i = 0; i < 20; i++) {
				hashTable.put("CS"+i, i);
				System.out.println("size() : " + hashTable.size() + "\tcapacity : " + hashTable.arr_Capacity);
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error: " + e);

			return false;
		}
	}

	// Try to put and get data of the same hash code.
	public static boolean test4() {
		try {
			HashTableMap hashTable = new HashTableMap();
			
			hashTable.put(1, "CS1");
			hashTable.put(11, "CS11");
			hashTable.put(21, "CS21");
			hashTable.put(31, "CS31");
			hashTable.put(41, "CS41");
			
			System.out.println(hashTable.get(1));
			System.out.println(hashTable.get(11));
			System.out.println(hashTable.get(41));
			
			return true;
		} catch (Exception e) {
			System.out.println("Error: " + e);

			return false;
		}
	}

	// clear
	public static boolean test5() {
		try {
			HashTableMap hashTable = new HashTableMap();
			
			hashTable.put("CS_Comment1", "Fun");
			hashTable.put("CS_Comment2", "Interesting");
			
			System.out.println("Before clearing the hash table, size() : " + hashTable.size());

			hashTable.clear();

			System.out.println("After clearing the hash table, size() : " + hashTable.size());
			return true;
		} catch (Exception e) {
			System.out.println("Error: " + e);

			return false;
		}
	}

}
