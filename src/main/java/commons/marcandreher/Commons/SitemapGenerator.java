package commons.marcandreher.Commons;

import java.io.File;
import java.net.MalformedURLException;

import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

public class SitemapGenerator {

    private WebSitemapGenerator wsg = null;
    private String domain;

    public SitemapGenerator(String domain, String path) throws MalformedURLException {
        wsg = new WebSitemapGenerator(domain, new File(path));
        this.domain = domain;
    }

    public void addUrl(String url) {
        wsg.addUrl(domain + url);
    }

    public void addSitemapUrl(WebSitemapUrl url) {
        wsg.addUrl(url);
    }

    public void write() {
        wsg.write();
    }
    
}
