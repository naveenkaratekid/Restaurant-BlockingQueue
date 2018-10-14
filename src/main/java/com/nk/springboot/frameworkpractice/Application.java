package com.nk.springboot.frameworkpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.filter.ApplicationContextHeaderFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
/**
 * Hello world!
 *
 */

@SpringBootApplication
@ComponentScan("com.nk")
public class Application 
{
    public static void main( String[] args )
    {
    		/*int hex = 0x1F;
    		double pi = 3.14_15F;
    		int binary = 0b1011;
    		int decimal = 25;
    		
    		System.out.println("Hex: " + hex);
    		System.out.println("Binary: " + binary);
    		System.out.println("Decimal: " + decimal);
    		*/
    		
    		/*ApplicationContext ctx =*/ SpringApplication.run(Application.class, args);
    }
}
