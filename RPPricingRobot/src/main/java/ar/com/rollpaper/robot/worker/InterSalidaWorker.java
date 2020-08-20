package ar.com.rollpaper.robot.worker;

import java.util.Date;

public interface InterSalidaWorker {

	void setEntroEjecutarWorker();
	void setSalioEjecutarWorker();
	void setResultadoEjecucion(Date fecha, String resultado);
}
