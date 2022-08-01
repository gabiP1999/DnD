package com.Games;

public class Resource {
    private int currAmount;
    private int resourcePool;
    public Resource(int curr,int pool){
        if(curr<0 | pool<0)throw new IllegalArgumentException();
        currAmount=curr;
        resourcePool=pool;
    }
    public void increaseResourcePool(int amount){
        amountCheck(amount);
        resourcePool+=amount;
    }
    public void increase(int amount){
        amountCheck(amount);
        int res = currAmount+amount;
        if(res>resourcePool)
            currAmount=resourcePool;
        else
            currAmount=res;
    }
    public void decrease(int amount){
        amountCheck(amount);
        int res = currAmount-amount;
        if(res<0)
            currAmount=0;
        else
            currAmount=res;

    }
    private void amountCheck(int amount){
        if(amount<0)throw new IllegalArgumentException("Amount can't be negative!");
    }
    public int getCurrAmount(){
        return currAmount;
    }
    public int getResourcePool(){
        return resourcePool;
    }
    public String toString(){
        return currAmount+"/"+resourcePool;
    }
}
