package com.example.helloboot;

public class HelloController {
    // 웹에 요청에 http 메소드가 get 으로 되어있는 것만 받겠다. /hello 로 되어있는 url 만 처리 하겠다.
    public String hello(String name) {
        return "Hello " + name;
    }
}

// 컨트롤러는 : 스프링 컨테이너 안에서 마치 웹 컨테이너 안에있는 웹 컴포넌트 처럼 웹에 요청을 받아서 그 결과를 리턴해주는 것