## IoC, DI, 컨테이너
-  프로그램의 제어 흐름은 이제 AppConfig가 가져간다. 
- AppConfig는 OrderServiceImpl 이 아닌 OrderService 인터페이스의 다른 구현 객체를 생성하고 실행할 수 도 있다.  
- 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것을 제어의 역전(IoC)이라 한다.
- AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해 주는 것을 IoC 컨테이너 또는 DI 컨테이너라 한다.
- Assembler - 앱 구성구성 관련된 것들을 조립한다. Application Service를 이 Layer에서 다루고 레고블럭처럼 조립을 하는것을 의미한다. (오브젝트를 만드는곳이라고 해서 Object Factory 라고도 한다.)


### 프레임워크 vs 라이브러리
- 프레임워크가 내가 작성한 코드를 제어하고, 대신 실행하면 그것은 프레임워크가 맞다. (JUnit)
- JUnit은 IoC 컨테이너 중 하나이다. 
- 반면에 내가 작성한 코드가 직접 제어의 흐름을 담당한다면 그것은 프레임워크가 아니라 라이브러리다.

## 정적인 클래스 의존관계
- 애플리케이션을 실행하지 않아도 분석할 수 있다.
- 그런데 이러한 클래스 의존관계 만으로는 실제 어떤 객체가 OrderServiceImpl 에 주입 될지 알 수 없다.

## 동적인 객체(인스턴스) 의존 관계 
- 애플리케이션 실행 시점에 실제 생성된 객체 인스턴스의 참조가 연결된 의존 관계다.
- 애플리케이션 실행 시점(런타임)에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달해서 클라이언트와 서버의 실제 의존관계가 연결 되는 것을 의존관계 주입이라 한다.
- AppConfig.class안에 등록된 @Bean을 모두 스프링 컨테이너에 등록해준다. 

```java
ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
```

```java
//1. 함수이름 
//2. 반환타입
applicationContext.getBean("memberService", MemberServiceImpl.class);
```

```java
//@Bean으로 등록한것이 로그로 나오게 된다. 
16:32:02.033 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'appConfig'
16:32:02.041 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'memberService'
16:32:02.076 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'getMemberRepository'
16:32:02.079 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'orderService'
16:32:02.084 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'discountPolicy'
```

## 스프링 컨테이너
- ApplicationContext 를 스프링 컨테이너라 한다. 
- 스프링 컨테이너는 @Configuration 이 붙은 AppConfig 를 설정(구성) 정보로 사용한다. 
- 여기서 @Bean 이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다.
- 전에는 개발자가 필요한 객체를 AppConfig 를 사용해서 직접 조회했지만, 이제부터는 스프링 컨테이너를 통해서 필요한 스프링 빈(객체)를 찾아야 한다. 
- 스프링 빈은 applicationContext.getBean() 메서드를 사용해서 찾을 수 있다.