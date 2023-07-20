package com.idtech.block;

import com.idtech.BaseMod;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;

public class LandmineBlock extends Block {
    private static Properties properties = Properties.of(Material.STONE);

    //static instances for registration
    public static Block INSTANCE = new LandmineBlock(properties).setRegistryName(BaseMod.MODID,"landmine");
    public static Item ITEM = BlockUtils.createBlockItem(INSTANCE, CreativeModeTab.TAB_MISC);

    public LandmineBlock(Properties properties) {
        super(properties);
    }



    @Override
    public void stepOn(Level levelIn, BlockPos posIn, BlockState blockStateIn, Entity entityIn) {
        super.stepOn(levelIn, posIn, blockStateIn, entityIn);

        entityIn.hurt(DamageSource.GENERIC, 65F);
        // KABOOM
        levelIn.explode(entityIn, posIn.getX(), posIn.getY() + 0.25, posIn.getZ(), 5.0F, Explosion.BlockInteraction.BREAK);
        entityIn.setDeltaMovement(0,1,0);
    }
}
