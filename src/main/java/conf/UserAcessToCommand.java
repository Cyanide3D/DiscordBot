package conf;

public class UserAcessToCommand {
    String[] admIDs = {"320967415863312386","534894366448156682"};
    String[] modIDs = {"508681121572061218"};
    public boolean checkAdm(String usrID){
        for(int i = 0; i < admIDs.length;i++){
            if(admIDs[i].equalsIgnoreCase(usrID)){
                return true;
            }
        }
        return false;
    }
    public boolean checkMod(String usrID){
        for(int i = 0; i < modIDs.length;i++){
            if(modIDs[i].equalsIgnoreCase(usrID)){
                return true;
            }
        }
        return false;
    }
}
