package com.practise.Smart_Arena.service.smsService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practise.Smart_Arena.exception.AllExceptions;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component
@EnableScheduling
public class SMSTokenSchedular {

    public String SMS_TOKEN;

    @Value("${ESKIZ_EMAIL}")
    private String ESKIZ_EMAIL;

    @Value("${ESKIZ_PASSWORD}")
    private String ESKIZ_PASSWORD;

    final private Logger logger = LogManager.getLogger(SMSTokenSchedular.class);

    //    @Scheduled(cron = "50 17 12 * * ?")
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void smsTokenGenerateTask() throws IOException {
        if (SMS_TOKEN == null || isJwtExpired(SMS_TOKEN)) {
            Response response = null;
            try {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("email", ESKIZ_EMAIL)
                        .addFormDataPart("password", ESKIZ_PASSWORD)
                        .build();
                Request request = new Request.Builder()
                        .url("https://notify.eskiz.uz/api/auth/login")
                        .method("POST", body)
                        .build();
                response = client.newCall(request).execute();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            String jsonResponse = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonResponse);
            SMS_TOKEN = jsonObject.getJSONObject("data").getString("token");
            logger.info("Yangi SMSToken generatsiya qilindi: {}", SMS_TOKEN);
        }
        logger.info("SMSTOKEN yaroqli:{}", SMS_TOKEN);
    }

    public boolean isJwtExpired(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new AllExceptions.IllegalArgumentException("Invalid JWT token format");
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> claims = objectMapper.readValue(payload, new TypeReference<>() {});

            long exp = ((Number) claims.get("exp")).longValue();

            return exp * 1000 < System.currentTimeMillis() + 1000 * 60 * 60 * 24;

        } catch (Exception e) {
            throw new RuntimeException("Error while checking JWT expiration", e);
        }
    }
}
