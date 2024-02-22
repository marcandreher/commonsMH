package commons.marcandreher.Engine;

public class Meta {

    public static String siteTitle = "CommonsMH";
    public static String domain = "http://localhost/";

    public String robots = "index, follow";
    public String openGraphType = "website";
    public String twitterCardType = "summary";

    public String canonial;
    public String url;

    public String image;
    public String openGraphImage;

    public String description;
    public String openGraphDescription;
    public String twitterDescription;

    public String title;
    public String openGraphTitle;
    public String twitterTitle;

    public String[] keywords;

    public Meta(String image, String title, String description, String url, String... keywords) {
        this.image = image;
        this.openGraphImage = image;
        this.description = description;
        this.openGraphDescription = description;
        this.twitterDescription = description;
        this.title = title;
        this.openGraphTitle = title;
        this.twitterTitle = title;
        this.url = url;
        this.canonial = url;
        this.keywords = keywords;
    }

    public void setImage(String image) {
        this.image = image;
        this.openGraphImage = image;
    }

    public void setKeywords(String... keywords) {
        this.keywords = keywords;
    }

    public void setTitle(String title) {
        title = title + " | " + siteTitle;
        this.title = title;
        this.twitterTitle = title;
        this.openGraphTitle = title;
    }

    public void setDescription(String description) {
        this.description = description;
        this.twitterDescription = description;
        this.openGraphDescription = description;
    }

    public void setUrl(String url) {
        url = domain + url;
        this.url = url;
        this.canonial = url;
    }

}
