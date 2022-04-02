package com.example.demo.caffeine;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;



/**
 * Service that uses caching.
 */
@Service
public class AddressService
{
    private final static Logger LOG = LoggerFactory.getLogger(AddressService.class);

    /*
    Finally, because the cache manager is itself a Spring bean,
    we can also autowire it into any other bean and work with it directly:
    */

    @Autowired
    private CacheManager cacheManager;

    /*
    Using the @Cacheable annotation without parameters will force
    Spring to use default names for both the cache and cache key.
     */
    //// value = cache 저장소, key = 저장소 key값
    @Cacheable(cacheNames = "addresses")
    public String getAddress(long customerId)  {
        LOG.info("Method getAddress is invoked for customer {}", customerId);

        return "123 Main St";
    }

    //localhost:8080/address2/3 -> 처음에 찍혀
    //그 다음부터는 안찍혀
    public String getAddress2(long customerId) {
        if(cacheManager.getCache("addresses2").get(customerId) != null) {
            return cacheManager.getCache("addresses2").get(customerId).get().toString();
        }

        LOG.info("Method getAddress2 is invoked for customer {}", customerId);

        String address = "123 Main St2";

        cacheManager.getCache("addresses2").put(customerId, address);

        return address;
    }

//    @Cacheable(value="address_cache", key="customerId")
//    public AddressDTO getAddress(long customerId){
//        if(cacheManager.containsKey(customerId)){
//            return cacheManager.get(customerId);
//        }
//    }
    /*참고한 사이트 : https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-libraries */
}
