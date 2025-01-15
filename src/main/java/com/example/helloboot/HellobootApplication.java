package com.example.helloboot;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class HellobootApplication {

    public static void main(String[] args) {
        // Spring boot 에 Containerless 개발준비
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        // WebServer 타입으로 받음
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
            HelloController helloController = new HelloController();

            servletContext.addServlet("frontcontroller", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    // 인증, 보안, 다국어, 공통 기능
                    // 서블릿 컨테이너의 맵핑 기능을 프론트 컨트롤러가 담당한다.

                    if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                        String name = req.getParameter("name");

                        String ret = helloController.hello(name);

                        resp.setStatus(HttpStatus.OK.value());
                        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                        resp.getWriter().println(ret);

                    }
                    else if (req.getRequestURI().equals("/user")) {
                        //
                    }
                    else {
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }
                }
            }).addMapping("/*");
            // / 밑으로 들어오는 모든 요청을 다 받기 위해서 /* 라고 한다. -> frontcontroller 가 됨
            // /hello 라고 시작하는 url 패턴에다가 mapping 함

        }); // getWebServer -> 서블릿 컨테이너 생성 함수
        webServer.start();  // 실행 -> 톰캣 서블릿 컨테이너 동작

        // 404 리턴하는 것은 : 톰캣 서버까지 잘 뜨고, 서블릿 컨테이너가 시작됨을 확인
        // 기능을 수행하는 웹 컴포넌트를 넣는다. 서블릿 컨테이너 안에 들어가는 웹 컴포넌트 == 서블릿
        // 요청을 서블릿의 요청으로 넘겨준다. Mapping 시켜주면 됨
    }

}

// 서블릿 컨테이너의 대명사 = 톰캣
// 서블릿(Servlet)이란 동적 웹 페이지를 만들 때 사용되는, 자바 기반의 웹 애플리케이션 프로그래밍 기술
//              : 클라이언트 요청을 처리하고 그 결과를 반환하는 기술
// 서블릿은 웹 요청과 응답의 흐름을 간단한 메서드 호출만으로 체계적으로 다룰 수 있게 해준다.