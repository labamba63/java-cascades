package pl.gov.coi.cascades.server.persistance.stub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
@Configuration
class PersistanceStubConfiguration {

    @Bean
    TemplateIdGateway produceTemplateIdGateway() {
        return new TemplateIdGatewayStub();
    }

    @Bean
    DatabaseInstanceGateway produceDatabaseInstanceGateway() {
        return new DatabaseInstanceGatewayStub();
    }

    @Bean
    UserGatewayStub produceUserGateway() {
        return new UserGatewayStub();
    }

    @Bean
    DatabaseLimitGatewayStub produceDatabaseLimitGateway() {
        return new DatabaseLimitGatewayStub();
    }

    @Bean
    DatabaseIdGateway produceDatabaseIdGateway() {
        return new DatabaseIdGatewayStub();
    }

    @Bean
    DatabaseTypeStub produceDatabaseType() {
        return new DatabaseTypeStub();
    }

}