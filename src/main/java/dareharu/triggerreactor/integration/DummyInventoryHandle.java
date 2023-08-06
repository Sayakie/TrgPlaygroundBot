package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;
import io.github.wysohn.triggerreactor.core.bridge.IInventory;
import io.github.wysohn.triggerreactor.core.bridge.IItemStack;
import io.github.wysohn.triggerreactor.core.main.IInventoryHandle;
import io.github.wysohn.triggerreactor.core.manager.trigger.inventory.InventoryTrigger;

@Singleton
public final class DummyInventoryHandle implements IInventoryHandle {

    @Override
    public IInventory createInventory(final int size, final String name) {
        return null;
    }

    @Override
    public void fillInventory(final InventoryTrigger trigger, final int size, final IInventory inventory) {}

    @Override
    public Class<?> getItemClass() {
        return null;
    }

    @Override
    public IItemStack wrapItemStack(final Object item) {
        return null;
    }

    @Override
    public void setContents(final IInventory inventory, final IItemStack[] items) {}

    @Override
    public IItemStack[] getContents(final IInventory inventory) {
        return null;
    }

    @Override
    public boolean removeLore(final IItemStack itemStack, final int index) {
        return false;
    }

    @Override
    public boolean setLore(final IItemStack itemStack, final int index, final String lore) {
        return false;
    }

    @Override
    public void addItemLore(final IItemStack itemStack, final String lore) {}

    @Override
    public void setItemTitle(final IItemStack itemStack, final String title) {}

    @Override
    public String toString() {
        return "PlaygroundInventoryHandle";
    }

}
