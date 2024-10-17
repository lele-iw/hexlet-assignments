package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;

// BEGIN


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @Bean
    public Daytime createBean() {
        if ( 6 <= LocalDateTime.now().getHour() & LocalDateTime.now().getHour() < 22) {
            return new Day();
        } else {
            return new Night();
        }
    }
    // END
}
