package com.example.config;

import com.example.exceptions.ProductNotFoundException;
import com.example.exceptions.UserNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class FeignConfig {
//    @Bean
//    public ErrorDecoder errorDecoder(){
//        return new CustomErrorDecoder();
//    }
//
//    public static class CustomErrorDecoder implements ErrorDecoder{
//        private final ErrorDecoder errorDecoder = new Default();
//        @Override
//        public Exception decode(String s, Response response) {
//            if(response.status() == 404){
//                if(s.contains("UserClient")){
//                    return new UserNotFoundException("User not found!");
//                }else if(s.contains("ProductClient")){
//                    return new ProductNotFoundException("Product not found!");
//                }
//            }
//            if(response.status() >= 500){
//                return new RuntimeException("Service not available");
//            }
//            return errorDecoder.decode(s, response);
//        }
//    }
//}
