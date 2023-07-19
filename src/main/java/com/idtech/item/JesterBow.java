package com.idtech.item;

import com.idtech.BaseMod;
import com.idtech.ModTab;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Random;

public class JesterBow extends BowItem {

    public static Item.Properties properties = new Item.Properties().tab(ModTab.INSTANCE);
    public static Item INSTANCE = new JesterBow(properties).setRegistryName("jesterbow");

    public JesterBow(Item.Properties properties) {
        super(properties);
    }

    public AbstractArrow randArrowEffect(AbstractArrow abstractarrow){

        // RNG logic
        Random rng = new Random();
        int roll = rng.nextInt(13); //Unlucky 13

        if (abstractarrow instanceof Arrow) {

            switch (roll) {
                default: break;
                case 1: ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600));
                    break;
                case 2: ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.WITHER, 600));
                    break;
                case 3: ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600));
                    break;
                case 4: ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.POISON, 600));
                    break;
                case 5: ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600));
                    break;
                case 6: ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 600));
                    break;
                case 7: ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.HUNGER, 600));
                    break;
                case 8: ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600));
                    break;
                case 9: ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.GLOWING, 600));
                    break;
                case 10: ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.LEVITATION, 600));
                    break;
                case 11: ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600));
                    break;
                case 12: ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.HARM, 600));
                    break;
            }

        }
        return abstractarrow;
    }

    @Override
    public void releaseUsing(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {
        if (p_40669_ instanceof Player) {
            Player player = (Player)p_40669_;
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, p_40667_) > 0;
            ItemStack itemstack = player.getProjectile(p_40667_);

            int i = this.getUseDuration(p_40667_) - p_40670_;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(p_40667_, p_40668_, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, p_40667_, player));
                    if (!p_40668_.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrow = arrowitem.createArrow(p_40668_, itemstack, player);
                        abstractarrow = customArrow(abstractarrow);
                        abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractarrow.setCritArrow(true);
                        }

                        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, p_40667_);
                        if (j > 0) {
                            abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, p_40667_);
                        if (k > 0) {
                            abstractarrow.setKnockback(k);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, p_40667_) > 0) {
                            abstractarrow.setSecondsOnFire(100);
                        }

                        p_40667_.hurtAndBreak(1, player, (p_40665_) -> {
                            p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                        });
                        if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                            abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        // Apply random effect
                        abstractarrow = randArrowEffect(abstractarrow);

                        p_40668_.addFreshEntity(abstractarrow);
                    }

                    p_40668_.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (p_40668_.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }
}
