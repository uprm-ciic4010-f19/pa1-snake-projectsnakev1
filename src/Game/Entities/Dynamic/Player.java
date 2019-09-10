package Game.Entities.Dynamic;

import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import Game.GameStates.State;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

    public int lenght;
    public boolean justAte;
    private Handler handler;
    public double score;		// Public score variable
    public int xCoord;
    public int yCoord;
    public int speed = 7;	//Added public variable for speed
    public int moveCounter;

    public String direction;//is your first name one?

    public Player(Handler handler){
        this.handler = handler;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        direction= "Right";
        justAte = false;
        lenght= 1;

    }

    public void tick(){
        moveCounter++;
        if(moveCounter>=speed) {			// speed changed to 2 - Changed for a public variable with the speed
            checkCollisionAndMove();
            moveCounter=0;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
            direction="Up";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
            direction="Down";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
            direction="Left";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)){
            direction="Right";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
        	State.setState(handler.getGame().pauseState);						// escape button for pause state
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ADD)) {
        	//Added speed increase change
        	speed--;
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_SUBTRACT)) {
        	//Added speed decrease change
        	speed++;
        }
    }

    public void checkCollisionAndMove(){
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        int x = xCoord;
        int y = yCoord;
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
        	handler.getWorld().body.addLast(new Tail(x, y,handler));			// N key for adding piece of tail
        }
        switch (direction){
            case "Left":
                if(xCoord==0){
                    xCoord = handler.getWorld().GridWidthHeightPixelCount-1;
                }else{
                    xCoord--;
                }
                break;
            case "Right":
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    xCoord = 0;			// teleport
                }else{
                    xCoord++;
                }
                break;
            case "Up":
                if(yCoord==0){
                    yCoord = handler.getWorld().GridWidthHeightPixelCount-1;		//teleport
                }else{
                    yCoord--;
                }
                break;
            case "Down":
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    yCoord = 0;				// teleport
                }else{
                    yCoord++;
                }
                break;
        }
        handler.getWorld().playerLocation[xCoord][yCoord]=true;


        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            Eat();
        }

        if(!handler.getWorld().body.isEmpty()) {
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
        }

    }

    public void render(Graphics g,Boolean[][] playeLocation){
        Random r = new Random();
    	g.setColor(Color.BLACK);
    	g.setFont(new Font("TimesRoman", Font.BOLD, 50));
    	g.drawString("Score: " + String.valueOf(this.getScore()), 10, 750);
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                g.setColor(Color.GREEN); // green color for body

                if(playeLocation[i][j]||handler.getWorld().appleLocation[i][j]){
                    g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
                }

            }
        }
        if(handler.getWorld().player.isJustAte() == true) {
        	g.drawString("Score: " + String.valueOf(this.getScore()) , 400, 100);
        }


    }

    public void Eat(){
        lenght++;
        Tail tail= null;
        handler.getWorld().appleLocation[xCoord][yCoord]=false;
        handler.getWorld().appleOnBoard=false;
        switch (direction){
            case "Left":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                    	score += Math.sqrt(2*(score+1));
                    	speed -= (6 + 1); 
                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1);
                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1);
                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                    	score += Math.sqrt(2*(score+1));
                    	speed -= (6 + 1); 
                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1);
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                        }else{
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1); 
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                        }
                    }

                }
                break;
            case "Right":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=0){
                    	score += Math.sqrt(2*(score+1));
                    	speed -= (6 + 1); 
                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1);
                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1);
                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                    	score += Math.sqrt(2*(score+1));
                    	speed -= (6 + 1);
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1);
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                        }else{
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1);
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                        }
                    }

                }
                break;
            case "Up":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                    	score += Math.sqrt(2*(score+1));
                    	speed -= (6 + 1);
                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                    }else{
                        if(this.xCoord!=0){
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1);
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1);
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                    	score += Math.sqrt(2*(score+1));
                    	speed -= (6 + 1);
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1);
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1);
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
            case "Down":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=0){
                    	score += Math.sqrt(2*(score+1));
                    	speed -= (6 + 1);
                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                    }else{
                        if(this.xCoord!=0){
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1);
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1);
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        } System.out.println("Tu biscochito");
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                    	score += Math.sqrt(2*(score+1));
                    	speed -= (6 + 1);
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1); 
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                        	score += Math.sqrt(2*(score+1));
                        	speed -= (6 + 1); 
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
        }
        handler.getWorld().body.addLast(tail);
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
    }

    public void kill(){
        lenght = 0;
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                handler.getWorld().playerLocation[i][j]=false;
                State.setState(handler.getGame().overState);

            }
        }
    }

    public boolean isJustAte() {
        return justAte;
    }

    public void setJustAte(boolean justAte) {
        this.justAte = justAte;
    }
    
    public double getScore() {
		return score;
	}
    
    public void setCore(double score) {
    	this.score = score;
    }
}
