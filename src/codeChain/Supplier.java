package codeChain;

public interface Supplier<T> extends Block{
	T get();
}
