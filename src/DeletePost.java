import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by thy2134 on 1/31/16.
 */
public class DeletePost {
    public DeletePost(ArrayList<String> postID, ArrayList<String> gallID, String id, String pw, String user_no) {
        String UA = "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";
        // 디씨 모바일은 UA로 접속 거르니까 모바일 UA 추가.
        Map<String,String> cookies = new LoginDC(id, pw).cookies; // 로그인 세션 정보가 담긴 쿠키.

            System.out.println("======POST REMOVAL PROCESS START======");
            for (int i = 0; i < postID.size(); i++) {
                try {
                    Connection.Response res = Jsoup.connect("http://m.dcinside.com/_option_write.php")
                            .data("no", postID.get(i).replace(" ", ""), "id", gallID.get(i).replace(" ", ""), "user_no", user_no.replace(" ", ""), "page", "", "mode", "board_del")
                                    // 지울 정보 POST 하는 부분. 갤 ID / 원글 고유번호 / 자신의 고유번호 / 지울 요소가 글이라는 모드 설정 및 자잘한 요소 보냄.
                            .method(Connection.Method.POST)
                            .userAgent(UA)
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .header("Accept-Encoding", "gzip, deflate, br")
                            .header("Accept-Language", "en-US,en;q=0.5")
                            .header("Host", "m.dcinside.com")
                            .referrer(URLEncoder.encode("http://m.dcinside.com/view.php?id=doosanbears_new&no=5497159", "utf-8"))
                            .cookies(cookies)
                            .execute(); // 제거 POST 부분. 딱히 건드려야 할 보안 연결은 없음.

                    // 디씨 모바일 접속 및 POST 시에는 헤더들을 많이 요구함(리퍼러 필수!). 하나라도 빠질 시 POST 에러 뜨므로 모두 추가.


                    System.out.println(res.body());
                    System.out.println(gallID.get(i) + " " + postID.get(i));
                    System.out.println("\n");
                // Response Body가 1이면 성공했다는 뜻.
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e1 ) {
                    System.out.println("Post has been already deleted.");

                }
            }

        System.out.println("======POST REMOVAL PROCESS END======");




    }
}
