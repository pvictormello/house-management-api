package house.management.api.util;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ImageCompressor {
    
    private static final float COMPRESSION_QUALITY = 0.7f; 
    private static final int MAX_WIDTH = 1920;
    private static final int MAX_HEIGHT = 1080;

    private ImageCompressor(){}

    public static byte[] compressImage(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
        BufferedImage image = ImageIO.read(inputStream);

        if (image == null) {
            throw new IOException("Unsupported image format");
        }

        image = resizeImage(image);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        if ("image/jpeg".equalsIgnoreCase(contentType)) {
            compressJpeg(image, outputStream);
        } else if ("image/png".equalsIgnoreCase(contentType)) {
            compressPng(image, outputStream);
        } else {
            throw new IOException("Unsupported image type: " + contentType);
        }

        return outputStream.toByteArray();
    }

    private static void compressJpeg(BufferedImage image, ByteArrayOutputStream outputStream) throws IOException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) throw new IOException("No JPEG writer found");

        ImageWriter writer = writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(COMPRESSION_QUALITY);

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream)) {
            writer.setOutput(ios);
            writer.write(null, new IIOImage(image, null, null), param);
        } finally {
            writer.dispose();
        }
    }

    private static void compressPng(BufferedImage image, ByteArrayOutputStream outputStream) throws IOException {
        ImageIO.write(image, "png", outputStream);
    }

    private static BufferedImage resizeImage(BufferedImage originalImage) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        
        if (originalWidth <= MAX_WIDTH && originalHeight <= MAX_HEIGHT) {
            return originalImage;
        }

        double aspectRatio = (double) originalWidth / originalHeight;
        int newWidth;
        int newHeight;

        if (originalWidth > originalHeight) {
            newWidth = MAX_WIDTH;
            newHeight = (int) (newWidth / aspectRatio);
        } else {
            newHeight = MAX_HEIGHT;
            newWidth = (int) (newHeight * aspectRatio);
        }

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.createGraphics().drawImage(
            originalImage.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH), 
            0, 0, null);
        
        return resizedImage;
    }
}
