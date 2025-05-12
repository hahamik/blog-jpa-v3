package shop.mtcoding.blog;

/*
    리스트 = [1,2,3]얘도 열거형 (가변)
    튜플 = [1,2,3] 얘도 열거형 (불변)
 */

public enum ApplyEnum { // 열거형
    PASS("합격"),FAIL("불합격"); // 영어는 이렇게 가능한데 한글은 조금 다름

    public String value;

    ApplyEnum(String value) { //values에 넣을 값을 PASS 뒤에 넣으면 됨
        this.value = value;
    }
}
