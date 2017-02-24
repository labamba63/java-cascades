package pl.gov.coi.cascades.server.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import java.util.Date;

@RequiredArgsConstructor
public class DatabaseInstance {

    @Getter
    private final DatabaseId databaseId;
    @Getter
    private final TemplateId templateId;
    @Getter
    private final DatabaseType databaseType;
    @Getter
    private final String instanceName;
    @Getter
    private final int reuseTimes;
    @Getter
    private final String databaseName;
    @Getter
    private final UsernameAndPasswordCredentials credentials;
    @Getter
    private final NetworkBind networkBind;
    private final Date created;

    /**
     * Method gives date of database creation.
     *
     * @return Date of database creation.
     */
    public Date getCreated() {
        return Date.class.cast(created.clone());
    }

    /**
     * Setter for network bind.
     *
     * @param networkBind Given network bind.
     * @return Instance of database.
     */
    public DatabaseInstance setNetworkBind(NetworkBind networkBind) {
        throw new UnsupportedOperationException();
    }

}
