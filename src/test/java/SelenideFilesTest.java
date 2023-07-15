import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Configuration;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.FileDownloadMode.PROXY;
import static com.codeborne.selenide.Selenide.*;

public class SelenideFilesTest {
    //использовать, если нет href. запрос идет через сниффер
//    static {
//        Configuration.proxyEnabled = true;
//        Configuration.fileDownload = PROXY;
//    }

    @Test
    void downloadFileTest() throws IOException {
        open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloaded = $("[data-testid=raw-button]").download();
        //$(a[href*='README.txt'] -- *= Показывает вхождения
        //download() - кликает по элементу, который указан и ожидаем,
        //что в этом элементе есть атрибут href И начнется скачивание файла
        //Если нужно протестировать исключение не пишется try catch, Пишется:
//        FileNotFoundException exception = Assertions.assertThrows(
//                FileNotFoundException.class,
//                () -> $("#raw-url").download()
//        );
//        Assertions.assertEquals("File not found", exception.getMessage());
// - InputStream (позволяет читать файл побайтово, подходит для работы с любыми файлами)
// - Reader (позволяет читать файл посимвольно) - абстрактные классы, отвечают за чтение файлов

        //OutputStream (позволяет записывать любой файл в виде байтов)
        // / Writer (позволяет записывать любой файл посимвольно) - за запись файлов

        try (InputStream is = new FileInputStream(downloaded)) {
            byte[] bytes = is.readAllBytes();//прочитать все байты из этого файла
            String content = new String(bytes, StandardCharsets.UTF_8); //передаем байты и кодировку
            Assertions.assertTrue(content.contains("This repository is the home of _JUnit 5_."));
        }

    }

    @Test
    void uploadFileTest() throws Exception {
        open("https://fineuploader.com/demos.html");
        $("input[type='file']").uploadFromClasspath("cat.png"); //позволяет загрузить файлы из resources
        //корень путь начинается с этой папки, не включая ее, т.е. если внутри есть папка - путь - папка/файл, а не просто файл
        //можно добавлять сразу несколько файлов, через запятую
        $(".qq-file-name").shouldHave(text("cat.png"));
    }

    @Test
    void downloadPdfFileTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File downloaded = $("a[href*='junit-user-guide-5.9.3.pdf']").download();
        PDF pdf = new PDF(downloaded);
        Assertions.assertEquals("JUnit 5 User Guide", pdf.title);
    }

    @Test
    void downloadXlsFileTest() throws Exception {
        open("https://excelvba.ru/programmes/Teachers?ysclid=lfcu77j9j9951587711");
        File downloaded = $("a[href*='teachers.xls']").download();
        XLS xls = new XLS(downloaded);

        Assertions.assertEquals("1. Общие положения",
                xls.excel.getSheetAt(0).
                        getRow(1)
                        .getCell(4)
                        .getStringCellValue());
    }
}
