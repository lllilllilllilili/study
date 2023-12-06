## feature
- 향상된 의사 난수 생성기 
  - 의사 난수 생성기(Pseudo-Random Number Generator)를 위한 새로운 인터페이스 타입과 구현을 제공
- 신규 Mac OS 렌더링 파이프라인
  - Apple 메탈 API를 사용하는 Mac OS용 Java 파이프라인을 구현
- 텍스트 블록 기능 추가
  - 기존 String을 여러 줄 작성할 때 사용 가능한 기능, 가독성 있는 코드 지원
- Switch 표현식 기능 향상
  - Switch 문 이용 시 값을 반환하여 이용 가능 하며, 람다 스타일 구문을 사용 가능
- Record Data class 추가
  - immutable 객체를 생성하는 새로운 유형의 클래스로 기존 toString, equals, hashCode Method에 대한 구현을 자동 제공
- Instanceof 매칭
  - 이전 버전 경우 Instanceof 내부에서 객체를 캐스팅 하는 과정이 필요하였으나, 캐스팅 과정을 내부에서 지원할 수 있도록 변경
- NumberFormat 클래스 기능 향상
  - 기존 숫자 Format 클래스(NumberFormat) 내 Method 추가(getCompactNumberInstance)
- DateTimeFormatter 클래스 기능 향상
  - 기존 날짜 Format 클래스(DateTimeFormatter) 내 패턴 Method 형식 추가("B")
- 봉인(Sealed) 클래스
  - 무분별한 상속을 막기 위한 목적으로 등장한 기능으로 지정한 클래스 외 상속을 허용하지 않으며, 지정한 클래스 외 상속 불가능
- Stream.toList() 기능 추가
  - 기존, Stream을 List로 변환 시 Collectors에서 기능을 찾아 사용했다면 Java17 부터는 Collectors호출 없이 toList()만으로 변환이 가능

Switch Expressions (Preview)


기본적인 Switch Expression
String day = "MONDAY";
String typeOfDay = switch (day) {
case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "Weekday";
case "SATURDAY", "SUNDAY" -> "Weekend";
default -> "Invalid day";
};
System.out.println(typeOfDay);


다중 라인의 경우
int number = 2;
String result = switch (number) {
case 1 -> {
System.out.println("One");
yield "Number is one";
}
case 2 -> {
System.out.println("Two");
yield "Number is two";
}
default -> "Other number";
};
System.out.println(result);


Switch Expression과 함께 enum 사용하기
enum Day { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }

Day day = Day.MONDAY;
String typeOfDay = switch (day) {
case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Weekday";
case SATURDAY, SUNDAY -> "Weekend";
};
System.out.println(typeOfDay);




Text Blocks
Java에서 Text Blocks (텍스트 블록)는 Java 13부터 도입된 기능으로, 주로 여러 줄에 걸친 문자열을 쉽고 깔끔하게 작성할 수 있게 해줍니다. 이 기능은 주로 HTML, JSON, SQL 등과 같은 여러 줄로 이루어진 텍스트를 문자열로 표현할 때 유용합니다.

멀티라인 문자열 지원
\n 여러 줄에 걸쳐 쓰지 않아도 된다.
문자열 시작과 끝의 구분
세 개의 큰따옴표(`"""`) 로 시작하고 끝나는 구조를 가지고 있다.
자동 줄바꿈 처리
Text Block 내부에서 줄바꿈을 자동으로 처리
공백 제어
문자열의 형식을 유지하면서 앞쪽에 있는 공백을 자동으로 제거
이스케이프 시퀀스 사용
text block 안에서도 \n, \t 를 사용할 수 있다.
String html = """
<html>
<body>
<p>Hello, world</p>
</body>
</html>
""";
Dynamic Application Class-Data Sharing
Records (Standard)
Java에서 "Records"는 Java 14에서 도입된 기능으로, 클래스를 더 간결하게 정의할 수 있는 방법을 제공합니다. 이는 데이터 전송 객체(Data Transfer Objects, DTO)나 간단한 값 객체(Value Objects)를 정의할 때 유용합니다. "Records"는 자동으로 final 필드, 생성자, getter 메서드, equals(), hashCode(), toString() 메서드를 생성해 줍니다.

Record의 기본 구조
Record를 사용하면 클래스를 아주 간단하게 정의할 수 있습니다. 예를 들어, Person 클래스를 Record를 사용하여 정의한다면 다음과 같이 작성할 수 있습니다

public record Person(String name, int age) {}
Record 사용 예시
Record 정의

public record Employee(String name, int id) {}
여기서 Employee는 두 개의 속성 name과 id를 가진 record입니다. Java는 이 두 필드에 대한 getter 메서드(name(), id())를 자동으로 생성합니다.



Record 인스턴스 생성:

Employee employee = new Employee("John Doe", 123);
Record는 불변 객체이므로, 한 번 생성된 후에는 그 상태가 변경되지 않습니다.



Record의 메서드 사용:

```java
String name = employee.name();
int id = employee.id();

System.out.println(employee);  // Employee[name=John Doe, id=123]
```
toString(), equals(), hashCode() 메서드가 자동으로 오버라이드되어 제공됩니다.

Record 내의 필드는 모두 final이며 불변입니다.


Pattern Matching for instanceof
Java에서 "Pattern Matching for instanceof"는 Java 14부터 프리뷰 기능으로 도입되었으며, Java 16에서 정식 기능으로 확정되었습니다. 이 기능은 instanceof 연산자를 사용할 때 타입 검사와 타입 변환을 보다 간결하고 안전하게 만들어 줍니다.

Pattern Matching for instanceof의 주요 특징
타입 검사와 캐스팅의 결합: 기존에는 instanceof를 사용하여 객체의 타입을 확인한 후, 해당 객체를 해당 타입으로 캐스팅해야 했습니다. Pattern Matching을 사용하면 이 두 단계를 하나의 단계로 결합할 수 있습니다.

코드 간결성 향상: 이 기능을 사용하면 코드가 더 간결해지고, 가독성이 향상됩니다. 불필요한 중복 코드를 줄일 수 있습니다.

안전성 증가: 타입 캐스팅의 오류 가능성을 줄이고, 코드의 안전성을 향상시킵니다.

as-is
```java
if (object instanceof String) {
String str = (String) object;
// str 사용
}
```


to-be
```java
if (object instanceof String str) {
// str을 직접 사용할 수 있음
}
```
이 예시에서, instanceof 연산자를 사용하여 object가 String 타입인지 확인하고, String 타입으로 캐스팅하는 것을 한 줄의 코드로 간소화했습니다. 이렇게 함으로써 코드가 더 간결하고, 가독성이 향상되며, 캐스팅 과정에서 발생할 수 있는 오류를 줄일 수 있습니다.

Sealed Classes (Preview)

sealed class 나 interface는 상속을 미리 지정된 하위 클래스만 허용할 수 있도록 한다.
상속 수현하는 클래스는 final, non-sealed, sealed 중 하나로 선언되어야 한다.
sealed  : sealed 키워드는 클래스 혹은 인터페이스 상속을 제한한다.  sealed 로 선언된 클래스는 permits 절에 나열된 하위 클래스만 상속받을 수 있다.
public sealed class Shape permits Circle, Rectangle {
// ... 클래스 구현
}
non-sealed  : 인터페이스 혹은 클래스가 추가 하위 클래스나 인터페이스를 가질 수  있음을 명시적으로 나타낸것이다.
public sealed class Shape permits Circle, Rectangle { /* ... */ }

public non-sealed class Rectangle extends Shape { /* ... */ }

// Rectangle을 상속하는 새로운 하위 클래스를 생성할 수 있다.
public class RoundedRectangle extends Rectangle { /* ... */ }
final  : 더 이상 상속되지 않는 것을 의미한다. 추가 하위 클래스를 가질 수 없다.
sealed 로 선언된 Circle, Rectangle 을 가질 수 있지만 차이점은 Circle은 추가 상속을 가질 수 없다. 반면에 Rectangle은 추가 상속을 받을 수 있다.
```java
public sealed class Shape permits Circle, Rectangle { /* ... */ }

public final class Circle extends Shape { /* ... */ }

public non-sealed class Rectangle extends Shape { /* ... */ }

```
Pattern Matching for Switch (Preview)
"Pattern Matching for Switch"는 Java의 스위치(switch) 문을 확장하여 패턴 매칭 기능을 추가하는 것을 목표로 하는 기능입니다. 이 기능은 Java 17에서 프리뷰 기능으로 처음 소개되었습니다. 이를 통해 개발자들은 스위치 문 내에서 더 복잡한 데이터 타입과 조건을 효과적으로 처리할 수 있게 되었습니다.

```java
Object obj = // ...

String formatted = switch (obj) {
case Integer i -> String.format("int %d", i);
case Long l    -> String.format("long %d", l);
case Double d  -> String.format("double %f", d);
case String s  -> String.format("String %s", s);
default        -> obj.toString();
};
```
