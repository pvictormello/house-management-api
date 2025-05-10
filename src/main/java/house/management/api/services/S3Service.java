package house.management.api.services;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import house.management.api.util.ImageCompressor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
    private final S3Client s3Client;
    private final String bucketName;
    private final String bucketUrl;

    public S3Service(@Value("${aws.s3.bucket-name}") String bucketName,
            @Value("${aws.accessKeyId}") String accessKey,
            @Value("${aws.secretKey}") String secretKey,
            @Value("${aws.region}") String region,
            @Value("${aws.s3.bucket-url}") String bucketUrl) {
        this.bucketName = bucketName;
        this.bucketUrl = bucketUrl;
        s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    public String uploadFile(MultipartFile file, String prefix) throws IOException {
        if (!Objects.requireNonNullElse(file.getContentType(), "default").startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        byte[] imageBytes;
        try {
            imageBytes = ImageCompressor.compressImage(file);
        } catch (IOException e) {
            imageBytes = file.getBytes();
        }

        String originalFilename = Objects.requireNonNullElse(file.getOriginalFilename(), "default");
        String fileExtension = originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String fileName = System.currentTimeMillis() + "_" + prefix + fileExtension;
    
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(imageBytes));

        return fileName;
    }

    public String getFullImageUrl(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        return bucketUrl + "/" + fileName;
    }

    @Cacheable("images")
    public byte[] getImage(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        return s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes()).asByteArray();
    }
}