package dareharu.triggerreactor.util;

public final class ExceptionUtils {

    private ExceptionUtils() {}

    public static String handleException(final Throwable throwable) {
        Throwable cause = throwable;

        final var sb = new StringBuilder(throwable.getClass().getSimpleName());
        while (cause != null) {
            sb.append("\n").append("  Caused by: ").append(cause.getMessage());
            cause = cause.getCause();
        }

        return sb.toString();
    }

}
