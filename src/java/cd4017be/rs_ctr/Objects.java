package cd4017be.rs_ctr;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import cd4017be.api.recipes.RecipeScriptContext.ConfigConstants;
import cd4017be.lib.block.OrientedBlock;
import cd4017be.lib.item.BaseItemBlock;
import cd4017be.lib.templates.TabMaterials;
import cd4017be.lib.util.TooltipUtil;
import cd4017be.rs_ctr.block.*;
import cd4017be.rs_ctr.item.*;
import cd4017be.rs_ctr.tileentity.*;

/**
 * 
 * @author CD4017BE
 */
@EventBusSubscriber(modid = Main.ID)
@ObjectHolder(value = Main.ID)
public class Objects {

	//Creative Tabs
	public static TabMaterials tabCircuits = new TabMaterials(Main.ID);

	//Blocks
	public static final BlockRedstonePort RS_PORT = null;
	public static final OrientedBlock SPLITTER = null;
	public static final OrientedBlock ANALOG_COMB = null;
	public static final OrientedBlock LOGIC_COMB = null;
	public static final OrientedBlock NUM_COMB = null;
	
	//ItemBlocks
	public static final ItemRedstonePort rs_port = null;
	public static final BaseItemBlock splitter = null;
	public static final BaseItemBlock analog_comb = null;
	public static final BaseItemBlock logic_comb = null;
	public static final BaseItemBlock num_comb = null;

	//Items
	public static final ItemSignalWire wire = null;
	public static final ItemWirelessCon wireless = null;
	public static final ItemConstantPlug constant = null;
	public static final ItemStatusLamp lamp = null;
	public static final ItemWireTag tag = null;

	public static void init() {
		tabCircuits.item = new ItemStack(wire);
	}

	public static void initConstants(ConfigConstants c) {
		
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> ev) {
		TooltipUtil.CURRENT_DOMAIN = Main.ID;
		ev.getRegistry().registerAll(
				new BlockRedstonePort("rs_port", Material.ROCK, SoundType.STONE, RedstonePort.class).setCreativeTab(tabCircuits).setLightOpacity(0).setHardness(0.5F),
				OrientedBlock.create("splitter", Material.ROCK, SoundType.STONE, 3, SignalSplitter.class, PropertyGateOrient.GATE_ORIENT).setBlockBounds(new AxisAlignedBB(0.25, 0, 0, 0.75, 1, 0.25)).setLightOpacity(0).setCreativeTab(tabCircuits),
				OrientedBlock.create("analog_comb", Material.ROCK, SoundType.STONE, 3, AnalogCombiner.class, PropertyGateOrient.GATE_ORIENT).setBlockBounds(new AxisAlignedBB(0.25, 0, 0, 0.75, 1, 0.25)).setLightOpacity(0).setCreativeTab(tabCircuits),
				OrientedBlock.create("logic_comb", Material.ROCK, SoundType.STONE, 3, LogicCombiner.class, PropertyGateOrient.GATE_ORIENT).setBlockBounds(new AxisAlignedBB(0.25, 0, 0, 0.75, 1, 0.25)).setLightOpacity(0).setCreativeTab(tabCircuits),
				OrientedBlock.create("num_comb", Material.ROCK, SoundType.STONE, 3, NummericCombiner.class, PropertyGateOrient.GATE_ORIENT).setBlockBounds(new AxisAlignedBB(0.25, 0, 0, 0.75, 1, 0.25)).setLightOpacity(0).setCreativeTab(tabCircuits)
		);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> ev) {
		TooltipUtil.CURRENT_DOMAIN = Main.ID;
		ev.getRegistry().registerAll(
				new ItemRedstonePort(RS_PORT),
				new BaseItemBlock(SPLITTER),
				new BaseItemBlock(ANALOG_COMB),
				new BaseItemBlock(LOGIC_COMB),
				new BaseItemBlock(NUM_COMB),
				new ItemSignalWire("wire").setCreativeTab(tabCircuits),
				new ItemWirelessCon("wireless").setCreativeTab(tabCircuits),
				new ItemConstantPlug("constant").setCreativeTab(tabCircuits),
				new ItemStatusLamp("lamp").setCreativeTab(tabCircuits),
				new ItemWireTag("tag").setCreativeTab(tabCircuits)
		);
	}

}
