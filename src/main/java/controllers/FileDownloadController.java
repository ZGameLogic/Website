package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public void download(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("fileName") String fileName) throws IOException {
		 try {
		      // get your file as InputStream
		      InputStream is = new FileInputStream(new File(downloadFolder + "\\" + fileName));
		      // copy it to response's OutputStream
		      org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
		      response.flushBuffer();
		    } catch (IOException ex) {
		    	System.out.println("bad");
		    }
	}
	
	/*
	@GetMapping("/downloads/{fileName:.+}")
	public void download(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("fileName") String fileName) throws IOException {
		// get absolute path of the application
		ServletContext context = request.getServletContext();

		// construct the complete absolute path of the file
		String fullPath = downloadFolder + "\\" + fileName;
		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		// get MIME type of the file
		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}

		// set content attributes for the response
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// set headers for the response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// get output stream of the response
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outStream.close();
	}
	*/
}
