package pl.gov.coi.cascades.server.domain.deletetemplate;

import pl.gov.coi.cascades.contract.service.Violation;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public interface Response {

    boolean isSuccessful();

    void addViolation(Violation violation);

    Iterable<Violation> getViolations();

}
