package com.github.luizgfranca;


import com.github.luizgfranca.pipe.Pipe;

import java.util.ArrayList;
import java.util.List;


public class Main {

	private static final int BUFFER_SIZE = 1000;
	private static final int NUMBER_OF_PRODUCERS = 10;
	private static final int NUMBER_OF_CONSUMERS = 2;

	public static void main( String[] args )
		throws InterruptedException {

		List<Thread> producers = new ArrayList<>();
		List<Thread> consumers = new ArrayList<>();

		final var pipe = new Pipe( BUFFER_SIZE );

		for(int i = 0; i < NUMBER_OF_PRODUCERS; i ++) {
			producers.add( new Thread( new MessageProducer( Integer.toString( i ), pipe )) );
		}

		for(int i = 0; i < NUMBER_OF_CONSUMERS; i ++) {
			consumers.add( new Thread(new MessageConsumer( Integer.toString( i ), pipe )) );
		}

		producers.forEach( Thread::start );
		consumers.forEach( Thread::start );

		for ( Thread producer : producers ) { producer.join(); }
		for ( Thread consumer : consumers ) { consumer.join(); }

	}
}