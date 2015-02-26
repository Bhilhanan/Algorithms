/**
   A stop watch that accumulates time. You can repeatedly start and stop it.
*/

public class StopWatch
{
    private long elapsedTime;
    private long startTime;
    private boolean isRunning;

    /**
       Construct a stop watch that is in the stopped state
       and has no accumulated time.
    */
    public StopWatch()
    {
	elapsedTime = 0;
	isRunning = false;
    }

    /**
       Starts the stop watch.
    */
    public void start()
    {
	if (isRunning)
	    return;
	isRunning = true;
	startTime = System.currentTimeMillis();
    }

    /**
       Stops the stop watch. Time stops accumulating and is added to the elapsed time.
    */
    public void stop()
    {
	if (!isRunning)
	    return;
	isRunning = false;
	long endTime = System.currentTimeMillis();
	elapsedTime = elapsedTime + endTime - startTime;
    }

    /**
       Return the total elapsed time
    */
    public long getElapsedTime()
    {
	if (isRunning)
	{
	    long endTime = System.currentTimeMillis();
	    return elapsedTime + endTime - startTime;
	}
	
	return elapsedTime;
    }
    
    /**
       Stops the watch and resets the elapsed time to 0.
    */
    public void reset()
    {
	elapsedTime = 0;
	isRunning = false;
    }
}
