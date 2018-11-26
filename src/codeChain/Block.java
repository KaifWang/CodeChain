package codeChain;

import java.util.List;
import java.util.function.Function;

public class Block{

	private List<Class<?>> neededArgumentTypes;
	private Function<ArgumentList, ArgumentList> code;
	
	/**
	 * @param argument
	 * @param code
	 */
	public Block(List<Class<?>> neededArgumentTypes, Function<ArgumentList, ArgumentList> code) {
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



	ArgumentList execute(ArgumentList arguments) {
		return code.apply(arguments);
	}
	
}
