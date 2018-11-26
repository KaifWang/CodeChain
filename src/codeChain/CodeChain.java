package codeChain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class CodeChain {
	
	
	private class Block{

		private List<Class<?>> neededArgumentTypes;
		private Function<ArgumentMap, ArgumentMap> code;
		private ArgumentMap arguments;
		/**
		 * @param argument
		 * @param code
		 */
		private Block(List<Class<?>> neededArgumentTypes, Function<ArgumentMap, ArgumentMap> code) {
			super();
			this.neededArgumentTypes = neededArgumentTypes;
			this.code = code;
		}
		
		/**
		 * @return the arguments
		 */
		private List<Class<?>> getNeededArgumentTypes() {
			return neededArgumentTypes;
		}
		
		private void setArguments() {
			arguments = new ArgumentMap();
			for(Class<?> type : getNeededArgumentTypes()) {
				if(!allowableArguments.containsKey(type)) {
					System.out.println("No such argument type exists");
				}
				arguments.put(allowableArguments.get(type));
			}
		}

		private ArgumentMap execute() {
			setArguments();
			return code.apply(arguments);
		}
		
	}

	private LinkedList<Block> blockList = new LinkedList<Block>();
	private HashMap<Class<?>, Object> allowableArguments = new HashMap<Class<?>, Object>();
	
	private void addToMap(ArgumentMap arguments) {
		for(Object argument : arguments.values()) {
			allowableArguments.put(argument.getClass(), argument);
		}
	}
	
	public void buildBlock(List<Class<?>> neededArgumentTypes, Function<ArgumentMap, ArgumentMap> code) {
		blockList.add(new Block(neededArgumentTypes, code));
	}
	
	
	public ArgumentMap execute(ArgumentMap arguments) {
		ArgumentMap currentResults = null;
		addToMap(arguments);
		Iterator<Block> iterator = blockList.iterator();
		while(iterator.hasNext()) {
			Block currentBlock = iterator.next();
			currentResults = currentBlock.execute();
			addToMap(currentResults);
		}
		return currentResults;
	}
}
