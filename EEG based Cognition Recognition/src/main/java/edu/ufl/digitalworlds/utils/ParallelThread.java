package edu.ufl.digitalworlds.utils;

import edu.ufl.digitalworlds.utils.ProgressListener;

public abstract class ParallelThread implements Runnable{

	private ProgressListener progress_listener;
	private Thread thread;
	
	public void addProgressListener(ProgressListener progress_listener)
	{
		this.progress_listener=progress_listener;
	}
	
	public boolean isRunning()
	{
		if(thread!=null) return true; else return false;
	}
	
	protected void setMaxProgress(int value)
	{
		if(progress_listener!=null) progress_listener.setMaxProgress(value);
	}
	
	protected void setProgress(int value)
	{
		if(progress_listener!=null) progress_listener.setProgress(value);
	}
	
	public void start(String name)
	{
		thread = new Thread(this);
        thread.setName(name);
        thread.start();
	}
	
	public void startSerial()
	{
		thread = new Thread(this);
        run();
	}
	
	public void stop()
	{
		thread=null;
	}
	
	public void sleep(long milliseconds)
	{
		try {Thread.sleep(milliseconds);} catch (InterruptedException e) {}
	}
}
