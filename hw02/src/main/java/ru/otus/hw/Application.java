package ru.otus.hw;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw.service.TestRunnerService;

@ComponentScan(basePackages = "ru.otus") // Для сканирвания компонентов в пакете 'ru.otus'
@Configuration // Используется совместно с ComponentScan
@PropertySource("classpath:application.properties") // Подключение ресурсного файла 'application.properties'
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();
    }

}