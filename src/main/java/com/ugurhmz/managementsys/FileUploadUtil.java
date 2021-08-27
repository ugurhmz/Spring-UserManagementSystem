package com.ugurhmz.managementsys;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	
		// Save Photos
		public static void saveFile(String uploadDirectory, String fileName, MultipartFile multipartFile) throws IOException {
			
			Path uploadPath = Paths.get(uploadDirectory);
			
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			
			try(InputStream inputStream = multipartFile.getInputStream()) {
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				
			} catch(IOException ex) {
				throw new IOException("Could not save file : " + fileName, ex);
			}
		
		}
		
		// Clean old user photos
		public static void  cleanDirectory(String directory) {
			Path directoryPath = Paths.get(directory);
			
			
			try {
				Files.list(directoryPath).forEach(file ->{
					if(!Files.isDirectory(file)) {
							try {
								Files.delete(file);
							} catch(IOException ex) {
								System.out.println("Could not delete file: "+ file);
							}
					}
					
					
				});
				
			} catch(IOException ex) {
				System.out.println("Could not list directory : "+ directoryPath);
			}
			
		}
		
		
	
}
