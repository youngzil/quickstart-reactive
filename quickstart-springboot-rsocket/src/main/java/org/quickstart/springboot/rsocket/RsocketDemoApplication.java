package org.quickstart.springboot.rsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RsocketDemoApplication {

  // http://localhost:8080/tweets/linustorvalds

  // 参考https://dzone.com/articles/rsocket-with-spring-boot-amp-js-zero-to-hero

  public static void main(String[] args) {
    SpringApplication.run(RsocketDemoApplication.class, args);
  }

}
