package com.mygdx.game.Items;

import java.util.ArrayList;

public class Bag {
    //fai la struttura dati
    ArrayList<ArrayList<Item>> bag = new ArrayList<>();


    public Bag(){}

    public void insert(Item item){
        if(bag.isEmpty()){
            bag.add(new ArrayList<Item>());
            bag.get(0).add(item);
        }else{
            boolean itemFound = false;
            for (ArrayList<Item> array : bag) {
                if(array.get(0).type == item.type || array.isEmpty()){
                    array.add(item);
                    itemFound = true;
                    break;
                }
                }
            if(!itemFound){
                bag.add(new ArrayList<Item>());
                bag.get(bag.size() - 1).add(item);
            }

        }       
    }

    public void remove(Item item){
        if(!bag.isEmpty()){
            for (ArrayList<Item> array : bag) {
                if(array.get(0).type == item.type){
                    array.remove(item);
                    if(array.isEmpty()){
                        bag.remove(array);
                    }
                    break;
                }
                

            }
        }
    }

    //getters and setters
    public ArrayList<ArrayList<Item>> getBag() {
        return bag;
    }
}
