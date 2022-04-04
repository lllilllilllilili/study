## 함수형 프로그래밍
### 1급 시민의 조건 (대상은 객체가 된다.)
- 함수/메서드의 매개변수로 전달
- 함수/메서드의 반환값이 될 수 있는지
- 변수에 담을 수 있는지
- 고차함수는 함수를 인자로 받거나 반환하는 함수이다. 그리고 이 객체를 만족하기 위해서는 일급객체(First-Class Citizen)이어야 한다. 
- Java에서는 Functional Interface를 통해서 1급 시민의 조건을 만족시킨다. 

### 왜 이걸 알아야 하죠?
- 역할에 충실할 수 있다. 
- 패러다임의 전환이 이루어질 수 있다. 

### Lambda Expression
- 람다로 아래처럼 구현할 수 있다. + Function Interface
- 오버라이드 해서 사용하기도 하는데 아래처럼 사용하기도 한다. 

```java
 Function<Integer, Integer> myAdder = x-> x + 10;
 int result = myAdder.apply(5);
 System.out.println(result);
```

- 두 개의 파라미터를 받는 과정이 있다.

```java
 BiFunction<Integer, Integer, Integer> add =  (x,  y) -> x+y;
        int result2 = add.apply(3,5);
        System.out.println(result2);
```

- 세 개를 받는 경우도 있을까? 직접 구현하면 된다. 
```java

@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}
```

```java
TriFunction<Integer, Integer, Integer, Integer> addThreeNumbers = (Integer x, Integer y, Integer z) -> {
    return x + y + z;
};
System.out.println(addThreeNumbers.apply(3,2,5));
```

