package dareharu.triggerreactor.integration.bukkit;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import com.destroystokyo.paper.entity.ai.MobGoals;
import dareharu.triggerreactor.util.Main;
import dareharu.triggerreactor.util.UnimplementedOperationException;
import io.papermc.paper.math.Position;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import net.dv8tion.jda.api.JDA;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.Recipe;
import org.bukkit.loot.LootTable;
import org.bukkit.packs.DataPackManager;
import org.bukkit.potion.PotionBrewer;
import org.bukkit.structure.StructureManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Singleton;
import java.io.File;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Singleton
@SuppressWarnings("ConstantValue")
public final class DummyServer extends ServerMock implements Server {

    private static final String FALLBACK_IP_ADDRESS = "127.0.0.1";

    private static final double[] FIXED_TPS = new double[]{ 20, 20, 20 };

    public DummyServer() {
        motd(MiniMessage.miniMessage().deserialize("<rainbow>TriggerReactor Playground Server"));
    }

    @Override
    public boolean isPrimaryThread() {
        return true;
    }

    @Override
    public @NotNull String getName() {
        return "TriggerReactor Playground";
    }

    @Override
    public @NotNull String getVersion() {
        return String.format("%s (MC: %s)", getName(), getMinecraftVersion());
    }

    @Override
    public int getPort() {
        return 25565;
    }

    @Override
    public @NotNull String getIp() {
        return FALLBACK_IP_ADDRESS;
    }

    @Override
    public @NotNull List<String> getInitialEnabledPacks() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull List<String> getInitialDisabledPacks() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull DataPackManager getDataPackManager() {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull String getResourcePack() {
        return "";
    }

    @Override
    public @NotNull String getResourcePackHash() {
        return "";
    }

    @Override
    public @NotNull String getResourcePackPrompt() {
        return "";
    }

    @Override
    public boolean isResourcePackRequired() {
        return false;
    }

    @Override
    public long getConnectionThrottle() {
        return 5000L;
    }

    @Override
    public int getTicksPerAnimalSpawns() {
        return 400;
    }

    @Override
    public int getTicksPerMonsterSpawns() {
        return 1;
    }

    @Override
    public int getTicksPerWaterSpawns() {
        return 1;
    }

    @Override
    public int getTicksPerWaterAmbientSpawns() {
        return 1;
    }

    @Override
    public int getTicksPerWaterUndergroundCreatureSpawns() {
        return 1;
    }

    @Override
    public int getTicksPerAmbientSpawns() {
        return 1;
    }

    @Override
    public int getTicksPerSpawns(final @NotNull SpawnCategory spawnCategory) {
        return switch (spawnCategory) {
            case ANIMAL, WATER_ANIMAL -> 400;
            default -> 1;
        };
    }

    @Override
    public boolean isTickingWorlds() {
        return true;
    }

    @Override
    public boolean unloadWorld(final @NotNull String name, final boolean save) {
        final @Nullable World world = getWorld(name);

        if (world != null) {
            return unloadWorld(world, save);
        }

        return false;
    }

    @Override
    public boolean unloadWorld(final @NotNull World name, final boolean save) {
        // TODO
        return true;
    }

    @Override
    public @Nullable World getWorld(final @NotNull NamespacedKey worldKey) {
        if (worldKey == null) {
            return null;
        }

        for (final World world : getWorlds()) {
            if (world.getKey().equals(worldKey)) {
                return world;
            }
        }

        return null;
    }

    @Override
    public @NotNull WorldBorder createWorldBorder() {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull ItemStack createExplorerMap(
        final @NotNull World world,
        final @NotNull Location location,
        final @NotNull StructureType structureType
    ) {
        return createExplorerMap(world, location, structureType, 128, false);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull ItemStack createExplorerMap(
        final @NotNull World world,
        final @NotNull Location location,
        final @NotNull StructureType structureType,
        final int radius,
        final boolean findUnexplored
    ) {
        throw new UnimplementedOperationException();
    }

    @Override
    public void reloadData() {}

    @Override
    public @NotNull Logger getLogger() {
        return super.getLogger();
        // TODO
        // return DummyLogger.identity();
    }

    @Override
    public void savePlayers() {
        throw new UnimplementedOperationException();
    }

    @Override
    public @Nullable Recipe getCraftingRecipe(final @NotNull ItemStack[] craftingMatrix, final @NotNull World world) {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull ItemStack craftItem(
        final @NotNull ItemStack[] craftingMatrix,
        final @NotNull World world,
        final @NotNull Player player
    ) {
        throw new UnimplementedOperationException();
    }

    @Override
    public void resetRecipes() {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull Map<String, String[]> getCommandAliases() {
        throw new UnimplementedOperationException();
    }

    @Override
    public void shutdown() {
        throw new UnimplementedOperationException();
    }

    @Override
    public @Nullable OfflinePlayer getOfflinePlayerIfCached(final @NotNull String name) {
        throw new UnimplementedOperationException();
    }

    @Override
    public void banIP(final @NotNull InetAddress address) {
        throw new UnimplementedOperationException();
    }

    @Override
    public void unbanIP(final @NotNull InetAddress address) {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull CommandSender createCommandSender(final @NotNull java.util.function.Consumer<? super Component> feedback) {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull File getWorldContainer() {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull InventoryMock createInventory(final @Nullable InventoryHolder owner, final @NotNull InventoryType type, final @NotNull Component title) {
        if (title instanceof TextComponent text) {
            return createInventory(owner, type, text.content());
        }

        return createInventory(owner, type);
    }

    @Override
    public @NotNull InventoryMock createInventory(
        final @Nullable InventoryHolder owner,
        final int size,
        final @NotNull Component title
    ) throws IllegalArgumentException {
        if (size < 9 || size % 9 != 0 || size > 54) {
            throw new IllegalArgumentException("Invalid size");
        }

        if (title instanceof TextComponent text) {
            return createInventory(owner, InventoryType.CHEST, text.content());
        }

        return createInventory(owner, InventoryType.CHEST);
    }

    @Override
    public @NotNull Merchant createMerchant(final @Nullable Component title) {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull Merchant createMerchant(final @Nullable String title) {
        throw new UnimplementedOperationException();
    }

    @Override
    public int getMonsterSpawnLimit() {
        return 128;
    }

    @Override
    public int getAnimalSpawnLimit() {
        return 16;
    }

    @Override
    public int getWaterAnimalSpawnLimit() {
        return 16;
    }

    @Override
    public int getWaterAmbientSpawnLimit() {
        return 32;
    }

    @Override
    public int getWaterUndergroundCreatureSpawnLimit() {
        return 16;
    }

    @Override
    public int getAmbientSpawnLimit() {
        return 32;
    }

    @Override
    public int getSpawnLimit(final @NotNull SpawnCategory spawnCategory) {
        return switch (spawnCategory) {
            case MONSTER -> getMonsterSpawnLimit();
            case ANIMAL -> getAnimalSpawnLimit();
            case WATER_ANIMAL -> getWaterAnimalSpawnLimit();
            case WATER_AMBIENT -> getWaterAmbientSpawnLimit();
            case WATER_UNDERGROUND_CREATURE -> getWaterUndergroundCreatureSpawnLimit();
            case AXOLOTL -> 4;
            default -> -1;
        };
    }

    private int idelTimeout = 5;

    @Override
    public void setIdleTimeout(final int threshold) {
        this.idelTimeout = threshold;
    }

    @Override
    public int getIdleTimeout() {
        return idelTimeout;
    }

    @Override
    public @NotNull ChunkGenerator.ChunkData createVanillaChunkData(final @NotNull World world, final int x, final int z) {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull double[] getTPS() {
        return FIXED_TPS;
    }

    @Override
    public @NotNull long[] getTickTimes() {
        final long milliseconds = System.currentTimeMillis();

        return new long[] {
            milliseconds,
            milliseconds - 300_000,
            milliseconds - 900_000
        };
    }

    @Override
    public @NotNull double getAverageTickTime() {
        return 20;
    }

    @Override
    public @Nullable Advancement getAdvancement(final @NotNull NamespacedKey key) {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull Iterator<Advancement> advancementIterator() {
        return Collections.emptyIterator();
    }

    @Override
    public @NotNull BlockData createBlockData(final @NotNull String data) throws IllegalArgumentException {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull BlockData createBlockData(final @NotNull Material material, final @Nullable String data) {
        throw new UnimplementedOperationException();
    }

    @Override
    public <T extends Keyed> @NotNull Iterable<Tag<T>> getTags(final @NotNull String registry, final @NotNull Class<T> clazz) {
        return Collections.emptyList();
    }

    @Override
    public @Nullable LootTable getLootTable(final @NotNull NamespacedKey key) {
        return null;
    }

    @Override
    public @NotNull List<Entity> selectEntities(final @NotNull CommandSender sender, final @NotNull String selector) throws IllegalArgumentException {
        return Collections.emptyList();
    }

    @Override
    public @NotNull StructureManager getStructureManager() {
        throw new UnimplementedOperationException();
    }

    @Override
    public <T extends Keyed> @Nullable Registry<T> getRegistry(final @NotNull Class<T> clazz) {
        return null;
    }

    @Override
    public void reloadPermissions() {}

    @Override
    public boolean reloadCommandAliases() {
        return false;
    }

    @Override
    public boolean suggestPlayerNamesWhenNullTabCompletions() {
        return false;
    }

    @Override
    public int getCurrentTick() {
        return 20;
    }

    @Override
    public boolean isStopping() {
        return Main.current().jda() == null || Main.current().jda().getStatus() != JDA.Status.CONNECTED;
    }

    @Override
    public @NotNull MobGoals getMobGoals() {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull io.papermc.paper.datapack.DatapackManager getDatapackManager() {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull PotionBrewer getPotionBrewer() {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull RegionScheduler getRegionScheduler() {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull AsyncScheduler getAsyncScheduler() {
        throw new UnimplementedOperationException();
    }

    @Override
    public @NotNull GlobalRegionScheduler getGlobalRegionScheduler() {
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean isOwnedByCurrentRegion(final @NotNull World world, final @NotNull Position position) {
        return false;
    }

    @Override
    public boolean isOwnedByCurrentRegion(
        final @NotNull World world,
        final @NotNull Position position,
        final int squareRadiusChunks
    ) {
        return false;
    }

    @Override
    public boolean isOwnedByCurrentRegion(final @NotNull Location location) {
        return false;
    }

    @Override
    public boolean isOwnedByCurrentRegion(final @NotNull Location location, final int squareRadiusChunks) {
        return false;
    }

    @Override
    public boolean isOwnedByCurrentRegion(final @NotNull World world, final int chunkX, final int chunkZ) {
        return false;
    }

    @Override
    public boolean isOwnedByCurrentRegion(final @NotNull World world, final int chunkX, final int chunkZ, final int squareRadiusChunks) {
        return false;
    }

    @Override
    public boolean isOwnedByCurrentRegion(final @NotNull Entity entity) {
        return false;
    }

    @Override
    public String toString() {
        return "PlaygroundServer";
    }

}
