package shop.mtcoding.blog.integre;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shop.mtcoding.blog._core.util.JwtUtil;
import shop.mtcoding.blog.board.BoardRequest;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserRequest;

import static org.hamcrest.Matchers.matchesPattern;


@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    private String accessToken;

    @BeforeEach
    public void setUp(){
        // 테스트 시작 전에 실행 할 코드
        System.out.println("setUp");
        User ssar = User.builder().id(1).username("ssar").build();
        accessToken = JwtUtil.create(ssar);
    }

    @AfterEach
    public void tearDown(){
        // 테스트 후 정리할 코드
        System.out.println("tearDown");
    }
    @Test
    public void getBoardOne_test() throws Exception {
        // given
        Integer id = 1;

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/s/api/board/{id}", id)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.title").value("제목1"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.content").value("내용1"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.isPublic").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.userId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d+")));
    }


    @Test
    public void getBoardDetail_test() throws Exception {
        // given
        Integer boardId = 5;


        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders.get("/api/board/{id}/detail", boardId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

            // then
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(5));
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.title").value("제목5"));
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.content").value("내용5"));
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.isPublic").value(false));
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.isBoardOwner").value(false));
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.isLove").value(false));
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.loveCount").value(1));
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.username").value("ssar"));
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt")
                    .value(matchesPattern("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}\\+00:00$")));
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.loveId").value(Matchers.nullValue()));
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.replies").isArray());
            actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.replies").isEmpty());


        }

    @Test
    public void update_test()throws Exception{
        //given
        Integer userId = 1;
        Integer boardId = 1;
        BoardRequest.UpdateDTO reqDTO = new BoardRequest.UpdateDTO();
        reqDTO.setTitle("===제목===");
        reqDTO.setContent("===내용===");
        reqDTO.setIsPublic(false);

        String requestBody = om.writeValueAsString(reqDTO);
//        System.out.println(requestBody);

        //when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .put("/s/api/board/{id}", boardId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
        );

        //eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        //then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.title").value("===제목==="));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.content").value("===내용==="));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.isPublic").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.userId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt")
                .value(matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d+$")));

    }



    @Test
    public void save_test() throws Exception {
        //given 가짜 데이터 -> 객체가 아니라 json
        BoardRequest.SaveDTO resqDTO = new BoardRequest.SaveDTO();
        resqDTO.setTitle("제목21");
        resqDTO.setContent("내용21");
        resqDTO.setIsPublic(true);

        String requestBody = om.writeValueAsString(resqDTO);
//        System.out.println(requestBody);

        //when 테스트 실행
        // 통신을 하면 당연히 헤더와 바디를 다시 줌 ResultActions안에는 그러면 헤더와 바디가 잇음
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/s/api/board")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
        );
        //eye 결과 눈으로 확인
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        //then 결과 코드로 검증
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(21));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.title").value("제목21"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.content").value("내용21"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.isPublic").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.userId").value(1));
    }

    @Test
    public void list_test() throws Exception {
        // given (가짜데이터)
        String page = "1";
        String keyword = "제목1";

        // when (테스트 실행)
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/")
                        .param("keyword", keyword)
                        .param("page", page)
        );

        // eye (결과 눈으로 검증)
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then (결과 코드로 검증)
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.boards[0].id").value(16));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.boards[0].title").value("제목16"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.boards[0].content").value("내용16"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.boards[0].isPublic").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.boards[0].userId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.boards[0].createdAt")
                .value(matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d+$")));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.prev").value(0));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.next").value(2));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.current").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.size").value(3));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.totalCount").value(11));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.totalPage").value(4));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.isFirst").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.isLast").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.keyword").value("제목1"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.numbers", Matchers.hasSize(4)));
    }
}
