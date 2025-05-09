package shop.mtcoding.blog.temp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import shop.mtcoding.blog.user.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TokenTest {

    @Test
    public void create_test(){
        User user = User.builder()
                .id(1)
                .username("ssar")
                .password("$2a$10$bLZ/mq1.fBzBxQmc/ML/8OL1YfG7r5UVzDc.1qd/i/heFT6A1DwZ.")
                .email("ssar@nate.com")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        String jwt = JWT.create()
                .withSubject("blogv3")
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60))
                .withClaim("id",user.getId()) // 사용자를 식별할 수 있는 정보 하나만 넣으면됨
                .withClaim("username",user.getUsername())
                .sign(Algorithm.HMAC256("metacoding")); // HMAC256 단방향 해쉬 알고리즘임 "metacoding" < secret값
        System.out.println(jwt);
        // 198 156 236 87 42 53 186 254 56 151 169 7 107 178 5 197 147 172 56 100 145 97 133 14 17 46 135 193 73 199 201 144
        // xpzsVyo1uv44l6kHa7IFxZOsOGSRYYUOES6HwUnHyZA
    }

    @Test
    public void verify_test(){
        // 2025.05.09.11:50까지만 유효함
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJibG9ndjMiLCJpZCI6MSwiZXhwIjoxNzQ2NzU5MDkyLCJ1c2VybmFtZSI6InNzYXIifQ.WwyAp0wOVnojgxKMlxj-Y2-IPG04mBzE2B4ovM-gaVQ";

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("metacoding")).build().verify(jwt); // 맞는지 검증하고 -> 만료되었는지 확인
        Integer id = decodedJWT.getClaim("id").asInt();
        String username = decodedJWT.getClaim("username").asString();
        System.out.println(id);
        System.out.println(username);

        User user = User.builder()
                .id(id)
                .username(username)
                .build();
    }
}
