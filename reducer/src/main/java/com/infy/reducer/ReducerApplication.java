package com.infy.reducer;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infy.reducer.datacompressor.DataCompressor;
import com.infy.reducer.datacompressor.DataCompressorImpl;
import com.infy.reducer.dataconverter.DataConverter;
import com.infy.reducer.dataconverter.DataConverterImpl;
import com.infy.reducer.entity.Person;
import com.infy.reducer.file.CompressedFile;

@SpringBootApplication
public class ReducerApplication<T> {

	public static String ENTITY_NAME;
	private final Class<T> entityClass;

	public ReducerApplication(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void process() {
		try {

			String path = "src/main/resources/Person.json";
			String json = readFileAsString(path);

			File f = new File("Person.json");

			Properties properties = new Properties();
			try {
				InputStream inputStream = ReducerApplication.class.getClassLoader()
						.getResourceAsStream("application.properties");
				properties.load(inputStream);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			ENTITY_NAME = properties.getProperty("entity_name");

			Class<T> entityClass = (Class T) Class.forName(ENTITY_NAME);

			DataConverter<T> dataConverter = new DataConverterImpl<>(entityClass);

			DataCompressor<T> dataCompressor = new DataCompressorImpl<>(dataConverter);

			T convertedObject = dataConverter.jsonToJavaObject(json);

			byte[] compressedData = dataCompressor.compress(convertedObject);

			CompressedFile.bytetoFile(compressedData);
			T decompressedData = dataCompressor.decompress(compressedData);

			String result = dataConverter.javaObjectToJson(decompressedData);
			System.out.println(result) ;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SpringApplication.run(ReducerApplication.class, args);
		ReducerApplication<Person> reducer = new ReducerApplication<>(Person.class) ;
		reducer.process();
	}

	public static String readFileAsString(String path) throws Exception {
		return new String(Files.readAllBytes(Paths.get(path)));
	}

}
