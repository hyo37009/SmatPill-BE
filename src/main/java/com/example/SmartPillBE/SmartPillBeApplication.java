package com.example.SmartPillBE;

import com.example.SmartPillBE.repository.PillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SmartPillBeApplication {
	private final PillRepository pillRepository;

	public static void main(String[] args) {
		SpringApplication.run(SmartPillBeApplication.class, args);
	}

	private void init(){

	}

}
