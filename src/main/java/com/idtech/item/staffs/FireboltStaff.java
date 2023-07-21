package com.idtech.item.staffs;

import com.idtech.ModTab;
import com.idtech.entity.projectiles.ExplosionProjectile;
import com.idtech.item.FireballWandItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

public class FireboltStaff extends AbstractStaff {

    private static Properties properties = new Properties().tab(ModTab.INSTANCE);
    public static Item INSTANCE = new FireboltStaff(properties).setRegistryName("fbstaff");

    public FireboltStaff(Properties properties) {
        super(properties);
    }

    @Override
    protected void staffAction(Level level, Player player, ItemStack itemStack) {

        if (!level.isClientSide) {
            SmallFireball projectile = new SmallFireball(level, player,
                    0, 0, 0);
            projectile.setItem(itemStack);
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(projectile);
        }
    }

}
