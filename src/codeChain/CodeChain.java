package codeChain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class CodeChain {
	private LinkedList<Block> blockList = new LinkedList<Block>();
	private HashMap<Class<?>, Object> argumentMap = new HashMap<Class<?>, Object>();
	private void addToMap(ArgumentMap arguments) {
		for(Object argument : arguments.values()) {
			argumentMap.put(argument.getClass(), argument);
		}
	}
	
	public void add(Block block) {
		blockList.add(block);
	}
	
	
	public ArgumentMap execute(ArgumentMap arguments) {
		ArgumentMap currentResults = null;
		addToMap(arguments);
		Iterator<Block> iterator = blockList.iterator();
		while(iterator.hasNext()) {
			Block currentBlock = iterator.next();
			ArgumentMap currentArguments = new ArgumentMap();
			for(Class<?> type : currentBlock.getNeededArgumentTypes()) {
				if(!argumentMap.containsKey(type)) {
					//error
				}
				currentArguments.put(argumentMap.get(type));
			}
			currentResults = currentBlock.execute(currentArguments);
			addToMap(currentResults);
		}
		return currentResults;
	}
}
