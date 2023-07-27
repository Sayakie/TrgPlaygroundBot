package dareharu.triggerreactor.util;

import io.github.wysohn.triggerreactor.core.script.interpreter.Executor;
import io.github.wysohn.triggerreactor.core.script.interpreter.Placeholder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class PlaygroundInterpreterUtils {

    private PlaygroundInterpreterUtils() {}

    private static final Executor EMPTY_EXEUCTOR = ( (timing, vars, ctx, args) -> null );
    private static final Executor ECHO_EXEUCTOR = ( (timing, vars, ctx, args) -> {
        if (ctx instanceof StringBuilder sb) {
            sb.append(args[0]).append("\n");
        }
        return null;
    } );

    static final Map<String, Executor> EXECUTORS = new HashMap<>();

    static final Map<String, Placeholder> PLACEHOLDERS = new HashMap<>();

    public static Map<String, Executor> getExecutors() {
        return Collections.unmodifiableMap(EXECUTORS);
    }

    public static Map<String, Placeholder> getPlaceholders() {
        return Collections.unmodifiableMap(PLACEHOLDERS);
    }

    static {
        EXECUTORS.put("ACTIONBAR", EMPTY_EXEUCTOR);
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

        PLACEHOLDERS.put("air", (timing, ctx, vars, args) -> 20);
        PLACEHOLDERS.put("biome", (timing, ctx, vars, args) -> "THE_VOID");
        PLACEHOLDERS.put("blockname", (timing, ctx, vars, args) -> "STONE");
        PLACEHOLDERS.put("cmdline", (timing, ctx, vars, args) -> "");
        PLACEHOLDERS.put("count", (timing, ctx, vars, args) -> 64);
        PLACEHOLDERS.put("currenttimeseconds", (timing, ctx, vars, args) -> System.currentTimeMillis() / 1000);
        PLACEHOLDERS.put("emptyslot", (timing, ctx, vars, args) -> -1);
        PLACEHOLDERS.put("emptyslots", (timing, ctx, vars, args) -> 0);
        PLACEHOLDERS.put("entityname", (timing, ctx, vars, args) -> "Entity");
        PLACEHOLDERS.put("exp", (timing, ctx, vars, args) -> 0);
        PLACEHOLDERS.put("explevel", (timing, ctx, vars, args) -> 0);
        PLACEHOLDERS.put("firstgroup", (timing, ctx, vars, args) -> "null");
        PLACEHOLDERS.put("food", (timing, ctx, vars, args) -> 20);
        PLACEHOLDERS.put("gamemode", (timing, ctx, vars, args) -> "SURVIVAL");
        PLACEHOLDERS.put("group", (timing, ctx, vars, args) -> null);
        PLACEHOLDERS.put("haseffect", (timing, ctx, vars, args) -> false);
        PLACEHOLDERS.put("haspermission", (timing, ctx, vars, args) -> false);
        PLACEHOLDERS.put("health", (timing, ctx, vars, args) -> 20);
        PLACEHOLDERS.put("helditem", (timing, ctx, vars, args) -> "AIR");
        PLACEHOLDERS.put("helditemdisplayname", (timing, ctx, vars, args) -> "AIR");
        PLACEHOLDERS.put("helditemhasenchant", (timing, ctx, vars, args) -> false);
        PLACEHOLDERS.put("helditemid", (timing, ctx, vars, args) -> -1);
        PLACEHOLDERS.put("helditemlore", (timing, ctx, vars, args) -> "");
        PLACEHOLDERS.put("helditemname", (timing, ctx, vars, args) -> "AIR");
        PLACEHOLDERS.put("id", (timing, ctx, vars, args) -> "AIR");
        PLACEHOLDERS.put("idname", (timing, ctx, vars, args) -> "AIR");
        PLACEHOLDERS.put("ip", (timing, ctx, vars, args) -> "localhost/[0:0:0:0:0:0:0:1]:25565");
        PLACEHOLDERS.put("isburning", (timing, ctx, vars, args) -> false);
        PLACEHOLDERS.put("isflying", (timing, ctx, vars, args) -> false);
        PLACEHOLDERS.put("isnumber", (timing, ctx, vars, args) -> {
            try {
                Integer.parseInt((String) args[0]);
                return true;
            } catch (final Throwable ignored) {
                return false;
            }
        });
        PLACEHOLDERS.put("isop", (timing, ctx, vars, args) -> true);
        PLACEHOLDERS.put("issneaking", (timing, ctx, vars, args) -> false);
        PLACEHOLDERS.put("issprinting", (timing, ctx, vars, args) -> false);
        PLACEHOLDERS.put("killername", (timing, ctx, vars, args) -> "wysohn");
        PLACEHOLDERS.put("lore", (timing, ctx, vars, args) -> null);
        PLACEHOLDERS.put("maxhealth", (timing, ctx, vars, args) -> 20);
        PLACEHOLDERS.put("money", (timing, ctx, vars, args) -> Integer.MAX_VALUE);
        PLACEHOLDERS.put("mysql", (timing, ctx, vars, args) -> null);
        PLACEHOLDERS.put("name", (timing, ctx, vars, args) -> "AIR");
        PLACEHOLDERS.put("offhanditem", (timing, ctx, vars, args) -> "AIR");
        PLACEHOLDERS.put("onlineplayers", (timing, ctx, vars, args) -> 1);
        PLACEHOLDERS.put("packlist", (timing, ctx, vars, args) -> new int[0]);
        PLACEHOLDERS.put("pitch", (timing, ctx, vars, args) -> 0.0f);
        PLACEHOLDERS.put("yaw", (timing, ctx, vars, args) -> 0.0f);
        PLACEHOLDERS.put("playerinv", (timing, ctx, vars, args) -> "AIR");
        PLACEHOLDERS.put("playerloc", (timing, ctx, vars, args) -> "world@0,0,0");
        PLACEHOLDERS.put("playerlocexact", (timing, ctx, vars, args) -> "world@0,0,0");
        PLACEHOLDERS.put("playername", (timing, ctx, vars, args) -> "TriggerReactor");
        PLACEHOLDERS.put("playeruuid", (timing, ctx, vars, args) -> new UUID(0, 0));
        PLACEHOLDERS.put("prefix", (timing, ctx, vars, args) -> "");
        PLACEHOLDERS.put("random", (timing, ctx, vars, args) -> {
            if (args.length == 1) {
                return ThreadLocalRandom.current().nextInt(Ints.tryParse(args[0].toString()));
            } else if (args.length >= 2) {
                final int start = Ints.tryParse(args[0].toString());
                final int end = Ints.tryParse(args[1].toString());

                return start + ThreadLocalRandom.current().nextInt(end - start);
            }

            return 0;
        });
        PLACEHOLDERS.put("round", (timing, ctx, vars, args) -> {
            if (args.length == 1) {
                if (args[0].toString().contains(".")) {
                    return Doubles.tryParse(args[0].toString());
                } else {
                    return Ints.tryParse(args[0].toString());
                }
            } else if (args.length == 2) {
                return Ints.tryParse(args[0].toString(), Ints.tryParse(args[1].toString(), 10));
            }

            return 0;
        });
        PLACEHOLDERS.put("slot", (timing, ctx, vars, args) -> null);
        PLACEHOLDERS.put("suffix", (timing, ctx, vars, args) -> "");
        PLACEHOLDERS.put("time", (timing, ctx, vars, args) -> System.currentTimeMillis() % 24000);
        PLACEHOLDERS.put("tps", (timing, ctx, vars, args) -> 20.0d);
        PLACEHOLDERS.put("world", (timing, ctx, vars, args) -> "world");
        PLACEHOLDERS.put("worldname", (timing, ctx, vars, args) -> "world");
        PLACEHOLDERS.put("x", (timing, ctx, vars, args) -> 0);
        PLACEHOLDERS.put("y", (timing, ctx, vars, args) -> 0);
        PLACEHOLDERS.put("z", (timing, ctx, vars, args) -> 0);
    }

}
