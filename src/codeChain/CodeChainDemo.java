package codeChain;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;

public class CodeChainDemo {

	public static void main(String[] args) {
		CodeChain c = new CodeChain();
		LinkedList<Class<?>> neededType = new LinkedList<Class<?>>();
		neededType.add(Integer.class);
		neededType.add(String.class);
		Block b = new Block(neededType, a ->{String s = (String)a.getArguments().getLast();	ArgumentList results = new ArgumentList();
		results.getArguments().add(s); return results;});
		c.add(b);
		ArgumentList input = new ArgumentList();
		input.getArguments().add(new Integer(5));
		input.getArguments().add(new String("abc"));
		System.out.println(c.execute(input).getArguments().get(0));
	}

}
