### Hystrix 
- 외부의 서버와 연결이 되지 않는 등 여러 이유로 실패할 수 있다. 
- Hystrix는 장애상황을 견딜 수 있도록 도와준다. 

### Hystrix 특징 
- 다른 서비스 호출 시 서비스 지연 실패 방지
- 분산 시스템의 연쇄 실패 방지
- Fallback
- 모니터링과 알람

### 문제
![](https://github.com/Netflix/Hystrix/wiki/images/soa-1-640.png) 
- Client가 여러 의존성을 가지고 있기 때문에 지연이 발생하게 되면 연쇄적으로 상황이 악화된다. 
1. Client에서 요청을 disconnect 하지 못하고 축적
2. Client가 수용할 수 있는 thread pool 보다 요청이 축적
3. 대기 하는 Packet이 생기기 시작하고 CPU, Memory 사용량이 증가
4. socket buffer에 패킷 쌓인다.
5. 메모리 증가
6. socket buffer의 양보다 많이 쌓이면 packet drop 발생
7. Client 서비스 중단 위기

### 해결
1. Container등의 



### ref
- https://sabarada.tistory.com/52 
- https://github.com/Netflix/Hystrix/wiki 