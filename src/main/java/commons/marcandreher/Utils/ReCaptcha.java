package commons.marcandreher.Utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import commons.marcandreher.Commons.GetRequest;


public class ReCaptcha {

    protected String API_URL = "https://www.google.com/recaptcha/api/siteverify";
    private String secret;
    private Boolean isDisabled = false;

    public ReCaptcha(String secret) {
        this.secret = secret;
    }

    public void disable() {
        isDisabled = true;
    }

    public void enable() {
        isDisabled = false;
    }


   public boolean handleCaptcha(String responseG, String userAgent) throws IOException, InterruptedException {

        if (isDisabled) {
            return true;
        }

        String apiUrl = "https://www.google.com/recaptcha/api/siteverify";
        String queryParams = "secret=" + secret + "&response=" + responseG;
        String requestUrl = apiUrl + "?" + queryParams;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new GetRequest(requestUrl).send(userAgent));

            if (!jsonNode.has("success") || !jsonNode.get("success").asBoolean()) {
                return false;
            }
            return true;
        } finally {
        }
    }

}
