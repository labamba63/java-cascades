package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.annotation.WillNotClose;
import java.io.InputStream;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.06.17.
 */
@Builder
@AllArgsConstructor
public class Upload {
    @Getter
    @WillNotClose
    private final InputStream inputStream;
    @Getter
    private final Long size;
    @Getter
    private final String contentType;

}
