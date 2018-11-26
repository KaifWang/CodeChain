package codeChain;

import java.util.Collection;
import java.util.HashMap;


public class ArgumentMap {
	private HashMap<Class<?>, Object> arguments = new HashMap<Class<?>, Object>();
	
	public void put(Object argument) {
		arguments.put(argument.getClass(), argument);
	}
	
	public <T> T get(Class<T> type) {
		return (T) arguments.get(type);
	}
	
	Collection<Object> values(){
		return arguments.values();
	}
	
	
}
