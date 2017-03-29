package pl.gov.coi.cascades.server.persistance.stub;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.gov.coi.cascades.server.Environment;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.domain.User;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
@Profile(Environment.DEVELOPMENT_NAME)
@Configuration
class PersistanceStubConfiguration {

    public static final String STUB_DATABASE = "stub-database";

    @Bean
    @Named(STUB_DATABASE)
    Map<Object, User> produceStubDatabase() {
        return new HashMap<>();
    }

    @ConditionalOnMissingBean
    @Bean
    TemplateIdGateway produceTemplateIdGateway() {
        return new TemplateIdGatewayStub();
    }

    @ConditionalOnMissingBean
    @Bean
    DatabaseInstanceGateway produceDatabaseInstanceGateway() {
        return new DatabaseInstanceGatewayStub();
    }

    @ConditionalOnMissingBean
    @Bean
    UserGatewayStub produceUserGateway(@Named(STUB_DATABASE) Map<Object, User> database) {
        return new UserGatewayStub(database);
    }

    @ConditionalOnMissingBean
    @Bean
    DatabaseLimitGatewayStub produceDatabaseLimitGateway() {
        return new DatabaseLimitGatewayStub();
    }

    @ConditionalOnMissingBean
    @Bean
    DatabaseIdGateway produceDatabaseIdGateway() {
        return new DatabaseIdGatewayStub();
    }

    @ConditionalOnMissingBean
    @Bean
    DatabaseTypeStub produceDatabaseType() {
        return new DatabaseTypeStub();
    }

}
