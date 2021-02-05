package com.movie.service;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class Upload {
	
public String fileUplaod(MultipartHttpServletRequest mRequest) {
		
		String file_name = "";
		String uploadPath = "C:\\Users\\yyj01\\OneDrive\\문서\\GitHub\\MovieReview\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\MovieReview\\resources\\upload\\";
		
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DATE);
		
		Iterator<String> iterator = mRequest.getFileNames();
		
		while(iterator.hasNext()) {
			String uploadFileName = iterator.next();
			
			MultipartFile mFile = mRequest.getFile(uploadFileName);
			
			String originalFileName = mFile.getOriginalFilename();
			
			String homedir = uploadPath + year + "-" + month + "-" + day;
			
			File path1 = new File(homedir);
			if(!path1.exists()) {
				path1.mkdirs();
			}
			
			String saveFileName = originalFileName;
			
			if(saveFileName != null && !saveFileName.equals("")) {
				saveFileName = System.currentTimeMillis() + "_" + saveFileName;
				
				try {
					mFile.transferTo(new File(homedir + "\\" + saveFileName));
					file_name =  year + "-" + month + "-" + day + "\\" + saveFileName;
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return file_name;
	}
}
