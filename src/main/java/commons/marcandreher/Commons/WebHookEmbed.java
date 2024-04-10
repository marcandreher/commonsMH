package commons.marcandreher.Commons;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedAuthor;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedField;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedFooter;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedTitle;

public class WebHookEmbed {

    private OffsetDateTime timestamp = null;
    private Integer color = null;
    private String description = null;
    private String thumbnailUrl = null;
    private String imageUrl = null;
    private EmbedFooter embedFooter = null;
    private EmbedTitle embedTitle = null;
    private EmbedAuthor embedAuthor = null;
    private List<EmbedField> embedFields = new ArrayList<>();

    public OffsetDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getColor() {
        return this.color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public EmbedFooter getEmbedFooter() {
        return this.embedFooter;
    }

    public void setEmbedFooter(@NotNull String text, @Nullable String icon) {
        this.embedFooter = new EmbedFooter(text, icon);
    }

    public EmbedTitle getEmbedTitle() {
        return this.embedTitle;
    }

    public void setEmbedTitle(@NotNull String text, @Nullable String url) {
        this.embedTitle = new EmbedTitle(text, url);
    }

    public EmbedAuthor getEmbedAuthor() {
        return this.embedAuthor;
    }

    public void setEmbedAuthor(@NotNull String name, @Nullable String iconUrl, @Nullable String url) {
        this.embedAuthor = new EmbedAuthor(name, iconUrl, url);
    }

    public List<EmbedField> getEmbedFields() {
        return this.embedFields;
    }

    public void setEmbedFields(EmbedField... embedFields) {
        this.embedFields = Arrays.asList(embedFields);
    }


    public WebhookEmbed getEmbed() {
        return new WebhookEmbed(timestamp, color, description, thumbnailUrl, imageUrl, embedFooter, embedTitle, embedAuthor, embedFields);
    }
    
}
