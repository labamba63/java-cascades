package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.ConnectionStringProducer;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.DatabaseTypeDTO;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Credentials;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.NetworkBind;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseIdGatewayStub;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.03.17.
 */
public class DatabaseInstanceMapperTest {

    private static final String PASSWORD = "12345678";
    private static final String USERNAME = "Ben Affleck";
    private static final String HOST = "db01.lab.internal";
    private static final int PORT = 5432;
    private static final String DATABASE_ID = "19";
    private static final Long DATABASE_ID_AS_LONG = 45L;
    private static final String DATABASE_TYPE = "stub";
    private static final String INSTANCE_NAME = "ora12e34";
    private static final String DATABASE_NAME = "oracle 12c";
    private static final String SERVER_ID = "5v36y5646";
    private static final String VERSION = "0.0.1";
    private static final long TEMPLATE_ID = 8958395489L;
    private static final long ID = -2174612783412L;
    private static final String TEMPLATE_ID_NAME = "oracle_template";
    private static final String GENERATED_ID = "ffg456js";
    private Date created = Date.from(Instant.now());

    @Mock
    private DatabaseTypeClassNameService databaseTypeClassNameService;

    @Mock
    private DatabaseTypeDTO databaseTypeDTO;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testToHibernateEntity() throws Exception {
        // given
        DatabaseInstanceMapper databaseInstanceMapper = new DatabaseInstanceMapper(
            databaseTypeClassNameService
        );

        // when
        DatabaseInstance actual = databaseInstanceMapper.toHibernateEntity(DatabaseIdGatewayStub.INSTANCE1);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(DATABASE_ID_AS_LONG);
        assertThat(actual.getTemplate()
            .getName())
            .isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getTemplate().getName());
        assertThat(actual.getTemplate()
            .getGeneratedId())
            .isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getTemplate().getId());
        assertThat(actual.getTemplate()
            .getStatus().name())
            .isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getTemplate().getStatus().name());
        assertThat(actual.getTemplate()
            .getServerId())
            .isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getTemplate().getServerId());
        assertThat(actual.getTemplate()
            .isDefault())
            .isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getTemplate().isDefault());
        assertThat(actual.getTemplate()
            .getVersion())
            .isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getTemplate().getVersion());
        assertThat(actual.getType()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getDatabaseType().getName());
        assertThat(actual.getInstanceName()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getInstanceName());
        assertThat(actual.getReuseTimes()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getReuseTimes());
        assertThat(actual.getDatabaseName()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getDatabaseName());
        assertThat(actual.getCredentials()
            .getPassword())
            .isEqualTo(Arrays.toString(DatabaseIdGatewayStub.INSTANCE1.getCredentials().getPassword()));
        assertThat(actual.getCredentials()
            .getUsername())
            .isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getCredentials().getUsername());
        assertThat(actual.getNetworkBind()
            .getHost())
            .isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getNetworkBind().getHost());
        assertThat(actual.getNetworkBind()
            .getPort())
            .isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getNetworkBind().getPort());
        assertThat(actual.getStatus().name()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getStatus().name());
        assertThat(actual.getCreated()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getCreated());
    }

    @Test
    public void testFromHibernateEntity() throws Exception {
        // given
        when(databaseTypeClassNameService.getDatabaseType(anyString())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTO);
        doNothing().when(databaseTypeDTO).resolve();
        DatabaseInstanceMapper databaseInstanceMapper = new DatabaseInstanceMapper(
            databaseTypeClassNameService
        );
        Credentials credentials = new Credentials();
        credentials.setPassword(PASSWORD);
        credentials.setUsername(USERNAME);
        NetworkBind networkBind = new NetworkBind();
        networkBind.setHost(HOST);
        networkBind.setPort(PORT);
        Template template = new Template();
        template.setDefault(false);
        template.setName(TEMPLATE_ID_NAME);
        template.setServerId(SERVER_ID);
        template.setVersion(VERSION);
        template.setId(TEMPLATE_ID);
        template.setGeneratedId(GENERATED_ID);
        template.setStatus(TemplateIdStatus.CREATED);
        pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance hibernateInstance
            = new pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance();
        hibernateInstance.setId(DATABASE_ID_AS_LONG);
        hibernateInstance.setTemplate(template);
        hibernateInstance.setType(DATABASE_TYPE);
        hibernateInstance.setInstanceName(INSTANCE_NAME);
        hibernateInstance.setReuseTimes(1);
        hibernateInstance.setDatabaseName(DATABASE_NAME);
        hibernateInstance.setCredentials(credentials);
        hibernateInstance.setNetworkBind(networkBind);
        hibernateInstance.setStatus(pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus.LAUNCHED);
        hibernateInstance.setCreated(created);

        // when
        pl.gov.coi.cascades.server.domain.DatabaseInstance actual = databaseInstanceMapper.fromHibernateEntity(hibernateInstance);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getDatabaseId().getId()).isEqualTo(DATABASE_ID);
        assertThat(actual.getTemplate().getId()).isEqualTo(GENERATED_ID);
        assertThat(actual.getTemplate().isDefault()).isEqualTo(template.isDefault());
        assertThat(actual.getTemplate().getServerId()).isEqualTo(template.getServerId());
        assertThat(actual.getTemplate().getStatus().name()).isEqualTo(template.getStatus().name());
        assertThat(actual.getTemplate().getVersion()).isEqualTo(template.getVersion());
        assertThat(actual.getDatabaseType()).isEqualTo(null);
        assertThat(actual.getInstanceName()).isEqualTo(INSTANCE_NAME);
        assertThat(actual.getReuseTimes()).isEqualTo(1);
        assertThat(actual.getDatabaseName()).isEqualTo(DATABASE_NAME);
        assertThat(actual.getCredentials().getPassword()).isEqualTo(PASSWORD.toCharArray());
        assertThat(actual.getCredentials().getUsername()).isEqualTo(USERNAME);
        assertThat(actual.getNetworkBind().getHost()).isEqualTo(HOST);
        assertThat(actual.getNetworkBind().getPort()).isEqualTo(PORT);
        assertThat(actual.getStatus()).isEqualTo(DatabaseStatus.LAUNCHED);
        assertThat(actual.getCreated()).isEqualTo(created);
    }

    @Test
    public void testDatabaseIdMapping() {
        // given
        Credentials credentials = new Credentials();
        credentials.setPassword(PASSWORD);
        credentials.setUsername(USERNAME);
        NetworkBind networkBind = new NetworkBind();
        networkBind.setHost(HOST);
        networkBind.setPort(PORT);
        pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance hibernateInstance
            = new pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance();
        hibernateInstance.setId(DATABASE_ID_AS_LONG);
        Template template = new Template();
        template.setDefault(false);
        template.setGeneratedId(GENERATED_ID);
        template.setName(TEMPLATE_ID_NAME);
        template.setServerId(SERVER_ID);
        template.setId(TEMPLATE_ID);
        template.setStatus(TemplateIdStatus.CREATED);
        template.setVersion(VERSION);
        hibernateInstance.setTemplate(template);
        hibernateInstance.setType(DATABASE_TYPE);
        hibernateInstance.setInstanceName(INSTANCE_NAME);
        hibernateInstance.setReuseTimes(1);
        hibernateInstance.setDatabaseName(DATABASE_NAME);
        hibernateInstance.setCredentials(credentials);
        hibernateInstance.setNetworkBind(networkBind);
        hibernateInstance.setStatus(pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus.DELETED);
        hibernateInstance.setCreated(created);
        hibernateInstance.setId(ID);
        DatabaseInstanceMapper databaseInstanceMapper = new DatabaseInstanceMapper(
            databaseTypeClassNameService
        );
        when(databaseTypeClassNameService.getDatabaseType(anyString()))
            .thenReturn(new DatabaseTypeDTOStub(DATABASE_TYPE));

        // when
        pl.gov.coi.cascades.server.domain.DatabaseInstance model =
            databaseInstanceMapper.fromHibernateEntity(hibernateInstance);
        DatabaseInstance mapped = databaseInstanceMapper.toHibernateEntity(model);

        // then
        assertThat(mapped).isNotSameAs(hibernateInstance);
        assertThat(mapped.getId()).isEqualTo(ID);
        assertThat(model.getDatabaseId().getId()).isEqualTo("-rr04ayic");
    }

    private static final class DatabaseTypeDTOStub extends DatabaseTypeDTO {
        private DatabaseTypeDTOStub(String type) {
            super(new DatabaseType() {

                @Override
                public String getName() {
                    return type;
                }

                @Override
                public ConnectionStringProducer getConnectionStringProducer() {
                    return null;
                }
            });
        }
    }

}
