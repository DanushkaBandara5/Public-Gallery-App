package lk.ijse.dep10.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
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
        System.out.println(imgDirPath);
        File ImageDir = new File(imgDirPath);
        String[] imageFiles = ImageDir.list();
        System.out.println(imageFiles);
        for (String imageFileName : imageFiles) {
            UriComponentsBuilder uriComponentsBuilder = uriBuilder.cloneBuilder();
            String url =uriComponentsBuilder.pathSegment("images",imageFileName).toUriString();
            imageUrlList.add(url);
        }
        return  imageUrlList;


    }
}
