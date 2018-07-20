package pl.gov.coi.cascades.server.domain.deletetemplate;

import lombok.Builder;
import lombok.Getter;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.User;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@Builder
public class Request {

    @Getter
    private final String templateGeneratedId;

    public Request(String templateGeneratedId) {
        this.templateGeneratedId = templateGeneratedId;
    }
}
