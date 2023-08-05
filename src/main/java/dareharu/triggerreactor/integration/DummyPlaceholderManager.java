package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;
import dareharu.triggerreactor.util.Doubles;
import dareharu.triggerreactor.util.Ints;
import io.github.wysohn.triggerreactor.core.manager.js.IBackedMapProvider;
import io.github.wysohn.triggerreactor.core.script.interpreter.Executor;
import io.github.wysohn.triggerreactor.core.script.interpreter.Placeholder;
import io.github.wysohn.triggerreactor.tools.CaseInsensitiveStringMap;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public final class DummyPlaceholderManager implements IBackedMapProvider<Placeholder> {

    private static final Map<String, Placeholder> PLACEHOLDERS = new CaseInsensitiveStringMap<>();

    static {
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

    @Override
    public Map<String, Placeholder> getBackedMap() {
        return PLACEHOLDERS;
    }

}
