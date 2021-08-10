package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
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
	public void download(HttpServletResponse response,
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
}
