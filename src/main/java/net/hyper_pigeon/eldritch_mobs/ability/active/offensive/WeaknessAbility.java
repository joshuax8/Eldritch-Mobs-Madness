package net.hyper_pigeon.eldritch_mobs.ability.active.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;

public class WeaknessAbility implements Ability {

    private final EldritchMobsConfig.WeaknessConfig weaknessConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.weaknessConfig;
    private final long cooldown = weaknessConfig.cooldown;
    private long nextUseTime = 0L;

    @Override
    public String getName() {
        return weaknessConfig.name;
    }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.ACTIVE;
    }

    @Override
    public AbilitySubType getAbilitySubType() {
        return AbilitySubType.OFFENSIVE;
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public boolean canUseAbility(MobEntity mobEntity) {
        return mobEntity.world.getTime() > nextUseTime && mobEntity.getTarget() != null;
    }

    @Override
    public void onAbilityUse(MobEntity mobEntity) {
        if (mobEntity.getTarget() != null && mobEntity.getTarget().isAlive() && mobEntity.canSee(mobEntity.getTarget())) {
            long time = mobEntity.getEntityWorld().getTime();
            if (time > nextUseTime) {
                nextUseTime = time + cooldown;
                LivingEntity target = mobEntity.getTarget();
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, weaknessConfig.duration, weaknessConfig.amplifier));
            }
        }
    }
}
