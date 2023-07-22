package lk.ijse.dep10.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/images")
public class ImageHttpController {
    @Autowired
    private ServletContext servletContext;
    @GetMapping
    public List<String> getMapping(UriComponentsBuilder uriBuilder){
        List<String> imageUrlList =new ArrayList<>();
        String imgDirPath = servletContext.getRealPath("/images");
        File ImageDir = new File(imgDirPath);
        String[] imageFiles = ImageDir.list();
        for (String imageFileName : imageFiles) {
            UriComponentsBuilder uriComponentsBuilder = uriBuilder.cloneBuilder();
            String url =uriComponentsBuilder.pathSegment("images",imageFileName).toUriString();
            imageUrlList.add(url);
        }
        return  imageUrlList;


    }
    @GetMapping("/download")
    public String downloadImages(@RequestParam(name = "q") String imageUrl) {
        try {
            imageUrl="images/"+imageUrl;
            System.out.println(imageUrl);
            String realPath = servletContext.getRealPath(imageUrl);
            System.out.println(realPath);
            File file = new File(realPath);
            System.out.println(file.exists());
            FileInputStream fis = new FileInputStream(file);
            byte[]bytes=new byte[(int)file.length()];
            fis.read(bytes);
            String base64Data = Base64.getEncoder().encodeToString(bytes);
            return base64Data;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }
}
