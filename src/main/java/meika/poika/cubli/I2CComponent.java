package meika.poika.cubli;

/*******************************************************************************
 * Copyright (c) 2016 Julien Louette & Gaël Wittorski
 * Modified 2024 by Arttu Salminen
 *
 * This file WAS part of Raspoid, MODIFIED to work independently with new version of Pi4J.
 *
 * Raspoid is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Raspoid is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Raspoid.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

import com.pi4j.io.i2c.*;

/**
 * <b>This class is used as an abstraction for each i2c component of the framework.
 * This povides usefull utilities to connect to, read data from and write data to i2c devices.</b>
 *
 * @author Julien Louette &amp; Ga&euml;l Wittorski
 * @version 1.0
 */
public class I2CComponent {

    /**
     * The maximum available i2c device address is 0x77.
     * <p>A seven bit wide address space theoretically allows 128 I2C addresses.
     * However, some addresses are reserved for special purposes. Only
     * 112 addresses are then available with the 7 bit address scheme.</p>
     */
    public static final int MAX_I2C_ADDRESS = 0x77;

    /**
     * The minimum available i2c device address is 0x03.
     * <p>A seven bit wide address space theoretically allows 128 I2C addresses.
     * However, some addresses are reserved for special purposes. Thus, only
     * 112 addresses are available with the 7 bit address scheme.</p>
     */
    public static final int MIN_I2C_ADDRESS = 0x03;

    /**
     * The abstraction of the i2c device. It allows data to be read or written to the device.
     */
    protected I2C device;

    /**
     * Constructor for a new i2c component, connected on the bus at the specific i2cAddress.
     * @param i2cAddress the i2c device address of the corresponding component.
     */
    public I2CComponent(int i2cAddress) {
        if (i2cAddress > MAX_I2C_ADDRESS || i2cAddress < MIN_I2C_ADDRESS)
            throw new RuntimeException("Invalid i2c address.");

        I2CProvider i2CProvider = CubliApp.PI4J_CONTEXT.provider("linuxfs-i2c");
        I2CConfig i2cConfig = I2C.newConfigBuilder(CubliApp.PI4J_CONTEXT).id("MPU6050").bus(1).device(i2cAddress).build();
        device = i2CProvider.create(i2cConfig);

        System.out.println("I2CComponent created with address " + device.getDevice());
    }

    /**
     * Get the abstraction of the i2c device.
     * It then allows data to be read or written to the device.
     * @return the abstraction of the i2c device.
     * @see com.pi4j.io.i2c.I2C
     */
    public I2C getDevice() {
        return device;
    }

    /**
     * This method writes an unsigned value directly on the i2c device
     * (not on a specific register on this device).
     * @param value the unsigned value to be written on the device. This value must be in the 0..255 interval.
     */
    public void writeUnsignedValueDirectlyToI2CDevice(int value) {
        if(value > 255 || value < 0)
            throw new IllegalArgumentException("The unsigned value must be in the [0;255] interval.");

        device.write((byte)value);
    }

    /**
     * This method writes an unsigned value on the i2c device,
     * on a specific register of this device.
     * @param reg the destination register on the i2c device.
     * @param value the unsigned value to be written on the device. This value must be in the 0..255 interval.
     */
    public void writeUnsignedValueToRegister(int reg, int value) {
        if(value > 255 || value < 0)
            throw new IllegalArgumentException("The unsigned value muste be in the [0;255] interval");

        device.writeRegister(reg, value);
    }

    /**
     * Reads the content of the reg register (8bits),
     * and returns this value in the 0..255 interval if read operation was successfull.
     * A negative number is returned for an error.
     * @param reg the local address on the i2c device where the data must be read.
     * @return the byte value read on the device: a positive number in the 0..255 interval if reading was successful;
     *  a negative number (<0) if reading failed.
     * @see I2CComponent#readSignedRegisterValue(int)
     */
    public int readUnsignedRegisterValue(int reg) {
        int result = device.readRegister(reg);
        if (result < 0)
            throw new RuntimeException("Error when reading the register " + reg + ".");
        return result;
    }

    /**
     * Reads the content of the reg register (8bits),
     * and returns the signed integer representation of this value in the -128..127 interval
     * if read operation was successfull. A negative number under -128 is returned for an error.
     * @param reg the local address on the i2c device where the data must be read.
     * @return the integer signed representation of the byte value read on the device:
     *  a positive number in the -128..127 interval if reading was successful;
     *  a negative number (<128) if reading failed.
     * @see I2CComponent#readUnsignedRegisterValue(int)
     */
    public int readSignedRegisterValue(int reg) {
        int value = readUnsignedRegisterValue(reg);
        if(value < 0)
            value -= 128;
        return value > 127 ? value-256 : value;
    }

    /**
     * Reads the unsigned content of two consecutive registers (reg and reg + 1) (16bits),
     * and returns the big endian combination of those values.
     * @param reg the local address on the i2c device of the first register to be read.
     * @return the unsigned big endian combination of the reads of registers reg and reg + 1 on the i2c device.
     * @see I2CComponent#readUnsignedRegisterValue(int)
     */
    public int readTwoUnsignedRegisters(int reg) {
        int reg1Content = readUnsignedRegisterValue(reg);
        int reg2Content = readUnsignedRegisterValue(reg + 1);
        if(reg1Content < 0 || reg2Content < 0)
            throw new RuntimeException("Error when reading the two consecutive registers " + reg + " and " + (reg+1) + ".");
        return (reg1Content << 8) + reg2Content;
    }

    /**
     * Reads the signed content of two consecutive registers (reg and reg + 1) (16bits),
     * and returns the big endian combination of those values.
     * @param reg the local address on the i2c device of the first register to be read.
     * @return the signed big endian combination of the reads of registers reg and reg + 1 on the i2c device.
     * @see I2CComponent#readUnsignedRegisterValue(int)
     * @see I2CComponent#readSignedRegisterValue(int)
     */
    public int readTwoSignedRegsiters(int reg) {
        int reg1Content = readSignedRegisterValue(reg);
        int reg2Content = readUnsignedRegisterValue(reg + 1);
        if(reg1Content < -128 || reg2Content < 0)
            throw new RuntimeException("Error when reading the two consecutive registers " + reg + " and " + (reg+1) + ".");
        return (reg1Content << 8) + reg2Content;
    }

}