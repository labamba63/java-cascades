package pl.gov.coi.cascades.server.presentation.deletetemplate;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.gov.coi.cascades.server.domain.deletetemplate.Request;
import pl.gov.coi.cascades.server.domain.deletetemplate.Response;
import pl.gov.coi.cascades.server.domain.deletetemplate.UseCase;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@RequestMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Controller
public class DeleteTemplateController {

    private final UseCase useCase;

    public DeleteTemplateController(UseCase useCase) {
        this.useCase = useCase;
    }

    @RequestMapping(
        value = "/template/{id}",
        method = RequestMethod.DELETE
    )
    public ResponseEntity<Response> deleteDatabase(@PathVariable("id") String templateGeneratedIdAsString) {

        Request.RequestBuilder requestBuilder = Request.builder()
            .templateGeneratedId(templateGeneratedIdAsString);

        Request request = requestBuilder.build();
        Presenter presenter = new Presenter();
        useCase.execute(
            request,
            presenter
        );

        return presenter.createModel();
    }

}
