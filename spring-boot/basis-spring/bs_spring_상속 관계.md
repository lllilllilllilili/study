## 스프링 빈 조회 - 상속 관계
- 부모타입을 조회하면 자식 빈은 다 끌려나온다. 
- 그래서 모든 자바 객체의 최고 부모인 Object 타입으로 조회하면, 모든 스프링 빈을 조회한다.

```java
AnnotationConfigApplicationContext ac =
        new AnnotationConfigApplicationContext(TestConfig.class);


    @Test
    @DisplayName("부모 타입으로 조회, 자식이 둘 이상 있으면, 중복 오류가 발생한다.")
    void findBeanByParentTypeDuplicate(){

        assertThrows(NoUniqueBeanDefinitionException.class,
                     ()->ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입으로 조회, 자식이 둘 이상 있으면, 빈 이름을 지정하면 된다.")
    void findBeanByParentTypeBeanName(){
        RateDiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", RateDiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")
    void findBeanBySubType(){
        //아래 보면 DiscountPolicy 에서 rateDiscountPolicy의 return 타입이 RateDiscountPolicy임!
        RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByParentType(){
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for(String key : beansOfType.keySet()){
            System.out.println("key ="+key+" value="+beansOfType.get(key));
        }

        /*

        결과 :
        key =rateDiscountPolicy value=hello.core.discount.RateDiscountPolicy@8576fa0
        key =fixDiscountPolicy value=hello.core.discount.FixDiscountPolicy@7582ff54

        아래 있는거 참고하면 다형성으로 DiscountPolicy인데 각각 new RateDiscountPolicy(),new RateDiscountPolicy() 를 뿜어냄
         */
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - Object")
    void findAllBeanByObjectType(){
        //스프링에 있는 빈까지 다 나오게 된다. 
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for(String key : beansOfType.keySet()){
            System.out.println("key= "+key+" value="+beansOfType.get(key));
        }
    }




    @Configuration
    static class TestConfig {

        //반환 타입은 인터페이스를 하는것이 좋다.
        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy(){
            return new FixDiscountPolicy();
        }
    }
```