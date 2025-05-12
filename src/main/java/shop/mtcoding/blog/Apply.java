package shop.mtcoding.blog;

import lombok.Data;
import lombok.Getter;


@Getter
public class Apply {
    private Integer id;//지원번호
    private String name;//지원자명
    private Integer comId;// 회사 아이디
    private String status; // 1. 합격 2. 불합격 (도메인 설정)

    public Apply(Integer id, String name, Integer comId, ApplyEnum status) {
        this.id = id;
        this.name = name;
        this.comId = comId;
        this.status = status.value;
    }
}
