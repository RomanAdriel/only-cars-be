package grupo6.demo.service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.s3.model.ObjectMetadata;
import grupo6.demo.service.api.AWSS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Service
public class AWSS3ServiceImpl implements AWSS3Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(AWSS3ServiceImpl.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Override
    public String uploadFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String newFileName = System.currentTimeMillis() + "_" + originalFileName;

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            metadata.setContentDisposition("attachment; filename=\"" + originalFileName + "\"");

            PutObjectRequest request = new PutObjectRequest(bucketName, newFileName, file.getInputStream(), metadata);
            amazonS3.putObject(request);

            return getObjectUrl(newFileName);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<String> getObjectsFromS3() {
        ListObjectsV2Result result = amazonS3.listObjectsV2(bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        List<String> list = objects.stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());
        return list;
    }

    @Override
    public String getObjectUrl(String key) {
        // Generate the "Object URL" of the file in S3
        URL objectUrl = amazonS3.getUrl(bucketName, key);
        return objectUrl.toString();
    }
}