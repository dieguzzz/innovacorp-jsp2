package com.innovacorp.correos;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

public class CorreoAPI {
    private static final String API_KEY = "00fd9ea3bb9cfb5e9bdde51d8e44a928";
    private static final String SECRET_KEY = "0699633e50dd88ed6b7a981eb56bf5f3";

    public static boolean enviarCorreo(String destinatario, String asunto, String mensaje) {
        MailjetClient client = new MailjetClient(
                API_KEY,
                SECRET_KEY

        );

        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "fertruckx3113@gmail.com")
                                        .put("Name", "InnovaCorp"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", destinatario)
                                                .put("Name", "Recipient Name")))
                                .put(Emailv31.Message.SUBJECT, asunto)
                                .put(Emailv31.Message.TEXTPART, mensaje)
                                .put(Emailv31.Message.HTMLPART, "<h3>" + mensaje + "</h3>")
                                .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));

        try {
            MailjetResponse response = client.post(request);
            return response.getStatus() == 200;
        } catch (MailjetException e) {
            e.printStackTrace();
            return false;
        }
    }
}
