package marcandreher.commons.Utils;

/**
 * A simple stopwatch utility class that measures elapsed time.
 */
public class Stopwatch {
    
    private long startTime;
    private long elapsedTime;
    private boolean isRunning;

    /**
     * Starts the stopwatch. If the stopwatch is already running, this method does nothing.
     */
    public void start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;
        }
    }

    /**
     * Stops the stopwatch. If the stopwatch is not running, this method does nothing.
     * The elapsed time since the stopwatch was started is added to the total elapsed time.
     */
    public void stop() {
        if (isRunning) {
            elapsedTime += System.currentTimeMillis() - startTime;
            isRunning = false;
        }
    }

    /**
     * Resets the stopwatch to zero. The stopwatch is stopped if it is running.
     */
    public void reset() {
        elapsedTime = 0;
        isRunning = false;
    }

    /**
     * Returns the total elapsed time in milliseconds.
     * If the stopwatch is running, the current elapsed time is added to the total elapsed time.
     * @return the total elapsed time in milliseconds
     */
    public long getElapsedTime() {
        if (isRunning) {
            return elapsedTime + (System.currentTimeMillis() - startTime);
        } else {
            return elapsedTime;
        }
    }
}