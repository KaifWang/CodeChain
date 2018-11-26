package codeChain;

import java.util.List;
import java.util.function.Function;

public class Block{

	private List<Class<?>> neededArgumentTypes;
	private Function<ArgumentMap, ArgumentMap> code;
	
	/**
	 * @param argument
	 * @param code
	 */
	public Block(List<Class<?>> neededArgumentTypes, Function<ArgumentMap, ArgumentMap> code) {
		super();
		this.neededArgumentTypes = neededArgumentTypes;
		this.code = code;
	}
	
	/**
	 * @return the arguments
	 */
	List<Class<?>> getNeededArgumentTypes() {
		return neededArgumentTypes;
	}



	ArgumentMap execute(ArgumentMap arguments) {
		return code.apply(arguments);
	}
	
}
