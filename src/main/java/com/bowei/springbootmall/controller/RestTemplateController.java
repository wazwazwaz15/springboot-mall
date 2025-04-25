package com.bowei.springbootmall.controller;


import com.bowei.springbootmall.model.RestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class RestTemplateController {

    private RestTemplate restTemplate = new RestTemplate();


    @GetMapping("/restApi")
    public RestBean getRestBean() {
        RestBean bean =
                restTemplate.getForObject("https://mocki.io/v1/3e805b49-63d6-45ad-bbc8-cfea958e9ba5", RestBean.class);
        return bean;

    }


    @GetMapping("/restApi2")
    public ResponseEntity<RestBean> getRestBean2() {
        ResponseEntity<RestBean> entity=
                restTemplate.exchange("https://mocki.io/v1/3e805b49-63d6-45ad-bbc8-cfea958e9ba5"
                        ,HttpMethod.GET
                        ,null
                        ,RestBean.class);

        log.info(entity.getStatusCode().toString());


        return entity;

    }


}
