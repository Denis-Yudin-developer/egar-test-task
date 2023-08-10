package com.denisyudin.CarGuide;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CarGuideApplication {
	public static void main(String[] args) {
		SpringApplication.run(CarGuideApplication.class, args);
	}
}
