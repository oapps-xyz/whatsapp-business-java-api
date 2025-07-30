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
 * Example to test which endpoint is being used for marketing messages
 */
public class TestMarketingEndpointExample {
    public static void main(String[] args) {
        WhatsappApiFactory factory = WhatsappApiFactory.newInstance(TestConstants.TOKEN);
        WhatsappBusinessCloudApi whatsappBusinessCloudApi = factory.newBusinessCloudApi();

        // Test 1: Regular template message (should go to /messages)
        System.out.println("=== Testing Regular Template Message ===");
        var regularTemplate = new TemplateMessage()
                .setLanguage(new Language(LanguageType.EN_US))
                .setName("regular_template");

        var regularMessage = MessageBuilder.builder()
                .setTo(PHONE_NUMBER_1)
                .buildTemplateMessage(regularTemplate);

        try {
            whatsappBusinessCloudApi.sendMessage(PHONE_NUMBER_ID, regularMessage);
            System.out.println("✅ Regular template message sent successfully");
        } catch (Exception e) {
            System.out.println("❌ Regular template message failed: " + e.getMessage());
        }

        // Test 2: Marketing template message (should go to /marketing_messages)
        System.out.println("\n=== Testing Marketing Template Message ===");
        var marketingTemplate = new TemplateMessage()
                .setLanguage(new Language(LanguageType.EN_US))
                .setName("marketing_template")
                .setCategory(Category.MARKETING); // This triggers the marketing endpoint

        var marketingMessage = MessageBuilder.builder()
                .setTo(PHONE_NUMBER_1)
                .buildTemplateMessage(marketingTemplate);

        try {
            whatsappBusinessCloudApi.sendMessage(PHONE_NUMBER_ID, marketingMessage);
            System.out.println("✅ Marketing template message sent successfully");
        } catch (Exception e) {
            System.out.println("❌ Marketing template message failed: " + e.getMessage());
        }

        System.out.println("\n=== Check the console output above to see which endpoint was used ===");
        System.out.println("🔍 Look for: 'Routing marketing template message to /marketing_messages endpoint'");
        System.out.println("🔍 Look for: 'Routing regular message to /messages endpoint'");
    }
} 