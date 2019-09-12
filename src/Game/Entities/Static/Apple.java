package Game.Entities.Static;

import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Apple {

    private Handler handler;
    public boolean goodApple;
    public int xCoord;
    public int yCoord;

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
    }
    public boolean isGood() { 
    	if(goodApple == true) {
    		return true; 
    	}else {
    		return false;
    	}
    }
    public void setGoodApple(boolean goodApple) {
    	this.goodApple = goodApple;
    }
    public boolean getGoodApple() {
    	return goodApple;
    }


}
