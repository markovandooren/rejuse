package org.aikodi.rejuse.io.stream;

import static org.aikodi.contract.Contract.requireNotNull;

import java.util.concurrent.atomic.AtomicBoolean;

import java.io.IOException;
import java.io.InputStream;

public class StreamSaver implements Runnable {

	private StringBuilder _builder = new StringBuilder();
	private AtomicBoolean done = new AtomicBoolean(false);
	
	private InputStream _stream;
	
	public StreamSaver(InputStream stream) {
		requireNotNull(stream);
		
		_stream = stream;
	}
	
	public void run() {
		int character;
		try {
			while((character = _stream.read()) != -1) {
				_builder.append((char)character);
			}
			done.set(true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String value() {
		while(! done.get()) {
		}
		return _builder.toString();
	}
}
