`MemberServiceIntegrationTest`

```java
package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired  MemberService memberService;
    //spring 구현체에서 올라온다.
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");


        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        //Assertions.assertThat(member.getName()).isEqualTo(findMember.getName()); -< static info >-
        assertThat(member.getName()).isEqualTo(findMember.getName());
        //5:43
    }

    @Test
    // -< 중복회원 로직 검증이 되는지, throw 가 잘 나오는지 보아야 한다.
    public void 중복_회원_예외(){
        //when
        Member member = new Member();
        member.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");


        //given
        memberService.join(member);
        //IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        //assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //System.out.println(memberService.join(member));
        try{
            memberService.join(member2);
            fail();
        }catch(IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123");
        }
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
```
