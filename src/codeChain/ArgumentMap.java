package codeChain;

import java.util.Collection;
import java.util.HashMap;

/*
 * A data structure using Map, supports multiple inputs and outputs for arguments and results,
 * do the necessary type casting for user to use
 */
public class ArgumentMap {
	private HashMap<Class<?>, Object> arguments = new HashMap<Class<?>, Object>();
	
	/*
	 * put a argument into the map
	 */
	public void put(Object argument) {
		arguments.put(argument.getClass(), argument);
	}
	
	/*
	 * get the corresponding argument based on specified type
	 */
	public <T> T get(Class<T> type){
		return (T) arguments.get(type);
	}
	
	/*
	 * Package helper method, get all the values of the arguments
	 */
	Collection<Object> values(){
		return arguments.values();
	}
	
	
}
