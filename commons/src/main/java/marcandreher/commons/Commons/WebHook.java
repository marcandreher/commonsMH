package marcandreher.commons.Commons;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;

public class WebHook {

    private WebhookClient client;

    private Object sendable;

    public WebHook(String url) {
        client = WebhookClient.withUrl(url);
    }

    public void setWebHookEmbed(WebhookEmbed embed) {
        sendable = embed;
    }

    public void setWebHookMessage(String message) {
        sendable = message;
    }


    public void send() {
        if(sendable instanceof WebhookEmbed) {
            client.send((WebhookEmbed)sendable);
        }else if(sendable instanceof String){
            client.send((String)sendable);
        }
    }

    public void close() {
        client.close();
    }


    
}
