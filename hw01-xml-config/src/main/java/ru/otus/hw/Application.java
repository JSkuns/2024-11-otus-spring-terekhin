import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.TestRunnerService;

/**
 * Основной класс
 */
public class Application {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();

    }
}