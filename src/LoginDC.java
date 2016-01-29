import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

/**
 * Created by kyujin on 1/29/16.
 */
public class LoginDC {
    public Map<String, String> cookies;
    public LoginDC(String id, String pw) {
        String UA = "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";
        // 디씨 모바일은 UA로 접속 거르니까 모바일 UA 추가.

        try {
            Connection.Response res = Jsoup.connect("https://dcid.dcinside.com/join/mobile_login_ok.php")
                    .data("user_id", id, "user_pw", pw, "mode", "", id, "")
                    .method(Connection.Method.POST) // 당연하게도 POST 이용한 로그인.
                    .userAgent(UA)
                    .referrer("http://m.dcinside.com/login.php?r_url=%2Fview.php%3Fid%3Ddoosanbears_new%26no%3D5497196")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Host", "dcid.dcinside.com")
                    .timeout(10000)
                    .execute(); // 로그인 POST 부분. 딱히 건드려야 할 보안 연결은 없음.

                    // 디씨 모바일 접속 및 POST 시에는 헤더들을 많이 요구함(리퍼러 필수!). 하나라도 빠질 시 POST 에러 뜨므로 모두 추가.


           cookies = res.cookies(); // PHP SESSION ID 담긴 쿠키 전달해주기.
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
