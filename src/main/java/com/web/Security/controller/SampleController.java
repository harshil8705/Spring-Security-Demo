package com.web.Security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SampleController {

    @GetMapping("/public/get/greetings")
    public ResponseEntity<String> getPublicResponse() {
        return new ResponseEntity<>("Hello Respected Viewer!!", HttpStatus.OK);
    }

    @GetMapping("/admin/get/greetings")
    public ResponseEntity<String> getAdminsResponse() {
        return new ResponseEntity<>("Hello Respected Admin!!", HttpStatus.OK);
    }

    @GetMapping("/trainer/get/greetings")
    public ResponseEntity<String> getTrainersResponse() {
        return new ResponseEntity<>("Hello Respected Trainer!!", HttpStatus.OK);
    }

    @GetMapping("/user/get/greetings")
    public ResponseEntity<String> getUsersResponse() {
        return new ResponseEntity<>("Hello Respected User!!", HttpStatus.OK);
    }

}
