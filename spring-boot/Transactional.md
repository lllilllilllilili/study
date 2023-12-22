# Transactional

## 작업 단위 묶어주지 않았을 경우
- 애플리케이션 커넥션 풀을 통해서 객체를 가지고 온다. 
- 하나의 커넥션은 세션으로 처리 (별개의 작업)

## 데이터베이스 관점
- 하나의 세션으로 처리가 가능해진다. 
- 간단한 예시
```java
@Override
public Response create(Command command) {
    Connection conn = dataSource.getConnection();
    conn.setAutoCimmit(false); //원자성 고려
    try { //try-catch를 같이 쓴다. 
        ...    
        conn.commit();
    } catch (Exception e) {
        conn.rollback();
    }
        }
```
- 위와 같이 코드를 작성하면 실수가 발생할 여지가 많이 생긴다.
- 중복 코드가 많아진다. 
- 주된 관심사가 아닌 코드가 서비스 레이어가 담긴다. 
- 특정 기술에 종속적인 코드가 된다.

## 정리
- 트랜잭션 시작 -> 관심사 로직 수행 -> 커밋 또는 롤백
- 스프링에서는 Proxy 객체 도입 
- create 상속 하고 메소드를 재정의한다.
- 재정의 할때 트랜잭션 여는 코드 작성하고 원래 수행하고자하는 target 객체를 호출해서 메소드 실행 
- 