package lk.ijse.dep10.app.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/images")
public class ImageHttpController {
    @GetMapping
    public String getMapping(){
        return "this is a image gallery";
    }
}
