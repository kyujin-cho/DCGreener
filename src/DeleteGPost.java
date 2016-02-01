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
    public DeleteGPost(ArrayList<String> postgID, ArrayList<String> postID, ArrayList<String> gallID, ArrayList<String> cID, String id, String pw) {
        String UA = "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";

        Map<String,String> cookies = new LoginDC(id, pw).cookies; // 로그인 세션 정보가 담긴 쿠키.
        System.out.println("======GALLOG POST REMOVAL PROCESS START======");

        for (int i = 0; i < postgID.size(); i++) {
            try {
                Connection.Response res = Jsoup.connect("http://gallog.dcinside.com/inc/_deleteArticle.php")
                        .data("rb", "", "dTp", "1", "gid", id, "cid", cID.get(i), "page", "",
                                "pno", postID.get(i), "no", postID.get(i), "logNo", postgID.get(i),
                                "id", gallID.get(i), "nate", "", "con_key", "aaaaaaaaaaaaaaaaaaaa")
                                // 지울 정보 POST 하는 부분. 갤 ID / 원글 고유번호 / 자신의 고유번호 / 지울 요소가 글이라는 모드 설정 및 자잘한 요소 보냄.
                        .method(Connection.Method.POST)
                        .userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5376e Safari/8536.25")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Accept-Encoding", "gzip, deflate, br")
                        .header("Accept-Language", "en-US,en;q=0.5")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .header("Accept", "*/*")
                        .referrer(URLEncoder.encode("http://gallog.dcinside.com", "utf-8"))
                        .cookies(cookies)
                        .execute();

                if (res.body().contains("잘못된 접속입니다.")) System.out.println("잘못된 접속");
                else System.out.println("gid : " + id + " cid : " + cID.get(i) + " pno : " + postID.get(i) + "\n"
                                + " no : " + postID.get(i) + " logNo : " + postgID.get(i) + " id : " + gallID.get(i));
                System.out.println("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("======GALLOG POST REMOVAL PROCESS END======");

    }
}
