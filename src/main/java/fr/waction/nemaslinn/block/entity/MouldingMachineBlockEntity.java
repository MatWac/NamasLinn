package fr.waction.nemaslinn.block.entity;

import fr.waction.nemaslinn.block.MouldingMachineBlock;
import fr.waction.nemaslinn.block.RawOreFoundryBlock;
import fr.waction.nemaslinn.fluid.ModFluids;
import fr.waction.nemaslinn.item.ModItems;
import fr.waction.nemaslinn.networking.ModMessages;
import fr.waction.nemaslinn.networking.packet.FluidSyncS2CPacket;
import fr.waction.nemaslinn.screen.MoultingMachineMenu;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MouldingMachineBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getItem().equals(ModItems.MOLTEN_OSMIUM_BUCKET.get()) || stack.getItem().equals(ModItems.MOLTEN_THORIUM_BUCKET.get()) || stack.getItem().equals(ModItems.MOLTEN_YTTRIUM_BUCKET.get());
                case 1 -> stack.getItem().equals(ModItems.SWORD_MOLD.get()) || stack.getItem().equals(ModItems.PICKAXE_MOLD.get());
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final FluidTank FLUID_TANK = new FluidTank(4000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == ModFluids.MOLTEN_OSMIUM.get() || stack.getFluid() == ModFluids.MOLTEN_YTTRIUM.get() || stack.getFluid() == ModFluids.MOLTEN_THORIUM.get();
        }
    };

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 400;

    private Fluid actualFluid ;
    private Item actualMold;

    public MouldingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MOULDING_MACHINE.get(), pos, state);

        this.actualFluid = MouldingMachineBlockEntity.this.FLUID_TANK.getFluid().getFluid();
        this.actualMold = MouldingMachineBlockEntity.this.itemHandler.getStackInSlot(1).getItem();

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MouldingMachineBlockEntity.this.progress;
                    case 1 -> MouldingMachineBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> MouldingMachineBlockEntity.this.progress = value;
                    case 1 -> MouldingMachineBlockEntity.this.maxProgress = value;
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
        return Component.literal(" Moulting Machine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {

        ModMessages.sendToClients(new FluidSyncS2CPacket(this.getFluidStack(), worldPosition));
        return new MoultingMachineMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt = FLUID_TANK.writeToNBT(nbt);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        FLUID_TANK.readFromNBT(nbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    public static void tick(Level level, BlockPos pos, BlockState state, MouldingMachineBlockEntity pEntity) {

        boolean lit = pEntity.isLit();
        boolean changed = false;

        if (level.isClientSide()) {
            return;
        }

        if (canFill(pEntity)) {
            fill(pEntity);
            changed = true;
        }

        if (hasMoltenOre(pEntity)) {

            pEntity.progress++;
            changed = true;

            if(!pEntity.actualMold.equals(pEntity.itemHandler.getStackInSlot(1).getItem())){
                pEntity.resetProgress();
            }

            if (pEntity.progress >= pEntity.maxProgress) {
                mold(pEntity, pEntity.actualFluid);
                changed = true;
            }

            pEntity.actualFluid = pEntity.FLUID_TANK.getFluid().getFluid();

        } else {
            pEntity.resetProgress();
            changed = true;
        }

        if (lit != pEntity.isLit()) {
            state = state.setValue(MouldingMachineBlock.LIT, Boolean.valueOf(pEntity.isLit()));
            level.setBlock(pos, state, 3);
            changed = true;
        }

        pEntity.actualMold = pEntity.itemHandler.getStackInSlot(1).getItem();


        if (changed) {
            setChanged(level, pos, state);
        }
    }

    private static boolean canFill(MouldingMachineBlockEntity entity) {


        boolean isRightFluid = entity.FLUID_TANK.getFluid().getRawFluid().getBucket() == entity.itemHandler.getStackInSlot(0).getItem();
        boolean isEmpty = entity.FLUID_TANK.getFluid().isEmpty();
        boolean hasRightItem = entity.itemHandler.getStackInSlot(0).getItem().equals(ModItems.MOLTEN_OSMIUM_BUCKET.get()) ||
                entity.itemHandler.getStackInSlot(0).getItem().equals(ModItems.MOLTEN_THORIUM_BUCKET.get()) ||
                entity.itemHandler.getStackInSlot(0).getItem().equals(ModItems.MOLTEN_YTTRIUM_BUCKET.get());

        return entity.FLUID_TANK.getFluidAmount() < entity.FLUID_TANK.getCapacity() && (isRightFluid || isEmpty) && hasRightItem;
    }

    private static void fill(MouldingMachineBlockEntity entity) {

        FluidStack stack = new FluidStack(Objects.requireNonNull(getFluidFromItem(entity.itemHandler.getStackInSlot(0).getItem())), 1000);
        entity.itemHandler.extractItem(0, 1, false);
        entity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);
        entity.itemHandler.setStackInSlot(0, new ItemStack(Items.BUCKET));
    }

    private static boolean hasMoltenOre(MouldingMachineBlockEntity entity) {

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        return hasMold(inventory) && canInsertItemIntoOutputSlot(inventory) && hasEnoughtFluid(entity);
    }

    private static boolean hasEnoughtFluid(MouldingMachineBlockEntity entity){
         if(entity.itemHandler.getStackInSlot(1).getItem().equals(ModItems.PICKAXE_MOLD.get())){
             if(entity.FLUID_TANK.getFluidAmount() >= 3000){
                 return true;
             }else return false;
         }else if(entity.itemHandler.getStackInSlot(1).getItem().equals(ModItems.SWORD_MOLD.get())){
             if(entity.FLUID_TANK.getFluidAmount() >= 2000){
                 return true;
             }else return false;
        }else return false;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void mold(MouldingMachineBlockEntity entity, Fluid fluid) {

        if(entity.itemHandler.getStackInSlot(1).getItem().equals(ModItems.PICKAXE_MOLD.get())){
            entity.FLUID_TANK.drain(3000, IFluidHandler.FluidAction.EXECUTE);
        }else if (entity.itemHandler.getStackInSlot(1).getItem().equals(ModItems.SWORD_MOLD.get())){
            entity.FLUID_TANK.drain(2000, IFluidHandler.FluidAction.EXECUTE);
        }else return;
        entity.itemHandler.setStackInSlot(2, new ItemStack(getItemFromFluid(fluid, entity.itemHandler.getStackInSlot(1).getItem())));
    }

    private static boolean hasMold(SimpleContainer inventory) {
        return inventory.getItem(1).getItem().equals(ModItems.PICKAXE_MOLD.get()) || inventory.getItem(1).getItem().equals(ModItems.SWORD_MOLD.get());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).isEmpty();
    }

    private boolean isLit() {
        return this.FLUID_TANK.getFluidAmount() > 0;
    }

    private static Fluid getFluidFromItem(Item item) {

        if (item.equals(ModItems.MOLTEN_YTTRIUM_BUCKET.get())) {
            return ModFluids.MOLTEN_YTTRIUM.get();
        } else if (item.equals(ModItems.MOLTEN_OSMIUM_BUCKET.get())) {
            return ModFluids.MOLTEN_OSMIUM.get();
        } else if (item.equals(ModItems.MOLTEN_THORIUM_BUCKET.get())) {
            return ModFluids.MOLTEN_THORIUM.get();
        } else return null;
    }

    private static Item getItemFromFluid(Fluid fluid, Item item) {

        if (item.equals(ModItems.PICKAXE_MOLD.get())) {
            if (fluid.equals(ModFluids.MOLTEN_THORIUM.get())) {
                return ModItems.THORIUM_PICKAXE_HEAD.get();
            }else if (fluid.equals(ModFluids.MOLTEN_YTTRIUM.get())) {
                return ModItems.YTTRIUM_PICKAXE_HEAD.get();
            }else if (fluid.equals(ModFluids.MOLTEN_OSMIUM.get())) {
                return ModItems.OSMIUM_PICKAXE_HEAD.get();
            }else return null;
        } else if (item.equals(ModItems.SWORD_MOLD.get())) {
            if (fluid.equals(ModFluids.MOLTEN_THORIUM.get())) {
                return ModItems.THORIUM_SWORD_BLADE.get();
            }else if (fluid.equals(ModFluids.MOLTEN_YTTRIUM.get())) {
                return ModItems.YTTRIUM_SWORD_BLADE.get();
            }else if (fluid.equals(ModFluids.MOLTEN_OSMIUM.get())) {
                return ModItems.OSMIUM_SWORD_BLADE.get();
            }else return null;
        } else return null;
    }
}