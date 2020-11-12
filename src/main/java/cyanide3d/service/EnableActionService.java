package cyanide3d.service;

import cyanide3d.dao.EnableActionDao;
import cyanide3d.exceprtion.UnsupportedActionException;
import cyanide3d.exceprtion.UnsupportedStateException;
import cyanide3d.model.ActionState;

import java.util.List;

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
        if(!state.equalsIgnoreCase("true") || !state.equalsIgnoreCase("false")){
            new UnsupportedStateException(state);
        }
        if (!checkRegAction(action)){
            new UnsupportedActionException(action);
        }
        if(!checkAction(action)){
            dao.create(action.toLowerCase(), state.toLowerCase());
            stateAction.add(new ActionState(action.toLowerCase(),state.toLowerCase()));
        }else {
            dao.update(action.toLowerCase(), state.toLowerCase());
            stateAction.stream()
                    .filter(actionState -> actionState.getAction().equalsIgnoreCase(action))
                    .forEach(actionState -> actionState.setState(state.toLowerCase()));
        }
    }

    public boolean checkAction(String action){
        for(ActionState actionState : stateAction){
            if(actionState.getAction().equalsIgnoreCase(action))
                return true;
        }
        return false;
    }

    public boolean checkRegAction(String action){
        for (String regAction : ACTION_LIST){
            if (regAction.equalsIgnoreCase(action)) return true;
        }
        return false;
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
