package cyanide3d.service;

import cyanide3d.dao.EnableActionDao;
import cyanide3d.exceprtion.UnsupportedActionException;
import cyanide3d.exceprtion.UnsupportedStateException;
import cyanide3d.model.ActionState;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class EnableActionService {
    private final String[] ACTION_LIST = {"joinleave", "blacklist", "joinform", "logging"};
    public static EnableActionService instance;
    List<ActionState> stateAction;
    final EnableActionDao dao;

    private EnableActionService() {
        dao = new EnableActionDao();
        stateAction = dao.list();
    }

    public void setState(String action, String state) throws UnsupportedStateException, UnsupportedActionException {
        if(!state.equalsIgnoreCase("true") && !state.equalsIgnoreCase("false")){
            throw new UnsupportedStateException(state);
        }
        if (!Arrays.stream(ACTION_LIST).anyMatch(checkAction -> checkAction.equalsIgnoreCase(action))){
            throw new UnsupportedActionException(action);
        }
        if(!stateAction.stream().anyMatch(actionState -> actionState.getAction().equalsIgnoreCase(action))){
            dao.create(action.toLowerCase(), state.toLowerCase());
            stateAction.add(new ActionState(action.toLowerCase(),state.toLowerCase()));
        }else {
            dao.update(action.toLowerCase(), state.toLowerCase());
            stateAction.stream()
                    .filter(actionState -> actionState.getAction().equalsIgnoreCase(action))
                    .forEach(actionState -> actionState.setState(state.toLowerCase()));
        }
    }

    public boolean getState(String action){
        for(ActionState actionState : stateAction){
            if(actionState.getAction().equalsIgnoreCase(action))
                return Boolean.parseBoolean(actionState.getState());
        }
        return false;
    }

    public static EnableActionService getInstance(){
        if(instance == null) instance = new EnableActionService();
        return instance;
    }


}
