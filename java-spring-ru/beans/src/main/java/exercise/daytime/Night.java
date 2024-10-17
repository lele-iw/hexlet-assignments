package exercise.daytime;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

public class Night implements Daytime {
    private String name = "night";

    public String getName() {
        return name;
    }

    // BEGIN
    @PostConstruct
    public void message(){
        System.out.println("Night bean has been created");
    }
    // END
}
