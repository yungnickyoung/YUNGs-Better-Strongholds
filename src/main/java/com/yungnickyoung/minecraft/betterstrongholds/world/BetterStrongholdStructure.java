package com.yungnickyoung.minecraft.betterstrongholds.world;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterstrongholds.BetterStrongholds;
import com.yungnickyoung.minecraft.betterstrongholds.world.jigsaw.JigsawConfig;
import com.yungnickyoung.minecraft.betterstrongholds.world.jigsaw.JigsawManager;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
public class BetterStrongholdStructure extends Structure<NoFeatureConfig> {
    public BetterStrongholdStructure(Codec<NoFeatureConfig> p_i231996_1_) {
        super(p_i231996_1_);
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return BetterStrongholdStructure.Start::new;
    }

    @Override
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.STRONGHOLDS;
    }

    /**
     * Returns the name displayed when the locate command is used.
     * I believe (not 100% sure) that the lowercase form of this value must also match
     * the key of the entry added to Structure.field_236365_a_ during common setup.
     */
    @Override
    public String getStructureName() {
        return "Better Stronghold";
    }

    /**
     * || ONLY WORKS IN FORGE 34.1.12+ ||
     *
     * This method allows us to have mobs that spawn naturally over time in our structure.
     * No other mobs will spawn in the structure of the same entity classification.
     * The reason you want to match the classifications is so that your structure's mob
     * will contribute to that classification's cap. Otherwise, it may cause a runaway
     * spawning of the mob that will never stop.
     *
     * NOTE: getDefaultSpawnList is for monsters only and getDefaultCreatureSpawnList is
     *       for creatures only. If you want to add entities of another classification,
     *       use the StructureSpawnListGatherEvent to add water_creatures, water_ambient,
     *       ambient, or misc mobs. Use that event to add/remove mobs from structures
     *       that are not your own.
     *
     * NOTE 2: getSpawnList and getCreatureSpawnList is the vanilla methods that Forge does
     *         not hook up. Do not use those methods or else the mobs won't spawn. You would
     *         have to manually implement spawning for them. Stick with Forge's Default form
     *         as it is easier to use that.
     */
    private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = ImmutableList.of(
        new MobSpawnInfo.Spawners(EntityType.ILLUSIONER, 100, 4, 9),
        new MobSpawnInfo.Spawners(EntityType.VINDICATOR, 100, 4, 9)
    );
    @Override
    public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
        return STRUCTURE_MONSTERS;
    }

    private static final List<MobSpawnInfo.Spawners> STRUCTURE_CREATURES = ImmutableList.of(
        new MobSpawnInfo.Spawners(EntityType.SHEEP, 30, 10, 15),
        new MobSpawnInfo.Spawners(EntityType.RABBIT, 100, 1, 2)
    );
    @Override
    public List<MobSpawnInfo.Spawners> getDefaultCreatureSpawnList() {
        return STRUCTURE_CREATURES;
    }

    /**
     * shouldStartAt
     *
     * Vanilla has its own complex behavior for stronghold spawning.
     * We don't worry about that here - instead, we just prevent spawning close to the initial world spawn.
     * This is identical behavior to the stronghold in Repurposed Structures.
     */
    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        return (chunkX * chunkX) + (chunkZ * chunkZ) > 10000;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        @ParametersAreNonnullByDefault
        public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
            // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;

            // TODO - config options for min/maxY
            int minY = 30;
            int maxY = 31;
            int y = rand.nextInt(maxY - minY) + minY;

            BlockPos blockpos = new BlockPos(x, y, z);
            JigsawConfig villageConfig = new JigsawConfig(
                () -> dynamicRegistryManager.getRegistry(Registry.JIGSAW_POOL_KEY)
                    .getOrDefault(new ResourceLocation(BetterStrongholds.MOD_ID, "starts")),
                16 // TODO - config option for max stronghold size
            );

            // All a structure has to do is call this method to turn it into a jigsaw based structure!
            JigsawManager.assembleJigsawStructure(
                dynamicRegistryManager,
                villageConfig,
                chunkGenerator,
                templateManagerIn,
                blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
                this.components, // The list that will be populated with the jigsaw pieces after this method.
                this.rand,
                false,
                false
            );

            // Sets the bounds of the structure once you are finished.
            this.recalculateStructureSize();

            // Vanilla method of adjusting y-coordinate
//            this.func_214628_a(chunkGenerator.getSeaLevel(), this.rand, 10);

            // Debug log the coordinates of the center starting piece.
            BetterStrongholds.LOGGER.debug("Better Stronghold at {} {} {}",
                this.components.get(0).getBoundingBox().minX,
                this.components.get(0).getBoundingBox().minY,
                this.components.get(0).getBoundingBox().minZ
            );
        }
    }
}
