import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.FileDownloadMode.PROXY;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideFilesTest {
    //использовать, если нет href. запрос идет через сниффер
//    static {
//        Configuration.proxyEnabled = true;
//        Configuration.fileDownload = PROXY;
//    }

    @Test
    void downloadFileTest() throws IOException {
        open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloaded = $("#raw-url").download();
        //$(a[href*='README.txt'] -- *= Показывает вхождения
        //download() - кликает по элементу, который указан и ожидаем,
        //что в этом элементе есть атрибут href И начнется скачивание файла
        //Если нужно протестировать исключение не пишется try catch, Пишется:
//        FileNotFoundException exception = Assertions.assertThrows(
//                FileNotFoundException.class,
//                () -> $("#raw-url").download()
//        );
//        Assertions.assertEquals("File not found", exception.getMessage());

        try (InputStream is = new FileInputStream(downloaded)) {
            byte[] bytes = is.readAllBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);
            Assertions.assertTrue(content.contains("This repository is the home of _JUnit 5_."));
        }

    }
}
