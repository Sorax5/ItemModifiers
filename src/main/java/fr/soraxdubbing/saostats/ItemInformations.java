package fr.soraxdubbing.saostats;

import org.bukkit.potion.PotionType;

import java.util.HashMap;

public class ItemInformations {
    private String name;
    private String description;
    private HashMap<PotionType,Integer> potions;
    private HashMap<String,Double> doubleAttributes;
    private HashMap<String,Integer> intAttributes;

    public ItemInformations() {
        this.potions = new HashMap<>();
        this.doubleAttributes = new HashMap<>();
        this.intAttributes = new HashMap<>();
        this.name = "";
        this.description = "";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
