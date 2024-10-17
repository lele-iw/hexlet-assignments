package exercise.controller;

import exercise.daytime.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

// BEGIN
@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    @Autowired
    private Daytime daytime;

    @GetMapping("")
    public String hello() {
        return "It is " + daytime.getName() + " now! Welcome to Spring!";
    }
}
// END

//    Внедрите бин типа Daytime в контроллер.
//        Реализуйте в контроллере обработчик, который будет обрабатывать GET-запросы на адрес /welcome
//        и выводить приветствие в зависимости от текущего времени. Приветствие должно иметь вид It is night now! Welcome to Spring!
//
//        Для получения текущего времени суток используйте метод getName()
//        у объекта типа Daytime, который мы внедрили. Запустите приложение и убедитесь, что выводится верное приветствие.