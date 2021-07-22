package com.amsdams.filevalidation;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FileService {

	public List<File> getFiles(String location) {
        File file = new File(location);
        File[] files = file.listFiles();
        return Arrays.asList(files);
        
    }
	
	public File getFile(String location) {
        File file = new File(location);
        return file;
        
    }
}
