package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 08.06.17.
 */
public class ValidatorTest {

    private static final String TEST = "src/test/resources";
    private Validator validator;
    private String id = "template_id";
    private String jsonName = "test.json";
    private String serverId = "1234";
    private String status = "created";
    private String version = "0.0.1";
    private boolean containsJson = false;
    private ResponseImpl response;
    private String path;

    @Mock
    private Request request;

    @Mock
    private InputStream zipFile;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        response = new ResponseImpl();
        Path currentRelativePath = Paths.get("");
        path = currentRelativePath.toAbsolutePath().toString() + File.separator + TEST + File.separator;
    }

    @Test
    public void testValidateJsonFileStructureIfHasNotUndeployScriptField() throws FileNotFoundException {
        // given
        InputStream is = new FileInputStream(new File(path + "test6.zip"));
        when(request.getZipFile()).thenReturn(is);
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        validator.validateIfZipContainsJsonFile(path);

        // when
        validator.validateJsonFileStructure(path);

        // then
        assertThat(response.getViolations()).hasSize(1);
        boolean containsField = false;
        for(Violation violation : response.getViolations()) {
            containsField = violation.getMessage().contains(
                "Loaded JSON file does not have required fields."
            );
        }
        assertThat(containsField).isTrue();
    }

    @Test
    public void testValidateJsonFileStructureIfHasNotDeployScriptField() throws FileNotFoundException {
        // given
        InputStream is = new FileInputStream(new File(path + "test5.zip"));
        when(request.getZipFile()).thenReturn(is);
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        validator.validateIfZipContainsJsonFile(path);

        // when
        validator.validateJsonFileStructure(path);

        // then
        assertThat(response.getViolations()).hasSize(1);
        boolean containsField = false;
        for(Violation violation : response.getViolations()) {
            containsField = violation.getMessage().contains(
                "Loaded JSON file does not have required fields."
            );
        }
        assertThat(containsField).isTrue();
    }

    @Test
    public void testValidateJsonFileStructureIfHasNotStatusField() throws FileNotFoundException {
        // given
        InputStream is = new FileInputStream(new File(path + "test4.zip"));
        when(request.getZipFile()).thenReturn(is);
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        validator.validateIfZipContainsJsonFile(path);

        // when
        validator.validateJsonFileStructure(path);

        // then
        assertThat(response.getViolations()).hasSize(1);
        boolean containsField = false;
        for(Violation violation : response.getViolations()) {
            containsField = violation.getMessage().contains(
                "Loaded JSON file does not have required fields."
            );
        }
        assertThat(containsField).isTrue();
    }

    @Test
    public void testValidateJsonFileStructureIfHasNotServerIdField() throws FileNotFoundException {
        // given
        InputStream is = new FileInputStream(new File(path + "test3.zip"));
        when(request.getZipFile()).thenReturn(is);
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        validator.validateIfZipContainsJsonFile(path);

        // when
        validator.validateJsonFileStructure(path);

        // then
        assertThat(response.getViolations()).hasSize(1);
        boolean containsField = false;
        for(Violation violation : response.getViolations()) {
            containsField = violation.getMessage().contains(
                "Loaded JSON file does not have required fields."
            );
        }
        assertThat(containsField).isTrue();
    }

    @Test
    public void testValidateJsonFileStructureIfHasNotIsDefaultField() throws FileNotFoundException {
        // given
        InputStream is = new FileInputStream(new File(path + "test2.zip"));
        when(request.getZipFile()).thenReturn(is);
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        validator.validateIfZipContainsJsonFile(path);

        // when
        validator.validateJsonFileStructure(path);

        // then
        assertThat(response.getViolations()).hasSize(1);
        boolean containsField = false;
        for(Violation violation : response.getViolations()) {
            containsField = violation.getMessage().contains(
                "Loaded JSON file does not have required fields."
            );
        }
        assertThat(containsField).isTrue();
    }

    @Test
    public void testValidateJsonFileStructureIfHasNotNameField() throws FileNotFoundException {
        // given
        InputStream is = new FileInputStream(new File(path + "test1.zip"));
        when(request.getZipFile()).thenReturn(is);
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        validator.validateIfZipContainsJsonFile(path);

        // when
        validator.validateJsonFileStructure(path);

        // then
        assertThat(response.getViolations()).hasSize(1);
        boolean containsField = false;
        for(Violation violation : response.getViolations()) {
            containsField = violation.getMessage().contains(
                "Loaded JSON file does not have required fields."
            );
        }
        assertThat(containsField).isTrue();
    }

    @Test
    public void testValidateJsonFileStructureIfHasNotVersionField() throws FileNotFoundException {
        // given
        InputStream is = new FileInputStream(new File(path + "test10.zip"));
        when(request.getZipFile()).thenReturn(is);
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        validator.validateIfZipContainsJsonFile(path);

        // when
        validator.validateJsonFileStructure(path);

        // then
        assertThat(response.getViolations()).hasSize(1);
        boolean containsField = false;
        for(Violation violation : response.getViolations()) {
            containsField = violation.getMessage().contains(
                "Loaded JSON file does not have required fields."
            );
        }
        assertThat(containsField).isTrue();
    }

    @Test
    public void testValidateIfZipContainsJsonFile() throws IOException {
        // given
        String content = "application/rar";
        when(request.getContentType()).thenReturn(content);
        InputStream is = new FileInputStream(new File(path + "test9.zip"));
        when(request.getZipFile()).thenReturn(is);
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );

        // when
        validator.validateIfZipContainsJsonFile(path);

        // then
        for(Violation violation : response.getViolations()) {
            assertThat(violation.getMessage()).doesNotContain(
                "Loaded zip does not contains required JSON file."
            );
        }
    }

    @Test
    public void testValidateWithViolations() {
        // given
        String content = "application/rar";
        when(request.getContentType()).thenReturn(content);
        when(request.getZipFile()).thenReturn(zipFile);
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );

        // when
        validator.validate(path);

        // then
        assertThat(response.getViolations()).hasSize(2);
    }

    @Test
    public void testValidateIfZipContainsJsonFileWhenExOccurred() throws IOException {
        // given
        String content = "application/rar";
        when(request.getContentType()).thenReturn(content);
        when(request.getZipFile()).thenReturn(zipFile);
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        when(request.getZipFile()).thenReturn(null);

        // then
        expectedException.expect(EidIllegalStateException.class);
        expectedException.expectMessage(containsString("20170605:113002"));

        // when
        validator.validateIfZipContainsJsonFile(path);
    }

    @Test
    public void testValidateIfZipContainsJsonFileWhenErrorOccurred() throws IOException {
        // given
        String content = "application/rar";
        when(request.getContentType()).thenReturn(content);
        when(request.getZipFile()).thenReturn(zipFile);
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );

        // when
        validator.validateIfZipContainsJsonFile(path);

        // then
        assertThat(response.getViolations()).hasSize(1);
    }

    @Test
    public void testValidateJsonFileStructureIfHasFields() {
        // given
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        String id = "template";
        String serverId = "3050";
        String status = "created";
        String version = "345435.0.3";

        // when
        validator.validateJsonFileStructure(path);

        // then
        assertThat(validator.getId()).isEqualTo(id);
        assertThat(validator.getServerId()).isEqualTo(serverId);
        assertThat(validator.getStatus()).isEqualTo(status);
        assertThat(validator.getVersion()).isEqualTo(version);
        assertThat(validator.isDefault()).isTrue();
    }

    @Test
    public void testValidateScriptsFormat() {
        // given
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );

        // when
        validator.validateScriptsFormat(path);

        // then
        assertThat(response.getViolations()).hasSize(0);
    }

    @Test
    public void testValidateIfScriptsExist() {
        // given
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );

        // when
        validator.validateIfScriptsExist(path);

        // then
        assertThat(response.getViolations()).hasSize(0);
    }

    @Test
    public void testValidateZipWhenContentIsNotZip() {
        // given
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        String content = "application/rar";
        when(request.getContentType()).thenReturn(content);

        // when
        validator.validateZip();

        // then
        assertThat(response.getViolations()).hasSize(1);
    }

    @Test
    public void testValidateZipWhenContentIsZip() {
        // given
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        String content = "application/zip";
        when(request.getContentType()).thenReturn(content);

        // when
        validator.validateZip();

        // then
        assertThat(response.getViolations()).hasSize(0);
    }

    @Test
    public void testGetId() throws Exception {
        // when
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        String actual = validator.getId();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(id);
    }

    @Test
    public void testIsDefault() throws Exception {
        // when
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        boolean actual = validator.isDefault();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    public void testGetStatus() throws Exception {
        // when
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        String actual = validator.getStatus();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(status);
    }

    @Test
    public void testGetServerId() throws Exception {
        // when
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        String actual = validator.getServerId();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(serverId);
    }

    @Test
    public void testGetVersion() throws Exception {
        // when
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName,
            containsJson
        );
        String actual = validator.getVersion();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(version);
    }

    @Test
    public void testBuilder() throws Exception {
        // when
        Validator validatorBuilder = Validator.builder()
            .id(id)
            .isDefault(true)
            .jsonFilename(jsonName)
            .request(request)
            .response(response)
            .serverId(serverId)
            .status(status)
            .version(version)
            .build();

        // then
        assertThat(validatorBuilder).isNotNull();
    }

    private static final class ResponseImpl implements Response {

        @Getter
        private final Collection<Violation> violations = new HashSet<>();
        @Getter
        private String id;
        @Getter
        private String status;
        @Getter
        private boolean isDefault;
        @Getter
        private String serverId;
        @Getter
        private String version;

        @Override
        public void addViolation(Violation violation) {
            violations.add(violation);
        }

        @Override
        public boolean isSuccessful() {
            return violations.isEmpty();
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        @Override
        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public void setDefault(boolean isDefault) {
            this.isDefault = isDefault;
        }

        @Override
        public void setServerId(String versionId) {
            this.serverId = serverId;
        }

        @Override
        public void setVersion(String version) {
            this.version = version;
        }
    }

}
