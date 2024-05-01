package commons.marcandreher.Input.Commands;

import java.util.ArrayList;

import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.GetRequest;
import commons.marcandreher.Commons.GetRequest.DebugGETRequest;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Input.Command;
import commons.marcandreher.Utils.Color;

public class TestRoute implements Command {

    @Override
    public void executeAction(String[] args, Flogger logger) {
        if (args.length == 1 || args.length > 3) {
            logger.log(Prefix.ERROR, getAlias(), 0);
            return;
        }
        int amount = 200;
        if(args.length == 3) {
            amount = Integer.parseInt(args[2]);
        }
        ArrayList<DebugGETRequest> debugGETRequests = new ArrayList<>();
        String domain = args[1];
        logger.log(Prefix.INFO, "Testing:" + domain, 0);
        GetRequest request = new GetRequest(domain);
        try {
            for (int i = 0; i < amount; i++) {
                debugGETRequests.add(request.sendDebug("(compatible; commonsMH debugger) github.com@marcandreher/1.0"));
                Thread.sleep(50);
            }
        } catch (Exception e) {
        }

        int successed = 0;
        int failed = 0;
        int total = debugGETRequests.size();
        long avgTime = 0;

        for (DebugGETRequest debugGETRequest : debugGETRequests) {
            avgTime += debugGETRequest.elapsedTime;
            if (debugGETRequest.failed || debugGETRequest.failed == false && debugGETRequest.statusCode != 200) {
                failed++;
            } else {
                successed++;
            }
        }

        avgTime /= total;

        double successPercentage = (double) successed / total * 100;

        logger.log(Prefix.INFO, "Total Requests: " + total, 0);
        logger.log(Prefix.INFO, "Successful Requests: " + successed, 0);
        logger.log(Prefix.INFO, "Failed Requests: " + failed, 0);
        logger.log(Prefix.INFO, "Percentage of Successful Requests: " + successPercentage + "%", 0);
        logger.log(Prefix.INFO, "Avg Time: " + avgTime + "ms", 0);

    }

    @Override
    public String getAlias() {
        return "request <" + Color.GREEN + "string " + Color.RESET + ":protocol://domain> <" + Color.GREEN + "int" +  Color.RESET + ":amount" + Color.GREEN + "?" + Color.RESET +">";
    }

    @Override
    public String getDescription() {
        return "Unittest a Route or test stabillity";
    }

    @Override
    public String getName() {
        return "request";
    }

}
