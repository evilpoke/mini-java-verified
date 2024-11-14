
public class CRC {
	/*
	 * Ansatz: String Bit fuer Bit verarbeiten
	 */

	private int poly;

	private int degree;

	public int getDegree() {
		return degree;
	}

	public CRC(int poly) {
		this.poly = poly;
		degree = -1;
		// Der Grad des Polynoms ergibt sich aus der Position des hoechstwertigsten
		// 1-Bits
		// >>> stellt sicher, dass das auch mit negativen Zahlen klappt!
		for (int p = poly; p != 0; p >>>= 1)
			degree++;
	}

	/**
	 * Verarbeitet ein weiteres Bit in der Berechnung des CRC-Wertes
	 *
	 * @param crc der bisherige CRC-Wert
	 * @param bit das zu verarbeitende Bit
	 * @return der neue CRC-Wert
	 */
	private int crcBit(int crc, int bit) {
		// Wir haengen das Bit vorne an den bisherigen CRC-Wert
		crc = (crc << 1) | bit;
		// Ist der CRC-Wert groß genug geworden (genug Zahlen 'heruntergeholt' in der
		// der schriftlichen Division)...
		if (crc >>> degree != 0)
			// ... wenden wir die XOR-Operation an, ...
			return crc ^ poly;
		// ... sonst nicht.
		return crc;
	}

	/**
	 * Erweitert die Berechnung des CRC-Wertes um 'degree' viele 0-Bits
	 *
	 * @param crc der bisherige CRC-Wert
	 * @return der neue CRC-Wert
	 */
	private int crcEnd(int crc) {
		for (int i = 0; i < degree; i++)
			crc = crcBit(crc, 0);
		return crc;
	}

	private int crcASCII(int crc, int symbol) {
		// Innerhalb eines Zeichens verarbeiten wir Bit fuer Bit
		for (int i = 0; i < 7; i++)
			crc = crcBit(crc, (symbol >>> (6 - i)) & 1);
		return crc;
	}

	public int crcASCIIString(String s) {
		int crc = 0;
		// Wir berechnen den CRC-Wert Zeichen fuer Zeichen
		for (int i = 0; i < s.length(); i++)
			crc = crcASCII(crc, s.charAt(i));
		return crcEnd(crc);
	}
}
