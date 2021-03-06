/*
 * Quick-Key Toolset Project.
 * Copyright (C) 2010 FedICT.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version
 * 3.0 as published by the Free Software Foundation.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, see 
 * http://www.gnu.org/licenses/.
 */
package be.fedict.util;

import java.math.BigInteger;
import be.fedict.eidtoolset.exceptions.UnsupportedEncodingException;

public class TextUtils {
	public final static String hexChars[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
	final static public int tagLen = 1;
	public static byte stringCharToBCDByte(String data, int pos) {
		return (byte) (Integer.parseInt(data.substring(pos, pos + 2), 16));
	}
	public static String hexDump(BigInteger big) {
		return hexDump(big.toByteArray());
	}
	public static String hexDump(byte[] data) {
		return hexDump(data, 0, data.length);
	}
	public static String hexDump(byte[] data, int length) {
		return hexDump(data, 0, length);
	}
	public static String hexDump(byte[] data, int offset, int length) {
		String result = "";
		String part = "";
		for (int i = 0; i < MathUtils.min(data.length, length); i++) {
			part = "" + hexChars[(byte) (MathUtils.unsignedInt(data[offset + i]) / 16)] + hexChars[(byte) (MathUtils.unsignedInt(data[offset + i]) % 16)];
			result = result + part;
		}
		return result;
	}
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
	static void print(byte[] response) {
		System.err.println("Response  " + hexDump(response));
	}
	public static int getTagIdentifier(byte[] data, int pos) {
		return data[pos];
	}
	public static int getDataLen(byte[] data, int pos) {
		int i = 0;
		int len = 0;
		while (MathUtils.unsignedInt(data[pos + i]) == 255) {
			len = len * 255 + 255;
			i++;
		}
		len += MathUtils.unsignedInt(data[pos + i]);
		return len;
	}
	// Returns the length of the data length field
	public static int getLenLen(byte[] data, int pos) {
		int i = 0;
		while (MathUtils.unsignedInt(data[pos + i]) == 255) {
			i++;
		}
		return i + 1;
	}
	public static String getString(byte[] data, int pos, int len) throws UnsupportedEncodingException, java.io.UnsupportedEncodingException {
		return new String(data, pos, len, "UTF-8");
	}
	public static byte[] selectBytes(byte[] data, int pos, int len) throws UnsupportedEncodingException {
		byte[] res = new byte[len];
		for (int i = 0; i < len; i++)
			res[i] = data[pos + i];
		return res;
	}
	public static final byte[] shortToByteArray(final short value) {
		return new byte[] { (byte) (value >>> 8), (byte) (value) };
	}
	public static final short byteArrayToShort(final byte[] b) {
		return (short) (((b[0] & 0xFF) << 8) + (b[1] & 0xFF));
	}
}
