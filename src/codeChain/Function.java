package codeChain;

public interface Function<T,R> extends Block{
	R apply(T argument);
}
