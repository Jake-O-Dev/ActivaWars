package nl.activakingdoms.wars;

import java.util.ArrayList;

public class Setting {

    private String name;
    private ArrayList<String> validAnswers;
    private String defaultValue;
    private String permission;
    private String value;


    public Setting(String name, ArrayList<String> validAnswers, String defaultValue, String permission) {
        this.name = name;
        this.validAnswers = validAnswers;
        this.defaultValue = defaultValue;
        this.permission = permission;
        this.value = defaultValue;
    }


    public String getName() {
        return name;
    }

    public ArrayList<String> getValidAnswers() {
        return validAnswers;
    }

    public String getDefault() {
        return defaultValue;
    }

    public String getPermission() {
        return permission;
    }

    public void setValue(String answer) {
        value = answer;
    }

    public String getValue() {
        return value;
    }
}
