package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;
import io.github.wysohn.triggerreactor.core.script.interpreter.TaskSupervisor;

import java.util.concurrent.*;

@Singleton
public final class DummyTaskSupervisor implements TaskSupervisor {

    private final static ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void runTask(final Runnable task) {
        task.run();
    }

    @Override
    public <T> Future<T> submitSync(final Callable<T> task) {
        return immediate(task);
    }

    @Override
    public void submitAsync(final Runnable task) {
        EXECUTOR.execute(task);
    }

    @Override
    public boolean isServerThread() {
        return true;
    }

    @Override
    public Thread newThread(final Runnable target, final String name, final int priority) {
        final Thread thread = new Thread(target, name);
        thread.setPriority(priority);
        return thread;
    }

    private <T> Future<T> immediate(final Callable<T> task) {
        return new Future<>() {
            private T result = null;
            private Exception exception = null;

            {
                try {
                    result = task.call();
                } catch (Exception e) {
                    exception = e;
                }
            }

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
                return true;
            }

            @Override
            public T get() throws ExecutionException {
                if (exception != null) {
                    throw new ExecutionException(exception);
                }

                return result;
            }

            @Override
            public T get(final long timeout, final TimeUnit unit) throws ExecutionException {
                if (exception != null) {
                    throw new ExecutionException(exception);
                }

                return result;
            }

        };
    }

}
