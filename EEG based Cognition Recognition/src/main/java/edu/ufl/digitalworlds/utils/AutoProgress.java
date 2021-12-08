package edu.ufl.digitalworlds.utils;

public class AutoProgress extends ParallelThread{

	double duration;
	double factor;
	int progress;
	
	public AutoProgress(int start_duration, double progress_factor)
	{
		duration=start_duration;
		factor=progress_factor;
	}
	
	@Override
	public void run() {
		int value=0;
		setMaxProgress(100);
		setProgress(value);

		while(isRunning() && value<100)
		{
			duration*=factor;
			if(isRunning()){value+=1;setProgress(value);}
			sleep((int)duration);
		}
		stop();
	}
}
