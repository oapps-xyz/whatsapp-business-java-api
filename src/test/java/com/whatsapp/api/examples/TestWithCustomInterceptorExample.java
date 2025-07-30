package com.whatsapp.api.examples;

import com.whatsapp.api.TestConstants;
import com.whatsapp.api.WhatsappApiFactory;
import com.whatsapp.api.domain.messages.Language;
import com.whatsapp.api.domain.messages.Message.MessageBuilder;
import com.whatsapp.api.domain.messages.TemplateMessage;
import com.whatsapp.api.domain.templates.type.Category;
import com.whatsapp.api.domain.templates.type.LanguageType;
import com.whatsapp.api.impl.WhatsappBusinessCloudApi;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static com.whatsapp.api.TestConstants.PHONE_NUMBER_1;
import static com.whatsapp.api.TestConstants.PHONE_NUMBER_ID;

/**
 * Example showing how to use a custom interceptor to log which endpoints are being called
 */
public class TestWithCustomInterceptorExample {
    public static void main(String[] args) {
        // Create a custom interceptor to log the URLs
        Interceptor urlLoggingInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String url = request.url().toString();
                
                System.out.println("🌐 [HTTP Request] URL: " + url);
                
                if (url.contains("/marketing_messages")) {
                    System.out.println("✅ CONFIRMED: Using /marketing_messages endpoint");
                } else if (url.contains("/messages")) {
                    System.out.println("✅ CONFIRMED: Using /messages endpoint");
                }
                
                return chain.proceed(request);
            }
        };

        // Note: You would need to modify the library to accept custom interceptors
        // For now, this is just an example of what you could do
        
        WhatsappApiFactory factory = WhatsappApiFactory.newInstance(TestConstants.TOKEN);
        WhatsappBusinessCloudApi whatsappBusinessCloudApi = factory.newBusinessCloudApi();

        // Test marketing template message
        System.out.println("=== Testing Marketing Template Message ===");
        var marketingTemplate = new TemplateMessage()
                .setLanguage(new Language(LanguageType.EN_US))
                .setName("marketing_template")
                .setCategory(Category.MARKETING);

        var marketingMessage = MessageBuilder.builder()
                .setTo(PHONE_NUMBER_1)
                .buildTemplateMessage(marketingTemplate);

        try {
            whatsappBusinessCloudApi.sendMessage(PHONE_NUMBER_ID, marketingMessage);
            System.out.println("✅ Marketing template message sent successfully");
        } catch (Exception e) {
            System.out.println("❌ Marketing template message failed: " + e.getMessage());
        }
    }
} 