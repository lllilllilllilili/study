## AOP란?
AOP는 Aspect Oriented Programming 즉, 관점 지향 프로그래밍의 약어이다. 여러 클래스에 나눠져 있는 책임을 Aspect 로 캡슐화하는 접근 방식이다. AOP는 로깅, 트랜잭션, 보안, 인증, 캐싱 공통적인 로직이 요구된다. 이러한 공통의 로직을 횡단 관심사(cross-cutting concern) 라고 부른다. 공통 로직을 애플리케이션, 비즈니스, 데이터 계층에서 구현하게 되면 코드 유지관리가 어렵게 된다. 

## AOP 주요 용어
- Aspect: 흩어진 관심사를 모듈화 하는 것, 부가기능을 모듈화 한다. 
- Target: Aspect를 적용되는 곳
- Advice: 실질적으로 어떤 일을 해야하는지 적어놓은것, 부가기능을 담은 구현체다.
- JointPoint: Advice가 적용될 위치
- PointCut: JointPoint의 스펙을 정의한 것

## 스프링 AOP 특징
- 프록시 패턴 기반의 AOP 구현체이다.
- 스프링 빈에만 AOP를 적용할 수 있다는 특징이 있습니다. 

## 코드분석
- [여기](https://thewayitwas.tistory.com/534)
