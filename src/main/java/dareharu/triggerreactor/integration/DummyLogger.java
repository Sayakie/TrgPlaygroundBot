package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;

import java.util.MissingResourceException;
import java.util.logging.Logger;

@Singleton
public final class DummyLogger extends Logger {

    private static final Logger IDENTITY = new DummyLogger(DummyLogger.class.getCanonicalName(), null);

    static {
        // TODO
        // IDENTITY.addHandler();
    }

    /**
     * Protected method to construct a logger for a named subsystem.
     * <p>
     * The logger will be initially configured with a null Level
     * and with useParentHandlers set to true.
     *
     * @param name               A name for the logger.  This should
     *                           be a dot-separated name and should normally
     *                           be based on the package name or class name
     *                           of the subsystem, such as java.net
     *                           or javax.swing.  It may be null for anonymous Loggers.
     * @param resourceBundleName name of ResourceBundle to be used for localizing
     *                           messages for this logger.  May be null if none
     *                           of the messages require localization.
     * @throws MissingResourceException if the resourceBundleName is non-null and
     *                                  no corresponding resource can be found.
     */
    private DummyLogger(final String name, final String resourceBundleName) {
        super(name, resourceBundleName);
    }

    public static Logger identity() {
        return IDENTITY;
    }

}
