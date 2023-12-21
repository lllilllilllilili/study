## JDK Proxy
- JDK Proxy는 인터페이스를 기반으로 합니다. 따라서, 구현해야 할 인터페이스가 미리 정의되어 있어야 합니다.

- 스프링 AOP in Spring Framework:
    - 예를 들어, 스프링 프레임워크에서 AOP를 사용할 때, 대상 클래스는 특정 인터페이스를 구현하고 있어야 합니다.
      MyService 인터페이스와 이를 구현하는 MyServiceImpl 클래스가 있다고 가정합시다.
      스프링 AOP를 사용하여 MyService 인터페이스의 메서드 호출에 로깅이나 트랜잭션 관리 등의 부가 기능을 적용할 수 있습니다.
      스프링은 MyService 인터페이스를 기반으로 JDK Proxy를 생성하여, MyServiceImpl의 메서드 호출을 가로챕니다.
- CGLib Proxy 사용 사례
    - CGLib은 클래스를 상속받아 프록시를 생성합니다. 인터페이스가 필요하지 않기 때문에, 인터페이스를 구현하지 않은 클래스에도 적용할 수 있습니다.
    - 스프링 프레임워크에서의 Bean 프록시 생성:
      인터페이스가 없는 스프링 빈(Spring Bean) 클래스에 대해 AOP를 적용할 때 CGLib이 사용됩니다.
      MyComponent라는 일반 클래스가 있고, 이 클래스에 AOP를 적용하고자 한다면 스프링은 CGLib를 사용하여 MyComponent의 서브클래스를 생성합니다.
      생성된 서브클래스는 MyComponent의 모든 메서드를 오버라이드하고, 추가적인 부가 기능(예: 메서드 호출 전후에 로깅)을 수행합니다.

## 로깅을 위한 Aspect 생성
```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {

    // Pointcut 정의: 어떤 메서드가 실행될 때 로깅할 것인지를 정의합니다.
    @Pointcut("execution(* com.example.service.*.*(..))")
    private void selectAllMethods() {}

    // Before Advice: 메서드 실행 전에 로깅을 수행합니다.
    @Before("selectAllMethods()")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("Before method: " + joinPoint.getSignature());
    }

    // 여기에 After, AfterReturning, AfterThrowing, Around 등의 Advice를 추가할 수 있습니다.
}
```
### 서비스 클래스 정의
```java
package com.example.service;

public class MyService {
    public void someMethod() {
        System.out.println("Executing someMethod");
    }
}
```

### 스프링 설정
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public MyService myService() {
        return new MyService();
    }

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
```

## 실행
```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MyService myService = context.getBean(MyService.class);
        myService.someMethod();
    }
}
```
여기서 @Before("selectAllMethogds()")는 AOP 어드바이스(Advice)의 한 종류로, selectAllMethods()라고 정의된 포인트컷(Pointcut)에 해당하는 모든 메서드 실행 전에 beforeAdvice 메서드를 실행하라는 의미