package commons.marcandreher.Engine;

public class Meta {

    public static String siteTitle = "CommonsMH";
    public static String domain = "http://localhost";

    private String robots = "index, follow";
    private String openGraphType = "website";
    private String twitterCardType = "summary";


    private String url;

    private String image;

    private String description;

    private String title;


    private String[] keywords;

    public Meta(String image, String title, String description, String url, String... keywords) {
        this.image = image;
        this.description = description;
        this.title = title;
        this.url = url;
        this.keywords = keywords;
    }

    public Meta() {
        
    }


    public String getRobots() {
        return this.robots;
    }

    public void setRobots(String robots) {
        this.robots = robots;
    }

    public String getOpenGraphType() {
        return this.openGraphType;
    }

    public void setOpenGraphType(String openGraphType) {
        this.openGraphType = openGraphType;
    }

    public String getTwitterCardType() {
        return this.twitterCardType;
    }

    public void setTwitterCardType(String twitterCardType) {
        this.twitterCardType = twitterCardType;
    }

    public String getUrl() {
        return domain + this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return this.title + " | " + siteTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String... keywords) {
        this.keywords = keywords;
    }
    

}
