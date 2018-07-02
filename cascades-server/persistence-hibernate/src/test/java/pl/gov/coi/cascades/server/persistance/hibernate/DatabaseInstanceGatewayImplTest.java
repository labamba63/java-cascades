package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.DatabaseInstanceMapper;

import java.util.List;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class DatabaseInstanceGatewayImplTest {

    @Mock
    private DatabaseInstanceMapper databaseInstanceMapper;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private DatabaseInstanceGatewayImpl databaseInstanceGateway;

    @Before
    public void init() {
        databaseInstanceGateway = new DatabaseInstanceGatewayImpl(
            databaseInstanceMapper
        );
    }

    public void shouldLaunchDatabase() {
        //when
        databaseInstanceGateway.save(getDatabaseInstance());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldGetRemoteServerId() {
        //when
        databaseInstanceGateway.getRemoteServerId();
    }

    private DatabaseInstance getDatabaseInstance() {
        return DatabaseInstance.builder().build();
    }
}