import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kyujin on 2/1/16.
 */
public class GetGReply {
    public ArrayList<String> rcIDs = new ArrayList<>();
    public GetGReply(String id, Map<String,String> cookies) {
        String UA = "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";

        int pageno = 0;
        try {
            Document doc = Jsoup.connect("http://gallog.dcinside.com/inc/_mainGallog.php?gid=" + id)
                    .cookies(cookies)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .userAgent(UA)
                    .referrer("http://gallog.dcinside.com").get();
            String page = doc.select("div[id=IframeDivC] table[width=750] tbody tr[width=735]").first().text();
            Matcher findpage = Pattern.compile("\\/ 남긴댓글 [0-9]*").matcher(page);
            if (findpage.find()) pageno = Integer.parseInt(findpage.group(0).replace("/ 남긴댓글 ", ""));
            if (pageno%10 == 0) {
                pageno = pageno / 10;
            } else {
                pageno = pageno / 10 + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=1; i<=pageno; i++) {
            try {
                Document doc = Jsoup.connect("http://gallog.dcinside.com/inc/_mainGallog.php?gid=" + id + "&rpage=" + i)
                        .cookies(cookies)
                        .header("X-Requested-With", "XMLHttpRequest")
                        .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .userAgent(UA)
                        .referrer("http://gallog.dcinside.com").get();
                Elements link = doc.select("td[align=center] img[src=http://wstatic.dcinside.com/gallery/skin/gallog/icon_01.gif]");
                for (Element e : link) {
                    rcIDs.add(e.attr("onclick").split("&amp;")[1].replace("cid=", ""));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
