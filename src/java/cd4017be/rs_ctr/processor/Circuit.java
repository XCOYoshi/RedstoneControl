package cd4017be.rs_ctr.processor;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.IntConsumer;

import cd4017be.rs_ctr.api.signal.SignalReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * parent template class for all ASM compiled circuit implementations.
 * @author CD4017BE
 */
public abstract class Circuit implements INBTSerializable<NBTTagCompound> {

	/**the circuit "serial number" */
	protected UUID ID;
	/**bitmap of which input pins trigger interrupt */
	protected int interruptPins;
	/**IO buffers */
	public int[] inputs, outputs;
	/**callbacks to notify changed output signals */
	public IntConsumer[] callbacks;

	/**
	 * The main update routine
	 * @return whether the circuit state changed
	 */
	public abstract boolean tick();

	/**
	 * overrides the circuit's internal state with the given one
	 * @param state new state
	 */
	public abstract void setState(StateBuffer state);

	/**
	 * @return the circuit's current internal state
	 */
	public abstract StateBuffer getState();

	/**
	 * @param pin input pin index
	 * @return whether the given input should trigger an interrupt
	 */
	public boolean isInterrupt(int pin) {
		return (interruptPins >> pin & 1) != 0;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("intpin", (byte)interruptPins);
		nbt.setIntArray("in", inputs);
		nbt.setIntArray("out", outputs);
		nbt.setTag("state", getState().nbt);
		nbt.setLong("IDm", ID.getMostSignificantBits());
		nbt.setLong("IDl", ID.getLeastSignificantBits());
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		interruptPins = nbt.getByte("intpin") & 0xff;
		inputs = nbt.getIntArray("in");
		outputs = nbt.getIntArray("out");
		Arrays.fill(callbacks = new IntConsumer[outputs.length], SignalReceiver.NOP);
		setState(new StateBuffer(nbt.getCompoundTag("state")));
		ID = new UUID(nbt.getLong("IDm"), nbt.getLong("IDl"));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Serial-ID = ").append(ID);
		sb.append("\nInput signals = ").append(Arrays.toString(inputs));
		sb.append("\nOutput signals = ").append(Arrays.toString(outputs));
		NBTTagCompound nbt;
		try {nbt = getState().nbt;}
		catch(Exception e) {nbt = null;}
		sb.append("\nMemory states = ").append(nbt);
		return sb.toString();
	}

	/**
	 * @return the actual implemented instance of this circuit (if it isn't already)
	 * @see UnloadedCircuit
	 */
	public Circuit load() {
		return this;
	}

}
