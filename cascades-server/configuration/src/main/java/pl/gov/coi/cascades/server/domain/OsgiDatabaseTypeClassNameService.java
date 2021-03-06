package pl.gov.coi.cascades.server.domain;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.server.OsgiBeanLocator;
import pl.wavesoftware.eid.exceptions.Eid;

import javax.inject.Inject;

/**
 * Service for database class type name.
 */
public class OsgiDatabaseTypeClassNameService implements DatabaseTypeClassNameService {

    @VisibleForTesting
    protected static final String ERROR_MESSAGE_FORMAT = "Given database type name: %s is not available. " +
        "Maybe you should install a plugin with that implementation.";
    @VisibleForTesting
    protected static final String FOUND_DATABASE_TYPE_LOG_FORMAT = "Found database type implementation: %s " +
        "for given type name: %s";
    private static final String PROPERTY_PATH_TYPE = "type";
    private final Logger logger;
    private final OsgiBeanLocator osgiBeanLocator;

    OsgiDatabaseTypeClassNameService(OsgiBeanLocator osgiBeanLocator, Logger logger) {
        this.logger = logger;
        this.osgiBeanLocator = osgiBeanLocator;
    }

    @Inject
    public OsgiDatabaseTypeClassNameService(OsgiBeanLocator osgiBeanLocator) {
        this(
            osgiBeanLocator,
            LoggerFactory.getLogger(OsgiDatabaseTypeClassNameService.class)
        );
    }

    @Override
    public DatabaseTypeDTO getDatabaseType(String type) {
        Iterable<DatabaseType> databaseTypes = osgiBeanLocator.getBeans(DatabaseType.class);
        for (DatabaseType databaseType : databaseTypes) {
            if (matches(type, databaseType)) {
                if (logger.isDebugEnabled()) {
                    logger.debug(new Eid("20170419:000325").makeLogMessage(
                        FOUND_DATABASE_TYPE_LOG_FORMAT,
                        databaseType.getClass().getName(),
                        type
                    ));
                }
                return new DatabaseTypeDTO(databaseType);
            }
        }
        return newErrorDTO(ERROR_MESSAGE_FORMAT, type);
    }

    private static boolean matches(String typeName, DatabaseType databaseType) {
        return matchesByName(typeName, databaseType)
            || matchesByFQCN(typeName, databaseType);
    }

    private static boolean matchesByName(String typeName, DatabaseType databaseType) {
        return databaseType.getName().equalsIgnoreCase(typeName);
    }

    private static boolean matchesByFQCN(String typeFQCN, DatabaseType databaseType) {
        String databaseTypeFQCN = databaseType.getClass().getName();
        return databaseTypeFQCN.equals(typeFQCN);
    }

    private static DatabaseTypeDTO newErrorDTO(String messageFormat, Object... arguments) {
        return new DatabaseTypeDTO(
            new ViolationImpl(
                String.format(messageFormat, arguments),
                PROPERTY_PATH_TYPE
            )
        );
    }

}
