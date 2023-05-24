package com.infy.reducer.datacompressor;


public interface DataCompressor<T> {
	byte[] compress(T t) throws Exception ;
	T decompress(byte[] compressedData) throws Exception ;
}
