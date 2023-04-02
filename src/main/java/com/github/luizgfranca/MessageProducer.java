package com.github.luizgfranca;


import com.github.luizgfranca.pipe.Pipe;


public class MessageProducer implements Runnable {

	private final Pipe mPipe;

	private final String mName;

	public MessageProducer( String name, Pipe pipe ) {
		mPipe = pipe;
		mName = name;
	}

	@Override
	public void run() {

		int counter = 0;

		while(true) {
			try {
				mPipe.write( "PRODUCER " + mName + ": " + "msg " + Integer.toString( counter ) );
			} catch ( InterruptedException e ) {
				throw new RuntimeException( e );
			}

			counter ++;
		}

	}
}