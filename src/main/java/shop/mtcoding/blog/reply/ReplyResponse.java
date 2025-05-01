package shop.mtcoding.blog.reply;

import lombok.Data;

import java.sql.Timestamp;

public class ReplyResponse {

    @Data
    public static class DTO{
        private Integer id;
        private String content;
        private Integer userId;
        private Integer boardId;
        private String createdAt; // 날짜는 String으로 return한다.


        public DTO(Reply reply) {
            this.id = reply.getId();
            this.content = reply.getContent();
            this.userId = reply.getUser().getId();
            this.boardId = reply.getBoard().getId();
            this.createdAt = reply.getCreatedAt().toString();
        }
    }
}
