package commons.marcandreher.Commons;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.GoogleCodeSitemapUrl.Options;

import commons.marcandreher.Commons.Flogger.Prefix;

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
            wsg.addUrl(options.build());
        } catch (Exception e) {
            Flogger.instance.log(Prefix.ERROR, e.getMessage(), 0);
        }
    }

    public void write() {
        wsg.write();
    }
    
}
