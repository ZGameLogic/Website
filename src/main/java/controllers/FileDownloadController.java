package controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FileDownloadController {

	private File downloadFolder;

	@PostConstruct
	public void initialize() {
		downloadFolder = new File("downloads");
		if (!downloadFolder.exists()) {
			downloadFolder.mkdirs();
		}
	}

	
	@GetMapping("/downloads/{fileName:.+}")
	public ResponseEntity<Resource> download(HttpServletResponse response, HttpServletRequest request, @PathVariable("fileName") String fileName) {
		
		// Load file as Resource
        Resource resource = null;
		try {
			resource = new UrlResource("file:" + downloadFolder + "//" + fileName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
        	
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .header("Contend-Description", "File Transfer")
                .header("Content-type", "application/octet-stream")
                .header("Expires", "0")
                .body(resource);
	}
	
}
