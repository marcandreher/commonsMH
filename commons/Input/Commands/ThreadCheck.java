package marcandreher.commons.Input.Commands;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import marcandreher.commons.Commons.Flogger;
import marcandreher.commons.Commons.Flogger.Prefix;
import marcandreher.commons.Input.Command;
import marcandreher.commons.Utils.Color;


public class ThreadCheck implements Command {

    @Override
    public void executeAction(String[] args, Flogger logger) {
        if(args.length < 2) {
            logger.log(Prefix.INFO, getAlias(), 0);
            return;
        }

        if (args[1].equalsIgnoreCase("all")) {
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);

            for (ThreadInfo threadInfo : threadInfos) {
                logger.log(Prefix.INFO, "Thread ID: " + threadInfo.getThreadId(), 0);
                logger.log(Prefix.INFO, "Thread Name: " + threadInfo.getThreadName(), 0);
                logger.log(Prefix.INFO, "Thread State: " + threadInfo.getThreadState(), 0);
            }
        } else {
            logger.log(Prefix.INFO, getAlias(), 0);
        }
    }

    @Override
    public String getAlias() {
        return "logger <" + Color.GREEN + "string" + Color.RESET + ":all>";
    }

    @Override
    public String getDescription() {
        return "Check your threads";
    }

    @Override
    public String getName() {
        return "threadcheck";
    }

}
