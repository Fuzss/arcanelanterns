package fuzs.arcanelanterns.data;

import fuzs.arcanelanterns.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator dataGenerator, String modId, @Nullable ExistingFileHelper fileHelper) {
        super(dataGenerator, modId, fileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModRegistry.LANTERN_MAKER_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModRegistry.LIFE_LANTERN_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModRegistry.FERAL_LANTERN_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModRegistry.LOVE_LANTERN_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModRegistry.WAILING_LANTERN_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModRegistry.BOREAL_LANTERN_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModRegistry.BRILLIANT_LANTERN_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModRegistry.WARDING_LANTERN_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModRegistry.CONTAINING_LANTERN_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModRegistry.WITHERING_LANTERN_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModRegistry.CLOUD_LANTERN_BLOCK.get());
    }
}
