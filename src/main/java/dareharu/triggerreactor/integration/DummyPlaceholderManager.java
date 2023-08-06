package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;
import dareharu.triggerreactor.util.Doubles;
import dareharu.triggerreactor.util.Ints;
import io.github.wysohn.triggerreactor.core.manager.js.IBackedMapProvider;
import io.github.wysohn.triggerreactor.core.script.interpreter.Placeholder;
import io.github.wysohn.triggerreactor.tools.CaseInsensitiveStringMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static dareharu.triggerreactor.util.LocalVariableUtils.requireEvent;
import static dareharu.triggerreactor.util.LocalVariableUtils.requirePlayer;
import static net.kyori.adventure.text.Component.text;

@Singleton
public final class DummyPlaceholderManager implements IBackedMapProvider<Placeholder> {

    private static final Map<String, Placeholder> PLACEHOLDERS = new CaseInsensitiveStringMap<>();

    static {
        PLACEHOLDERS.put("air", (timing, vars, ctx, args) -> requirePlayer(vars).getRemainingAir());
        PLACEHOLDERS.put("biome", (timing, vars, ctx, args) -> {
            final var player = requirePlayer(vars);

            return player.getWorld().getBiome(player.getLocation().toBlockLocation()).name();
        });
        PLACEHOLDERS.put("blockname", (timing, vars, ctx, args) -> {
            final var player = requirePlayer(vars);
            final World world;
            final int x, y, z;

            if (args.length == 3) {
                world = player.getWorld();
                x = (int) args[0];
                y = (int) args[1];
                z = (int) args[2];
            } else if (args.length == 4) {
                world = Bukkit.getWorld((String) args[0]);
                x = (int) args[1];
                y = (int) args[2];
                z = (int) args[3];
            } else {
                world = player.getWorld();
                x = player.getLocation().getBlockX();
                y = player.getLocation().getBlockY();
                z = player.getLocation().getBlockZ();
            }

            return world.getBlockAt(x, y, z).getType().name().toLowerCase(Locale.ENGLISH);
        });
        PLACEHOLDERS.put("cmdline", (timing, vars, ctx, args) -> "");
        PLACEHOLDERS.put("count", (timing, vars, ctx, args) -> {
            if (args.length == 0) {
                return 0;
            }

            final var maybeItemStack = args[0];
            if (maybeItemStack instanceof ItemStack itemStack && itemStack.getType() != Material.AIR) {
                return itemStack.getAmount();
            }

            return 0;
        });
        PLACEHOLDERS.put("currenttimeseconds", (timing, vars, ctx, args) -> System.currentTimeMillis() / 1000);
        PLACEHOLDERS.put("emptyslot", (timing, vars, ctx, args) -> requirePlayer(vars).getInventory().firstEmpty());
        PLACEHOLDERS.put("emptyslots", (timing, vars, ctx, args) -> {
            final var player = requirePlayer(vars);
            final var contents = player.getInventory().getContents();

            return Arrays.stream(contents).filter(Objects::isNull).count();
        });
        PLACEHOLDERS.put("entityname", (timing, vars, ctx, args) -> {
            final var event = requireEvent(vars);

            if (event instanceof EntityEvent entityEvent) {
                return entityEvent.getEntity().getName();
            }

            return "";
        });
        PLACEHOLDERS.put("exp", (timing, vars, ctx, args) -> requirePlayer(vars).getExp());
        PLACEHOLDERS.put("explevel", (timing, vars, ctx, args) -> requirePlayer(vars).getLevel());
        PLACEHOLDERS.put("firstgroup", (timing, vars, ctx, args) -> "Operator");
        PLACEHOLDERS.put("food", (timing, vars, ctx, args) -> requirePlayer(vars).getFoodLevel());
        PLACEHOLDERS.put("gamemode", (timing, vars, ctx, args) -> requirePlayer(vars).getGameMode().name());
        PLACEHOLDERS.put("group", (timing, vars, ctx, args) -> new String[]{ "Operator", "default" });
        PLACEHOLDERS.put("haseffect", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof String effectName) {
                final var effect = PotionEffectType.getByName(effectName);
                if (effect == null) {
                    return false;
                }

                final var player = requirePlayer(vars);
                return player.hasPotionEffect(effect);
            }

            return false;
        });
        PLACEHOLDERS.put("haspermission", (timing, vars, ctx, args) -> true);
        PLACEHOLDERS.put("health", (timing, vars, ctx, args) -> requirePlayer(vars).getHealth());
        PLACEHOLDERS.put("helditem", (timing, vars, ctx, args) -> requirePlayer(vars).getInventory().getItemInMainHand());
        PLACEHOLDERS.put("helditemdisplayname", (timing, vars, ctx, args) -> {
            final var itemStack = requirePlayer(vars).getInventory().getItemInMainHand();
            if (itemStack.hasItemMeta()) {
                final var itemMeta = itemStack.getItemMeta();

                if (itemMeta.hasDisplayName()) {
                    //noinspection DataFlowIssue
                    return LegacyComponentSerializer.legacySection().serialize(itemMeta.displayName());
                }
            }

            return "";
        });
        PLACEHOLDERS.put("helditemhasenchant", (timing, vars, ctx, args) -> {
            final var level = args.length == 2 ? Ints.tryParse((String) args[1]) : 0;
            if (args.length >= 1 && args[0] instanceof String enchantName) {
                final var enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantName));
                if (enchant == null) {
                    return false;
                }

                final var itemStack = requirePlayer(vars).getInventory().getItemInMainHand();
                if (itemStack.hasItemMeta()) {
                    final var itemMeta = itemStack.getItemMeta();

                    if (!itemMeta.hasEnchants()) {
                        return false;
                    }

                    final var enchants = itemMeta.getEnchants();
                    if (level == 0) {
                        return enchants.containsKey(enchant);
                    } else {
                        return level == enchants.get(enchant);
                    }
                }
            }

            return false;
        });
        PLACEHOLDERS.put("helditemid", (timing, vars, ctx, args) -> requirePlayer(vars).getInventory().getItemInMainHand().getType().getId());
        PLACEHOLDERS.put("helditemlore", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof Number indexN) {
                final var index = indexN.intValue();
                if (index < 0) {
                    return "";
                }

                final var itemStack = requirePlayer(vars).getInventory().getItemInMainHand();
                if (itemStack.hasItemMeta()) {
                    final var lores = itemStack.lore();

                    if (lores == null || lores.size() <= index) {
                        return "";
                    }

                    return LegacyComponentSerializer.legacySection().serialize(lores.get(index));
                }
            }

            return false;
        });
        PLACEHOLDERS.put("helditemname", (timing, vars, ctx, args) -> requirePlayer(vars).getInventory().getItemInMainHand().getType().name());
        PLACEHOLDERS.put("id", (timing, vars, ctx, args) -> {
            final var item = args.length == 0
                ? requirePlayer(vars).getInventory().getItemInMainHand()
                : args[0] instanceof ItemStack itemStack
                    ? itemStack
                    : requirePlayer(vars).getInventory().getItemInMainHand();

            return item.getType();
        });
        PLACEHOLDERS.put("idname", (timing, vars, ctx, args) -> {
            final var item = args.length == 0
                ? requirePlayer(vars).getInventory().getItemInMainHand()
                : args[0] instanceof ItemStack itemStack
                    ? itemStack
                    : requirePlayer(vars).getInventory().getItemInMainHand();

            return item.getType().name();
        });
        PLACEHOLDERS.put("ip", (timing, vars, ctx, args) -> "/127.0.0.1");
        PLACEHOLDERS.put("isburning", (timing, vars, ctx, args) -> requirePlayer(vars).getFireTicks() > 0);
        PLACEHOLDERS.put("isflying", (timing, vars, ctx, args) -> requirePlayer(vars).isFlying());
        PLACEHOLDERS.put("isnumber", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof String maybeNumber) {
                try {
                    Double.parseDouble(maybeNumber);
                    return true;
                } catch (final Throwable ignored) {
                    return false;
                }
            }

            return false;
        });
        PLACEHOLDERS.put("isop", (timing, vars, ctx, args) -> requirePlayer(vars).isOp());
        PLACEHOLDERS.put("issneaking", (timing, vars, ctx, args) -> requirePlayer(vars).isSneaking());
        PLACEHOLDERS.put("issprinting", (timing, vars, ctx, args) -> requirePlayer(vars).isSprinting());
        PLACEHOLDERS.put("killername", (timing, vars, ctx, args) -> {
            final var event = args.length == 1 ? args[0] : requireEvent(vars);
            if (event instanceof PlayerDeathEvent playerDeathEvent) {
                return playerDeathEvent.getEntity().getKiller().getName();
            }

            return null;
        });
        PLACEHOLDERS.put("lore", (timing, vars, ctx, args) -> {
            final var itemStack = args.length == 1 && args[0] instanceof ItemStack itemStackFromArg
                ? itemStackFromArg
                : requirePlayer(vars).getInventory().getItemInMainHand();

            if (itemStack.hasItemMeta()) {
                final var lores = itemStack.lore();
                if (lores == null) {
                    return null;
                }

                final var linedUpLores = lores.stream().reduce((Component) text(), (acc, cur) -> acc.append(cur).append(text("\n")));
                return LegacyComponentSerializer.legacySection().serialize(linedUpLores);
            }

            return null;
        });
        PLACEHOLDERS.put("maxhealth", (timing, vars, ctx, args) -> requirePlayer(vars).getMaxHealth());
        PLACEHOLDERS.put("money", (timing, vars, ctx, args) -> Integer.MAX_VALUE);
        PLACEHOLDERS.put("mysql", (timing, vars, ctx, args) -> null);
        PLACEHOLDERS.put("name", (timing, vars, ctx, args) -> {
            final var itemStack = requirePlayer(vars).getInventory().getItemInMainHand();
            if (itemStack.hasItemMeta()) {
                final var itemMeta = itemStack.getItemMeta();

                if (itemMeta.hasDisplayName()) {
                    //noinspection DataFlowIssue
                    return LegacyComponentSerializer.legacySection().serialize(itemMeta.displayName());
                }
            }

            return itemStack.getType().name().toLowerCase(Locale.ENGLISH);
        });
        PLACEHOLDERS.put("offhanditem", (timing, vars, ctx, args) -> requirePlayer(vars).getInventory().getItemInOffHand());
        PLACEHOLDERS.put("onlineplayers", (timing, vars, ctx, args) -> Bukkit.getOnlinePlayers().size());
        PLACEHOLDERS.put("packlist", (timing, vars, ctx, args) -> new int[0]);
        PLACEHOLDERS.put("pitch", (timing, vars, ctx, args) -> requirePlayer(vars).getLocation().getPitch());
        PLACEHOLDERS.put("yaw", (timing, vars, ctx, args) -> requirePlayer(vars).getLocation().getYaw());
        PLACEHOLDERS.put("playerinv", (timing, vars, ctx, args) -> {
            final var inventory = requirePlayer(vars).getInventory();
            final var index = args.length == 1 && args[0] instanceof Integer indexFromArg
                ? indexFromArg
                : -1;

            if (0 <= index && index < inventory.getSize()) {
                final var itemStack = inventory.getItem(index);
                if (itemStack != null) {
                    return itemStack;
                }
            }

            return new ItemStack(Material.AIR);
        });
        PLACEHOLDERS.put("playerloc", (timing, vars, ctx, args) -> {
            final var location = requirePlayer(vars).getLocation();

            return location.getWorld().getName() + "@" +
                location.getX() + "," +
                location.getY() + "," +
                location.getZ();
        });
        PLACEHOLDERS.put("playerlocexact", (timing, vars, ctx, args) -> {
            final var location = requirePlayer(vars).getLocation().toBlockLocation();

            return location.getWorld().getName() + "@" +
                location.getX() + "," +
                location.getY() + "," +
                location.getZ();
        });
        PLACEHOLDERS.put("playername", (timing, vars, ctx, args) -> requirePlayer(vars).getName());
        PLACEHOLDERS.put("playeruuid", (timing, vars, ctx, args) -> requirePlayer(vars).getUniqueId().toString());
        PLACEHOLDERS.put("prefix", (timing, vars, ctx, args) -> "");
        PLACEHOLDERS.put("random", (timing, vars, ctx, args) -> {
            if (args.length == 1) {
                return ThreadLocalRandom.current().nextInt(Ints.tryParse(args[0].toString()));
            } else if (args.length >= 2) {
                final int start = Ints.tryParse(args[0].toString());
                final int end = Ints.tryParse(args[1].toString());

                return start + ThreadLocalRandom.current().nextInt(end - start);
            }

            return 0;
        });
        PLACEHOLDERS.put("round", (timing, vars, ctx, args) -> {
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
        PLACEHOLDERS.put("slot", (timing, vars, ctx, args) -> {
            if (args.length == 1 && args[0] instanceof Integer index && index > 0) {
                final @Nullable InventoryEvent event = switch (requireEvent(vars)) {
                    case InventoryOpenEvent it -> it;
                    case InventoryClickEvent it -> it;
                    case InventoryCloseEvent it -> it;
                    default -> null;
                };

                if (event != null && index < event.getInventory().getSize()) {
                    return event.getInventory().getItem(index);
                }
            }

            return new ItemStack(Material.AIR);
        });
        PLACEHOLDERS.put("suffix", (timing, vars, ctx, args) -> "");
        PLACEHOLDERS.put("time", (timing, vars, ctx, args) -> requirePlayer(vars).getWorld().getTime());
        PLACEHOLDERS.put("tps", (timing, vars, ctx, args) -> 20.0d);
        PLACEHOLDERS.put("world", (timing, vars, ctx, args) -> requirePlayer(vars).getWorld());
        PLACEHOLDERS.put("worldname", (timing, vars, ctx, args) -> requirePlayer(vars).getWorld().getName());
        PLACEHOLDERS.put("x", (timing, vars, ctx, args) -> requirePlayer(vars).getLocation().getBlockX());
        PLACEHOLDERS.put("y", (timing, vars, ctx, args) -> requirePlayer(vars).getLocation().getBlockY());
        PLACEHOLDERS.put("z", (timing, vars, ctx, args) -> requirePlayer(vars).getLocation().getBlockZ());
    }

    @Override
    public Map<String, Placeholder> getBackedMap() {
        return PLACEHOLDERS;
    }

    @Override
    public String toString() {
        return "PlaygroundPlaceholderManager";
    }

}
