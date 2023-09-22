package grupo6.demo.service.api;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {

    String uploadFile(MultipartFile file);

    List<String> getObjectsFromS3();

    String getObjectUrl(String key);
}