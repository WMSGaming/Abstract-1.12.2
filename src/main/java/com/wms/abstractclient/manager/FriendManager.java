package com.wms.abstractclient.manager;

import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import java.util.List;

public class FriendManager {
    public static List<String> friends;
    public FriendManager(){
        friends = new ArrayList<>();
    }
    public static void addFriend(EntityPlayer friend){
        friends.add(friend.getName());
    }
    public static void removeFriend(EntityPlayer friend){
        friends.remove(friend.getName());
    }

    public static List<String> getFriends(){
        return friends;
    }
    public static boolean isFriend(EntityPlayer friend){
        boolean isFriended = false;
        for(String ign : friends){
            if(ign.equalsIgnoreCase(friend.getName())){
                isFriended = true;
            }
        }
        return isFriended;
    }
    public static boolean isNameFriend(String friend){
        boolean isFriended = false;
        for(String ign : friends){
            if(ign.equalsIgnoreCase(friend)){
                isFriended = true;
            }
        }
        return isFriended;
    }
}
