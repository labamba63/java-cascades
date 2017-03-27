package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import lombok.Setter;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.DatabaseTypeDTO;
import pl.gov.coi.cascades.server.domain.Error;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsImpl;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Credentials;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.NetworkBind;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus;
import pl.wavesoftware.eid.utils.EidPreconditions;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Arrays;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
public class DatabaseInstanceMapper implements Mapper<DatabaseInstance, pl.gov.coi.cascades.server.domain.DatabaseInstance> {

    private final DatabaseTypeClassNameService databaseTypeClassNameService;

    @Inject
    public DatabaseInstanceMapper(DatabaseTypeClassNameService databaseTypeClassNameService) {
        this.databaseTypeClassNameService = databaseTypeClassNameService;
    }

    @Override
    public DatabaseInstance toHibernateEntity(pl.gov.coi.cascades.server.domain.DatabaseInstance databaseInstance) {
        Credentials credentials = new Credentials();
        credentials.setPassword(Arrays.toString(databaseInstance.getCredentials().getPassword()));
        credentials.setUsername(databaseInstance.getCredentials().getUsername());

        NetworkBind networkBind = new NetworkBind();
        networkBind.setHost(databaseInstance.getNetworkBind().getHost());
        networkBind.setPort(databaseInstance.getNetworkBind().getPort());

        DatabaseStatus databaseStatus = databaseInstance.getStatus()
            .equals(pl.gov.coi.cascades.server.domain.DatabaseStatus.LAUNCHED)
            ? DatabaseStatus.LAUNCHED
            : DatabaseStatus.DELETED;

        DatabaseInstance instance = new DatabaseInstance();
        instance.setDatabaseId(databaseInstance.getDatabaseId().getId());
        instance.setTemplateId(databaseInstance.getTemplateId().getId());
        instance.setDatabaseType(databaseInstance.getDatabaseType().getName());
        instance.setInstanceName(databaseInstance.getInstanceName());
        instance.setReuseTimes(databaseInstance.getReuseTimes());
        instance.setDatabaseName(databaseInstance.getDatabaseName());
        instance.setCredentials(credentials);
        instance.setNetworkBind(networkBind);
        instance.setStatus(databaseStatus);
        instance.setCreated(databaseInstance.getCreated());

        return instance;
    }

    @Override
    public pl.gov.coi.cascades.server.domain.DatabaseInstance fromHibernateEntity(DatabaseInstance databaseInstance) {
        EidPreconditions.checkNotNull(databaseInstance.getDatabaseId(), "20170324:155926");
        EidPreconditions.checkNotNull(databaseInstance.getTemplateId(), "20170324:155955");
        EidPreconditions.checkNotNull(databaseInstance.getDatabaseType(), "20170324:160730");
        EidPreconditions.checkNotNull(databaseInstance.getInstanceName(), "20170327:100935");
        EidPreconditions.checkNotNull(databaseInstance.getReuseTimes(), "20170327:100959");
        EidPreconditions.checkNotNull(databaseInstance.getDatabaseName(), "20170327:101019");
        EidPreconditions.checkNotNull(databaseInstance.getCredentials(), "20170327:083701");
        EidPreconditions.checkNotNull(databaseInstance.getNetworkBind(), "20170327:083729");
        EidPreconditions.checkNotNull(databaseInstance.getStatus(), "20170327:083800");
        EidPreconditions.checkNotNull(databaseInstance.getCreated(), "20170327:101053");
        EidPreconditions.checkNotNull(databaseInstance.getNetworkBind().getPort(), "20170327:084555");

        DatabaseId databaseId = new DatabaseId(databaseInstance.getDatabaseId());
        TemplateId templateId = new TemplateId(databaseInstance.getTemplateId());
        DatabaseTypeDTO databaseTypeDTO = databaseTypeClassNameService.getDatabaseType(databaseInstance.getDatabaseType());
        DatabaseType databaseType = new DtoFetcher(databaseTypeDTO).getDatabaseType();
        UsernameAndPasswordCredentials credentials = new UsernameAndPasswordCredentialsImpl(
            databaseInstance.getCredentials().getUsername(),
            databaseInstance.getCredentials().getPassword().toCharArray()
        );
        pl.gov.coi.cascades.contract.domain.NetworkBind networkBind = new NetworkBindImpl(
            databaseInstance.getNetworkBind().getHost(),
            databaseInstance.getNetworkBind().getPort()
        );
        pl.gov.coi.cascades.server.domain.DatabaseStatus databaseStatus = databaseInstance.getStatus()
            .equals(DatabaseStatus.DELETED)
            ? pl.gov.coi.cascades.server.domain.DatabaseStatus.DELETED
            : pl.gov.coi.cascades.server.domain.DatabaseStatus.LAUNCHED;

        return new pl.gov.coi.cascades.server.domain.DatabaseInstance(
            databaseId,
            templateId,
            databaseType,
            databaseInstance.getInstanceName(),
            databaseInstance.getReuseTimes(),
            databaseInstance.getDatabaseName(),
            credentials,
            networkBind,
            databaseStatus,
            databaseInstance.getCreated()
        );
    }

    private static final class NetworkBindImpl implements pl.gov.coi.cascades.contract.domain.NetworkBind {
        private String host;
        private int port;

        NetworkBindImpl(String host,
                               int port) {
            this.host = host;
            this.port = port;
        }

        @Override

        public String getHost() {
            return host;
        }

        @Override
        public int getPort() {
            return port;
        }
    }

    private static final class DtoFetcher {
        @Setter
        private Error error;
        @Setter
        private DatabaseType databaseType;

        private DtoFetcher(DatabaseTypeDTO dto) {
            dto.onFail(this::setError)
                .onSuccess(this::setDatabaseType)
                .resolve();
        }

        @Nullable
        Error getError() {
            return error;
        }

        @Nullable
        DatabaseType getDatabaseType() {
            return databaseType;
        }
    }

}
