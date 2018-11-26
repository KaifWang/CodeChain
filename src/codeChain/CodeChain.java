package codeChain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

public class CodeChain {
		
	/*
	 * A block of code chain
	 */
	class Block{

		private List<Class<?>> neededArgumentTypes;
		private Function<ArgumentMap, ArgumentMap> code;
		private ArgumentMap arguments;
		/**
		 * @param argument
		 * @param code
		 * private Constructor to build a block
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
		
		/*
		 * Set arguments according to the neededArgumentTypes requested from the user
		 */
		private void setArguments() throws ArgumentTypeException {
			arguments = new ArgumentMap();
			for(Class<?> type : getNeededArgumentTypes()) {
				if(!allowableArguments.containsKey(type)) {
					throw new ArgumentTypeException("Needed type does not exist in the code chain");
				}
				arguments.put(allowableArguments.get(type));
			}
		}

		/*
		 * Execute this single block 
		 */
		private ArgumentMap execute() throws ArgumentTypeException{
			setArguments();
			return code.apply(arguments);
		}
		
		class BlockTestHook{

			public void setArguments() throws ArgumentTypeException{
				Block.this.setArguments();
			}

			public ArgumentMap execute() throws ArgumentTypeException{
				return Block.this.execute();
			}
			
		}
	}

	private LinkedList<Block> blockList = new LinkedList<Block>();
	private HashMap<Class<?>, Object> allowableArguments = new HashMap<Class<?>, Object>();
	
	/*
	 * Helper method, put all the input arguments into the code chain arguments
	 */
	private void addToMap(ArgumentMap arguments) {
		for(Object argument : arguments.values()) {
			allowableArguments.put(argument.getClass(), argument);
		}
	}
	
	/*
	 * build method for block, directly append to the end of the code chain. 
	 * Error handling: if the neededArgumentTypes user request for this block does not exist in the codechain,
	 * inform the user and do not build the block
	 */
	public void buildBlock(List<Class<?>> neededArgumentTypes, Function<ArgumentMap, ArgumentMap> code) {
			blockList.add(new Block(neededArgumentTypes, code));
	}
	
	
	/*
	 * Execute the entire code chain, results from previous blocks can be used as arguments for later blocks,
	 * Final result of the block chain will be the result of the last block
	 */
	public ArgumentMap execute(ArgumentMap arguments) throws ArgumentTypeException{
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
	
	class TestHook{
		public void addToMap(ArgumentMap arguments) {
			CodeChain.this.addToMap(arguments);
		}
		
		/*
		 * Helper method for testing
		 */
		public Block BlockConstructor(List<Class<?>> neededArgumentTypes, Function<ArgumentMap, ArgumentMap> code) {
			return new Block(neededArgumentTypes,code);
		}
		
		/*
		 * Helper method for testing
		 */
		public HashMap<Class<?>, Object> getAllowableArguments() {
			return CodeChain.this.allowableArguments;
		}
		
		/*
		 * Helper method for testing
		 */
		public LinkedList<Block> getBlockList(){
			return CodeChain.this.blockList;
		}
		
		
	}
}
