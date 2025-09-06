package raisetech.Student.Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/hello")
	public String hello(){
		return "Hello, World!";
	}

}
