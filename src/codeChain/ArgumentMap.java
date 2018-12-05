package codeChain;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author Kai Wang.
 * Data structure that supports multiple inputs and outputs for the code chain.
 * Map structure is used to ensure type safety.
 * Limitation: Only one instance of a type can be stored at one time.
 */
public class ArgumentMap {

	private HashMap<Class<?>, Object> arguments = new HashMap<Class<?>, Object>(); //Global variable: A hash map to store all its data
	

	/**
	 * Put an instance into the map, the key to the instance is the instance's type
	 * @param argument the instance that is put into the map
	 */
	public void put(Object argument) {
		arguments.put(argument.getClass(), argument);
	}
	
	/**
	 * Get an instance from the map
	 * @param type the type of the instance
	 * @return the instance corresponding to the type specified
	 */
	public <T> T get(Class<T> type){
		return (T) arguments.get(type);
	}
	
	/**
	 * Package private helper method, not exposed to external users
	 * @return collection of all instances in the map
	 */
	Collection<Object> values(){
		return arguments.values();
	}
	
	
}
