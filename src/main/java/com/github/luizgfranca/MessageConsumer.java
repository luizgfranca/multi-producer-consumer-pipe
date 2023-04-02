package com.github.luizgfranca;


import com.github.luizgfranca.pipe.Pipe;

import java.util.Queue;


public class MessageConsumer implements Runnable{

	private final Pipe mPipe;

	private final String mName;

	public MessageConsumer( String name, Pipe pipe ) {
		mPipe = pipe;
		mName = name;
	}

	private void log(String message) {

		System.out.println("CONSUMER " + mName + ": " + message);
	}

	@Override public void run() {

		log( "started" );

		while(true) {
			String item = null;

			try {
				item = mPipe.read();
			} catch ( InterruptedException e ) {
				throw new RuntimeException( e );
			}

			log( "<" + item + ">");
		}

	}
}