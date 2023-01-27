package com.wms.abstractclient.manager;

import com.google.gson.*;
import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class ConfigManager extends Thread{

    private static final File abstractDir = new File("Abstract");
    private static final String modulesPath = abstractDir.getAbsolutePath() + "/modules";
    private static final String Friends = "/Friends.json";


    // Loading part of configs

    public static void loadConfig(){
        try {
            loadFriends();
        } catch (Exception ignored) { }

        loadModules();

    }

    private static void loadFriends() throws IOException {
        Path path = Paths.get(abstractDir.getAbsolutePath(), Friends);
        if (!path.toFile().exists()) return;
        String rawJson = loadFile(path.toFile());
        JsonObject jsonObject = new JsonParser().parse(rawJson).getAsJsonObject();
        if (jsonObject.get("Friends") != null) {
            JsonArray friendObject = jsonObject.get("Friends").getAsJsonArray();
            friendObject.forEach(object -> FriendManager.friends.add(object.getAsString()));
        }
    }
    private static void loadModules(){
        for(Module m : AbstractClient.moduleManager.getModules()){
            loadModule(m);
        }
    }

    private static void loadModule(Module module){
        try {
            Path path = Paths.get(modulesPath, module.getName() + ".json");
            if (!path.toFile().exists()) return;
            String rawJson = loadFile(path.toFile());
            JsonObject jsonObject = new JsonParser().parse(rawJson).getAsJsonObject();

            if(jsonObject.get("Enabled") != null){
                if(jsonObject.get("Enabled").getAsBoolean()){
                    module.setEnabled(true);
                    module.enable();
                }else {
                    module.setEnabled(false);
                    module.disable();
                }
            }
            if(jsonObject.get("Bind") != null){
                module.setBind(jsonObject.get("Bind").getAsInt());
            }
            if(jsonObject.get("Displayed") != null){
                module.setDisplayed(jsonObject.get("Displayed").getAsBoolean());
            }

            for(Setting s : module.getSettings()){
                if(s.getType() == Setting.Type.OPTION){
                    if(jsonObject.get(s.getName()) != null) {
                        s.setEnumValue(jsonObject.get(s.getName()).getAsString());
                    }
                }
                if(s.getType() == Setting.Type.NUMBER){
                    if(jsonObject.get(s.getName()) != null) {
                        s.setValue(jsonObject.get(s.getName()).getAsFloat());
                    }
                }
                if(s.getType() == Setting.Type.BOOLEAN){
                    if(jsonObject.get(s.getName()) != null) {
                        s.setEnabled(jsonObject.get(s.getName()).getAsBoolean());
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // Saving part of configs
    @Override
    public void run() {
        saveConfig();
    }
    private static void saveConfig(){
        if (!abstractDir.exists() && !abstractDir.mkdirs()) System.out.println("Failed to create config folder");
        if (!new File(modulesPath).exists() && !new File(modulesPath).mkdirs()) {
            System.out.println("Failed to create modules folder");
        }
        saveModules();
        saveFriends();
    }

    private static void saveFriends(){
        try {
            Path path = Paths.get(abstractDir.getAbsolutePath(), Friends);
            createFile(path);
            JsonObject jsonObject = new JsonObject();
            JsonArray friendList = new JsonArray();
            FriendManager.getFriends().forEach(friendList::add);
            jsonObject.add("Friends",friendList);
            Gson gson = new Gson();
            Files.write(path,gson.toJson(new JsonParser().parse(jsonObject.toString())).getBytes());

        }catch (Exception e){
            System.out.print(e);
        }
    }

    private static void saveModules(){
        for(Module m : AbstractClient.moduleManager.getModules()){
            saveModule(m);
        }
    }


    private static void saveModule(Module m){
        try {

            Path path = Paths.get(modulesPath, m.getName() + ".json");
            createFile(path);
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("Enabled", new JsonPrimitive(m.isEnabled()));
            jsonObject.add("Bind", new JsonPrimitive(m.getBind()));
            jsonObject.add("Displayed", new JsonPrimitive(m.getDisplayed()));

            for(Setting s : m.getSettings()){
                if(s.getType() == Setting.Type.BOOLEAN){
                    jsonObject.add(s.getName(), new JsonPrimitive(s.isEnabled()));
                }
                if(s.getType() == Setting.Type.NUMBER){
                    jsonObject.add(s.getName(), new JsonPrimitive(s.getValue()));
                }
                if(s.getType() == Setting.Type.OPTION){
                    jsonObject.add(s.getName(), new JsonPrimitive(s.getEnumValue()));
                }
            }
            Gson gson = new Gson();
            Files.write(path, gson.toJson(new JsonParser().parse(jsonObject.toString())).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void createFile(Path path) {
        if (Files.exists(path)) new File(path.normalize().toString()).delete();
        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String loadFile(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file.getAbsolutePath());
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
