package fr.waction.nemaslinn.block.entity;

import fr.waction.nemaslinn.fluid.ModFluidTypes;
import fr.waction.nemaslinn.fluid.ModFluids;
import fr.waction.nemaslinn.item.ModItems;
import fr.waction.nemaslinn.networking.ModMessages;
import fr.waction.nemaslinn.networking.packet.FluidSyncS2CPacket;
import fr.waction.nemaslinn.recipe.RawOreFoundryRecipe;
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
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
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
import java.util.Optional;

public class RawOreFoundryBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot){
                case 0 -> isFuel(stack);
                case 1 -> hasRecipe(stack.getItem());
                case 2 -> stack.getItem().equals(Items.BUCKET);
                case 3 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final FluidTank FLUID_TANK = new FluidTank(4000){
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(!level.isClientSide()){
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }
        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == ModFluids.MOLTEN_OSMIUM.get() || stack.getFluid() == ModFluids.MOLTEN_YTTRIUM.get() || stack.getFluid() == ModFluids.MOLTEN_THORIUM.get();
        }
    };

    public void setFluid(FluidStack stack){
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack(){
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 400;
    private int fuel = 0;
    private int maxFuel;


    public RawOreFoundryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RAW_ORE_FOUNDRY.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> RawOreFoundryBlockEntity.this.progress;
                    case 1 -> RawOreFoundryBlockEntity.this.maxProgress;
                    case 2 -> RawOreFoundryBlockEntity.this.fuel;
                    case 3 -> RawOreFoundryBlockEntity.this.maxFuel;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> RawOreFoundryBlockEntity.this.progress = value;
                    case 1 -> RawOreFoundryBlockEntity.this.maxProgress = value;
                    case 2 -> RawOreFoundryBlockEntity.this.fuel = value;
                    case 3 -> RawOreFoundryBlockEntity.this.maxFuel = value;
                }

            }

            @Override
            public int getCount() {
                return 4;
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

        ModMessages.sendToClients(new FluidSyncS2CPacket(this.getFluidStack(), worldPosition));
        return new RawOreFoundryMenu(id, inventory,this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();

        }

        if(cap == ForgeCapabilities.FLUID_HANDLER){
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


    public static void tick(Level level, BlockPos pos, BlockState state, RawOreFoundryBlockEntity pEntity) {

        if (level.isClientSide()) {
            return;
        }

        if(canConsumeFuel(pEntity)){
            consumeFuel(pEntity);
            setChanged(level, pos, state);
        }

        if(hasMoltenOre(pEntity)){

                pEntity.fuel--;
                pEntity.progress++;
                setChanged(level, pos, state);

                if (pEntity.progress >= pEntity.maxProgress) {
                    meltItem(pEntity);
                    setChanged(level, pos, state);
                }
        } else if(pEntity.fuel >= 0){

            pEntity.fuel--;
            pEntity.resetProgress();
            setChanged(level, pos, state);

        }else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }

        if(hasEnoughFluid(pEntity)){
            fillBucket(pEntity);
        }
    }
    private static void meltItem(RawOreFoundryBlockEntity entity) {

        FluidStack stack = new FluidStack(Objects.requireNonNull(getFluidFromItem(entity.itemHandler.getStackInSlot(1).getItem())), 200);
        entity.itemHandler.extractItem(1, 1, false);
        entity.FLUID_TANK.fill(stack,IFluidHandler.FluidAction.EXECUTE);
        entity.resetProgress();
    }

    private static void consumeFuel(RawOreFoundryBlockEntity entity) {

        entity.maxFuel = ForgeHooks.getBurnTime(entity.itemHandler.getStackInSlot(0), RecipeType.SMELTING);
        entity.fuel = entity.maxFuel;
        entity.itemHandler.extractItem(0, 1, false);

    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void fillBucket(RawOreFoundryBlockEntity entity) {

        entity.itemHandler.extractItem(2,1,false);
        entity.FLUID_TANK.drain(1000,IFluidHandler.FluidAction.EXECUTE);
        System.out.println(getOutPutItem(entity));
        entity.itemHandler.setStackInSlot(3, new ItemStack(getOutPutItem(entity)));
    }

    private static boolean canConsumeFuel(RawOreFoundryBlockEntity entity) {

        return entity.fuel <= 0 && isRightFluid(entity) && isFuel(entity.itemHandler.getStackInSlot(0)) && !entity.itemHandler.getStackInSlot(1).isEmpty();
    }

    private static boolean hasEnoughFluid(RawOreFoundryBlockEntity entity) {

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        boolean hasBucket = entity.itemHandler.getStackInSlot(2).getItem().equals(Items.BUCKET);

        return entity.FLUID_TANK.getFluidAmount() >=1000 && hasBucket && canInsertItemIntoOutputSlot(inventory);
    }
    private static boolean hasMoltenOre(RawOreFoundryBlockEntity entity){

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        boolean hasFuel = entity.fuel > 0;
        return hasFuel && isRightFluid(entity) && hasRecipe(inventory.getItem(1).getItem());
    }

    private static boolean isRightFluid(RawOreFoundryBlockEntity entity) {


        if(entity.FLUID_TANK.isEmpty()){
            return true;
        }else {
            return entity.FLUID_TANK.getFluid().getFluid().equals(getFluidFromItem(entity.itemHandler.getStackInSlot(1).getItem()));
        }
    }

    public static boolean isFuel(ItemStack stack){
        return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) >0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(3).isEmpty();
    }

    private static boolean hasRecipe(Item item){

        return item.equals(ModItems.RAW_OSMIUM.get()) || item.equals(ModItems.RAW_THORIUM.get()) || item.equals(ModItems.RAW_YTTRIUM.get());
    }

    private static Item getOutPutItem(RawOreFoundryBlockEntity entity){

        System.out.println(entity.getFluidStack().getFluid());
        System.out.println(entity.getFluidStack());
        System.out.println(entity.getFluidStack().equals(new FluidStack(ModFluids.MOLTEN_THORIUM.get().getFlowing(), 1)));

       /* if(entity.getFluidStack().getFluid().getFluidType().equals(ModFluidTypes.MOLTEN_OSMIUM_FLUID_TYPE.get())){
            return ModItems.MOLTEN_OSMIUM_BUCKET.get();
        } else if (entity.getFluidStack().getFluid().getFluidType().equals(ModFluidTypes.MOLTEN_YTTRIUM_FLUID_TYPE.get())){
            return ModItems.MOLTEN_YTTRIUM_BUCKET.get();
        }else if (entity.getFluidStack().getFluid().getFluidType().equals(ModFluidTypes.MOLTEN_THORIUM_FLUID_TYPE.get())){
            return ModItems.MOLTEN_THORIUM_BUCKET.get();
        }else return null;*/

        return null;
    }

    private static Fluid getFluidFromItem(Item item){

        if(item.equals(ModItems.RAW_YTTRIUM.get())){
            return ModFluids.MOLTEN_YTTRIUM.get();
        } else if (item.equals(ModItems.RAW_OSMIUM.get())){
            return ModFluids.MOLTEN_OSMIUM.get();
        }else if (item.equals(ModItems.RAW_THORIUM.get())){
            return ModFluids.MOLTEN_THORIUM.get();
        }else return null;
    }

}