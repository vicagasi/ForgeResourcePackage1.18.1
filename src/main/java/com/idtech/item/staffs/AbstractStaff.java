package com.idtech.item.staffs;

import com.idtech.ModTab;
import com.idtech.item.FireballWandItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

import java.util.logging.Level;

public abstract class AbstractStaff extends Item {

    // STAFF VARIABLES
    protected String chargeTagName = "charges";
    // protected int STAFF_CHARGE = 1000;
    protected int MAX_CHARGE = 1000;
    protected int STAFF_COOLDOWN = 1;
    protected int CHARGE_USED = 10;


    private static Properties properties = new Properties().tab(ModTab.INSTANCE);

    public AbstractStaff(Properties properties) {
        super(properties);
    }

    public AbstractStaff(Properties properties, int uses) {
        super(properties);
        this.MAX_CHARGE = uses;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getCharges(stack) > 0;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xFFCC47;
    }

    @Override
    public int getBarWidth(ItemStack itemStack) {
        return Math.round((float)(this.MAX_CHARGE - getCharges(itemStack)) * 13.0F / (float)this.MAX_CHARGE);
    }

    public void setCharges(ItemStack itemStack, int num){
        itemStack.getOrCreateTagElement(chargeTagName).putInt(chargeTagName, num);
    }

    public int getCharges(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTagElement(this.chargeTagName);
        return compoundtag != null && compoundtag.contains(this.chargeTagName, 99) ? compoundtag.getInt(this.chargeTagName) : 0;
    }


    // Drains charge from staff
    /* protected int drainCharge(int charge){
        if(charge >= CHARGE_USED){
            charge = charge - CHARGE_USED;
        } else {
            charge = 0;
        }
        return charge;
    } */

    // Function that controls staff's effect
    protected abstract void staffAction(net.minecraft.world.level.Level level, Player player, ItemStack itemStack);

    @Override
    public InteractionResultHolder<ItemStack> use(net.minecraft.world.level.Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!player.getCooldowns().isOnCooldown(itemStack.getItem())) {

            if (!(getCharges(itemStack) >= this.MAX_CHARGE)) {
                player.awardStat(Stats.ITEM_USED.get(this));

                if (!player.getAbilities().instabuild) {
                    setCharges(itemStack, getCharges(itemStack) + CHARGE_USED);
                    player.getCooldowns().addCooldown(itemStack.getItem(), STAFF_COOLDOWN);
                }

                staffAction(level, player, itemStack);

                return InteractionResultHolder.success(itemStack);

            }

            return InteractionResultHolder.fail(itemStack);

        }

        return InteractionResultHolder.fail(itemStack);

    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CROSSBOW;
    }


}
