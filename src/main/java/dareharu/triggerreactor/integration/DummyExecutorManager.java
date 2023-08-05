package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;
import io.github.wysohn.triggerreactor.core.manager.js.IBackedMapProvider;
import io.github.wysohn.triggerreactor.core.script.interpreter.Executor;
import io.github.wysohn.triggerreactor.tools.CaseInsensitiveStringMap;

import java.util.Map;

@Singleton
public final class DummyExecutorManager implements IBackedMapProvider<Executor> {

    private static final Executor EMPTY_EXEUCTOR = (timing, vars, ctx, args) -> null;
    private static final Executor ECHO_EXEUCTOR = (timing, vars, ctx, args) -> {
        if (ctx instanceof StringBuilder sb) {
            sb.append(args[0]).append("\n");
        }

        return null;
    };

    private static final Map<String, Executor> EXECUTORS = new CaseInsensitiveStringMap<>();

    static {
        EXECUTORS.put("ACTIONBAR", ECHO_EXEUCTOR);
        EXECUTORS.put("BROADCAST", ECHO_EXEUCTOR);
        EXECUTORS.put("BURN", EMPTY_EXEUCTOR);
        EXECUTORS.put("CLEARCHAT", EMPTY_EXEUCTOR);
        EXECUTORS.put("CLEARENTITY", EMPTY_EXEUCTOR);
        EXECUTORS.put("CLEARPOTION", EMPTY_EXEUCTOR);
        EXECUTORS.put("CLOSEGUI", EMPTY_EXEUCTOR);
        EXECUTORS.put("CMD", EMPTY_EXEUCTOR);
        EXECUTORS.put("CMDCON", EMPTY_EXEUCTOR);
        EXECUTORS.put("CMDOP", EMPTY_EXEUCTOR);
        EXECUTORS.put("DOORCLOSE", EMPTY_EXEUCTOR);
        EXECUTORS.put("DOOROPEN", EMPTY_EXEUCTOR);
        EXECUTORS.put("DOORTOGGLE", EMPTY_EXEUCTOR);
        EXECUTORS.put("DROPITEM", EMPTY_EXEUCTOR);
        EXECUTORS.put("EXPLOSION", EMPTY_EXEUCTOR);
        EXECUTORS.put("FALLINGBLOCK", EMPTY_EXEUCTOR);
        EXECUTORS.put("GIVE", EMPTY_EXEUCTOR);
        EXECUTORS.put("GUI", EMPTY_EXEUCTOR);
        EXECUTORS.put("ITEMFRAMEROTATE", EMPTY_EXEUCTOR);
        EXECUTORS.put("ITEMFRAMESET", EMPTY_EXEUCTOR);
        EXECUTORS.put("KICK", EMPTY_EXEUCTOR);
        EXECUTORS.put("KILL", EMPTY_EXEUCTOR);
        EXECUTORS.put("LEVEROFF", EMPTY_EXEUCTOR);
        EXECUTORS.put("LEVERON", EMPTY_EXEUCTOR);
        EXECUTORS.put("LEVERTOGGLE", EMPTY_EXEUCTOR);
        EXECUTORS.put("LIGHTNING", EMPTY_EXEUCTOR);
        EXECUTORS.put("LOG", ECHO_EXEUCTOR);
        EXECUTORS.put("MESSAGE", ECHO_EXEUCTOR);
        EXECUTORS.put("MODIFYHELDITEM", EMPTY_EXEUCTOR);
        EXECUTORS.put("MODIFYPLAYER", EMPTY_EXEUCTOR);
        EXECUTORS.put("MONEY", EMPTY_EXEUCTOR);
        EXECUTORS.put("MYSQL", EMPTY_EXEUCTOR);
        EXECUTORS.put("PERMISSION", EMPTY_EXEUCTOR);
        EXECUTORS.put("POTION", EMPTY_EXEUCTOR);
        EXECUTORS.put("PUSH", EMPTY_EXEUCTOR);
        EXECUTORS.put("ROTATEBLOCK", EMPTY_EXEUCTOR);
        EXECUTORS.put("SCOREBOARD", EMPTY_EXEUCTOR);
        EXECUTORS.put("SERVER", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETBLOCK", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETCOUNT", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETFLYMODE", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETFLYSPEED", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETFOOD", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETGAMEMODE", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETHEALTH", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETHELDITEM", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETITEMLORE", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETITEMNAME", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETMAXHEALTH", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETOFFHAND", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETPLAYERINV", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETSATURATION", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETSLOT", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETTYPE", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETWALKSPEED", EMPTY_EXEUCTOR);
        EXECUTORS.put("SETXP", EMPTY_EXEUCTOR);
        EXECUTORS.put("SIGNEDIT", EMPTY_EXEUCTOR);
        EXECUTORS.put("SOUND", EMPTY_EXEUCTOR);
        EXECUTORS.put("SOUNDALL", EMPTY_EXEUCTOR);
        EXECUTORS.put("SPAWN", EMPTY_EXEUCTOR);
        EXECUTORS.put("TIME", EMPTY_EXEUCTOR);
        EXECUTORS.put("TP", EMPTY_EXEUCTOR);
        EXECUTORS.put("TPPOS", EMPTY_EXEUCTOR);
        EXECUTORS.put("VELOCITY", EMPTY_EXEUCTOR);
        EXECUTORS.put("WEATHER", EMPTY_EXEUCTOR);
    }

    @Override
    public Map<String, Executor> getBackedMap() {
        return EXECUTORS;
    }

}
