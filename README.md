# Sourish25 Heldables Mod

![Minecraft Forge](https://img.shields.io/badge/Minecraft%20Forge-1.19.2-blue)
![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-red)

A simple and lightweight Minecraft Forge mod that allows you to automate key presses using a macro key. Perfect for holding down keys like `Z` for skills in mods like **Tensura Reincarnated**!

---

## Features

- **Macro Key**: Press the `.` (period) key to toggle holding a specified key (e.g., `Z`).
- **Customizable**: Easily change the macro key and the key to be held in the code.
- **Lightweight**: Minimal performance impact, designed for smooth gameplay.
- **Forge Compatibility**: Built for Minecraft Forge 1.19.2.

---

## Installation

1. **Download the Mod**:
   - Download the latest `.jar` file from the [Releases](https://github.com/yourusername/sourish25-heldables/releases) page.

2. **Install Minecraft Forge**:
   - Download and install [Minecraft Forge](https://files.minecraftforge.net/) for Minecraft 1.19.2.

3. **Add the Mod**:
   - Place the downloaded `.jar` file into your `mods` folder:
     ```
     .minecraft/mods/
     ```

4. **Launch Minecraft**:
   - Launch Minecraft with the Forge profile and enjoy the mod!

---

## Usage

1. **Default Keybindings**:
   - **Macro Key**: `.` (period) to toggle holding the specified key.
   - **Held Key**: `Z` (customizable in the code).

2. **How It Works**:
   - Press the `.` key to start holding the `Z` key.
   - Press the `.` key again to stop holding the `Z` key.

3. **Customization**:
   - To change the macro key or the held key, modify the following lines in the code:
     ```java
     private static final KeyMapping MACRO_KEY = new KeyMapping("key.sourish25.macro", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD, "key.categories.sourish25");
     InputConstants.Key key = InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_Z);
     ```

---

## Code Overview

Here’s a quick look at the main functionality of the mod:

```java
@Mod("sourish25heldables")
public class Sourish25Heldables {

    private static final KeyMapping MACRO_KEY = new KeyMapping("key.sourish25.macro", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD, "key.categories.sourish25");

    private boolean isHoldingSkillKey = false;

    public Sourish25Heldables() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerKeyMappings);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(MACRO_KEY);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (MACRO_KEY.consumeClick()) {
            isHoldingSkillKey = !isHoldingSkillKey;
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player != null) {
                minecraft.player.displayClientMessage(Component.literal("Macro key pressed! Holding skill key: " + isHoldingSkillKey), true);
            }
        }

        if (isHoldingSkillKey) {
            simulateSkillKeyPress();
        } else {
            releaseSkillKey();
        }
    }

    private void simulateSkillKeyPress() {
        Minecraft minecraft = Minecraft.getInstance();
        InputConstants.Key key = InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_Z);
        KeyMapping.set(key, true);
    }

    private void releaseSkillKey() {
        Minecraft minecraft = Minecraft.getInstance();
        InputConstants.Key key = InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_Z);
        KeyMapping.set(key, false);
    }
}
