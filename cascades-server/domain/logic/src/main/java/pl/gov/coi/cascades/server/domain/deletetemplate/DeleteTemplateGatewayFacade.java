package pl.gov.coi.cascades.server.domain.deletetemplate;

import lombok.AllArgsConstructor;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;

import java.util.Optional;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@AllArgsConstructor
public class DeleteTemplateGatewayFacade {

    private final TemplateIdGateway templateIdGateway;
    private final DatabaseTemplateGateway databaseTemplateGateway;

    Optional<Template> findTemplate(String templateGeneratedId) {
        return templateIdGateway.find(templateGeneratedId);
    }

    void deleteTemplate(Template template) {
        setDeletedStatusInInformationDatabase(template);
        deleteInDatabase(template);
    }

    private void setDeletedStatusInInformationDatabase(Template template) {
        templateIdGateway.deleteTemplate(template);
    }

    private void deleteInDatabase(Template template) {
        databaseTemplateGateway.deleteTemplate(template);
    }
}
