package pl.gov.coi.cascades.server.domain.launchdatabase;

import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 03.03.17.
 */
@RequiredArgsConstructor
public class UsernameAndPasswordCredentialsImpl implements UsernameAndPasswordCredentials {

    private static final long serialVersionUID = 42L;
    private final String username;
    private final char[] password;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public char[] getPassword() {
        return password.clone();
    }
}
