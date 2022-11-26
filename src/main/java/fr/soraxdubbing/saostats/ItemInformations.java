package fr.soraxdubbing.saostats;

import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemInformations {
    private HashMap<PotionType,Integer> potions;
    private HashMap<String,Double> doubleAttributes;
    private HashMap<String,Integer> intAttributes;

    public ItemInformations() {
        this.potions = new HashMap<>();
        this.doubleAttributes = new HashMap<>();
        this.intAttributes = new HashMap<>();
    }

    public void addDoubleAttribute(String name, double value){
        doubleAttributes.put(name,value);
    }

    public void removeDoubleAttribute(String name){
        doubleAttributes.remove(name);
    }

    public boolean hasDoubleAttribute(String name){
        return doubleAttributes.containsKey(name);
    }

    public double getDoubleAttribute(String name){
        return doubleAttributes.get(name);
    }

    public void addIntAttribute(String name, int value){
        intAttributes.put(name,value);
    }

    public void removeIntAttribute(String name){
        intAttributes.remove(name);
    }

    public boolean hasIntAttribute(String name){
        return intAttributes.containsKey(name);
    }

    public int getIntAttribute(String name){
        return intAttributes.get(name);
    }

    public boolean doubleAttributesIsEmpty(){
        return doubleAttributes.isEmpty();
    }

    public boolean intAttributesIsEmpty(){
        return intAttributes.isEmpty();
    }

    public boolean potionsIsEmpty(){
        return potions.isEmpty();
    }

    public HashMap<PotionType,Integer> getPotions(){
        return potions;
    }

    public void addPotion(PotionType potionType, int level){
        potions.put(potionType,level);
    }

    public void removePotion(PotionType potionType){
        potions.remove(potionType);
    }

    public boolean hasPotion(PotionType potionType){
        return potions.containsKey(potionType);
    }
}
