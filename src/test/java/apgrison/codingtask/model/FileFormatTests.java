package apgrison.codingtask.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileFormatTests {

    @Test
    public void givenAFileFormat_whenCallingExtension_thenReturnCorrectFileExtension() {
        assertThat(FileFormat.XML.extension()).isEqualTo(".xml");
        assertThat(FileFormat.JSON.extension()).isEqualTo(".json");
    }

    @Test
    public void givenAFileFormatName_whenUsingDifferentCases_thenFileFormatIsReturned() {
        assertThat(FileFormat.getByName("xml")).isEqualTo(FileFormat.XML);
        assertThat(FileFormat.getByName("Xml")).isEqualTo(FileFormat.XML);
        assertThat(FileFormat.getByName("XML")).isEqualTo(FileFormat.XML);
        assertThat(FileFormat.getByName("json")).isEqualTo(FileFormat.JSON);
        assertThat(FileFormat.getByName("Json")).isEqualTo(FileFormat.JSON);
        assertThat(FileFormat.getByName("JSON")).isEqualTo(FileFormat.JSON);
    }

}
