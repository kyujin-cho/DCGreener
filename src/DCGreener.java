/**
 * Created by thy2134 on 1/26/16.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class DCGreener {

    public static void main(String[] args) {
        String UA = "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";

        Scanner s = new Scanner(System.in);
        System.out.println("ID? ");
        String id = s.nextLine(); // 키보드로 입력받는 본인의 ID. 키보드로 입력받지 않고 싶으면 s.nextLine() 부분을 "자신의 ID" 로 변경.
        System.out.println("PW? ");
        String pw = s.nextLine(); // 키보드로 입력받는 본인의 PW. 키보드로 입력받지 않고 싶으면 s.nextLine() 부분을 "자신의 암호" 로 변경.

        LoginDC login = new LoginDC(id, pw);

        GetPost post = new GetPost(id, login.cookies);
        GetReply reply = new GetReply(id, login.cookies); // 갤로그에서 댓 달린 글 고유번호 / 갤 ID 불러오기.

        ArrayList<String> gallID = post.gallIDs;
        ArrayList<String> postID = post.postIDs;
        ArrayList<String> postgID = post.postgIDs;

        ArrayList<String> rgallID = reply.rgallIDs; // 갤 ID들 담긴 리스트.
        ArrayList<String> rpostID = reply.rpostIDs; // 댓 달린 글 고유번호 담긴 리스트.
        ArrayList<String> rpostgID = reply.rpostgIDs;

        String userno = reply.user_no; // 회원 고유번호(인듯?).

        if (!post.isNone) {
            new DeletePost(postID, gallID, id, pw, userno);
            new DeleteGPost(postgID, postID, gallID, new GetGPost(id, login.cookies).cIDs, id, pw);
        }
        if (!reply.isNone) {
            DeleteReply dr = new DeleteReply(rpostID, rgallID, id, pw, userno); // 지우기 실행!
            new DeleteGReply(rpostgID, rpostID, rgallID, dr.replyIDs, new GetGReply(id, login.cookies).rcIDs, id, pw);
        }

        /*try {
            Document doc = Jsoup.connect("http://gallog.dcinside.com/inc/_mainGallog.php?page=1&gid=" + id)
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .referrer("http://gallog.dcinside.com/inc/_mainGallog.php?gid=" + id)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36")
                    .cookies(login.cookies)
                    .get();
            System.out.println(doc.html());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
