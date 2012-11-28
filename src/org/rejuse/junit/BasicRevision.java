package org.rejuse.junit;

public class BasicRevision extends AbstractRevision {

	public BasicRevision(String text) {
		String[] parts = text.split("\\.");
		int nbElements = parts.length;
		_numbers = new int[nbElements];
		for(int i = 0; i< nbElements; i++) {
			_numbers[i] = Integer.parseInt(parts[i]); 
		}
	}
	
	public BasicRevision(int... version) {
		_numbers = new int[version.length];
		for(int i = 0; i< version.length; i++) {
			_numbers[i] = version[i];
		}
	}
	
	@Override
	public int getNumber(int baseOneIndex) {
		return _numbers[baseOneIndex-1];
	}

	@Override
	public int length() {
		return _numbers.length;
	}

	private int[] _numbers;
}
