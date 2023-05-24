package com.infy.reducer.datacompressor;

import org.springframework.stereotype.Service;
import org.xerial.snappy.Snappy;
import com.infy.reducer.dataconverter.DataConverter;


@Service
public class DataCompressorImpl<T> implements DataCompressor<T> {
	
//	public static String ENTITY_NAME ;
//	
//	static {
//		Properties properties = new Properties() ;
//		try(InputStream inputStream = DataCompressorImpl.class.getClassLoader().getResourceAsStream("application.properties")){
//			properties.load(inputStream);
//		}
//		catch(Exception e )
//		{
//			e.printStackTrace(); 
//		}
//		ENTITY_NAME = properties.getProperty("entity_name") ;
//		
//	}
	
	private final DataConverter<T> dataConverter ;
	
	public DataCompressorImpl(DataConverter<T> dataConverter) {
		this.dataConverter = dataConverter ; 
	}
	
	@Override
	public byte[] compress(T object) throws Exception {
		
		byte[] jsonData = dataConverter.javaObjectToJson(object).getBytes() ;
		return Snappy.compress(jsonData);
		
	}
	
	@Override
	public T decompress(byte[] compressedData ) throws Exception {
		
		byte[] decompressedData = Snappy.uncompress(compressedData) ;
		String jsonData = new String(decompressedData) ;		
		return dataConverter.jsonToJavaObject(jsonData) ;
		
	}
}
