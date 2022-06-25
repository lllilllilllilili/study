- 가령 아래에 내용을 필터링 해야 한다고 생각해보자.

```java
select name from dishes where calorie < 400
```

- 컬렉션에 위 기능을 포함해서 구현할 수는 없을까?
- 또, 성능을 높이려면 멀티 코어 아키텍처를 활용해서 병렬로 컬렉션의 요소를 처리해야 한다.

### 스트림은 무엇인가?

- 자바8 API 추가된 기능이다.
- 스트림을 이용하면 멀티스레드 코드를 구현하지 않아도 데이터를 투명하게 병렬로 처리할 수 있다.

- lowCaloricDishes 는 `가비지 변수` 이다. (컨테이너 역할만하는 중간 변수이다.)

```java
List<String> lowCaloricDishesName = new ArrayList<>();
for(Dish dish: lowCaloricDishes) {
	lowCaloricDishesName.add(dish.getName());
}
```

```java
List<String> lowCaloricDishesName = 
						menu.stream()
								.filter(d -> d.getCalories() < 400) //400칼로리 이하 요리
								.sorted(comparing(Dish::getCalories)) //칼로리로 요리 정렬
								.map(Dish::getName) //요리명 추출
								.collect(toList()); //리스트 저장
```

이걸 아래의 parallelStream()으로 바꾸면 멀티코어 아키텍처에서 **병렬로 실행할 수 있다.**  

→ 7장에서 조금 더 자세히 다룬다. 

```java
List<String> lowCaloricDishesName = 
					menu.parallelStream()
							.filter(d->d.getCalories() < 400)
							.sorted(comparing(Dishes::getCalories))
							.map(Dish::getName)
							.collect(toList());
```

- 이처럼 스트림은 데이터 처리 파이프라인을 만드는데 효과적이다.
- 이와 같은 연산의 함수들을 high-level building block으로 이루어져 있어서 특정 스레딩 모델에 제한되지 않고 자유롭게 어떤 상황에서도 사용될 수 있다.
    - 그래서 병렬화 과정에서 스레드와 락을 걸 필요가 없다.
        
        ![스크린샷 2022-05-11 오후 6.58.31.png](../../Utils/Image/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202022-06-23%20%EC%98%A4%ED%9B%84%2011.44.30.png)
        
- 데이터 수집은 6장에서 한다.

```java
Map<Dish.Type, List<Dish>> dishesByType =
	menu.stream().collect(groupingBy(Dish::getType));

{
	FISH = [prawns, salmon],
	OTHER = [french fries, rice, season fruit, pizza],
	MEAT = [pork, beef, chicken]
}
```

- 구아바, 아파치, 람다제이와 같은 컬렉션을 제어하는데 도움이 되는 라이브러리를 제공한다.

### Stream 특징

- 간결하고 가독성이 좋다.
- 유연성이 좋다.
- 병렬성을 제공해서 성능이 좋다.

### 스트림 시작하기

- 데이터 처리 연산을 지원하도록 소스에서 추출된 연속된 요소
    - 연속된 요소
        - Collection - ArrayList 인지, LinkedList인지 시간과 공간의 복잡성과 관련된 요소 저장 및 접근 연산
        - Stream - filter, sorted, map 의 표현 계산식을 이룬다.
    - 소스
        - 스트림은 제공 소스로부터 데이터를 소비한다.
    - 데이터 처리 연산
        - 함수형 프로그래밍 언어에서 일반적으로 지원하는 연산과 데이터베이스와 비슷한 연산을 지원한다.
    - 파이프라이닝
        - 파이프라인을 구성한다.
    - 내부반복
        - iterator를 이용하는 컬렉션과 달리 스트림은 내부 반족을 지원한다.
    
    ![스크린샷 2022-05-11 오후 7.46.59.png](../../Utils/Image/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202022-06-23%20%EC%98%A4%ED%9B%84%2011.46.18.png)
    

```java
List<String> threeHighCaloricDishNames = 
		menu.stream()
				.filter(dish -> dish.getCalories() > 300)
				.map(Dish:getName)
				.limit(3)
				.collect(toList());

```

### 스트림과 컬렉션

- sequenced(연속된) - 순서와 상관없이 아무 값에나 접속하는 것이 아니라 순차적으로 접근한다.
- 컬렉션
    - 현재 자료구조가 포함하는 모든 값을 메모리에 저장한다.
- 스트림
    - 요청할 떄만 요소를 계산하는 고정된 자료구조다.
    - 게으르게 만들어지는 컬렉션이다.
    

### 딱 한번만 탐색할 수 있다.

### 외부 반복과 내부 반복

- for-each - 외부 반복
- 스트림 라이브러리 - 내부 반복

### 내부 반복이 더 좋은이유 !

- 작업을 투명하게 병렬로 처리하거나 최적화된 다양한 순서로 처리할 수 있기 때문이다.

스트림 라이브러리의 내부 반복은 데이터 표현과 하드웨어를 활용한 병렬성 구현을 자동으로 선택한다.

반면에 for-each를 이용하는 외부 반복에서는 병렬성을 스스로 관리해야 한다. (병렬성을 스스로 관리하는것은 병렬성을 포기하든지 아니면 synchronized로 시작하는 힘들고 긴 전쟁을 해야 한다.)

내부적으로 추상화가 더 되었다. 

![스크린샷 2022-05-11 오후 8.04.35.png](../../Utils/Image/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202022-06-23%20%EC%98%A4%ED%9B%84%2011.47.21.png)

### 스트림 연산

- filter.map.limit 는 서로 연결되어 있는 파이프라인을 형성한다.  (중간연산)
- collect로 파이프라인을 실행한 다음에 닫는다. (최종 연산)

### 중간 연산

- 중간 연산의 특징은 단말 연산을 스트림 파이프라인에 실행하기 전까지는 아무 연산도 수행하지 않는다. = 게으르다.

### 스트림 이용하기

- 질의를 수행할 (컬렉션 같은) 데이터 소스
- 스트림 파이프라인을 구성할 중간 연산 연결
- 스트림 파이프라인을 실행하고 결과를 만들 최종 연산

### 스트림은!

- 스트림은 소스에서 추출된 연속 요소로, 데이터 처리 연산을 지원한다.
- 스트림은 내부 반복을 지원한다. filter, map, sorted등의 연산을 반복적으로 추상화 한다.
- 스트림에는 중간 연산과 최종 연산이 있다.
- 게으르다.