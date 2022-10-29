package fr.waction.nemaslinn.block.entity;

import fr.waction.nemaslinn.item.ModItems;
import fr.waction.nemaslinn.screen.RawOreFoundryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RawOreFoundryBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 150;


    public RawOreFoundryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RAW_ORE_FOUNDRY.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> RawOreFoundryBlockEntity.this.progress;
                    case 1 -> RawOreFoundryBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> RawOreFoundryBlockEntity.this.progress = value;
                    case 1 -> RawOreFoundryBlockEntity.this.maxProgress = value;
                }

            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal(" Raw Ore Foundry");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new RawOreFoundryMenu(id, inventory,this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();

        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, RawOreFoundryBlockEntity pEntity) {

        if (level.isClientSide()) {
            return;
        }

        if (hasRecipe(pEntity)) {

            consumeFuel(pEntity);

            pEntity.progress++;
            setChanged(level, pos, state);

            if (pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }
    }

    private static void consumeFuel(RawOreFoundryBlockEntity pEntity) {

        pEntity.ge
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(RawOreFoundryBlockEntity pEntity) {

        if(hasRecipe(pEntity)){
            pEntity.itemHandler.extractItem(1, 0, false);
            pEntity.itemHandler.extractItem(2, 1, false);
            if (ModItems.RAW_OSMIUM.equals(pEntity.itemHandler.getStackInSlot(1))) {
                pEntity.itemHandler.setStackInSlot(3, new ItemStack(ModItems.MOLTEN_OSMIUM_BUCKET.get()));
            } else if (ModItems.RAW_YTTRIUM.equals(pEntity.itemHandler.getStackInSlot(1))) {
                pEntity.itemHandler.setStackInSlot(3, new ItemStack(ModItems.MOLTEN_YTTRIUM_BUCKET.get()));
            }else if (ModItems.RAW_THORIUM.equals(pEntity.itemHandler.getStackInSlot(1))) {
                pEntity.itemHandler.setStackInSlot(3, new ItemStack(ModItems.MOLTEN_THORIUM_BUCKET.get()));
            }


            pEntity.resetProgress();
        }

    }

    private static boolean hasRecipe(RawOreFoundryBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        boolean hasRawOreInFirstSlot = entity.itemHandler.getStackInSlot(1).getItem() == ModItems.RAW_OSMIUM.get() ||
                entity.itemHandler.getStackInSlot(1).getItem() == ModItems.RAW_THORIUM.get() || entity.itemHandler.getStackInSlot(1).getItem() == ModItems.RAW_YTTRIUM.get();

        boolean hasFuel = AbstractFurnaceBlockEntity.isFuel(entity.itemHandler.getStackInSlot(0));

        boolean hasBucket = entity.itemHandler.getStackInSlot(2).getItem() == Items.BUCKET;

        return hasRawOreInFirstSlot && hasFuel && hasBucket && canInsertItemIntoOutputSlot(inventory) ;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(3).isEmpty();
    }

}