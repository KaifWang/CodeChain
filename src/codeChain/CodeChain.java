package codeChain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Kai Wang.
 * A chain of code that will be executed in order.
 * Assumption: All the data used in the block of code chain should be either 
 * parsed to the code chain as initial arguments, or
 * the results from the previous block.
 * Otherwise an ArgumentTypeException will be thrown upon execution
 */
public class CodeChain {
		

	/**
	 * @author Kai Wang
	 * A single block of code chain
	 * All data and methods are private to achieve fully encapsulation,
	 * External users have no access to block implementation at all.
	 */
	class Block{

		private List<Class<?>> neededArgumentTypes;       //Stores the list of types that will be used in this block's implementation
		private Function<ArgumentMap, ArgumentMap> code;  //This block's implementation. A function takes an argument map as inputs and returns an argument map as output
		private ArgumentMap arguments;                    //Stores all the instances that will be used in this block's implementation
		
		/**
		 * A private constructor that builds a block
		 * This constructor can only be used externally through the buildBlock method in the code chain
		 * @param neededArgumentTypes The list of types that will be used in this block's implementation
		 * @param code This block's implementation
		 */
		private Block(List<Class<?>> neededArgumentTypes, Function<ArgumentMap, ArgumentMap> code) {
			super();
			this.neededArgumentTypes = neededArgumentTypes;
			this.code = code;
		}
		
		/**
		 * Get the needed argument types of the block
		 * @return the needed argument types of the block
		 */
		private List<Class<?>> getNeededArgumentTypes() {
			return neededArgumentTypes;
		}
		

		/**
		 * Set up the arguments that can be used according to the needed types for the block 
		 * @throws ArgumentTypeException if the needed types do not exist in the code chain
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

		/**
		 * Execute this block based on its implementation provided by the user
		 * @return the result of the execution 
		 * @throws ArgumentTypeException if the data used in the implementation do not exist in the code chain
		 */
		private ArgumentMap execute() throws ArgumentTypeException{
			setArguments();
			return code.apply(arguments);
		}
		
		/*
		 * Block class test hook
		 */
		class BlockTestHook{

			/*
			 * Helper method for testing
			 */
			public void setArguments() throws ArgumentTypeException{
				Block.this.setArguments();
			}

			/*
			 * Helper method for testing
			 */
			public ArgumentMap execute() throws ArgumentTypeException{
				return Block.this.execute();
			}
			
		}
	}

	private LinkedList<Block> blockList = new LinkedList<Block>();                          //Stores list of block in the code chain
	private HashMap<Class<?>, Object> allowableArguments = new HashMap<Class<?>, Object>(); //A hash map that stores all the data that could be used by its block.

	/**
	 * Private helper method that adds all the data in an argument map into the code chain allowable arguments map 
	 * @param arguments the argument map in which the data we want to add to the code chain map
	 */
	private void addToMap(ArgumentMap arguments) {
		for(Object argument : arguments.values()) {
			allowableArguments.put(argument.getClass(), argument);
		}
	}
	
	/** Build a block and add it to the code chain
	 * @param neededArgumentTypes the argument types that will be used in the code
	 * @param code the implementation of this block
	 */
	public void buildBlock(List<Class<?>> neededArgumentTypes, Function<ArgumentMap, ArgumentMap> code) {
			blockList.add(new Block(neededArgumentTypes, code));
	}
	
	
	/**
	 * Execute the entire code chain, the final result will be the result of the last block
	 * @param arguments The initial arguments for the code chain
	 * @return the final result of the code chain
	 * @throws ArgumentTypeException if any block of the code chain use data that do not exist in the code chain
	 */
	public ArgumentMap execute(ArgumentMap arguments) throws ArgumentTypeException{
		ArgumentMap currentResults = null;  //Default result is null if no block exists in the code chain
		addToMap(arguments);
		Iterator<Block> iterator = blockList.iterator();
		while(iterator.hasNext()) {
			Block currentBlock = iterator.next();
			currentResults = currentBlock.execute();
			addToMap(currentResults);
		}
		return currentResults;
	}
	
	/*
	 * CodeChain class Test hook
	 */
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
