package pl.gov.coi.cascades.server.domain.deletetemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.gov.coi.cascades.server.domain.ViolationImpl;

import javax.annotation.Nullable;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@Builder
@AllArgsConstructor
class Validator {

    private static final String PROPERTY_PATH_TEMPLATE_GENERATED_ID = "templateGeneratedId";
    private final Response response;
    private final Request request;
    @Nullable
    private final String templateGeneratedId;
    @Nullable
    private final Template template;

    boolean validate() {
        boolean isTemplateGeneratedIdPresent = validateTemplateGeneratedId();
        if (isTemplateGeneratedIdPresent) {
            validateIfTemplateGeneratedIdExistInDatabase();
            validateIfTemplateHasStatusDeleted();
        }
        return response.isSuccessful();
    }

    private void validateIfTemplateGeneratedIdExistInDatabase() {
        if (template == null) {
            Violation violationMessage = new ViolationImpl(
                "Given id of template doesn't exist in database.",
                PROPERTY_PATH_TEMPLATE_GENERATED_ID
            );
            response.addViolation(violationMessage);
        }
    }


    private void validateIfTemplateHasStatusDeleted() {
        if (template != null && template.getStatus() != null) {
            if (template.getStatus().equals(TemplateIdStatus.DELETED)) {
                Violation violationMessage = new ViolationImpl(
                    "Given id of template has status as DELETED.",
                    PROPERTY_PATH_TEMPLATE_GENERATED_ID
                );
                response.addViolation(violationMessage);
            }
        }
    }

    private boolean validateTemplateGeneratedId() {
        if (StringUtils.isBlank(templateGeneratedId)) {
            newError(
                PROPERTY_PATH_TEMPLATE_GENERATED_ID,
                "Given template generated id is not present."
            );
            return false;
        }
        return true;
    }

    private void newError(String path, String message, Object... parameters) {
        response.addViolation(
            new ViolationImpl(
                String.format(message, parameters),
                path
            )
        );
    }

}
