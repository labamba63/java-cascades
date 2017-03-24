package pl.gov.coi.cascades.server.persistance.hibernate.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 23.03.17.
 */
@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class DatabaseInstance {

    @GeneratedValue
    @Id
    private String databaseId;

    @Column
    private String templateId;

    @Column
    private String databaseType;

    @Column
    private String instanceName;

    @Column
    private int reuseTimes;

    @Column
    private String databaseName;

    @Embedded
    private Credentials credentials;

    @Embedded
    private NetworkBind networkBind;

    @Enumerated(EnumType.STRING)
    private DatabaseStatus status;

    @Column
    private Date created;

}
