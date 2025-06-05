package service;

import config.AppProperties;
import dao.CsvQuestionDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    // ioService был изначально прописан в spring-context.xml
    private final IOService ioService;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        var provider = new AppProperties("questions.csv");
        var dao = new CsvQuestionDao(provider);
        var questionList = dao.findAll();

        System.out.println(questionList);

    }

}
