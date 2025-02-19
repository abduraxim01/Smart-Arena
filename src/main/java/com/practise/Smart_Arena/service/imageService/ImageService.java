package com.practise.Smart_Arena.service.imageService;

import com.practise.Smart_Arena.exception.AllExceptions;
import lombok.SneakyThrows;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${SUPABASE_URL}")
    private String SUPABASE_URL;

    @Value("${SUPABASE_API_KEY}")
    private String SUPABASE_API_KEY;

    final private String BUCKET_NAME = "Smart  Arena images";

    final private OkHttpClient client = new OkHttpClient();

    public List<String> saveImages(List<MultipartFile> images) {
        return images.stream()
                .map(this::uploadImage)
                .toList();
    }

    public String uploadImage(MultipartFile image) {
        String filePath = "polya_image/" + UUID.randomUUID() + "_" + System.currentTimeMillis() + ".png";
        String imageUrl = SUPABASE_URL + "/storage/v1/object/" + BUCKET_NAME + "/" + filePath;
        try {
            byte[] fileBytes = image.getBytes();
            RequestBody requestBody = RequestBody.create(
                    fileBytes, MediaType.parse("image/*") // Change to match your image type
            );
            Request request = new Request.Builder()
                    .url(imageUrl)
                    .header("Authorization", "Bearer " + SUPABASE_API_KEY)
                    .header("Content-Type", "image/*") // Change if uploading other image types
                    .put(requestBody)
                    .build();
            if (client.newCall(request).execute().isSuccessful()) return imageUrl;
            else throw new AllExceptions.HttpResponseException("Failed to upload image");
        } catch (IOException e) {
            throw new AllExceptions.IOException("Problem with image");
        }
    }

}