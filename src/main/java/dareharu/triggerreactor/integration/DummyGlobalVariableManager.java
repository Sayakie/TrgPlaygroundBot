package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;
import io.github.wysohn.triggerreactor.core.manager.IGlobalVariableManager;

import java.util.HashMap;

@Singleton
public final class DummyGlobalVariableManager implements IGlobalVariableManager {

    private final HashMap<Object, Object> globalVariables = new HashMap<>();

    @Override
    public HashMap<Object, Object> getGlobalVariableAdapter() {
        return globalVariables;
    }

    @Override
    public String toString() {
        return "PlaygroundGlobalVariableManager";
    }

}
