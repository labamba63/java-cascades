package pl.gov.coi.cascades.server.domain.deletedatabase;

import pl.gov.coi.cascades.server.domain.Error;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 09.03.17.
 */
public class DeleteLaunchedDatabaseInstanceResponse {

    boolean isSuccessful() {
        return false;
    }

    void addError(Error error) {

    }

}
