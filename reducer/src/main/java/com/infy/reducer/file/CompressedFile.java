package com.infy.reducer.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

@Service
public class CompressedFile {
	static String FILEPATH = "src/main/resources/file.txt" ;
	static File file = new File(FILEPATH) ;
	public static void bytetoFile(byte[] jsonData) {
		try {
			OutputStream os = new FileOutputStream(file) ;
			os.write(jsonData) ;
			os.close() ;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
