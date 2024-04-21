package org.example.twitter.api

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import javax.crypto.SecretKey

@SpringBootApplication
class TwitterAnalogApiApplication {

    static void main(String[] args) {
//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        byte[] secretBytes = secretKey.getEncoded();
//        String jwtSecret = Base64.getEncoder().encodeToString(secretBytes);
//
//        System.out.println("Generated JWT Secret: " + jwtSecret);
        SpringApplication.run(TwitterAnalogApiApplication, args)
    }

}
