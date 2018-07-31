package ar.com.rollpaper.pricing.workers;

import java.util.List;

import javax.swing.SwingWorker;

public class InitWorker extends SwingWorker<Integer, Integer> {

	@Override
	protected Integer doInBackground() throws Exception {
		// Start
		publish(1);
		setProgress(1);

		// More work was done
		publish(2);
		setProgress(10);

		// Complete
		publish(3);
		setProgress(100);
		return 1;
	}

	@Override
	protected void process(List<Integer> chunks) {
		// Messages received from the doInBackground() (when invoking the publish()
		// method)
	}
}
