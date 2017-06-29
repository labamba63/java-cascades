package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.Getter;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 07.06.17.
 */
public class UseCaseImplTest {

    private static final String TEST = "src/test/resources";
    private static final String ZIP_EXTENSION = ".zip";

    @Mock
    private Request request;

    @Mock
    private Upload upload;

    @Mock
    private TemplateIdGateway templateIdGateway;

    @Mock
    private DatabaseTemplateGateway databaseTemplateGateway;

    @Mock
    private TemplateIdGeneratorService templateIdGeneratorService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @After
    public void after() {
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString() + File.separator + "src/test/resources" + File.separator;
        File dir = new File(path);
        checkNotNull(dir.listFiles(), "20170623:124049");
        for (File file : dir.listFiles()) {
            if (!file.getName().endsWith(ZIP_EXTENSION) && !file.isDirectory()) {
                file.delete();
            }
        }
    }

    @Test
    public void testExecuteWhenThereAreErrors() throws IOException {
        // given
        String content = "application/zip";
        when(request.getUpload()).thenReturn(upload);
        when(request.getUpload().getContentType()).thenReturn(content);
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString() + File.separator + TEST + File.separator;
        UseCaseImpl useCase = UseCaseImpl.builder()
            .templateIdGateway(templateIdGateway)
            .databaseTemplateGateway(databaseTemplateGateway)
            .templateIdGeneratorService(templateIdGeneratorService)
            .build();
        ResponseImpl response = new ResponseImpl();
        InputStream is = new FileInputStream(new File(path + "test15.zip"));
        when(request.getUpload().getInputStream()).thenReturn(is);

        // when
        useCase.execute(request, response);

        // then
        assertThat(response.getViolations()).hasSize(1);
        assertThat(response.isSuccessful()).isFalse();
        boolean containsField = false;
        for(Violation violation : response.getViolations()) {
            containsField = violation.getMessage().contains(
                "Loaded zip does not contain required JSON file."
            );
        }
        assertThat(containsField).isTrue();
    }

    @Test
    public void testExecuteWhenThereIsNoError() throws IOException {
        // given
        String name = "template";
        String serverId = "3050";
        String status = TemplateIdStatus.CREATED.name();
        String version = "345435.0.3";
        String content = "application/zip";
        when(request.getUpload()).thenReturn(upload);
        when(request.getUpload().getContentType()).thenReturn(content);
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString() + File.separator + TEST + File.separator;
        TemplateIdGeneratorService templateIdGeneratorService = new TemplateIdGeneratorService();
        UseCaseImpl useCase = UseCaseImpl.builder()
            .templateIdGateway(templateIdGateway)
            .databaseTemplateGateway(databaseTemplateGateway)
            .templateIdGeneratorService(templateIdGeneratorService)
            .build();
        ResponseImpl response = new ResponseImpl();
        InputStream is = new FileInputStream(new File(path + "test13.zip"));
        when(request.getUpload().getInputStream()).thenReturn(is);

        // when
        useCase.execute(request, response);

        // then
        assertThat(response.getViolations()).hasSize(0);
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.isDefault()).isTrue();
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getServerId()).isEqualTo(serverId);
        assertThat(response.getStatus()).isEqualTo(status);
        assertThat(response.getVersion()).isEqualTo(version);
    }

    @Test
    public void testBuilder() throws Exception {
        // when
        UseCaseImpl useCase = UseCaseImpl.builder()
            .templateIdGateway(templateIdGateway)
            .build();

        // then
        assertThat(useCase).isNotNull();
    }

    private static final class ResponseImpl implements Response {

        @Getter
        private final Collection<Violation> violations = new HashSet<>();
        @Getter
        private String id;
        @Getter
        private String name;
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
        public void setName(String name) {
            this.name = name;
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
        public void setServerId(String serverId) {
            this.serverId = serverId;
        }

        @Override
        public void setVersion(String version) {
            this.version = version;
        }
    }

}
