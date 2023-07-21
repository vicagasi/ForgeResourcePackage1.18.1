package com.idtech.item.staffs;

import com.idtech.ModTab;
import com.idtech.item.FireballWandItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.logging.Level;

public abstract class AbstractStaff extends Item {

    // STAFF VARIABLES
    public double STAFF_CHARGE = 1000;
    public double STAFF_COOLDOWN = 1;
    public double CHARGE_USED = 10;

    private static Properties properties = new Properties().tab(ModTab.INSTANCE);

    public AbstractStaff(Properties properties) {
        super(properties);
    }

    // Function that controls staff's effect
    protected abstract void staffAction(Level level, Player player, Entity entity);


}
