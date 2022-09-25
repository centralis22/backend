package edu.usc.marshall.centralis22.service;

import edu.usc.marshall.centralis22.service.action.ActionAdvanceStage;
import edu.usc.marshall.centralis22.service.action.ActionDummy;
import edu.usc.marshall.centralis22.service.action.ActionExecutor;
import org.springframework.stereotype.Service;

@Service
public class ActionDispatcher {

    void dispatch(String action, Object content) {

        ActionExecutor actionExecutor = null;

        switch(action) {
            case "advance_stage":
                actionExecutor = new ActionAdvanceStage();
                break;
            default:
                actionExecutor = new ActionDummy();
                break;
        }

        actionExecutor.execute(content);
    }
}
