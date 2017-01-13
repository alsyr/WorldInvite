package widgas.worldinvite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

/**
 * Created by AlSyR on 11/22/14.
 */
public class Splash extends Activity {


    class BoundedQueue <E>{

        private Object[] elements;
        private int head;
        private int tail;
        private int size;

        /**
         Constructs an empty queue.
         @param capacity the maximum capacity of the queue
         */
        public BoundedQueue(int capacity)
        {
            elements = new Object[capacity];
            head = 0;
            tail = 0;
            size = 0;
        }


        /**
         Removes the object at the head.
         @return the object that has been removed from the queue
         */
        public synchronized E remove() throws InterruptedException
        {
            while (size == 0) wait();
            E r = (E) elements[head];
            head++;
            size--;
            if (head == elements.length)
                head = 0;
            notifyAll();
            return r;
        }

        /**
         Appends an object at the tail.
         @param newValue the object to be appended
         */
        public synchronized void add(E newValue) throws InterruptedException
        {
            while (size == elements.length) wait();
            elements[tail] = newValue;
            tail++;
            size++;
            if (tail == elements.length)
                tail = 0;
            notifyAll();
        }
    }


    class Consumer implements Runnable{

        private BoundedQueue<Integer> queue;
        private int progressCount;
        private static final int DELAY = 10;
        final Intent mainIntent = new Intent(Splash.this,Login.class);

        /**
         Constructs the consumer object.
         @param aQueue the queue from which to retrieve counters
         @param count the max value of counters to consume
         */
        public Consumer(BoundedQueue<Integer> aQueue, int count)
        {
            queue = aQueue;
            progressCount = count;
        }

        public void run()
        {
            try
            {
                int i = 0;
                while (i < progressCount)
                {
                    Integer theProgress = queue.remove();
                    Thread.sleep(200);
                    progress.setProgress(theProgress);
                    i++;
                }
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
            catch (InterruptedException exception)
            {
            }
        }

    }


    class Producer implements Runnable{

        private BoundedQueue<Integer> queue;
        private int progressCount;

        private static final int DELAY = 10;

        /**
         Constructs the producer object.
         @param aQueue the queue into which to insert counters
         @param count the max value of counters to produce
         */
        public Producer(BoundedQueue<Integer> aQueue, int count)
        {
            queue = aQueue;
            progressCount = count;
        }

        public void run()
        {
            try
            {
                int i = 0;
                while (i < progressCount)
                {
                    queue.add(i);
                    i++;
                    Thread.sleep((int) (Math.random() * DELAY));
                }
            }
            catch (InterruptedException exception)
            {
            }
        }
    }


    private ProgressBar progress;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);

        int totalProgressTime = 20;

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(totalProgressTime);

        BoundedQueue<Integer> queue = new BoundedQueue<Integer>(10);

        Runnable produce = new Producer(queue, totalProgressTime);
        Runnable consume = new Consumer(queue, totalProgressTime);

        Thread theProducer = new Thread(produce);
        Thread theConsumer = new Thread(consume);

        theProducer.start();
        theConsumer.start();
    }

}
