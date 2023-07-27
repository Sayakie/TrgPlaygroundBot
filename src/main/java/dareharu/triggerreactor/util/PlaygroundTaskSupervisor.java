package dareharu.triggerreactor.util;

import io.github.wysohn.triggerreactor.core.script.interpreter.TaskSupervisor;

import java.util.concurrent.*;

public final class PlaygroundTaskSupervisor implements TaskSupervisor {

    public static final PlaygroundTaskSupervisor INSTANCE = new PlaygroundTaskSupervisor();

    private PlaygroundTaskSupervisor() {}

    @Override
    public <T> Future<T> submitSync(final Callable<T> callable) {
        return new Future<>() {
            private boolean processed;

            @Override
            public boolean cancel(final boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return this.processed;
            }

            @Override
            public T get() {
                final T identity;
                try {
                    identity = callable.call();
                } catch (final Exception ignored) {
                    return null;
                }

                this.processed = true;
                return identity;
            }

            @Override
            public T get(final long timeout, final TimeUnit unit) {
                return get();
            }
        };
    }

    @Override
    public void submitAsync(final Runnable runnable) {
        newThread(runnable, toString(), Thread.NORM_PRIORITY).start();
    }

    @Override
    public boolean isServerThread() {
        return true;
    }

    @Override
    public Thread newThread(final Runnable runnable, final String name, final int priority) {
        final var thread = new Thread(runnable);
        thread.setName(name);
        thread.setPriority(priority);

        return thread;
    }

    @Override
    public String toString() {
        return "PlaygroundTaskSupervisor";
    }

}
