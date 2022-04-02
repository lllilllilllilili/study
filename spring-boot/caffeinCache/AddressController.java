package com.example.demo.caffeine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController
{
    @Autowired
    private AddressService addressService;

    @GetMapping("/address/{id}")
    public ResponseEntity<String> getAddress(@PathVariable("id") long customerId) {
        return ResponseEntity.ok(addressService.getAddress(customerId));
    }

    @GetMapping("/address2/{id}")
    public ResponseEntity<String> getAddress2(@PathVariable("id") long customerId) {
        return ResponseEntity.ok(addressService.getAddress2(customerId));
    }
    //result -> 각 URL로 접근 시 처음 접근하는것은 나오는데 그렇지 않은건 캐시되는것 같음 즉, url 창에 입력해도 로깅이 뜨지 않음!
}
