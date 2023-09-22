package grupo6.demo.controller;

import java.util.List;

import grupo6.demo.service.api.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/s3")
public class UploadFileController {

    @Autowired
    private AWSS3Service awss3Service;

    @PostMapping(value = "/file")
    public ResponseEntity<String> uploadFile(@RequestPart(value="file") MultipartFile file) {
        awss3Service.uploadFile(file);
        String response = "The file "+file.getOriginalFilename()+" was uploaded successfully to S3";
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/file")
    public ResponseEntity<List<String>> listFiles() {
        return new ResponseEntity<List<String>>(awss3Service.getObjectsFromS3(), HttpStatus.OK);
    }


}
