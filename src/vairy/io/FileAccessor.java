package vairy.io;

public interface FileAccessor<T> {
	public T readFile();
	public Boolean writeFile(final T src);
}
