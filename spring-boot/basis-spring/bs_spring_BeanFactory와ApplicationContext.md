## BeanFactory와 ApplicationContext
- BeanFactory < ApplicationContext < AnnotationConfigApplicationContext (의존방향)
- BeanFactory
    - 스프링 컨테이너의 최상위 인터페이스다.
    - 스프링 빈을 관리하고 조회하는 역할을 담당한다.
    - getBean() : beanFactory에서 제공하는 기능이다. 
- ApplicationContext
    - BeanFactory 기능을 모두 상속받아서 제공한다.
    - 아래 4가지 기능은 일반 Application 만들 때 필요한 공통 기능이다. 
    - 메시지소스를 활용한 국제화 기능
        - 예를 들어서 한국에서 들어오면 한국어로, 영어권에서 들어오면 영어로 출력
    - 환경 변수
        - 로컬, 개발, 운영등을 구분해서 처리
        - stage 환경 = 운영과 밀접한 환경을 의미한다. 
    - 애플리케이션 이벤트
        - 이벤트를 발행하고 구독하는 모델을 편리하게 지원
    - 편리한 리소스 조회
        - 파일, 클래스패스, 외부 등에서 리소스를 편리하게 조회
- BeanFactory를 직접 사용할 일은 거의 없다. 부가기능이 포함된 ApplicationContext를 사용한다.
- BeanFactory나 ApplicationContext를 스프링 컨테이너라 한다. (중요)

## 자바코드, XML
- 스프링 컨테이너는 다양한 형식의 설정 정보를 받아드릴 수 있게 유연하게 설계되어 있다.
- new AnnotationConfigApplicationContext(AppConfig.class) AnnotationConfigApplicationContext 클래스를 사용하면서 자바 코드로된 설정 정보를 넘기면 된다.
- XML 설정 사용
    - XML을 사용하면 컴파일 없이 빈 설정 정보를 변경할 수 있는 장점이 있다. 

```java 
    <bean id="memberService" class="hello.core.member.MemberServiceImpl">
        <constructor-arg name="memberRepository" ref="memberRepository"/>
    </bean>

    <bean id="memberRepository" class="hello.core.member.MemoryMemberRepository"/>
```

```java
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(getMemberRepository());
    }

    @Bean
    public MemoryMemberRepository getMemberRepository() {
        return new MemoryMemberRepository();
    }
```

- xml 기반의 appConfig.xml 스프링 설정 정보와 자바 코드로 된 AppConfig.java 설정 정보를 비교해보면 거의 비슷하다는 것을 알 수 있다.

## 스프링 빈 설정 메타 정보 - BeanDefinition
- 스프링은 어떻게 이런 다양한 설정 형식을 지원하는 것일까? 그 중심에는 BeanDefinition 이라는 추상화가 있다.
    - 쉽게 이야기해서 역할과 구현을 개념적으로 나눈 것이다!(완전 중요)
    - XML을 읽어서 BeanDefinition을 만들면 된다.
    - 자바 코드를 읽어서 BeanDefinition을 만들면 된다.
    - 스프링 컨테이너는 자바 코드인지, XML인지 몰라도 된다. 오직 BeanDefinition만 알면 된다.
- BeanDefinition 을 빈 설정 메타정보라 한다.
    - @Bean , <bean> 당 각각 하나씩 메타 정보가 생성된다.
- 그래서 스프링 컨테이너가 빈데피니션을 가지고 동작한다로 이해하자.
- 스프링 컨테이너가 BeanDefinition에만 의존하도록 만들어져 있다.(추상화)


```java
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {

	private final AnnotatedBeanDefinitionReader reader;

	private final ClassPathBeanDefinitionScanner scanner;
...
```

- 위 reader가 appConfig.class를 읽고 BeanDefinition(빈 메타정보)을 만든다. (bean의 메타정보를 담는다고 생각)


```java
public class GenericXmlApplicationContext extends GenericApplicationContext {

	private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);
...
```
- 마찬가지로 위 reader가 appConfig.xml을 읽고 BeanDefinition을 만든다.

```java
@Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                System.out.println("beanDefinitionName = "+beanDefinitionName + "beanDefinition = "+beanDefinition);
            }
        }
    }

    /*
        beanDefinitionName = appConfigbeanDefinition = Generic bean: class [hello.core.AppConfig$$EnhancerBySpringCGLIB$$2fd30374]; scope=singleton; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null
        beanDefinitionName = memberServicebeanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=memberService; initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AppConfig
        beanDefinitionName = ❗️getMemberRepositorybeanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=getMemberRepository; initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AppConfig
        beanDefinitionName = orderServicebeanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=orderService; initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AppConfig
        beanDefinitionName = discountPolicybeanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=discountPolicy; initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AppConfig
    */


    /*
    아래는 GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");로 돌렸을 떄 

    beanDefinitionName = memberServicebeanDefinition = Generic bean: class [hello.core.member.MemberServiceImpl]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [appConfig.xml]
    beanDefinitionName = memberRepositorybeanDefinition = Generic bean: ❗️class [hello.core.member.MemoryMemberRepository]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [appConfig.xml]
    beanDefinitionName = orderServicebeanDefinition = Generic bean: class [hello.core.order.OrderServiceImpl]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [appConfig.xml]
    beanDefinitionName = discountPolicybeanDefinition = Generic bean: class [hello.core.discount.RateDiscountPolicy]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [appConfig.xml]
    */
```

- 뭔지 잘 모르지만, BeanClassName, factoryBeanName, factoryMethodName, Scope 등 값을 가지고 beanDefinition을 생성한다. 
- BeanDefinition을 직접 생성해서 스프링 컨테이너에 등록할 수 도 있다. 
- 스프링이 다양한 형태의 설정 정보를 BeanDefinition으로 추상화해서 사용하는 것 정도만 이해하면 된다.
- appConfig 같은것을 FactoryMethod를 통해서 등록한다고 한다.  