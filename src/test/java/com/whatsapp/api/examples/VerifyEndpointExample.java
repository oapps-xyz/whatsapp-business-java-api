package com.whatsapp.api.examples;

import com.whatsapp.api.TestConstants;
import com.whatsapp.api.WhatsappApiFactory;
import com.whatsapp.api.domain.messages.Language;
import com.whatsapp.api.domain.messages.Message.MessageBuilder;
import com.whatsapp.api.domain.messages.TemplateMessage;
import com.whatsapp.api.domain.templates.type.Category;
import com.whatsapp.api.domain.templates.type.LanguageType;
import com.whatsapp.api.impl.WhatsappBusinessCloudApi;

import static com.whatsapp.api.TestConstants.PHONE_NUMBER_1;
import static com.whatsapp.api.TestConstants.PHONE_NUMBER_ID;

/**
 * Example showing how to verify which endpoint will be used for a message
 */
public class VerifyEndpointExample {
    public static void main(String[] args) {
        WhatsappApiFactory factory = WhatsappApiFactory.newInstance(TestConstants.TOKEN);
        WhatsappBusinessCloudApi whatsappApi = factory.newBusinessCloudApi();

        // Create regular template message
        var regularTemplate = new TemplateMessage()
                .setLanguage(new Language(LanguageType.EN_US))
                .setName("regular_template");

        var regularMessage = MessageBuilder.builder()
                .setTo(PHONE_NUMBER_1)
                .buildTemplateMessage(regularTemplate);

        // Create marketing template message
        var marketingTemplate = new TemplateMessage()
                .setLanguage(new Language(LanguageType.EN_US))
                .setName("marketing_template")
                .setCategory(Category.MARKETING);

        var marketingMessage = MessageBuilder.builder()
                .setTo(PHONE_NUMBER_1)
                .buildTemplateMessage(marketingTemplate);

        // Verify which endpoint will be used
        System.out.println("=== Endpoint Verification ===");
        System.out.println("Regular template message will use /messages endpoint: " + 
                !whatsappApi.isMarketingMessage(regularMessage));
        System.out.println("Marketing template message will use /marketing_messages endpoint: " + 
                whatsappApi.isMarketingMessage(marketingMessage));

        // Send the messages
        System.out.println("\n=== Sending Messages ===");
        try {
            whatsappApi.sendMessage(PHONE_NUMBER_ID, regularMessage);
            System.out.println("✅ Regular template message sent successfully");
        } catch (Exception e) {
            System.out.println("❌ Regular template message failed: " + e.getMessage());
        }

        try {
            whatsappApi.sendMessage(PHONE_NUMBER_ID, marketingMessage);
            System.out.println("✅ Marketing template message sent successfully");
        } catch (Exception e) {
            System.out.println("❌ Marketing template message failed: " + e.getMessage());
        }
    }
} 