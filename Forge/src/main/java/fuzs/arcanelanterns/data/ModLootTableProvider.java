package fuzs.arcanelanterns.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import fuzs.arcanelanterns.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLootTableProvider extends LootTableProvider {
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> subProviders = ImmutableList.of(Pair.of(ModBlockLoot::new, LootContextParamSets.BLOCK));
    private final String modId;

    public ModLootTableProvider(DataGenerator dataGenerator, String modId) {
        super(dataGenerator);
        this.modId = modId;
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return this.subProviders;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationContext) {

    }

    private class ModBlockLoot extends BlockLoot {

        @Override
        protected void addTables() {
            this.dropSelf(ModRegistry.LANTERN_MAKER_BLOCK.get());
            this.dropSelf(ModRegistry.LIFE_LANTERN_BLOCK.get());
            this.dropSelf(ModRegistry.FERAL_LANTERN_BLOCK.get());
            this.dropSelf(ModRegistry.LOVE_LANTERN_BLOCK.get());
            this.dropSelf(ModRegistry.WAILING_LANTERN_BLOCK.get());
            this.dropSelf(ModRegistry.BOREAL_LANTERN_BLOCK.get());
            this.dropSelf(ModRegistry.BRILLIANT_LANTERN_BLOCK.get());
            this.dropSelf(ModRegistry.WARDING_LANTERN_BLOCK.get());
            this.dropSelf(ModRegistry.CONTAINING_LANTERN_BLOCK.get());
            this.dropSelf(ModRegistry.WITHERING_LANTERN_BLOCK.get());
            this.dropSelf(ModRegistry.CLOUD_LANTERN_BLOCK.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getEntries().stream()
                    .filter(e -> e.getKey().location().getNamespace().equals(ModLootTableProvider.this.modId))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toSet());
        }
    }
}
