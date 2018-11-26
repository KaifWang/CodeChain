package codeChain;

import java.util.LinkedList;

public class CodeChainDemo {

	public static void main(String[] args) {
		CodeChain c = new CodeChain();
		LinkedList<Class<?>> neededType = new LinkedList<Class<?>>();
		neededType.add(String.class);
		
		c.buildBlock(neededType, a ->{String s = a.get(String.class);	ArgumentMap results = new ArgumentMap();
		results.put(s); results.put(new String("5")); return results;});
		ArgumentMap input = new ArgumentMap();		
		input.put(new String("abc"));
		LinkedList<Class<?>> neededType2 = new LinkedList<Class<?>>();
		neededType2.add(Integer.class);
		c.buildBlock(neededType2, a ->{Integer i = a.get(Integer.class);	ArgumentMap results = new ArgumentMap();
		results.put(i); return results;});
		
		//System.out.println(c.execute(input).get(Integer.class));
	}

}
