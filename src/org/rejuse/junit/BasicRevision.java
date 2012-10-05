package org.rejuse.junit;

public class BasicRevision extends AbstractRevision {

	public BasicRevision(String text) {
		int index = 0;
		int nbElements = 1;
		while(index >= 0) {
			index = text.indexOf(".",index);
			nbElements++;
		}
		_numbers = new int[nbElements];
		index=0;
		for(int i = 0; i< nbElements; i++) {
			int endIndex = text.indexOf(".", index+1);
			_numbers[i] = Integer.parseInt(text.substring(index, endIndex)); 
		}
	}
	
	public BasicRevision(int... version) {
		_numbers = new int[version.length];
		for(int i = 0; i< version.length; i++) {
			_numbers[i] = version[i];
		}
	}
	
	@Override
	public int getNumber(int index) {
		return _numbers[index];
	}

	@Override
	public int length() {
		return _numbers.length;
	}

	private int[] _numbers;
}
