1. 의존성
- A는 B를 사용하기만 해도 의존한다. 
- Bread를 사용하고 있기 떄문에 의존하고 있다. 

```java
class Chef {
    public Hanburger makeHamburger() {
        Bread bread = new Bread();
        return Hanburger.build()
        .bread(bread);
    }
}
```

- 그런데 이거를 외부에서 주입하는 방식으로 변경할 수 있다. 
- new 는 사실상 하드코딩이다. 

```java
class Chef {
    public Hanburger makeHamburger(Bread bread) {
        Bread bread = new Bread();
        return Hamburger.build()
        .bread(bread); 
    }
}
```

### 의존성 역전 
- 의존성 주입 완전히 다른 개념이다. 
- DI VS DIP 
- 인터페이스 만들고 통신을 하게 되면 의존성 역전이 되었다고 볼 수 있다. 
- 화살표의 방향이 역전된다. 
- 의존성 역전은 상위모듈 하위모듈 모두 추상화에 의존해야 한다.  
- 좋은글) 고수준 정책을 구현하는 코드는 저수준 세부사항을 구현하는 코드에 절대로 의존해서는 안된다. 대신 세부사항이 정책에 의존해야한다.
- 그리고 import에 사용되는 경로에는 interface나 추상클래스만 사용해야 한다. 
- 추상클래스를 정책으로 볼 수 있다. 변동성이 큰 구체적인 요소가 세부사항이 된다. 