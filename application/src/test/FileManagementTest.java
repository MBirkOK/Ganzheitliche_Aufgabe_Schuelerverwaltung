import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class FileManagementTest {

    private String CSV_FILE_PATH = Paths.get("").toAbsolutePath().toString();

    @Test
    private void setupCSVFileExport(){
        System.out.println(CSV_FILE_PATH);
    }

    @Test
    private void setupCSVFileImport(){

    }

    public void importTeacherTest(){
    }
}
