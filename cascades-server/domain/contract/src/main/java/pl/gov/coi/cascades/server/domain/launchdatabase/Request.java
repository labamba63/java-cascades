package pl.gov.coi.cascades.server.domain.launchdatabase;

import lombok.Builder;
import lombok.Getter;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.User;
import pl.wavesoftware.eid.utils.EidPreconditions;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Class for providing request information for launching new database instance.
 */
@Builder
public class Request {

    @Getter
    private final String type;
    @Getter
    private final User user;
    @Nullable
    private final Template template;
    @Nullable
    private final String instanceName;

    /**
     * Default argument constructor.
     *
     * @param type Name of type class.
     * @param user          User of the database.
     * @param template    Given id of template (Optional).
     * @param instanceName  Given name of database instance (Optional).
     */
    public Request(String type,
                   User user,
                   @Nullable Template template,
                   @Nullable String instanceName) {
        this.type = EidPreconditions.checkNotNull(type, "20170228:153927");
        this.user = EidPreconditions.checkNotNull(user, "20170228:153954");
        this.template = template;
        this.instanceName = instanceName;
    }

    /**
     * Gets an optional id of template.
     *
     * @return Optional id of template.
     */
    public Optional<String> getTemplateId() {
        String idAsString = Optional.ofNullable(template)
            .map(Template::getId)
            .orElse(null);
        return Optional.ofNullable(idAsString);
    }

    /**
     * Gets an instance name of database.
     *
     * @return Optional instance name of database.
     */
    public Optional<String> getInstanceName() {
        return Optional.ofNullable(instanceName);
    }

}
