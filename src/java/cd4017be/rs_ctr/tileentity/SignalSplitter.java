package cd4017be.rs_ctr.tileentity;

import java.util.function.IntConsumer;

import cd4017be.lib.util.Orientation;
import cd4017be.rs_ctr.api.signal.MountedSignalPort;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;


/**
 * @author CD4017BE
 *
 */
public class SignalSplitter extends Gate {

	private final IntConsumer[] callbacks = new IntConsumer[4];
	private int state;
	private Orientation o = Orientation.N;

	{
		ports = new MountedSignalPort[] {
			new MountedSignalPort(this, 0, true).setName("port.rs_ctr.o"),
			new MountedSignalPort(this, 1, true).setName("port.rs_ctr.o"),
			new MountedSignalPort(this, 2, true).setName("port.rs_ctr.o"),
			new MountedSignalPort(this, 3, true).setName("port.rs_ctr.o"),
			new MountedSignalPort(this, 4, false).setName("port.rs_ctr.i")
		};
	}

	@Override
	public IntConsumer getPortCallback(int pin) {
		return (val)-> {
			if (val == state) return;
			state = val;
			for (IntConsumer c : callbacks)
				if (c != null)
					c.accept(val);
		};
	}

	@Override
	public void setPortCallback(int pin, IntConsumer callback) {
		callbacks[pin] = callback;
		if (callback != null) callback.accept(state);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		state = nbt.getInteger("state");
		o = Orientation.values()[nbt.getByte("o") & 0xf];
		orient();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("state", state);
		nbt.setByte("o", (byte)o.ordinal());
		return super.writeToNBT(nbt);
	}

	@Override
	public void updateContainingBlockInfo() {
		super.updateContainingBlockInfo();
		o = getOrientation();
		orient();
	}

	private void orient() {
		for (int i = 0; i < 4; i++)
			ports[i].setLocation(0.75F, 0.125F + i * 0.25F, 0.125F, EnumFacing.EAST, o);
		ports[4].setLocation(0.25F, 0.5F, 0.125F, EnumFacing.WEST, o);
	}

}
