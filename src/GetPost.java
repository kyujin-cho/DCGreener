import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by thy2134 on 1/31/16.
 */
public class GetPost {
    public ArrayList<String> postIDs = new ArrayList<>();
    public ArrayList<String> gallIDs = new ArrayList<>();
    public ArrayList<String> postgIDs = new ArrayList<>();
    public ArrayList<String> pageNos = new ArrayList<>();

    public boolean isNone = false;

    public GetPost(String id, Map<String, String> cookies) {


        String UA = "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";


        String postID;
        String gallID;
        String postgID;
        String pageNo;
        String pagenum;


        // 글 파싱 부분.
        try {
            Document doc = Jsoup.connect("http://m.dcinside.com/gallog/list.php?g_id=" + id + "&g_type=G")
                    .userAgent(UA)
                    .timeout(10000).get(); // PC버전 갤로그의 기술적 한계로 인해 모바일 갤로그로 접속.
            pagenum =  doc.select("span.pg_num_area1").first().text().replace("1/", ""); // 페이지 수 가져오기.
            for (int i=1; i<=Integer.parseInt(pagenum); i++) {
                doc = Jsoup.connect("http://m.dcinside.com/gallog/list.php?g_id=" + id + "&g_type=G&page=" + i)
                        .userAgent(UA)
                        .cookies(cookies)
                        .timeout(10000).get(); // 갤로그 페이지 넘기기.
                    /*태그 예제
                    <a href="./view.php?g_id=nerdykjc&id=doosanbears_new&no=5497196&page=1&g_type=Rs_type=&g_no=47" class="list_picture_a">
                    <span class="list_right">데파이 에레라 자리에 놓겠다고 한거같은데 시발놈
                    <span class="list_pic_re"></span><br><span class="list_pic_gall">[두산 베어스]</span>
                    <span class="list_pic_date">2015-08-03 02:30</span>
                    </span></a>*/
                Elements link = doc.select("ul.list_picture_u a.list_picture_a"); // 댓 달린 글 / 갤 ID 담겨있는 링크가 있는 태그 선택하기.
                String[] split;
                if(!link.first().html().contains("검색 결과가 없습니다.")) {
                    for (Element e : link) {
                        System.out.println(e.html());
                        // 링크 예제
                        // ./view.php?g_id=nerdykjc&id=doosanbears_new&no=5498816&page=1&g_type=G&s_type=&g_no=51
                        split = e.attr("href").split("&"); // 링크의 GET 방식은 &으로 구분하므로 &으로 나누기.
                        postID = split[2].replace("no=", "");
                        gallID = split[1].replace("id=", "");
                        pageNo = split[3].replace("page=", "");
                        postgID = split[6].replace("g_no=", "");
                        postIDs.add(postID);
                        gallIDs.add(gallID); // 갤 ID / 댓 담긴 글 고유번호 리스트에 등록.
                        pageNos.add(pageNo);
                        postgIDs.add(postgID);
                    }
                } else {
                    System.out.println("No post to delete.");
                    isNone = true;
                    break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e1) {
            e1.printStackTrace();
        }
    }
}
