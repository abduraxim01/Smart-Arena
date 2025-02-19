package com.practise.Smart_Arena.service.smsService;

import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

@Service
public class SendSMSService {

    final private Logger logger = LogManager.getLogger(SendSMSService.class);

    @Autowired
    private SMSTokenSchedular smsTokenSchedular;

    public String sendSMSForAuth(String phoneNumber) {
        Random random = new Random();
        String otp = String.format("%06d", random.nextInt(1000000)); // Ensures 6 digits
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("mobile_phone", "998" + phoneNumber.substring(3))
                    .addFormDataPart("message", "This is test from Eskiz")
                    .addFormDataPart("from", "4546")
                    .addFormDataPart("callback_url", "https://abduraxim.uz")
                    .build();
            Request request = new Request.Builder()
                    .url("https://notify.eskiz.uz/api/message/sms/send")
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + smsTokenSchedular.SMS_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            logger.info(response.body().string());
        } catch (IOException exception) {
            logger.error("SMS jo'natilmadi", exception);
        }
        return otp;
    }
}
