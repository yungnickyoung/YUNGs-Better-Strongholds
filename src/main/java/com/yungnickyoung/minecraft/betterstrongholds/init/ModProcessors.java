package com.yungnickyoung.minecraft.betterstrongholds.init;

import com.yungnickyoung.minecraft.betterstrongholds.BetterStrongholds;
import com.yungnickyoung.minecraft.betterstrongholds.world.processor.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ModProcessors {
    public static IStructureProcessorType<AirProcessor> AIR_PROCESSOR = () -> AirProcessor.CODEC;
    public static IStructureProcessorType<CobwebProcessor> COBWEB_PROCESSOR = () -> CobwebProcessor.CODEC;
    public static IStructureProcessorType<TorchProcessor> TORCH_PROCESSOR = () -> TorchProcessor.CODEC;
    public static IStructureProcessorType<LanternProcessor> LANTERN_PROCESSOR = () -> LanternProcessor.CODEC;
    public static IStructureProcessorType<BannerProcessor> BANNER_PROCESSOR = () -> BannerProcessor.CODEC;
    public static IStructureProcessorType<WaterloggedProcessor> WATERLOGGED_PROCESSOR = () -> WaterloggedProcessor.CODEC;
    public static IStructureProcessorType<OreProcessor> ORE_PROCESSOR = () -> OreProcessor.CODEC;
    public static IStructureProcessorType<RareBlockProcessor> RARE_BLOCK_PROCESSOR = () -> RareBlockProcessor.CODEC;

    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModProcessors::commonSetup);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterStrongholds.MOD_ID, "air_processor"), AIR_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterStrongholds.MOD_ID, "cobweb_processor"), COBWEB_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterStrongholds.MOD_ID, "torch_processor"), TORCH_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterStrongholds.MOD_ID, "lantern_processor"), LANTERN_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterStrongholds.MOD_ID, "banner_processor"), BANNER_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterStrongholds.MOD_ID, "waterlogged_processor"), WATERLOGGED_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterStrongholds.MOD_ID, "ore_processor"), ORE_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterStrongholds.MOD_ID, "rare_block_processor"), RARE_BLOCK_PROCESSOR);
        });
    }
}
