package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;
import dareharu.triggerreactor.util.Doubles;
import dareharu.triggerreactor.util.Ints;
import io.github.wysohn.triggerreactor.core.manager.js.IBackedMapProvider;
import io.github.wysohn.triggerreactor.core.script.interpreter.Executor;
import io.github.wysohn.triggerreactor.tools.CaseInsensitiveStringMap;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static dareharu.triggerreactor.util.LocalVariableUtils.requireEvent;
import static dareharu.triggerreactor.util.LocalVariableUtils.requirePlayer;

@Singleton
@SuppressWarnings("deprecation")
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
        EXECUTORS.put("GIVE", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof ItemStack itemStack) {
                final var inventory = requirePlayer(vars).getInventory();
                if (inventory.firstEmpty() > 0) {
                    inventory.addItem(itemStack);
                }
            }

            return null;
        });
        EXECUTORS.put("GUI", EMPTY_EXEUCTOR);
        EXECUTORS.put("ITEMFRAMEROTATE", EMPTY_EXEUCTOR);
        EXECUTORS.put("ITEMFRAMESET", EMPTY_EXEUCTOR);
        EXECUTORS.put("KICK", (timing, vars, ctx, args) -> {
            final @Nullable Player player;
            if (args.length > 0) {
                if (args[0] instanceof Player playerFromArg) {
                    player = playerFromArg;
                } else if (args[0] instanceof String maybePlayer) {
                    player = Bukkit.getPlayer(maybePlayer);
                } else {
                    player = null;
                }
            } else {
                player = requirePlayer(vars);
            }

            if (player != null) {
                player.kick();
            }
            return null;
        });
        EXECUTORS.put("KILL", EMPTY_EXEUCTOR);
        EXECUTORS.put("LEVEROFF", EMPTY_EXEUCTOR);
        EXECUTORS.put("LEVERON", EMPTY_EXEUCTOR);
        EXECUTORS.put("LEVERTOGGLE", EMPTY_EXEUCTOR);
        EXECUTORS.put("LIGHTNING", EMPTY_EXEUCTOR);
        EXECUTORS.put("LOG", ECHO_EXEUCTOR);
        EXECUTORS.put("MESSAGE", ECHO_EXEUCTOR);
        EXECUTORS.put("MODIFYHELDITEM", EMPTY_EXEUCTOR);
        EXECUTORS.put("MODIFYPLAYER", (timing, vars, ctx, args) -> {
            if (args.length == 2 && args[0] instanceof String type) {
                final var player = requirePlayer(vars);

                switch (type.toUpperCase(Locale.ENGLISH)) {
                    case "HEALTH" -> player.setHealth(Doubles.tryParse(args[1].toString(), 20));
                    case "MAXHEALTH" -> player.setMaxHealth(Doubles.tryParse(args[1].toString(), 20));
                    case "FOOD" -> player.setFoodLevel(Ints.tryParse(args[1].toString(), 20));
                    case "SATURATION" -> player.setSaturation((float) Doubles.tryParse(args[1].toString(), 20));
                    case "EXP" -> player.setExp((float) Doubles.tryParse(args[1].toString()));
                    case "WALKSPEED" -> player.setWalkSpeed((float) Doubles.tryParse(args[1].toString(), 1.0));
                    case "FLYSPEED" -> player.setFlySpeed((float) Doubles.tryParse(args[1].toString(), 1.0));
                    case "FLY" -> {
                        final boolean state = Ints.tryParse(args[1].toString()) > 0;
                        player.setAllowFlight(state);
                        player.setFlying(state);
                    }
                    case "GAMEMODE" -> player.setGameMode(GameMode.valueOf(args[1].toString()));
                }
            }

            return null;
        });
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
        EXECUTORS.put("SETFLYMODE", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof Boolean state) {
                final var player = requirePlayer(vars);
                player.setAllowFlight(state);
                player.setFlying(state);
            }

            return null;
        });
        EXECUTORS.put("SETFLYSPEED", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof Number speed && -1 <= speed.floatValue() && speed.floatValue() <= 1) {
                requirePlayer(vars).setFlySpeed(speed.floatValue());
            }

            return null;
        });
        EXECUTORS.put("SETFOOD", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof Number foodLevel && 0 <= foodLevel.floatValue()) {
                requirePlayer(vars).setFoodLevel(foodLevel.intValue());
            }

            return null;
        });
        EXECUTORS.put("SETGAMEMODE", (timing, vars, ctx, args) -> {
            final @Nullable GameMode gameMode;
            if (args.length == 1) {
                gameMode = switch (args[0]) {
                    case Integer i && 0 <= i && i < GameMode.values().length -> GameMode.values()[i];
                    case String s && !s.isEmpty() -> GameMode.valueOf(s.toUpperCase(Locale.ENGLISH));
                    default -> null;
                };
            } else {
                gameMode = null;
            }

            if (gameMode != null) {
                requirePlayer(vars).setGameMode(gameMode);
            }

            return null;
        });
        EXECUTORS.put("SETHEALTH", (timing, vars, ctx, args) -> {
            final var player = requirePlayer(vars);
            if (args.length == 1 && args[0] instanceof Number health && 0 <= health.doubleValue() && health.doubleValue() <= player.getMaxHealth()) {
                player.setHealth(health.doubleValue());
            }

            return null;
        });
        EXECUTORS.put("SETHELDITEM", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof ItemStack itemStack) {
                requirePlayer(vars).getInventory().setItemInMainHand(itemStack);
            }

            return null;
        });
        EXECUTORS.put("SETITEMLORE", (timing, vars, ctx, args) -> {
            final @Nullable String content;
            final @Nullable ItemStack itemStack;
            if (args.length == 1 && args[0] instanceof String contentFromArg) {
                content = contentFromArg;
                itemStack = requirePlayer(vars).getInventory().getItemInMainHand();
            } else if (args.length == 2 && args[0] instanceof String contentFromArg && args[1] instanceof ItemStack itemStackFromArg) {
                content = contentFromArg;
                itemStack = itemStackFromArg;
            } else {
                content = null;
                itemStack = null;
            }

            if (content != null && itemStack != null && !itemStack.getType().isAir()) {
                final var lores = Arrays.stream(content.split("\n")).map(MiniMessage.miniMessage()::deserialize).collect(Collectors.toList());

                final var itemMeta = itemStack.getItemMeta();
                itemMeta.lore(lores);
                itemStack.setItemMeta(itemMeta);
            }

            return null;
        });
        EXECUTORS.put("SETITEMNAME", (timing, vars, ctx, args) -> {
            final @Nullable String content;
            final @Nullable ItemStack itemStack;
            if (args.length == 1 && args[0] instanceof String contentFromArg) {
                content = contentFromArg;
                itemStack = requirePlayer(vars).getInventory().getItemInMainHand();
            } else if (args.length == 2 && args[0] instanceof String contentFromArg && args[1] instanceof ItemStack itemStackFromArg) {
                content = contentFromArg;
                itemStack = itemStackFromArg;
            } else {
                content = null;
                itemStack = null;
            }

            if (content != null && itemStack != null && !itemStack.getType().isAir()) {
                final var displayName = MiniMessage.miniMessage().deserialize(content);

                final var itemMeta = itemStack.getItemMeta();
                itemMeta.displayName(displayName);
                itemStack.setItemMeta(itemMeta);
            }

            return null;
        });
        EXECUTORS.put("SETMAXHEALTH", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof Number maxHealth && 0 < maxHealth.doubleValue() && maxHealth.doubleValue() < 2048) {
                requirePlayer(vars).setMaxHealth(maxHealth.doubleValue());
            }

            return null;
        });
        EXECUTORS.put("SETOFFHAND", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof ItemStack itemStack) {
                requirePlayer(vars).getInventory().setItemInOffHand(itemStack);
            }

            return null;
        });
        EXECUTORS.put("SETPLAYERINV", (timing, vars, ctx, args) -> {
            final var player = requirePlayer(vars);
            if (args.length == 2 && args[0] instanceof Integer slot
                && 0 < slot && slot < player.getInventory().getSize()
                && args[1] instanceof ItemStack itemStack) {
                player.getInventory().setItem(slot, itemStack);
            }

            return null;
        });
        EXECUTORS.put("SETSATURATION", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof Number saturation && 0 < saturation.floatValue()) {
                requirePlayer(vars).setSaturation(saturation.floatValue());
            }

            return null;
        });
        EXECUTORS.put("SETSLOT", (timing, vars, ctx, args) -> {
            if (args.length == 2 && args[0] instanceof Integer index && 0 < index && args[1] instanceof ItemStack itemStack) {
                final @Nullable InventoryEvent event = switch (requireEvent(vars)) {
                    case InventoryOpenEvent it -> it;
                    case InventoryClickEvent it -> it;
                    case InventoryCloseEvent it -> it;
                    default -> null;
                };

                if (event != null && index < event.getInventory().getSize()) {
                    event.getInventory().setItem(index, itemStack);
                }
            }

            return null;
        });
        EXECUTORS.put("SETTYPE", (timing, vars, ctx, args) -> {
            if (args.length == 2 && args[0] instanceof String type && !type.isBlank() && args[1] instanceof ItemStack itemStack) {
                itemStack.setType(Material.valueOf(type.toUpperCase(Locale.ENGLISH)));
            }

            return null;
        });
        EXECUTORS.put("SETWALKSPEED", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof Number speed && -1 <= speed.floatValue() && speed.floatValue() <= 1) {
                requirePlayer(vars).setWalkSpeed(speed.floatValue());
            }

            return null;
        });
        EXECUTORS.put("SETXP", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof Number expValue && 0 < expValue.floatValue() && expValue.floatValue() <= 1) {
                requirePlayer(vars).setExp(expValue.floatValue());
            }

            return null;
        });
        EXECUTORS.put("SIGNEDIT", EMPTY_EXEUCTOR);
        EXECUTORS.put("SOUND", EMPTY_EXEUCTOR);
        EXECUTORS.put("SOUNDALL", EMPTY_EXEUCTOR);
        EXECUTORS.put("SPAWN", (timing, vars, ctx, args) -> {
            final var player = requirePlayer(vars);
            final @Nullable Location location;
            final @Nullable EntityType entityType;
            switch (args[0]) {
                case EntityType entityTypeFromArg && args.length == 1 -> {
                    location = player.getLocation();
                    entityType = entityTypeFromArg;
                }
                case String s && args.length == 1 && !s.isBlank() -> {
                    location = player.getLocation();
                    entityType = EntityType.valueOf(s);
                }
                case Location locationFromArg && args.length == 2 && args[1] instanceof EntityType entityTypeFromArg -> {
                    location = locationFromArg;
                    entityType = entityTypeFromArg;
                }
                case Location locationFromArg && args.length == 2 && args[1] instanceof String s && !s.isBlank() -> {
                    location = locationFromArg;
                    entityType = EntityType.valueOf(s);
                }
                case null, default -> {
                    location = null;
                    entityType = null;
                }
            }

            if (location != null && entityType != null && entityType.isSpawnable()) {
                player.getWorld().spawnEntity(location, entityType);
            }

            return null;
        });
        EXECUTORS.put("TIME", (timing, vars, ctx, args) -> {
            if (args.length == 1) {
                final long time = switch (args[0]) {
                    case Number n -> n.longValue();
                    case String s && !s.isBlank() -> Ints.tryParse(s);
                    default -> 0;
                };

                if (time != 0) {
                    requirePlayer(vars).getWorld().setTime(time);
                }
            }

            return null;
        });
        EXECUTORS.put("TP", (timing, vars, ctx, args) -> {
            final @Nullable Player target;
            final @Nullable Location location;
            switch (args[0]) {
                case Location locationFromArg && args.length == 1 -> {
                    target = requirePlayer(vars);
                    location = locationFromArg;
                }
                case Player player && args.length == 1 -> {
                    target = requirePlayer(vars);
                    location = player.getLocation();
                }
                case Number x && args.length == 3 && args[1] instanceof Number y && args[2] instanceof Number z -> {
                    target = requirePlayer(vars);
                    location = new Location(target.getWorld(), x.doubleValue(), y.doubleValue(), z.doubleValue());
                }
                case Number x && args.length == 5 && args[1] instanceof Number y && args[2] instanceof Number z && args[3] instanceof Number yaw && args[4] instanceof Number pitch -> {
                    target = requirePlayer(vars);
                    location = new Location(target.getWorld(), x.doubleValue(), y.doubleValue(), z.doubleValue(), yaw.floatValue(), pitch.floatValue());
                }
                case Number x && args.length == 4 && args[1] instanceof Number y && args[2] instanceof Number z && args[3] instanceof Player player -> {
                    target = player;
                    location = new Location(target.getWorld(), x.doubleValue(), y.doubleValue(), z.doubleValue());
                }
                case Number x && args.length == 4 && args[1] instanceof Number y && args[2] instanceof Number z && args[3] instanceof String playerName -> {
                    target = Bukkit.getPlayer(playerName);
                    if (target == null) {
                        throw new RuntimeException("Player not found");
                    }
                    location = new Location(target.getWorld(), x.doubleValue(), y.doubleValue(), z.doubleValue());
                }
                case Number x && args.length == 6 && args[1] instanceof Number y && args[2] instanceof Number z && args[3] instanceof Number yaw && args[4] instanceof Number pitch && args[5] instanceof Player player -> {
                    target = player;
                    location = new Location(target.getWorld(), x.doubleValue(), y.doubleValue(), z.doubleValue(), yaw.floatValue(), pitch.floatValue());
                }
                case Number x && args.length == 6 && args[1] instanceof Number y && args[2] instanceof Number z && args[3] instanceof Number yaw && args[4] instanceof Number pitch && args[5] instanceof String playerName -> {
                    target = Bukkit.getPlayer(playerName);
                    if (target == null) {
                        throw new RuntimeException("Player not found");
                    }
                    location = new Location(target.getWorld(), x.doubleValue(), y.doubleValue(), z.doubleValue(), yaw.floatValue(), pitch.floatValue());
                }
                case null, default -> {
                    target = null;
                    location = null;
                }
            }

            if (target == null) {
                throw new RuntimeException("Player not found");
            }

            target.teleport(location);

            return null;
        });
        EXECUTORS.put("TPPOS", (timing, vars, ctx, args) -> {
            final @Nullable Player target;
            final double x, y, z;
            if (args.length == 3 && args[0] instanceof Number xn && args[1] instanceof Number yn && args[2] instanceof Number zn) {
                target = requirePlayer(vars);
                x = xn.doubleValue();
                y = yn.doubleValue();
                z = zn.doubleValue();
            } else if (args.length == 3 && args[0] instanceof String xs && args[1] instanceof String ys && args[2] instanceof String zs) {
                final BiFunction<String, Supplier<Double>, Double> relativeLocationOp = (s, v) -> {
                    if (s.indexOf('~') == 0) {
                        return v.get() + Doubles.tryParse(s.substring(1));
                    }

                    return Doubles.tryParse(s);
                };

                target = requirePlayer(vars);
                x = relativeLocationOp.apply(xs, target.getLocation()::getX);
                y = relativeLocationOp.apply(ys, target.getLocation()::getY);
                z = relativeLocationOp.apply(zs, target.getLocation()::getZ);
            } else {
                target = null;
                x = 0;
                y = 0;
                z = 0;
            }

            if (target == null) {
                throw new RuntimeException("No target specified or found");
            }

            target.teleport(new Location(target.getWorld(), x, y, z));
            return null;
        });
        EXECUTORS.put("VELOCITY", (timing, vars, ctx, args) -> {
            if (args.length == 3 && args[0] instanceof Number x && args[1] instanceof Number y && args[2] instanceof Number z) {
                requirePlayer(vars).setVelocity(new Vector(x.doubleValue(), y.doubleValue(), z.doubleValue()));
            } else if (args.length == 3 && args[0] instanceof String x && args[1] instanceof String y && args[2] instanceof String z) {
                requirePlayer(vars).setVelocity(new Vector(Doubles.tryParse(x), Doubles.tryParse(y), Doubles.tryParse(z)));
            }

            return null;
        });
        EXECUTORS.put("WEATHER", (timing, vars, ctx, args) -> {
            if (args.length == 2 && args[0] instanceof String worldName && args[1] instanceof Boolean state) {
                final @Nullable var world = Bukkit.getWorld(worldName);
                if (world == null) {
                    throw new RuntimeException("World not found");
                }

                world.setStorm(state);
            }

            return null;
        });
    }

    @Override
    public Map<String, Executor> getBackedMap() {
        return EXECUTORS;
    }

    @Override
    public String toString() {
        return "PlaygroundExecutorManager";
    }

}
