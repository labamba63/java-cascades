package pl.gov.coi.cascades.server.domain.deletetemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;

import java.util.Optional;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@Builder
@AllArgsConstructor
public class UseCaseImpl implements UseCase {

    private final DeleteTemplateGatewayFacade deleteTemplateGatewayFacade;

    /**
     * This method takes a pair of request and response objects. That ensures decoupling of presentation from domain.
     *
     * @param request Given request of deleting template from database.
     * @param response Given response of deleting template from database.
     */
    @Override
    public void execute(Request request, Response response) {
        Optional<Template> template = deleteTemplateGatewayFacade.findTemplate(request.getTemplateGeneratedId());

        Validator.ValidatorBuilder validatorBuilder = Validator.builder()
            .request(request)
            .response(response);

        validatorBuilder.templateGeneratedId(request.getTemplateGeneratedId());
        template.ifPresent(validatorBuilder::template);

        Validator validator = validatorBuilder.build();

        if (validator.validate()) {
            template.ifPresent(this::succeedResponse);
        }
    }

    private void succeedResponse(Template template) {
        Template templateNewStatus = Template.builder()
            .id(template.getId())
            .generatedId(template.getGeneratedId())
            .name(template.getName())
            .status(TemplateIdStatus.DELETED)
            .isDefault(template.isDefault())
            .serverId(template.getServerId())
            .version(template.getVersion())
            .build();

        deleteTemplateGatewayFacade.deleteTemplate(templateNewStatus);
    }
}
