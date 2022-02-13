// --== CS400 File Header Information ==--
// Name: 				Eric Choi
// Email: 				hchoi256@wisc.edu
// Team: 				ID
// TA:		 			Mu Cai
// Lecturer: 			Gary Dahl
// Notes to Grader: 	<optional extra notes>

import java.util.NoSuchElementException;

interface MapADT<KeyType, ValueType> {
	public boolean put(KeyType key, ValueType value);

	public ValueType get(KeyType key) throws NoSuchElementException;

	public int size();

	public boolean containsKey(KeyType key);

	public ValueType remove(KeyType key);

	public void clear();
}