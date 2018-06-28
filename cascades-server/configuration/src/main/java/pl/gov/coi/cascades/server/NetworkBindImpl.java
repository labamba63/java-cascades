package pl.gov.coi.cascades.server;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.gov.coi.cascades.contract.domain.NetworkBind;

@AllArgsConstructor
public class NetworkBindImpl implements NetworkBind {

    @Getter
    @Setter
    private String host;

    @Getter
    @Setter
    private int port;
}
