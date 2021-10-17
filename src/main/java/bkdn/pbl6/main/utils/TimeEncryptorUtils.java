package bkdn.pbl6.main.utils;

public class TimeEncryptorUtils {

	public static String encode(long input) {
		byte[] _o = new byte[4];
		int i = 0;
		for (; i < _o.length; i++) {
			int c = (int) (input % 64);
			input /= 64;
			if (c < 26) {
				c += 97;
			} else if (c < 52) {
				c += 39;
			} else if (c < 62) {
				c -= 4;
			} else {
				c = ((c & 1) == 1) ? 35 : 64;
			}
			_o[i] = (byte) c;
		}
		return new String(_o, 0, i);
	}

	public static long decode(String output) {
		long input = 0;
		byte[] _o = output.getBytes();
		for (int i = _o.length; i > 0;) {
			--i;
			int c = _o[i];
			input *= 64;
			if (c >= 97) {
				input += c - 97;
			} else if (c >= 65) {
				input += c - 39;
			} else if (c == 64) {
				input += 62;
			} else if (c >= 48) {
				input += c + 4;
			} else {
				input += 63;
			}
		}
		return input;
	}

	public static void main(String[] args) {
		long now = (System.currentTimeMillis() / 60000) & ((1 << 24) - 1);
		System.out.println(now);
		System.out.println(encode(now));
		System.out.println(decode(encode(now)));
		System.out.println(Long.toHexString(1 << 24 - 1));
		System.out.println(Long.toHexString((1 << 24) - 1));
		System.out.println(Long.toHexString(8 / 3 & 2));
	}

}
