/**
 * Created by kyujin on 1/26/16.
 */

import java.util.ArrayList;


public class DCGreener {

    public static void main(String[] args) {
        String id = "yousuck"; // 본인의 iD
        String pw = "foobar"; // 본인의 pW

        ConnectDC login = new ConnectDC(id); // 갤로그에서 댓 달린 글 고유번호 / 갤 ID 불러오기.

        ArrayList<String> gallID = login.gallIDs; // 갤 ID들 담긴 리스트.
        ArrayList<String> postID = login.postIDs; // 댓 달린 글 고유번호 담긴 리스트.
        String userno = login.user_no; // 회원 고유번호(인듯?).

        new DeleteReply(postID, gallID, id, pw, userno); // 지우기 실행!

    }
}
