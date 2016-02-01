/**
 * Created by thy2134 on 1/26/16.
 */

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Scanner;


public class DCGreener {

    public static void main(String[] args) {
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
        ArrayList<String> pageNo = post.pageNos;


        ArrayList<String> rgallID = reply.rgallIDs; // 갤 ID들 담긴 리스트.
        ArrayList<String> rpostID = reply.rpostIDs; // 댓 달린 글 고유번호 담긴 리스트.
        ArrayList<String> rpostgID = reply.rpostgIDs;
        ArrayList<String> rpageNo = reply.rpageNos;

        String userno = reply.user_no; // 회원 고유번호(인듯?).

        new DeletePost(postID, gallID, id, pw, userno);
        new DeleteReply(rpostID, rgallID, id, pw, userno); // 지우기 실행!
    }
}
