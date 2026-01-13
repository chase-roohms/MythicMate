package Authenticate;

import io.github.cdimascio.dotenv.Dotenv;

public class Authenticate {
    /*Private Fields                                                                            */
    /*==========================================================================================*/
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    
    private final static String PASSWORD = getEnvVar("MYTHICMATE_PASSWORD", "");
    private final static String TOKEN = getEnvVar("MYTHICMATE_TOKEN", "");
    private final static String GPT_KEY = getEnvVar("MYTHICMATE_GPT_KEY", "");
    
    private static String getEnvVar(String key, String defaultValue) {
        String value = dotenv.get(key);
        if (value == null || value.isEmpty()) {
            value = System.getenv(key);
        }
        return value != null ? value : defaultValue;
    }

    /*Login Function                                                                            */
    /*==========================================================================================*/
    public boolean login(String userPassword){
        return userPassword.equals(PASSWORD);
    }

    public String getToken(){
        return TOKEN;
    }

    public static String getGPTKey() {
        return GPT_KEY;
    }
}
