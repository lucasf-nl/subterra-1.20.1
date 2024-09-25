package net.apixelite.subterra.item.custom;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.apixelite.subterra.Subterra;
import net.apixelite.subterra.util.CustomRarity;
import net.apixelite.subterra.util.RecombobulatorUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.item.ItemStack.TooltipSection;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DrillItem extends PickaxeItem {

    private float attackDamage;
    private float baseMiningSpeed;
    private CustomRarity rarity;

    private boolean abilityActive;

    private static String engine = "";
    private static String tank = "";
    private static String upgrade = "";

    private static boolean hasUpgrade = false;

    private static int drillHasEngine = 0;
    private static int drillHasTank = 0;
    // private static int drillHasUpgrade = 0;

    private int stopperInt = 0;
    private int i = 0; 

    
    private static boolean hasNbtEngine = false;
    private static boolean hasNbtTank = false;
    // private boolean hasNbtUpgrade = false;
    private static int fuel = 3000;
    private static int maxFuel = 3000;
    private static int miningSpeed = 0;

    Timer timer = new Timer();

    public DrillItem(ToolMaterial material, float miningSpeed, int attackDamage, float attackSpeed, CustomRarity rarity, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
        this.attackDamage = attackDamage;
        this.baseMiningSpeed = miningSpeed;
        this.rarity = rarity;
    }


// NBT FUNCTIONS
    // edits the nbt on a item
    public static void editNbtData(ItemStack item, boolean value, int value2, String module, String name) {
        addOrRemoveNbtFromDrill(item, value, value2, module, name);
    }
    
    // adds nbt to the drill
    private static void addOrRemoveNbtFromDrill(ItemStack item, boolean value, int value2, String module, String name) {
        NbtCompound nbtData = new NbtCompound();
        ItemStack drill = item;
        switch (module) {
            case "engine":
                hasNbtEngine = value;
                drillHasEngine = value2;
                miningSpeed = getMininSpeedAddition(name);
                engine = name;
                break;
            case "tank":
                hasNbtTank = value;
                drillHasTank = value2;
                maxFuel = getTankFuel(name);
                fuel = maxFuel;
                tank = name;
                break;
            case "fuel":
                maxFuel = value2;
                fuel = value2;
                break;
            case "decrease_fuel":
                fuel = value2;
                break;
            case "speed":
                miningSpeed = value2;
                break;
            default:
                break;
        }

        nbtData.putBoolean("subterra.has_engine", hasNbtEngine);
        nbtData.putBoolean("subterra.has_tank", hasNbtTank);
        nbtData.putString("subterra.engine_name", engine);
        nbtData.putString("subterra.tank_name", tank);
        nbtData.putInt("subterra.engine", drillHasEngine);
        nbtData.putInt("subterra.tank", drillHasTank);
        nbtData.putInt("subterra.fuel", fuel);
        nbtData.putInt("subterra.max_fuel", maxFuel);
        nbtData.putInt("subterra.mining_speed", miningSpeed);

        drill.setNbt(nbtData);
    }


// ABLITY FUNCTIONS
    // the Pickaxe Ability
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (stopperInt == 0) {
            stopperInt++;
            abilityActive = true;
            player.getItemCooldownManager().set(this, 2400);
            player.sendMessage(Text.translatable("§7§lAbility Activated!"));
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    abilityActive = false;
                    player.sendMessage(Text.translatable("§cAbility Deactivated!"));
                    stopperInt--;
                }
            }, 20000);
        }
        return super.use(world, player, hand);
    }

 

// SET TOOLTIP FUNTION
    // changes the tooltip of the item
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        stack.addHideFlag(TooltipSection.ENCHANTMENTS);
        stack.addHideFlag(TooltipSection.MODIFIERS);
        tooltip.clear();
        setFuel(stack);

        // Stats of the item
        tooltip.add(Text.empty().append(this.getName()).formatted(this.rarity.formatting));
        tooltip.add(Text.literal("§8Mining level 8"));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.literal("§7Damage: §c" + ((int) this.attackDamage)));
        tooltip.add(Text.literal("§7Mining speed: §a" + ((int) getMiningSpeedMultiplier(stack, null))));
        // TO DO: tooltip.add(Text.literal("§7Mining fortune: §a"));

        // The enchantments
        if(stack.hasEnchantments()) {
            tooltip.add(Text.literal(""));
         
            tooltip.add(Text.literal("§6Enchantments:"));
            ItemStack.appendEnchantments(tooltip, stack.getEnchantments());
        }

        tooltip.add(Text.literal(""));

        // Drill parts
        // Fuel tank
        if (stack.getNbt().getBoolean("subterra.has_tank")) {
            tooltip.add(Text.literal("§a" + stack.getNbt().getString("subterra.tank_name")));
            tooltip.add(Text.literal("§7Increase your fuel capacity"));
            tooltip.add(Text.literal("§7to: §2" + stack.getNbt().getInt("subterra.max_fuel")));
        } else {
            tooltip.add(Text.literal("§7Fuel Tank: §cNot Installed"));
            tooltip.add(Text.literal("§7Increase your fuel capacity"));
            tooltip.add(Text.literal("§7with tank installed"));
        }
        tooltip.add(Text.literal(""));

        // Drill Engine
        if (stack.getNbt().getBoolean("subterra.has_engine")) {
            tooltip.add(Text.literal("§a" + stack.getNbt().getString("subterra.engine_name")));
            tooltip.add(Text.literal("§7Grants an extra "));
            tooltip.add(Text.literal("§6" + stack.getNbt().getInt("subterra.mining_speed") + " Mining Speed"));
        } else {
            tooltip.add(Text.literal("§7Drill Engine: §cNot Installed"));
            tooltip.add(Text.literal("§7IncreaSe your mining speed"));
            tooltip.add(Text.literal("§7with engine installed."));
        }
        tooltip.add(Text.literal(""));
        
        // Upgrade Module
        if (hasUpgrade) {
            tooltip.add(Text.literal("§a" + upgrade));
        } else {
            tooltip.add(Text.literal("§7Upgrade Module: §cNot Installed"));
            tooltip.add(Text.literal("§7Increase a certain stat with"));
            tooltip.add(Text.literal("§7module installed"));
        }
        tooltip.add(Text.literal(""));


        // Tool ability
        tooltip.add(Text.literal("§6Item Ability: Mining Speed Boost"));
        tooltip.add(Text.literal("§7Usage: §e§lRIGHT CLICK"));
        tooltip.add(Text.literal("§7Grants §a300% §6Mining Speed"));
        tooltip.add(Text.literal("§8Duration: §a20s"));
        tooltip.add(Text.literal("§8Cooldown: §a120s"));

        tooltip.add(Text.literal(""));

        // Fuel
        if(stack.getNbt().getInt("subterra.fuel") > 0) {
            if (getFuel(stack) > 10000) {
                tooltip.add(Text.literal("§7Fuel: " + "§2" + (getFuel(stack) / 1000) + "k§8/" + (getMaxFuel(stack) / 1000) + "k"));
            } else {
                tooltip.add(Text.literal("§7Fuel: " + "§2" + getFuel(stack) + "§8/" + (getMaxFuel(stack) / 1000) + "k"));
            }
        } else {
            tooltip.add(Text.literal("§7Fuel: §4Empty"));
        }

        tooltip.add(Text.literal(""));

        // Item Rarity
        if (!RecombobulatorUtil.hasRecombobulator(stack)) {
            tooltip.add(Text.literal("§l" + this.rarity + " DRILL").formatted(this.rarity.formatting));
        } else {
            tooltip.add(Text.literal("§kM§r §l" + this.rarity + " DRILL §kM").formatted(this.rarity.formatting));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }



// MODULE FUNCTIONS
    // returns the module installed
    public static int getModule(String item) {
        switch (item) {
            case "Drill Engine Tier I":
             return 1;
            case "Drill Engine Tier II":
                return 2;
            case "Drill Engine Tier III":
                return 3;
            case "Drill Engine Tier IV":
                return 4;
            case "Drill Engine Tier V":
                return 5;
            case "Fuel Tank Tier I":
                return 1;
            case "Fuel Tank Tier II":
                return 2;
            case "Fuel Tank Tier III":
                return 3;
            case "Fuel Tank Tier IV":
                return 4;
            case "has_engine":
                return drillHasEngine;
            case "has_tank":
                return drillHasTank;
            default:
                return 0;
        }
    }
    
    // checks if the item has a module installed
    public static boolean hasModule(String module, ItemStack stack) {

        // Checks engine
        if (module == "engine") {
            if (stack.getNbt().getInt("subterra.engine") > 0) {
                return true;
            } else {
                return false;
            }
        } 
        // Checks fuel tank
        else if (module == "tank") {
            if (stack.getNbt().getInt("subterra.tank") > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        
    }

    // adds a module to the item
    public static void addModuleToDrill(String module, String name, ItemStack item) {
        
        // Adds a engine to the item
        if (module == "engine") {
            engine = item.getName().getString();
            switch (name) {
                case "Drill Engine Tier I" -> drillHasEngine = 1;
                case "Drill Engine Tier II" -> drillHasEngine = 2;
                case "Drill Engine Tier III" -> drillHasEngine = 3;
                case "Drill Engine Tier IV" -> drillHasEngine = 4;
                case "Drill Engine Tier V" -> drillHasEngine = 5;
                default -> Subterra.LOGGER.info("Failed to change Engine " + name);
            }
        } 
        // Adds a tank to the item
        else if (module == "tank") {
            tank = item.getName().getString();
            switch (name) {
                case "Fuel Tank Tier I" -> drillHasTank = 1;
                case "Fuel Tank Tier II" -> drillHasTank = 2;
                case "Fuel Tank Tier III" -> drillHasTank = 3;
                case "Fuel Tank Tier IV" -> drillHasTank = 4;
                default -> Subterra.LOGGER.info("Failed to change Tank " + name);
            }
        } else {
            return;
        }
    }



// MINING SPEED FUNCTIONS
    public static int getMininSpeedAddition(String name) {
        return DrillEngine.getMiningSpeed(name);
    }



// FUEL FUNCTIONS   
    // sets the fuel to the new max fuel
    public static int getTankFuel(String name) {
        return FuelTank.getFuel(name);
    }
    
    // resets the fuel to the base value
    public static void resetFuelCapacity(ItemStack stack) {
        int fuel = stack.getNbt().getInt("subterra.fuel");
        editNbtData(stack, false, 3000, "fuel", "");
        if (fuel >= 3000) {
            editNbtData(stack, false, fuel, "decrease_fuel", "");
        }
    }
    
    // sets the fuel
    private void setFuel(ItemStack stack) {
        if (stack.getNbt().getInt("subterra.fuel") != 3000 && i < 1) {
            editNbtData(stack, false, 3000, "fuel", "");
            i++;
        }
    }

    // gets the fuel count
    public int getFuel(ItemStack stack) {
        return stack.getNbt().getInt("subterra.fuel");
    }

    // gets the maximum fuel of the item
    public int getMaxFuel(ItemStack stack) {
        return stack.getNbt().getInt("subterra.max_fuel");
    }



// BASIC FUNCTIONS
    // removes fuel after player mined a block
    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        Subterra.LOGGER.info("postMine: " + stack.getNbt().getInt("subterra.fuel"));

        int newFuel = stack.getNbt().getInt("subterra.fuel") - 1;
        editNbtData(stack, true, newFuel, "decrease_fuel", "");
        return super.postMine(stack, world, state, pos, miner);
    }
    
    // check if the player has enough fuel to mine
    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        ItemStack stack = miner.getEquippedStack(EquipmentSlot.MAINHAND);
        Subterra.LOGGER.info("canMine: " + stack.getNbt().getInt("subterra.fuel"));
        if(stack.getNbt().getInt("subterra.fuel") > 0) {
            return true;
        } else {
            return false;
            }
    }

    // required functions
    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        float miningSpeed = this.baseMiningSpeed + stack.getNbt().getInt("subterra.mining_speed");
        if (abilityActive) {
            return miningSpeed * 3;
        } else {
            return miningSpeed;
        }
    }
    @Override
    public boolean isFireproof() {
        return true;
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
    @Override
    public boolean isDamageable() {
        return false;
    }

}
