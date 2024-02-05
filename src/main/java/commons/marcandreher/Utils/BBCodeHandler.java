package commons.marcandreher.Utils;

public class BBCodeHandler {

    // TODO: Highly WIP:
    public static String bbCodeToHtml(String bbCode) {
        if(bbCode == null) {
            return null;
        }
        bbCode = bbCode.replaceAll("\\[b\\](.*?)\\[/b\\]", "<b>$1</b>");
        bbCode = bbCode.replaceAll("\\[i\\](.*?)\\[/i\\]", "<em>$1</em>");
        bbCode = bbCode.replaceAll("\\[u\\](.*?)\\[/u\\]", "<u>$1</u>");
        bbCode = bbCode.replaceAll("\\[url=(.*?)\\](.*?)\\[/url\\]", "<a href=\"$1\">$2</a>");
        bbCode = bbCode.replaceAll("\\[img=(.*?)\\]", "<img style=\"height:25%;width:25%;\" src=\"$1\" alt=\"\">");
        bbCode = bbCode.replaceAll("\\[code\\](.*?)\\[/code\\]", "<code>$1</code>");
        bbCode = bbCode.replaceAll("\\[quote\\](.*?)\\[/quote\\]", "<blockquote>$1</blockquote>");
        bbCode = bbCode.replaceAll("\\[br\\]", "<br/>");
        return bbCode;
    }

    public static String bbCodeToPlainText(String bbCode) {
        String plainText = bbCode.replaceAll("\\[.*?\\]", "");
        plainText = plainText.replaceAll("<br>", "").trim();
        return plainText;
    }
    
}
