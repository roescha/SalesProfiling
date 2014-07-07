package myshop.domain;

public enum DirectionOptions {
	Prev, Next;
	public int shift() {
		return this == Next ? 1 : -1;
	}
}
