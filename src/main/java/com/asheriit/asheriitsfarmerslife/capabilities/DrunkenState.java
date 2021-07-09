package com.asheriit.asheriitsfarmerslife.capabilities;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.ModMathHelper;
import com.asheriit.asheriitsfarmerslife.init.ModEffects;
import com.asheriit.asheriitsfarmerslife.network.to_client.AlcoholLevelMessageToClient;
import com.asheriit.asheriitsfarmerslife.network.to_client.ManipulateKeybindingsMessageToClient;
import com.asheriit.asheriitsfarmerslife.network.to_client.ResetKeybindingsMessageToClient;
import com.asheriit.asheriitsfarmerslife.network.to_server.OriginalKeyBindingMessageToServer;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.function.Supplier;

// TODO: Remove all unnecessary comments
// TODO: Revalidate all functions
public class DrunkenState {
    private String[] originalKeys;

    private static final byte MAX_ALCOHOL_LEVEL = 15;
    private static final int DRUNK_LEVEL_TICKS = 200;

    private int alcoholLevel;
    private boolean isDrunk;
    private int alcoholLevelTimeLeft;

    // Index 0 -> forward, 1 -> left, 2 -> right, 3 -> back
    private String[] keys = new String[]{
            "key.forward",
            "key.left",
            "key.right",
            "key.back"
    };

    public DrunkenState() {
        this.alcoholLevel = 0;
        this.alcoholLevelTimeLeft = DRUNK_LEVEL_TICKS;
        this.isDrunk = false;
        this.originalKeys = null;
        ModMathHelper.shuffleArray(this.keys);
    }

    /**
     * Remove the drunk effect for the given player
     * Notify the Client of the changes and reset the keybindings to defaults
     *
     * @param playerEntity: The player which calls the method
     */
    public void removeDrunkEffect(ServerPlayerEntity playerEntity) {
        this.isDrunk = false;
        this.alcoholLevel = 0;
        FarmersLife.LOGGER.info("Remove drunk effect!");
        // Send drunkLevel and isDrunk to client
        AlcoholLevelMessageToClient message = new AlcoholLevelMessageToClient(playerEntity.getUniqueID().toString(), this.alcoholLevel, this.isDrunk);
        FarmersLife.simpleChannel.send(PacketDistributor.DIMENSION.with(() -> playerEntity.dimension), message);
        // Send reset keybindings event to client
        ResetKeybindingsMessageToClient keybindingsMessageToClient = new ResetKeybindingsMessageToClient(playerEntity.getUniqueID(), this.originalKeys);
        FarmersLife.simpleChannel.send(PacketDistributor.DIMENSION.with(() -> playerEntity.dimension), keybindingsMessageToClient);
    }

    /**
     * Decrease the alcohol level by one,
     * if the time to decrease the alcohol level is zero.
     * Notifies the Client of the changes
     *
     * @param playerEntity: The player which calls the method
     */
    public void reduceAlcoholLevel(ServerPlayerEntity playerEntity) {
        if (!playerEntity.world.isRemote) {
            if (!this.isDrunk) {
                if (this.alcoholLevel <= 0) return;
                this.alcoholLevelTimeLeft--;

                if (this.alcoholLevelTimeLeft <= 0) {
                    this.alcoholLevelTimeLeft = DRUNK_LEVEL_TICKS;
                    this.alcoholLevel--;
                    FarmersLife.LOGGER.info("reduceAlcoholLevel: " + this.alcoholLevel);

                    // Send alcoholLevel and isDrunk to client
                    AlcoholLevelMessageToClient message = new AlcoholLevelMessageToClient(playerEntity.getUniqueID().toString(), this.alcoholLevel, this.isDrunk);
                    FarmersLife.simpleChannel.send(PacketDistributor.DIMENSION.with(() -> playerEntity.dimension), message);
                }
            }
        }
    }

    /**
     * Reset the time it takes to decrease the alcohol level
     * NOTE: Only call on Server
     */
    public void resetDrunkLeftTime() {
        this.alcoholLevelTimeLeft = DRUNK_LEVEL_TICKS;
    }

    /**
     * Client side method to update the movement keybindings.
     * Randomizes the array entries and sets the movement keys accordingly.
     *
     * @param player: Server player
     */
    public void updateManipulatedKeybindingsFromServer(ServerPlayerEntity player) {
        if (player == null) {
            return;
        }

        FarmersLife.LOGGER.info("Manipulate Keybindings!");
        ModMathHelper.shuffleArray(this.keys);
        // Client should manipulate keybindings for the given player
        ManipulateKeybindingsMessageToClient message = new ManipulateKeybindingsMessageToClient(player.getUniqueID(), this.originalKeys, this.keys);
        FarmersLife.simpleChannel.send(PacketDistributor.DIMENSION.with(() -> player.dimension), message);
    }

    /**
     * Increase the alcohol level or if the max level is reached set the given player drunk
     *
     * @param amount: amount of alcohol level to add
     * @param playerEntity: Player entity
     * @param shouldBeDrunk: Should the player be set to drunk
     * @param drunkEffectIn: Effect instance and probability
     */
    public void increaseDrunkLevel(int amount, PlayerEntity playerEntity, boolean shouldBeDrunk, Pair<Supplier<EffectInstance>, Float> drunkEffectIn) {
        if (playerEntity == null) return;

        if (!playerEntity.world.isRemote) {
            this.alcoholLevel += amount;
            FarmersLife.LOGGER.info("Increase alcohol level: " + this.alcoholLevel);
            if (shouldBeDrunk) {
                // Shuffle/Randomize the keys array (keys do not have to be synchronized!)
                ModMathHelper.shuffleArray(this.keys);
                // Client should manipulate keybindings for the given player
                ManipulateKeybindingsMessageToClient message = new ManipulateKeybindingsMessageToClient(playerEntity.getUniqueID(), this.originalKeys, this.keys);
                FarmersLife.simpleChannel.send(PacketDistributor.DIMENSION.with(() -> playerEntity.dimension), message);

                this.isDrunk = true;
                FarmersLife.LOGGER.info("Player is Drunk!");

                // set values when the player is drunk
                if (drunkEffectIn != null && drunkEffectIn.getKey() != null && drunkEffectIn.getKey().get() != null &&
                        playerEntity.world.rand.nextFloat() < drunkEffectIn.getValue()) {
                    this.resetDrunkLeftTime();
                    playerEntity.addPotionEffect(new EffectInstance(Effects.NAUSEA, drunkEffectIn.getKey().get().getDuration()));
                    // Update drunk effect to strongest effect
                    this.updateDrunkEffect(drunkEffectIn.getKey().get(), playerEntity);
                }
            }

            // Send drunkLevel and isDrunk to client
            AlcoholLevelMessageToClient message = new AlcoholLevelMessageToClient(playerEntity.getUniqueID().toString(), this.alcoholLevel, this.isDrunk);
            FarmersLife.simpleChannel.send(PacketDistributor.DIMENSION.with(() -> playerEntity.dimension), message);
        } else {
            // Save original movement keybindings and send to server
            if (!this.isDrunk) {
                FarmersLife.LOGGER.info("Send original Keybindings to Server!");
                this.originalKeys = new String[]{
                        KeyBinding.KEYBIND_ARRAY.get("key.forward").getKey().getTranslationKey(),
                        KeyBinding.KEYBIND_ARRAY.get("key.left").getKey().getTranslationKey(),
                        KeyBinding.KEYBIND_ARRAY.get("key.right").getKey().getTranslationKey(),
                        KeyBinding.KEYBIND_ARRAY.get("key.back").getKey().getTranslationKey()
                };
                OriginalKeyBindingMessageToServer message = new OriginalKeyBindingMessageToServer(this.originalKeys);
                FarmersLife.simpleChannel.sendToServer(message);
            }
        }
    }

    /**
     * Manipulate player keybindings for movement control
     */
    @OnlyIn(Dist.CLIENT)
    public void manipulateKeybindings() {
        GameSettings settings = Minecraft.getInstance().gameSettings;

        if (settings != null) {
            // originalKeys should always contain values, if they don't something went wrong
            for (String key : this.originalKeys) {
                if (key == null || key.length() < 4) {
                    throw new IllegalStateException("[DrunkenState::manipulateKeybindings] Could not find valid saved original keys! Got: " + Arrays.toString(this.originalKeys));
                }
            }

            FarmersLife.LOGGER.info("Modify game settings! Forward: " + this.getIndex("key.forward") +
                    ", Left: " + this.getIndex("key.left") +
                    ", Right: " + this.getIndex("key.right") +
                    ", Back: " + this.getIndex("key.back"));
            FarmersLife.LOGGER.info("Keys: " + Arrays.toString(this.keys));
            FarmersLife.LOGGER.info("OriginalKeys: " + Arrays.toString(this.originalKeys));
            // Manipulate movement settings
            settings.setKeyBindingCode(settings.keyBindForward, InputMappings.getInputByName(this.originalKeys[this.getIndex("key.forward")]));
            settings.setKeyBindingCode(settings.keyBindLeft, InputMappings.getInputByName(this.originalKeys[this.getIndex("key.left")]));
            settings.setKeyBindingCode(settings.keyBindRight, InputMappings.getInputByName(this.originalKeys[this.getIndex("key.right")]));
            settings.setKeyBindingCode(settings.keyBindBack, InputMappings.getInputByName(this.originalKeys[this.getIndex("key.back")]));
            KeyBinding.resetKeyBindingArrayAndHash();
        }
    }

    /**
     * Resets the manipulated keybindings to its defaults
     *
     * @param originalKeys: Original keybinding keys
     */
    @OnlyIn(Dist.CLIENT)
    public void resetMovementKeybindings(String[] originalKeys) {
        GameSettings settings = Minecraft.getInstance().gameSettings;
        if (settings != null) {
            for (String key : originalKeys) {
                if (key == null || key.length() < 4) return;
            }
            FarmersLife.LOGGER.info("Reset to OriginalKeys: " + Arrays.toString(originalKeys));
            settings.setKeyBindingCode(settings.keyBindForward, InputMappings.getInputByName(originalKeys[0]));
            settings.setKeyBindingCode(settings.keyBindLeft, InputMappings.getInputByName(originalKeys[1]));
            settings.setKeyBindingCode(settings.keyBindRight, InputMappings.getInputByName(originalKeys[2]));
            settings.setKeyBindingCode(settings.keyBindBack, InputMappings.getInputByName(originalKeys[3]));
            KeyBinding.resetKeyBindingArrayAndHash();
        }
    }

    /**
     * @param description: Key description
     * @return index for the "manipulated" key
     */
    private int getIndex(String description) {
        byte index = 0;
        for (byte i = 0; i < this.keys.length; i++) {
            if (this.keys[i].equals(description)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Sets the drunk effect considering if the given player is already drunk.
     * If the player is drunk the strongest of both effects gets chosen.
     * The duration of the effect is the sum of the current active and the new effect.
     *
     * @param effectInstance: Effect instance
     * @param player: Player entity to add effect to
     */
    private void updateDrunkEffect(EffectInstance effectInstance, PlayerEntity player) {
        if (effectInstance == null || player.world.isRemote) {
            return;
        }

        Effect strongestActiveDrunkEffect = null;
        int strength = 0;
        if (player.isPotionActive(ModEffects.DRUNK_STAGE_1.get())) {
            EffectInstance effInstance = player.getActivePotionEffect(ModEffects.DRUNK_STAGE_1.get());
            strongestActiveDrunkEffect = effInstance.getPotion();
            strength = 1;
        }
        if (player.isPotionActive(ModEffects.DRUNK_STAGE_2.get())) {
            EffectInstance effInstance = player.getActivePotionEffect(ModEffects.DRUNK_STAGE_2.get());
            strongestActiveDrunkEffect = effInstance.getPotion();
            strength = 2;
        }
        if (player.isPotionActive(ModEffects.DRUNK_STAGE_3.get())) {
            EffectInstance effInstance = player.getActivePotionEffect(ModEffects.DRUNK_STAGE_3.get());
            strongestActiveDrunkEffect = effInstance.getPotion();
            strength = 3;
        }

        if (strongestActiveDrunkEffect == null) {
            player.addPotionEffect(effectInstance);
            FarmersLife.LOGGER.info("no potion active");
            return;
        }

        Effect effectToAdd = effectInstance.getPotion();
        if (strongestActiveDrunkEffect.equals(effectToAdd)) {
            player.addPotionEffect(effectInstance);
            FarmersLife.LOGGER.info("Active potion is potion to add!");
            return;
        }

        int strengthOfEffectToAdd = 0;
        if (effectToAdd.equals(ModEffects.DRUNK_STAGE_1.get())) {
            strengthOfEffectToAdd = 1;
        } else if (effectToAdd.equals(ModEffects.DRUNK_STAGE_2.get())) {
            strengthOfEffectToAdd = 2;
        } else if (effectToAdd.equals(ModEffects.DRUNK_STAGE_3.get())) {
            strengthOfEffectToAdd = 3;
        }

        if (strength > strengthOfEffectToAdd) {
            player.addPotionEffect(new EffectInstance(strongestActiveDrunkEffect, effectInstance.getDuration()));
            FarmersLife.LOGGER.info("Active potion is stronger than new potion!");
        } else {
            player.addPotionEffect(effectInstance);
            FarmersLife.LOGGER.info("Strength: " + strength + ", new effect: " + effectInstance.toString());
            if (strength == 1) {
                player.removePotionEffect(ModEffects.DRUNK_STAGE_1.get());
            }
            if (strength == 2) {
                player.removePotionEffect(ModEffects.DRUNK_STAGE_2.get());
            }
            if (strength == 3) {
                player.removePotionEffect(ModEffects.DRUNK_STAGE_3.get());
            }
            FarmersLife.LOGGER.info("New potion has been set");
        }
    }

    public static DrunkenState createInstance() {
        return new DrunkenState();
    }

    // GETTER AND SETTER
    public int getAlcoholLevel() {
        return alcoholLevel;
    }

    public boolean isDrunk() {
        return isDrunk;
    }

    public String[] getOriginalKeys() {
        return originalKeys;
    }

    public int getAlcoholLevelTimeLeft() {
        return alcoholLevelTimeLeft;
    }

    public void setAlcoholLevelTimeLeft(int alcoholLevelTimeLeft) {
        this.alcoholLevelTimeLeft = alcoholLevelTimeLeft;
    }

    public void setOriginalKeys(String[] originalKeys) {
        this.originalKeys = originalKeys;
    }

    public void setAlcoholLevel(int alcoholLevel) {
        this.alcoholLevel = alcoholLevel;
    }

    public void setDrunk(boolean drunk) {
        isDrunk = drunk;
    }

    public byte getMaxAlcoholLevel() {
        return MAX_ALCOHOL_LEVEL;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    /**
     *
     */
    public static class DrunkenStateNBTStorage implements Capability.IStorage<DrunkenState> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<DrunkenState> capability, DrunkenState instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putBoolean("isDrunk", instance.isDrunk());
            nbt.putInt("alcoholLevel", instance.getAlcoholLevel());
            if (instance.originalKeys != null) {
                nbt.putString("forward", instance.getOriginalKeys()[0]);
                nbt.putString("left", instance.getOriginalKeys()[1]);
                nbt.putString("right", instance.getOriginalKeys()[2]);
                nbt.putString("back", instance.getOriginalKeys()[3]);
            }
            nbt.putInt("alcoholLevelTime", instance.getAlcoholLevelTimeLeft());
            return nbt;
        }

        @Override
        public void readNBT(Capability<DrunkenState> capability, DrunkenState instance, Direction side, INBT nbt) {
            CompoundNBT tags = (CompoundNBT) nbt;
            instance.setDrunk(tags.getBoolean("isDrunk"));
            instance.setAlcoholLevel(tags.getInt("alcoholLevel"));
            if (tags.contains("forward") && tags.contains("left") && tags.contains("right") && tags.contains("back")) {
                String keyForward = tags.getString("forward");
                String keyLeft = tags.getString("left");
                String keyRight = tags.getString("right");
                String keyBack = tags.getString("back");
                instance.setOriginalKeys(new String[]{keyForward, keyLeft, keyRight, keyBack});
            }
            instance.setAlcoholLevelTimeLeft(tags.getInt("alcoholLevelTime"));
        }
    }
}
