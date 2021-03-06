package cd4017be.rs_ctr.signal;

import cd4017be.lib.util.ItemFluidUtil;
import cd4017be.lib.util.Orientation;
import cd4017be.rs_ctr.Objects;
import cd4017be.rs_ctr.api.signal.IConnector;
import cd4017be.rs_ctr.api.signal.MountedSignalPort;
import cd4017be.rs_ctr.render.WireRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**
 * @author CD4017BE
 *
 */
public class Constant implements IConnector {

	public static final String ID = "const";
	public int value;

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("id", ID);
		nbt.setInteger("val", value);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		value = nbt.getInteger("val");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderConnection(World world, BlockPos pos, MountedSignalPort port, double x, double y, double z, int light, BufferBuilder buffer) {
		WireRenderer.instance.drawModel(buffer, (float)x, (float)y, (float)z, Orientation.fromFacing(port.face), light, "plug.main(1)");
	}

	@Override
	public AxisAlignedBB renderSize(World world, BlockPos pos, MountedSignalPort port) {
		return null;
	}

	@Override
	public void onRemoved(MountedSignalPort port, EntityPlayer player) {
		ItemStack stack = new ItemStack(Objects.constant);
		stack.setTagCompound(serializeNBT());
		if (player == null) ItemFluidUtil.dropStack(stack, port.getWorld(), port.getPos());
		else if (!player.isCreative()) ItemFluidUtil.dropStack(stack, player);
		port.owner.getPortCallback(port.pin).accept(0);
	}

	@Override
	public String displayInfo(MountedSignalPort port, int linkID) {
		return "\n" + value;
	}

	@Override
	public void onLoad(MountedSignalPort port) {
		port.owner.getPortCallback(port.pin).accept(value);
	}

}
