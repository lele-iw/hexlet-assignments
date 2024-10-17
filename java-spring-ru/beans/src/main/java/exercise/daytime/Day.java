package exercise.daytime;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

public class Day implements Daytime {
    private String name = "day";

    public String getName() {
        return name;
    }

    // BEGIN
    @PostConstruct
    public void message(){
        System.out.println("Day bean has been created");
    }
    // END
}
