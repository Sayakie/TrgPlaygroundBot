package dareharu.triggerreactor.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

public final class UnimplementedOperationException extends UnsupportedOperationException implements Serializable {

    @Serial
    private static final long serialVersionUID = -1528083861698639393L;

    /**
     * Constructs an UnimplementedOperationException with no detail message.
     */
    @ApiStatus.Internal
    public UnimplementedOperationException() {
        this("Not implemented");
    }

    /**
     * Constructs an UnimplementedOperationException with the specified detail message.
     *
     * @param message The detail message.
     */
    @ApiStatus.Internal
    public UnimplementedOperationException(final @NotNull String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * <p>Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param message The detail message (which is saved for later retrieval
     *                by the {@link Throwable#getMessage()} method).
     * @param cause The cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method). (A {@code null} value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    @ApiStatus.Internal
    public UnimplementedOperationException(final @NotNull String message, final @NotNull Throwable cause) {
        super(message, cause);
    }

}
