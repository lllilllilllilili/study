가상 스레드

JDK(Java Development Kit) 21은 처리량이 많은 동시 애플리케이션을 작성 및 유지 관리 및 관찰하는 문제를 크게 줄일 수 있는 경량 스레드인 최종 가상 스레드를 제공한다.

이 기능의 이면에는 서버 애플리케이션이 최적의 하드웨어 활용으로 효과적으로 확장할 수 있도록 하고, 기존 lang.Thread API 코드에 대한 최소한의 변경으로 가상 스레드 채택을 촉진하고, 현재 JDK 도구를 사용하여 가상 스레드의 디버깅 및 프로파일링을 단순화하는 것이 포함된다.

JDK 21은 가상 스레드가 있는 스레드 로컬 변수를 완벽하게 지원하여 기존 라이브러리와의 호환성을 높이고 가상 스레드를 사용하도록 작업 기반 코드의 마이그레이션을 지원한다.

## 배경
자바의 스레드는 OS의 스레드를 기반으로 한다.

자바의 전통적인 스레드는 OS 스레드를 랩핑(wrapping)한 것으로 이를 플랫폼 스레드 라고 정의한다. (자바의 전통적인 스레드=플랫폼 스레드)
따라서 Java 애플리케이션에서 스레드를 사용하는 코드는 실제적으로는 OS 스레드를 이용하는 방식으로 동작했다.
OS 커널에서 사용할 수 있는 스레드는 갯수가 제한적이고 생성과 유지 비용이 비싸다.
이 때문에 기존에 애플리케이션들은 비싼 자원인 플랫폼 스레드를 효율적으로 사용하기 위해서 스레드 풀(Thread Pool) 만들어서 사용해왔다.

## 처리량(throughput)의 한계

Spring Boot와 같은 애플리케이션의 기본적인 사용자 요청 처리 방식은 Thread Per Request 이다. 이는 하나의 request(요청)을 처리하기 위해서 하나의 스레드를 사용한다.
애플리케이션에서 처리량을 늘리려면 스레드를 늘려야 하지만 스레드를 무한정 늘릴 수 없다. (OS 스레드를 무한정 늘릴 수 없기 때문)
따라서 애플리케이션의 처리량(throughput)은 스레드 풀에서 감당할 수 있는 범위를 넘어서 늘어날 수 없다.
Blocking으로 인한 리소스 낭비

Thread per Request 모델에서는 요청을 처리하는 스레드에서 IO 작업 처리할 때 Blocking 이 일어난다.
이 때문에 스레드는 IO 작업이 마칠 때까지 다른 요청을 처리하지 못하고 기다려야 한다.(Blocking 동안 대기)
애플리케이션에 유입되는 요청이 많지 않거나 또는 스케일 아웃으로 충분히 커버할 수 있는 정도라면 문제가 없지만,
아주 많은 요청을 처리해야하는 상황이라면 Blocking 방식으로 인해 발생하는 낭비를 줄여야 할 필요가 있다.
이 때문에 Blocking 이 아니라 Non-blocknig 방식의 Reactive Programming이 발전하였다.

## Reactive Programming의 단점

처리량을 높이기 위한 방법으로 비동기 방식의 Reactive 프로그래밍이 발전해왔다.
한정된 자원인 플랫폼 스레드가 Blocking 되면서 대기하는 데 소요된 스레드 자원을 Non-blocking 방식으로 변경하면서 다른 요청을 처리하는데 사용할 수 있게 되었다.
대표적으로 Webflux 가 이렇게 Non-blocking으로 동작한다.
다만 이런 Reactive 코드는 작성하고 이해하는 비용을 높게 만들었다. (Mono, Flux)
또한 기존의 자바 프로그래밍의 패러다임은 스레드를 기반으로 하기 때문에 라이브러리들 모두 Reactive 방식에 맞게 새롭게 작성되어야 하는 문제가 있다.

## 자바 플랫폼의 디자인

자바 플랫폼은 전통적으로 스레드를 중심으로 구성되어 있었다.
스레드 호출 스택은 thread local을 사용하여 데이터와 컨텍스트를 연결하도록 설계되어 있다.
이 외에도 Exception, Debugger, Profile(JFR)이 모두 스레드를 기반으로 하고 있다.
Reactive 스타일로 코드를 작성하면 사용자의 요청이 스레드를 넘나들면서 처리되는데, 이 때문에 컨텍스트 확인이 어려워져 결국 디버깅이 힘들어졌다.

출처:
[ [자바] java 21 가상 스레드 Virtual Thread](https://blog.naver.com/PostView.naver?blogId=seban21&logNo=223138158582&categoryNo=12&parentCategoryNo=7&viewDate=&currentPage=&postListTopCurrentPage=&isAfterWrite=true)
[ Virtual Thread란 무엇일까? (1) ](https://findstar.pe.kr/2023/04/17/java-virtual-threads-1/)