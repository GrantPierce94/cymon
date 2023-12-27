package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/** 
 * Test controller class for the 'from-scratch' maven build  
 * @author David Liddle
 * @date 10/20/23
 */
@RestController
public class HelloController {

  /** GetMapping Tests the functionality of a GET request at '/' */
  @GetMapping("/")
  public String index() { return "This is a Test!"; } 

}
