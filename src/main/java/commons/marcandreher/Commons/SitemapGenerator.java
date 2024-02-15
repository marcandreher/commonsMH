package commons.marcandreher.Commons;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.GoogleCodeSitemapUrl.Options;

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

    public void addSitemapUrl(double priority, String url) {
        try {
            Options options = new Options(new URL(domain + url), "xml");
            options.priority(priority);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            options.lastMod(cal.getTime());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        wsg.write();
    }
    
}
