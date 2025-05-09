package shop.mtcoding.blog.temp;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

public class HashTest {

    @Test
    public void encode_test() {
        // $2a$10$bLZ/mq1.fBzBxQmc/ML/8OL1YfG7r5UVzDc.1qd/i/heFT6A1DwZ.
        // $2a$10$FEwxqyufMktyQCbJ98.33ekkUqWqwTDcex0uY4DfGvFYnRe14KZEm
        String password = "1234";

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(BCrypt.gensalt());
        System.out.println(encPassword);
    }

    @Test
    public void decode_test() {
        // $2a$10$bLZ/mq1.fBzBxQmc/ML/8OL1YfG7r5UVzDc.1qd/i/heFT6A1DwZ.
        // $2a$10$FEwxqyufMktyQCbJ98.33ekkUqWqwTDcex0uY4DfGvFYnRe14KZEm
        String dbPassword = "$2a$10$bLZ/mq1.fBzBxQmc/ML/8OL1YfG7r5UVzDc.1qd/i/heFT6A1DwZ.";
        String password = "1234";

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        if (dbPassword.equals(encPassword)) {
            System.out.println("비밀번호가 같아요");
        }else {
            System.out.println("비밀번호가 달라요");
        }
    }

    @Test
    public void decodev2_test() {
        // $2a$10$bLZ/mq1.fBzBxQmc/ML/8OL1YfG7r5UVzDc.1qd/i/heFT6A1DwZ.
        // $2a$10$FEwxqyufMktyQCbJ98.33ekkUqWqwTDcex0uY4DfGvFYnRe14KZEm
        String dbPassword = "$2a$10$bLZ/mq1.fBzBxQmc/ML/8OL1YfG7r5UVzDc.1qd/i/heFT6A1DwZ.";
        String password = "1234";

        Boolean isSame = BCrypt.checkpw(password,dbPassword);
        System.out.println(isSame);

    }
}
