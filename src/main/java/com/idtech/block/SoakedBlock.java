package com.idtech.block;

import com.idtech.BaseMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class SoakedBlock extends IceBlock {
    private static Properties properties = Properties.of(Material.ICE);

    //static instances for registration
    public static Block INSTANCE = new SoakedBlock(properties).setRegistryName(BaseMod.MODID,"soakedblock");
    public static Item ITEM = BlockUtils.createBlockItem(INSTANCE, CreativeModeTab.TAB_MISC);

    public SoakedBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level levelIn, BlockPos posIn, BlockState blockStateIn, Entity entityIn) {
        super.stepOn(levelIn, posIn, blockStateIn, entityIn);

        // If entity is on fire, put them out
        if(entityIn.isOnFire()){
            entityIn.clearFire();
        }

    }
}
