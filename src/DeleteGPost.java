import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by kyujin on 2/1/16.
 */
public class DeleteGPost {
    public DeleteGPost(ArrayList<String> postgID, ArrayList<String> pageNo, String id, String pw) {
        String UA = "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";

        Map<String,String> cookies = new LoginDC(id, pw).cookies; // 로그인 세션 정보가 담긴 쿠키.

        for (int i = 0; i < postgID.size(); i++) {
            try {
                Connection.Response res = Jsoup.connect("http://m.dcinside.com/_option_write.php")
                        .data("mode", "gallog_ListDel", "user_id", id, "g_type", "G", "s_type", "", "page", pageNo.get(i), "Gno", postgID.get(i))
                                // 지울 정보 POST 하는 부분. 갤 ID / 원글 고유번호 / 자신의 고유번호 / 지울 요소가 글이라는 모드 설정 및 자잘한 요소 보냄.
                        .method(Connection.Method.POST)
                        .userAgent(UA)
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Accept-Encoding", "gzip, deflate, br")
                        .header("Accept-Language", "en-US,en;q=0.5")
                        .header("Host", "m.dcinside.com")
                        .referrer(URLEncoder.encode("http://m.dcinside.com/gallog/list.php?g_id=" + id +"&g_type=G", "utf-8"))
                        .cookies(cookies)
                        .execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
