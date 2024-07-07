package com.tanvir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.config.EnableStateMachine;

@SpringBootApplication
public class StateMachineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StateMachineServiceApplication.class, args);
	}

}
