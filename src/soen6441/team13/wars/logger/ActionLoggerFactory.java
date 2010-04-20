package soen6441.team13.wars.logger;

public class ActionLoggerFactory {
	private static final ActionLogger actionLogger = new ActionLogger();

        /**
         * Return reference to ActionLogger
         * @return
         */
	public static ActionLogger getInstance(){	
		return actionLogger;
	}

}
