import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by kyujin on 1/29/16.
 */
//댓글 HTML 구조
/*
		<tr class="reply_line">
			<td class="user user_layer" user_id="nerdykjc" user_name="R3Mark" style="cursor: pointer;">
			<span title="R3Mark">R3Mark</span>
			<span class="right_nick">
			<img src="http://wstatic.dcinside.com/gallery/skin/gallog/g_fix.gif" title="nerdyk**(고정닉) : 갤로그로 이동합니다." style="margin-left:2px;cursor:pointer;" onclick="window.open('http://gallog.dcinside.com/nerdykjc');" height="15" border="0" width="12"></span></td>
			<td class="reply">데파이 에레라 자리에 놓겠다고 한거같은데 시발놈</td>
			<td class="retime">2015.08.03 02:30:13</td>
			<td rowspan="2" class="delete">
			<a href="javascript:del_comment('9592840','5497196','doosanbears_new','');">
			<img src="http://nstatic.dcinside.com/dgn/gallery/images/btn_close.gif" alt="삭제버튼"></a></td>
*/

public class DeleteReply {
    public DeleteReply(ArrayList<String> postID, ArrayList<String> gallID, String id, String pw, String user_no) {

        String UA = "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";
        // 디씨 모바일은 UA로 접속 거르니까 모바일 UA 추가.
        ArrayList<String> replyIDs = new ArrayList<String>(); // 댓글 고유번호를 담을 ArrayList.

        Map<String,String> cookies = new LoginDC(id, pw).cookies; // 로그인 세션 정보가 담긴 쿠키.


        for (int i = 0; i < postID.size(); i++) {
            try {

                String url = "http://m.dcinside.com/view.php?id=" + gallID.get(i) + "&no=" + postID.get(i); // 댓글 담긴 원글의 URL 만들기.
                Document doc = Jsoup.connect(url).cookies(cookies).userAgent(UA).get();  // 원글 접속.
                Elements reply = doc.select("span.inner_best"); // 댓글 정보가 담긴 HTML 태그 찾기.
                String boardid = "";

                if (doc.select("span.info a.btn.btn_gall").text().contains("갤로그가기")) { // 고닉인지 확인해 주는 부분.
                    boardid = doc.select("span.info a.btn.btn_gall").attr("href").replace("http://m.dcinside.com/gallog/home.php?g_id=", "");
                    // 원글러가 고닉일 시 고닉 ID를 넣어 주는 부분. 윾동일 시 빈 String으로 넘어감.
                }

                for (Element e : reply) {

                    if (e.select("a.id").attr("href").replace("\n", "").contains(id)) { // 글 쓴게 내가 맞나 확인하는 부분.
                        String replyID = e.select("a.btn_delete").attr("href")
                                .split(",")[0].replace("javascript:comment_del(", "").replace("\'", "");
                        replyIDs.add(replyID); // 내가 맞으면 ArrayList에 해당 댓글 고유번호 추가.

                            if (boardid.equals("")) { // 원글러가 윾동일때.
                            Connection.Response res = Jsoup.connect("http://m.dcinside.com/_option_write.php")
                                    .data("id", gallID.get(i).replace(" ", ""), "no", postID.get(i).replace(" ", ""), "iNo", replyID.replace(" ", ""), "user_no", user_no.replace(" ", ""), "board_id", boardid.replace(" ", ""), "best_chk", "", "mode", "comment_del")
                                    // 지울 정보 POST 하는 부분. 갤 ID / 원글 고유번호 / 댓글 고유번호 / 자신의 고유번호 / 윾동이니 아무것도 없는 board_no 및 자잘한 요소 보냄.
                                    .method(Connection.Method.POST)
                                    .userAgent(UA)
                                    .header("Content-Type", "application/x-www-form-urlencoded")
                                    .header("Accept-Encoding", "gzip, deflate, br")
                                    .header("Accept-Language", "en-US,en;q=0.5")
                                    .header("Host", "m.dcinside.com")
                                    .referrer(url)
                                    .cookies(cookies)
                                    .execute(); // 제거 POST 부분. 딱히 건드려야 할 보안 연결은 없음.

                                // 디씨 모바일 접속 및 POST 시에는 헤더들을 많이 요구함(리퍼러 필수!). 하나라도 빠질 시 POST 에러 뜨므로 모두 추가.


                                    System.out.println(res.body());
                                    System.out.println(gallID.get(i) + " " + postID.get(i) + " " + replyID + " " + user_no + " " + boardid);
                                    System.out.println(url);
                                    System.out.println("\n");

                                // Response Body가 1이면 성공했다는 뜻.
                        } else { // 원글러가 고닉일때.
                            Connection.Response res = Jsoup.connect("http://m.dcinside.com/_option_write.php")
                                    .data("id", gallID.get(i).replace(" ", ""), "no", postID.get(i).replace(" ", ""), "iNo", replyID.replace(" ", ""), "user_no", user_no.replace(" ", ""), "board_id", boardid.replace(" ", ""), "best_chk", "", "mode", "comment_del")
                                    // 지울 정보 POST 하는 부분. 갤 ID / 원글 고유번호 / 댓글 고유번호 / 자신의 고유번호 / 원글러의 고닉ID 및 자잘한 요소 보냄.
                                    .method(Connection.Method.POST)
                                    .userAgent(UA)
                                    .header("Content-Type", "application/x-www-form-urlencoded")
                                    .header("Accept-Encoding", "gzip, deflate, br")
                                    .header("Accept-Language", "en-US,en;q=0.5")
                                    .header("Host", "m.dcinside.com")
                                    .referrer(url)
                                    .cookies(cookies)
                                    .execute(); // 제거 POST 부분. 딱히 건드려야 할 보안 연결은 없음.

                                // 디씨 모바일 접속 및 POST 시에는 헤더들을 많이 요구함(리퍼러 필수!). 하나라도 빠질 시 POST 에러 뜨므로 모두 추가.


                                    System.out.println(res.body());
                                    System.out.println(gallID.get(i) + " " + postID.get(i) + " " + replyID + " " + user_no + " " + boardid);
                                    System.out.println(url);
                                    System.out.println("\n");

                                // Response Body가 1이면 성공했다는 뜻.

                        }
                    }
                }
            } catch (IOException e) { // 링크 연결이 안되면 이미 지워진 댓이라고 알리는 부분... 인데 쓸모가 없다.
                System.out.println("Reply has been already deleted.");
            }
        }
    }

}
