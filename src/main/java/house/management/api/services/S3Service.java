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

    public S3Service(@Value("${AWS_S3_BUCKET_NAME}") String bucketName,
            @Value("${AWS_ACCESS_KEY_ID}") String accessKey,
            @Value("${AWS_SECRET_KEY}") String secretKey,
            @Value("${AWS_REGION}") String region) {
        this.bucketName = bucketName;
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

    @Cacheable("images")
    public byte[] getImage(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        return s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes()).asByteArray();
    }

}