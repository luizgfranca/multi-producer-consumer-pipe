package com.github.luizgfranca.pipe;


import java.util.concurrent.Semaphore;


public class Pipe {

	final int mBufferSize;
	final String[] mBuffer;

	int mReadPosition;
	int mWritePosition;

	final Semaphore mSpaces;
	final Semaphore mItems;
	final Semaphore mWriteCoordinator;
	final Semaphore mReadCoordinator;

	public Pipe(int bufferSize) {
		mReadPosition = 0;
		mWritePosition = 0;

		mWriteCoordinator = new Semaphore( 1 );
		mReadCoordinator = new Semaphore( 1 );
		mItems = new Semaphore( 0 );

		mBufferSize = bufferSize;
		mBuffer = new String[mBufferSize];

		mSpaces = new Semaphore( mBufferSize );

	}

	int nextBufferPosition(int currentPosition) {
		return (currentPosition + 1) % mBufferSize;
	}

	public void write(String message)
		throws InterruptedException {
		mSpaces.acquire();
		mWriteCoordinator.acquire();

		mBuffer[mWritePosition] = message;
		mWritePosition = nextBufferPosition( mWritePosition );

		mItems.release();

		mWriteCoordinator.release();
	}

	public String read()
		throws InterruptedException {
		mItems.acquire();
		mReadCoordinator.acquire();

		var message = mBuffer[mReadPosition];
		mReadPosition = nextBufferPosition( mReadPosition );

		mSpaces.release();
		mReadCoordinator.release();

		return message;
	}

}
