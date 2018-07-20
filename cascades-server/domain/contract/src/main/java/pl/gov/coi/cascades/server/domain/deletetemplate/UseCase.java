package pl.gov.coi.cascades.server.domain.deletetemplate;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@FunctionalInterface
public interface UseCase {

    void execute(Request request, Response response);

}
