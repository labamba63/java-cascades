package pl.gov.coi.cascades.server.persistance.stub;

import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.User;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
final class DatabaseLimitGatewayStub implements DatabaseLimitGateway {

    private static final int DEFAULT_GLOBAL_LIMIT = 100;
    private static final int DEFAULT_USER_LIMIT = 5;
    private int globalLimit = DEFAULT_GLOBAL_LIMIT;
    private int userLimit = DEFAULT_USER_LIMIT;

    public void setGlobalLimit(int globalLimit) {
        this.globalLimit = globalLimit;
    }

    public void setUserLimit(int userLimit) {
        this.userLimit = userLimit;
    }

    @Override
    public boolean isPersonalLimitExceeded(User user) {
        return user.getDatabasesSize() >= userLimit;
    }

    @Override
    public int getPersonalLimitPerUser(User user) {
        return userLimit;
    }

    @Override
    public boolean isGlobalLimitExceeded() {
        return false;
    }

    @Override
    public int getGlobalLimit() {
        return globalLimit;
    }
}
