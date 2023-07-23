## 4.1 JPA를 이용한 리포지터리 구현

### 4.1.1 모듈 위치

- domain
    - repository (interface)
- infra
    - repositoryimpl (class)

실제 구현체가 인프라에 들어간다. 

### 4.1.2 리포지터리 기본 기능 구현

- id로 애그리거트 조회하기
- 애그리거트 저장하기

```jsx
public interface OrderRepository {
	Order findById(OrderNo no);
	void save(Order order);
}
```

인터페이스는 애그리거트 루트를 기준으로 작성한다. (여기서는 Order)

인터페이스 구현 클래스는 아래와 같이 구현할 수 있다.

JPA > EntityManager 

```jsx
@Repository
public class JpaOrderRepository implements OrderRepository {
	@PersistenceContext
	private EntityManager entityManger;

	@Override
	public Order findById(OrderNo id) {
		return entityManager.find(Order.class, id);
	}

	@Override
	public void save(Order oder) {
		//persist 메서드를 이용해서 애그리거트를 저장한다. 
		entityManger.persist(order);
	}
}
```

메서드 실행이 끝나면 트랜잭션을 커밋하는데 JPA는 트랜잭션 범위에서 변경된 객체의 데이터를 DB에 반영하기 위해 UPDATE 쿼리를 실행한다. 

```jsx
public class ChangeOrderService {
	@Transactional
	public void changeShippingInfo(OrderNo no, ShippingInfo newShippingInfo) {
	Optional<Order> orderOpt = orderRepository.findById(no);
	Order order = orderOpt.orElseThrow(()->new OrderNotFoundException());
	order.changeShippingInfo(newShippingInfo);
}
```

JPQL을 이용할 수 있다.

```jsx
@Override
public List<Order> findByOrdererId(String ordererId, int startRow, int fetchSize) {
	TypedQuery<Order> query = entityManager.createQuery(
		"select o from Order o" +
			 "where o.orderer.memberId.id = :orderId " +
			 "order by o.number.number desc",
		Order.class);
	query.setParameter("ordererId", ordererId);
	query.setFirstResult(startRow);
	query.setMaxResults(fetchSize);
	return query.getResultList();
}
```

## 4.2 스프링 데이터 JPA를 이용한 리포지터리 구현

```jsx
@Entity
@Table(name = "purchase_order")
@Access(AccessType.FIELD)
public class Order {
	@EmbeddedId
	private OrderNo number; //OrderNo가 식별자 타입
}
```

```jsx
public interface OrderRepository extends Repository<Order, OrderNo> {
	Optional<Order> findById(OrderNo id);
	void save(Order order);
}
```

OrderRepository를 리포지터리로 인식해서 알맞게 구현한 객체를 스프링 빈으로 등록한다.

```jsx
@Service
public class CancelOrderService {
	private OrderRepository orderRepository;

	public CancelOrderService(OrderRepository orderRepository, _생략) {
		this.orderRepository = orderRepository;
		_생략
	}

	@Transactional
	public void cancel(OrderNo orderNo, Canceller canceller) {
		Order order = orderRepository.findById(orderNo)
					.orElseThrow(()->new NoOrderException());
		if (!cancelPolicy.hasCancellationPermission(order, canceller)) {
			throw new NoCancellablePermission();
		}
		order.cancel();
	}
}
```

## 4.3 매핑 구현

### 4.3.1 엔티티와 밸류 기본 매핑 구현

- 애그리거트 루트는 엔티티인데 @Entity로 매핑설정한다.

한테이블에 엔티티와 밸류 데이터가 같이 있으면

- 밸류는 @Embeddable 로 매핑설정한다.
- 밸류 타입 프로퍼티는 @Embedded로 매핑설정한다.

루트 엔티티인 Order는 JPA의 @Entity 로 매핑한다. 

```jsx
@Entity
@Table(name = "purchase_order")
public class Order {
	...
}
```

Order에 속한 Orderer는 밸류이기 때문에 @Embeddable로 매핑한다.

```jsx
@Embeddable
public class Orderer {
	// MemberId에 정의된 칼럼 이름을 변경
	// @AttributeOverride 애노테이션 사용
	@Embedded
	@AttributeOverrides(
		@AttributeOverride(name = "id", column = @Column(name = "orderer_id"))
	)
	private MemberId memberId;
	
	@Column(name = "orderer_name")
	private String name;
}
```

여기서 MemberId는 id 프로퍼티와 매핑되어 테이블 칼럼 이름으로 “member_id”를 지정한다. 

```jsx
@Embeddable
public class MemberId implements Serializable {
	@Column(name = "member_id")
	private String id;
}
```

위 id값을 보면 memer_id 와 orderer_id 랑은 다르기 떄문에 이것을 매핑시키기 위해서 @AttributeOverride를 사용했다.

### 4.3.2 기본 생성자

- 엔티티와 밸류의 생성자는 객체를 생성할 때 필요한 것을 전달받는다.

```jsx
public class Receiver {
	private String name;
	private String phone;
	
	public Receiver(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}
}
```

- JPA에서 @ Entity와 @Embeddable 로 클래스를 매핑하려면 기본 생성자를 제공해야 한다.
- DB에서 데이터를 읽어와 매핑된 객체를 생성할 때 기본 생성자를 사용해서 객체를 생성

```jsx
@Embeddable
public class Receiver {
	@Column(name = "receiver_name")
	private String name;

	@Column(name = "receiver_phone")
	private String phone;
	
	protected Receiver() {} //JPA를 적용하기 위해 기본 생성자 추가, 다른 클래스에서 사용하지 못하게 함

	public Receiver (String name, String phone) {
		this.name = name;
		this.phone = phone;
	}
...
}
```

### 4.3.3 필드 접근 방식 사용

get/set 메서드를 추가하는것보다 의도가 명확하게 드러나는 메소드명을 사용하는것이 좋다.

- setState() → cancel()
- setShippingInfo() → changeShippingInfo()

```jsx
@Entity
@Access(AccessType.PROPERTY)
public class Order {

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}
...
}

@Entity
@Access(AccessType.FIELD)
public class Order {

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private OrderState state;

	// cancel(), changeShippingInfo() 도메인 기능 구현
	// 필요한 get 메서드 제공
...
}
```

## 4.3.4 AttributeConverter 를 이용한 밸류 매핑 처리

```jsx
public class Length {
	private int value;
	private String unit;
	...
}
//두개 이상의 프로퍼티를 가진 밸류 타입을 한 개 칼럼에 매핑하려면 
@Embeddable 애너테이션으로 매핑할 수 없다. 
```

- convertToDatabaseColumn() 메서드는 밸류 타입을 DB 칼럼 값으로 변환하는 기능을 구현
    - 밸류 타입 → DB 칼럼
- convertToEntityAtrribute() 메서드는 DB 칼럼 값을 밸류로 변환하는 기능을 구현
    - DB 칼럼 → 밸류 타입

### 4.3.5 밸류 컬렉션:별도 테이블 매핑

### 4.3.6 밸류 컬렉션: 한개 칼럼 매핑

- DB에서 한 개 칼럼에 콤마로 구분해서 저장해야 할 때가 있다.
- AttributeConverter를 사용하면 밸류 컬렉션을 한 개 칼럼에 쉽게 매핑할 수 있다.

```jsx
public class EmailSet {
	private Set<Email> emails = new HashSet<>();
	
	public EmailSet(Set<Email> emails) {
		this.emails.addAll(emails);
	}

	public Set<Email> getEmails() {
		return Collections.unmodifiableSet(emails);
	}
}
```

- 밸류 컬렉션을 위한 타입을 추가했으면 AttributeConverter를 구현해야 한다.

```jsx
public class EmailSetConverter implements AttributeConverter<EmailSet, String> {
	@Override
	public String convertToDatabaseColumn(EmailSet attribute) {
		if (attribute == null) return null;
		return attribute.getEmails().stream()
						.map(email -> email.getAddress())
						.collect(Collectors.joining(","));
	}

	...
}
```

EmailSet 타입 프로퍼티가 Converter로 EmailSetConverter를 사용하도록 지정한다.

```jsx
@Column(name = "emails")
@Convert(converter = EmailSetConverter.class)
private EmailSet emailSet;
```

### 4.3.7 밸류를 이용한 ID매핑

- 식별자 타입은 Serializable 타입이어야 해서 식별자로 사용할 때는 밸류탑은 Serializable 인터페이스를 상속해야한다.

```jsx
@Entity
@Table(name = "purchase_order")
public class Order {
	@EmbeddedId
	private OrderNo number;
	...
}

@Embeddable
public class OrderNo implements Serialiable {
	@Column(name = "order_number")
	private String number;
}
```

### 4.3.8 별도 테이블에 저장하는 밸류 매핑

- 애그리거트에서 루트 엔티티를 뺀 나머지 구성요소는 대부분 밸류이다.
- 엔티티가 확실하다면 다른 애그리거트는 아닌지 확인해봐야 한다.
    - 자신의 라이프사이클이 있는지 체크
- 상품 상세 화면에 고객의 리뷰를 보여주면
    - 상품 애그리거트에 고객 리뷰가 포함된다고 생각할 수 있다.
- Product와 Review는 함께 생성되지 않고 함께 변경되지 않기 때문에 서로 다른 애그리거트에 속한다.
- 애그리거트에 속한 객체가 밸류인지 엔티티인지 구분하는 방법은 고유 식별자를 갖는지 확인해봐야 한다.
- 근데 또 테이블의 식별자가 애그리거트 구성요소의 식별자라고 착각하면 안된다.(음??)
- 외래키로 연결되는 듯한 느낌은 `특정 프로퍼티를 별도 테이블에 보관하는것으로 접근` 해서 해당 테이블은 엔티티가 아니라 밸류로 봐야한다는 느낌
- 밸류는 @Embeddable로 매핑한다.

### 4.3.9 밸류 컬렉션을 @Entity로 매핑하기

### 4.3.10 ID참조와 조인 테이블을 이용한 단방향 M-N 매핑

- 애그리거트 간 집합 연관은 성능 상의 이유로 피해야 한다.
- 집합 연관이 필요하다면 ID 참조를 이용해 단방향 집합 연관을 적용해볼 수 있다.

```jsx
@Entity
@Table(name = "product")
public class Product {
	@EmbeddedId
	private ProductId id;
	
	@ElementCollection
	@CollectionTable(name = "product_category"
		joinColumns = @JoinColumn(name = "product_id"))
	private Set<CategoryId> categoryIds;
```