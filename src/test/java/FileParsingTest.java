import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import model.GlossaryModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileParsingTest {
    ClassLoader cl = FileParsingTest.class.getClassLoader();
    Gson gson = new Gson();

    @Test
    void csvTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("qa_guru.csv");
             Reader reader = new InputStreamReader(stream)) {
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> content = csvReader.readAll();

            Assertions.assertEquals(3, content.size());

            final String[] firstRow = content.get(0);
            final String[] secondRow = content.get(1);
            final String[] thirdRow = content.get(2);

            Assertions.assertArrayEquals(new String[] {"Teacher", "lesson"}, firstRow);
            Assertions.assertArrayEquals(new String[] {"Tuchs", "Files"}, secondRow);
            Assertions.assertArrayEquals(new String[] {"Vasenkov", "REST Assured"}, thirdRow);
        }
    }

    @Test
    void zipTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("sample.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                final String name = entry.getName();
                Assertions.assertTrue(name.contains("sample.txt"));
            }
        }
    }

    @Test
    void jsonTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("glossary.json");
             Reader reader = new InputStreamReader(stream)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            Assertions.assertEquals("example glossary", jsonObject.get("title").getAsString());
            Assertions.assertEquals("S", jsonObject.get("gloss_div")
                    .getAsJsonObject()
                    .get("title")
                    .getAsString());

            Assertions.assertTrue(jsonObject.get("gloss_div")
                    .getAsJsonObject()
                    .get("flag")
                    .getAsBoolean());
        }
    }

    @Test
    void improvedJsonTest() throws  Exception {
        try (InputStream stream = cl.getResourceAsStream("glossary.json");
             Reader reader = new InputStreamReader(stream)) {
            GlossaryModel glossary = gson.fromJson(reader, GlossaryModel.class);

            Assertions.assertEquals("example glossary", glossary.getTitle());
            Assertions.assertEquals("S", glossary.getGlossDiv().getTitle());
            Assertions.assertTrue(glossary.getGlossDiv().isFlag());
        }
    }
}
