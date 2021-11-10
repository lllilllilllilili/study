# 과제 2

1. assertThrow 를 써서 람다 함수를 사용하는 경우

```java
public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        //>-memberService.join(member2) 실행할껀데 예외가 터지게끔
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는  회원입니다.");
}
```

2. 1번과 같지 사용하지 않고 try-catch 를 사용하는 경우

```java
@Test
    // -< 중복회원 로직 검증이 되는지, throw 가 잘 나오는지 보아야 한다.
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        //>-memberService.join(member2) 실행할껀데 예외가 터지게끔

        //then
       try{
           memberService.join(member2);
           fail();
       }catch(IllegalStateException e){
           assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123");
       }

    }
```
