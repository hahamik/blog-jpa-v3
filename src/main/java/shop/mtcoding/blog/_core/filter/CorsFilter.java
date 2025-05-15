package shop.mtcoding.blog._core.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("CORS 필터 작동");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String origin = request.getHeader("Origin");
        log.debug("Origin : "+origin);

        response.setHeader("Access-Control-Allow-Origin",  "http://localhost:5000"); // 허용하고자 하는 ip만 열어줌
//        response.setHeader("Access-Control-Expose-Headers", "Authorization"); // JS로 Authorization라는 KEY 값에 JWT를 넣는걸 허용할지 설정/ 이런 애들 요청오면 응답해줄게 / 응답 여부 결정
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS"); // JS 요청
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Key, Content-Type, Accept, Authorization"); // 이런 애들은 요청오면 허용해줄게 / 클라이언트가 요청하는걸 허용하는지 // X-Key -> 커스터마이징 된 애 x를 붙이는게 커벤션임
        response.setHeader("Access-Control-Allow-Credentials", "true"); // 쿠키의 세션값 허용/ 클라이언트가 쿠키에 세션 담으면 거부할 수도 잇음

        // Preflight 요청을 허용하고 바로 응답하는 코드
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK); // SC_OK -> 200
        }else {
            chain.doFilter(req, res);
        }
    }
}