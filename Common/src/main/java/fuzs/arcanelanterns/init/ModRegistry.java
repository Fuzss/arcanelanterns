package fuzs.arcanelanterns.init;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.world.level.block.*;
import fuzs.arcanelanterns.world.level.block.entity.*;
import fuzs.puzzleslib.core.CommonAbstractions;
import fuzs.puzzleslib.core.CommonFactories;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import fuzs.puzzleslib.init.builder.ModBlockEntityTypeBuilder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class ModRegistry {
    private static final CreativeModeTab CREATIVE_MODE_TAB = CommonAbstractions.INSTANCE.creativeModeTab(ArcaneLanterns.MOD_ID, () -> new ItemStack(Items.LANTERN));
    private static final RegistryManager REGISTRY = CommonFactories.INSTANCE.registration(ArcaneLanterns.MOD_ID);
    public static final RegistryReference<Block> LANTERN_MAKER_BLOCK = REGISTRY.registerBlockWithItem("lantern_maker", () -> new LanternMakerBlock(BlockBehaviour.Properties.of(Material.STONE).destroyTime(4.0F).noOcclusion()), CREATIVE_MODE_TAB);
    public static final RegistryReference<Block> SPARK_BLOCK = REGISTRY.registerBlock("spark", () -> new SparkBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION).sound(SoundType.STONE).instabreak().noLootTable().lightLevel(state -> 15).noCollission().noOcclusion()));
    public static final RegistryReference<Block> LIFE_LANTERN_BLOCK = REGISTRY.registerBlockWithItem("life_lantern", () -> new LifeLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)), CREATIVE_MODE_TAB);
    public static final RegistryReference<Block> FERAL_LANTERN_BLOCK = REGISTRY.registerBlockWithItem("feral_lantern", () -> new FeralLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)), CREATIVE_MODE_TAB);
    public static final RegistryReference<Block> LOVE_LANTERN_BLOCK = REGISTRY.registerBlockWithItem("love_lantern", () -> new LoveLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)), CREATIVE_MODE_TAB);
    public static final RegistryReference<Block> WAILING_LANTERN_BLOCK = REGISTRY.registerBlockWithItem("wailing_lantern", () -> new WailingLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)), CREATIVE_MODE_TAB);
    public static final RegistryReference<Block> BOREAL_LANTERN_BLOCK = REGISTRY.registerBlockWithItem("boreal_lantern", () -> new BorealLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)), CREATIVE_MODE_TAB);
    public static final RegistryReference<Block> BRILIANT_LANTERN_BLOCK = REGISTRY.registerBlockWithItem("briliant_lantern", () -> new BriliantLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)), CREATIVE_MODE_TAB);
    public static final RegistryReference<Block> WARDING_LANTERN_BLOCK = REGISTRY.registerBlockWithItem("warding_lantern", () -> new WardingLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)), CREATIVE_MODE_TAB);
    public static final RegistryReference<Block> CONTAINING_LANTERN_BLOCK = REGISTRY.registerBlockWithItem("containing_lantern", () -> new ContainingLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)), CREATIVE_MODE_TAB);
    public static final RegistryReference<Block> WITHERING_LANTERN_BLOCK = REGISTRY.registerBlockWithItem("withering_lantern", () -> new WitheringLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)), CREATIVE_MODE_TAB);
    public static final RegistryReference<Block> CLOUD_LANTERN_BLOCK = REGISTRY.registerBlockWithItem("cloud_lantern", () -> new CloudLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)), CREATIVE_MODE_TAB);
    public static final RegistryReference<BlockEntityType<LanternMakerBlockEntity>> LANTERN_MAKER_BLOCK_ENTITY = REGISTRY.registerBlockEntityTypeBuilder("lantern_maker", () -> ModBlockEntityTypeBuilder.of(LanternMakerBlockEntity::new, LANTERN_MAKER_BLOCK.get()));
    public static final RegistryReference<BlockEntityType<LifeLanternBlockEntity>> LIFE_LANTERN_BLOCK_ENTITY = REGISTRY.registerBlockEntityTypeBuilder("life_lantern", () -> ModBlockEntityTypeBuilder.of(LifeLanternBlockEntity::new, LIFE_LANTERN_BLOCK.get()));
    public static final RegistryReference<BlockEntityType<FeralLanternBlockEntity>> FERAL_LANTERN_BLOCK_ENTITY = REGISTRY.registerBlockEntityTypeBuilder("feral_lantern", () -> ModBlockEntityTypeBuilder.of(FeralLanternBlockEntity::new, FERAL_LANTERN_BLOCK.get()));
    public static final RegistryReference<BlockEntityType<LoveLanternBlockEntity>> LOVE_LANTERN_BLOCK_ENTITY = REGISTRY.registerBlockEntityTypeBuilder("love_lantern", () -> ModBlockEntityTypeBuilder.of(LoveLanternBlockEntity::new, LOVE_LANTERN_BLOCK.get()));
    public static final RegistryReference<BlockEntityType<WailingLanternBlockEntity>> WAILING_LANTERN_BLOCK_ENTITY = REGISTRY.registerBlockEntityTypeBuilder("wailing_lantern", () -> ModBlockEntityTypeBuilder.of(WailingLanternBlockEntity::new, WAILING_LANTERN_BLOCK.get()));
    public static final RegistryReference<BlockEntityType<BorealLanternBlockEntity>> BOREAL_LANTERN_BLOCK_ENTITY = REGISTRY.registerBlockEntityTypeBuilder("boreal_lantern", () -> ModBlockEntityTypeBuilder.of(BorealLanternBlockEntity::new, BOREAL_LANTERN_BLOCK.get()));
    public static final RegistryReference<BlockEntityType<BriliantLanternBlockEntity>> BRILIANT_LANTERN_BLOCK_ENTITY = REGISTRY.registerBlockEntityTypeBuilder("briliant_lantern", () -> ModBlockEntityTypeBuilder.of(BriliantLanternBlockEntity::new, BRILIANT_LANTERN_BLOCK.get()));
    public static final RegistryReference<BlockEntityType<WardingLanternBlockEntity>> WARDING_LANTERN_BLOCK_ENTITY = REGISTRY.registerBlockEntityTypeBuilder("warding_lantern", () -> ModBlockEntityTypeBuilder.of(WardingLanternBlockEntity::new, WARDING_LANTERN_BLOCK.get()));
    public static final RegistryReference<BlockEntityType<ContainingLanternBlockEntity>> CONTAINING_LANTERN_BLOCK_ENTITY = REGISTRY.registerBlockEntityTypeBuilder("containing_lantern", () -> ModBlockEntityTypeBuilder.of(ContainingLanternBlockEntity::new, CONTAINING_LANTERN_BLOCK.get()));
    public static final RegistryReference<BlockEntityType<WitheringLanternBlockEntity>> WITHERING_LANTERN_BLOCK_ENTITY = REGISTRY.registerBlockEntityTypeBuilder("withering_lantern", () -> ModBlockEntityTypeBuilder.of(WitheringLanternBlockEntity::new, WITHERING_LANTERN_BLOCK.get()));
    public static final RegistryReference<BlockEntityType<CloudLanternBlockEntity>> CLOUD_LANTERN_BLOCK_ENTITY = REGISTRY.registerBlockEntityTypeBuilder("cloud_lantern", () -> ModBlockEntityTypeBuilder.of(CloudLanternBlockEntity::new, CLOUD_LANTERN_BLOCK.get()));

    public static void touch() {

    }
}
