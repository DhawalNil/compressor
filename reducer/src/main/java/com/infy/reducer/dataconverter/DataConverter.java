package com.infy.reducer.dataconverter;


public interface DataConverter<T> {
	
	T jsonToJavaObject(String jsonData ) throws Exception;
	String javaObjectToJson(T t1) throws Exception ;
}
