package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore { // 멀티파트 파일을 서버에 저장하는 역할

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
    	return fileDir + filename;
    }
    
    // 여러개 파일 저장
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IllegalStateException, IOException {
    	List<UploadFile> storeFileResult = new ArrayList<>();
    	for(MultipartFile multipartFile : multipartFiles) {
    		if(!multipartFile.isEmpty()) {
    			storeFileResult.add(storeFile(multipartFile));
    		}
    	}
    	return storeFileResult;
    }
    
    
    // 파일 저장 (multipartFile을 가지고 파일을 저장 후 UploadFile로 바꿔줌)
    public UploadFile storeFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
    	if(multipartFile.isEmpty()) {
    		return null;
    	}
    	
    	String originalFilename = multipartFile.getOriginalFilename(); // image.png
    	
    	// 서버에 저장하는 파일명
    	String storeFileName = createStoreFileName(originalFilename);
    	multipartFile.transferTo(new File(getFullPath(storeFileName)));
    	return new UploadFile(originalFilename, storeFileName);
    }
    
    private String extractExt(String originalFilename) {
    	// 확장자 꺼내오기(.png)
    	int pos = originalFilename.lastIndexOf("."); // 위치 가져옴
    	return originalFilename.substring(pos + 1); // 확장자
    }
    
    private String createStoreFileName(String originalFilename) {
    	// 서버에 저장하는 파일명
    	String ext = extractExt(originalFilename); // 확장자
    	String uuid = UUID.randomUUID().toString(); //"qwe-qwe-123-qwe-qqwe";
    	return uuid + "." + ext; // uuid에 확장자 붙이기
    	//"qwe-qwe-123-qwe-qqwe.png";
    }


}
