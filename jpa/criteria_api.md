1. Criteria API란?

자바 ORM 표준 JPA 프로그래밍에서 JPQL(객체지향쿼리) Java Persistence Query Language 을 자바 코드로 작성할 수 있도록 도와주는 빌더 클래스 API를 의미합니다. JPQL 조회를 위한 문자열 빌드에 하나의 대안으로 Java 오브젝트를 갖는 조회 빌드 API 입니다. JPQL은 런타임 중에 잘못된것이 있는지 확인해볼 수 있습니다. 


2. Criteria API 장단점
- 2-1. 장점
  - 빌더 클래스로 컴파일 단계에서 문법 오류를 확인해볼 수 있습니다.
쿼리문을 문자열 그대로 작성 시 발생할 수 있는 휴먼 에러를 방지할 수 있습니다. 
- 2-2. 단점
   - 코드가 복잡해지면 가독성이 떨어지게 됩니다. 
API 사용에 대한 러닝커브가 존재합니다. 

3. Criteria API 기본 쿼리
- Criteria API는 javax.persistence.criteria 패키지에서 관리되는것을 확인해볼 수 있습니다. 
1. getCriteriaBuilder 함수를 이용해서 Criteria Query Builder를 생성합니다. 

2. 쿼리 정보를 담기 위해 createQuery함수를 이용해 CriteriaQuery 객체를 생성하게 됩니다. 

3. select 절에 from절을 적용시키기 위해서 from 함수를 이용해 User.class를 받게됩니다.

4. 등록된 query 객체에 정보를 이용해 자바 코드 기반의 query 객체를 생성하게 됩니다. 

```java
CriteriaBuilder cb = em.getCriteriaBuilder(); //Criteria 쿼리 빌더 생성
 
CriteriaQuery<User> cq = cb.createQuery(User.class); //Criteria 생성
 
Root<User> user = cq.from(User.class); //from 절
cq.select(user); 
 
TypedQuery<User> query = em.createQuery(query); //select 절 생성
 
List<User> users = query.getResultList();
```

