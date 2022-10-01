package com.example.restful.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class MainController {

    String file = "src/main/resources/CV.jpg";

    @RequestMapping("/Weather")
    public String getWeather(@RequestParam String Location) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://weatherapi-com.p.rapidapi.com/current.json?q=q%3D" + Location))
                .header("X-RapidAPI-Key", "862eec23ddmsh5dce13392fe5f60p16c72djsn3cfcdd8fa329")
                .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    @RequestMapping(value = "/CV", method = RequestMethod.GET)
    public void getImageAsByteArray(HttpServletResponse response) throws IOException {
        File f = new File(file);
        if (f.exists() && !f.isDirectory()) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            BufferedImage img = ImageIO.read(new File(file));
            ImageIO.write(img, file.substring(file.lastIndexOf('.') + 1), os);
            InputStream in = new ByteArrayInputStream(os.toByteArray());
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            IOUtils.copy(in, response.getOutputStream());
        }
    }

    @RequestMapping("/TimeZone")
    public String getTimeZone(@RequestParam String Location) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://weatherapi-com.p.rapidapi.com/timezone.json?q=q%3D" + Location))
                .header("X-RapidAPI-Key", "862eec23ddmsh5dce13392fe5f60p16c72djsn3cfcdd8fa329")
                .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
