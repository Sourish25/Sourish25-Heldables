package com.sourish25.heldables;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.network.chat.Component;

import org.lwjgl.glfw.GLFW;

@Mod("sourish25heldables")
public class Sourish25Heldables {

    // Set the macro key to the period key (.)
    private static final KeyMapping MACRO_KEY = new KeyMapping("key.sourish25.macro", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD, "key.categories.sourish25");

    private boolean isHoldingSkillKey = false;

    public Sourish25Heldables() {
        // Register key mappings
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerKeyMappings);

        // Register this class to the event bus
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(MACRO_KEY);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        // Check if the macro key (.) is pressed
        if (MACRO_KEY.consumeClick()) {
            // Toggle the state of holding the skill key
            isHoldingSkillKey = !isHoldingSkillKey;
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player != null) {
                minecraft.player.displayClientMessage(Component.literal("Macro key pressed! Holding skill key: " + isHoldingSkillKey), true);
            }
        }

        // Simulate holding the skill key (e.g., Z) if the toggle is active
        if (isHoldingSkillKey) {
            simulateSkillKeyPress();
        } else {
            releaseSkillKey();
        }
    }

    private void simulateSkillKeyPress() {
        Minecraft minecraft = Minecraft.getInstance();
        // Simulate holding the Z key (replace GLFW.GLFW_KEY_Z with the key you want to simulate)
        InputConstants.Key key = InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_Z);
        KeyMapping.set(key, true); // Hold the key
    }

    private void releaseSkillKey() {
        Minecraft minecraft = Minecraft.getInstance();
        // Release the Z key (replace GLFW.GLFW_KEY_Z with the key you want to simulate)
        InputConstants.Key key = InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_Z);
        KeyMapping.set(key, false); // Release the key
    }
}