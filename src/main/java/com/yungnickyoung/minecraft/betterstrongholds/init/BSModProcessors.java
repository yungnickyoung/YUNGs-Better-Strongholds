package com.yungnickyoung.minecraft.betterstrongholds.init;

import com.yungnickyoung.minecraft.betterstrongholds.BetterStrongholds;
import com.yungnickyoung.minecraft.betterstrongholds.world.processor.*;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BSModProcessors {
    public static StructureProcessorType<AirProcessor> AIR_PROCESSOR = () -> AirProcessor.CODEC;
    public static StructureProcessorType<CobwebProcessor> COBWEB_PROCESSOR = () -> CobwebProcessor.CODEC;
    public static StructureProcessorType<TorchProcessor> TORCH_PROCESSOR = () -> TorchProcessor.CODEC;
    public static StructureProcessorType<LanternProcessor> LANTERN_PROCESSOR = () -> LanternProcessor.CODEC;
    public static StructureProcessorType<BannerProcessor> BANNER_PROCESSOR = () -> BannerProcessor.CODEC;
    public static StructureProcessorType<WaterloggedProcessor> WATERLOGGED_PROCESSOR = () -> WaterloggedProcessor.CODEC;
    public static StructureProcessorType<OreProcessor> ORE_PROCESSOR = () -> OreProcessor.CODEC;
    public static StructureProcessorType<RareBlockProcessor> RARE_BLOCK_PROCESSOR = () -> RareBlockProcessor.CODEC;
    public static StructureProcessorType<RedstoneProcessor> REDSTONE_PROCESSOR = () -> RedstoneProcessor.CODEC;
    public static StructureProcessorType<LegProcessor> LEG_PROCESSOR = () -> LegProcessor.CODEC;
//    public static StructureProcessorType<ArmorStandProcessor> ARMORSTAND_PROCESSOR = () -> ArmorStandProcessor.CODEC;
//    public static StructureProcessorType<ItemFrameProcessor> ITEMFRAME_PROCESSOR = () -> ItemFrameProcessor.CODEC;

    public static void init() {
        registerProcessors();
    }

    private static void registerProcessors() {
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(BetterStrongholds.MOD_ID, "air_processor"), AIR_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(BetterStrongholds.MOD_ID, "cobweb_processor"), COBWEB_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(BetterStrongholds.MOD_ID, "torch_processor"), TORCH_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(BetterStrongholds.MOD_ID, "lantern_processor"), LANTERN_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(BetterStrongholds.MOD_ID, "banner_processor"), BANNER_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(BetterStrongholds.MOD_ID, "waterlogged_processor"), WATERLOGGED_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(BetterStrongholds.MOD_ID, "ore_processor"), ORE_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(BetterStrongholds.MOD_ID, "rare_block_processor"), RARE_BLOCK_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(BetterStrongholds.MOD_ID, "redstone_processor"), REDSTONE_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(BetterStrongholds.MOD_ID, "leg_processor"), LEG_PROCESSOR);
//        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(BetterStrongholds.MOD_ID, "armorstand_processor"), ARMORSTAND_PROCESSOR);
//        Registry.register(Registry.STRUCTURE_PROCESSOR, new Identifier(BetterStrongholds.MOD_ID, "itemframe_processor"), ITEMFRAME_PROCESSOR);
    }
}
