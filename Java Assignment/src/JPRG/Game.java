package JPRG;

import javax.swing.*;

public class Game {
    
   private String name, historyMessage;
   private int end, x, steps, y;
   private int countStart = 1;
  
   public void setEndGame(){
       x++;
   }
   
   public int getEndGame(){
       end = x;
       return end;
   }
   
   public void setCountSteps(){
       y++;
   }
   
   public int getSteps(){
       steps = y;
       return steps;
   }
   
   public int resetEndGame(){
       x = 0;
       return x;
   }
   
   public int resetSteps(){
       y = 0;
       return y;
   }
   
   public void setName(String name1){
       name = name1;
   }
   
   public String getName(){
       return name;
   }
   
   public int setSN(){
       countStart++;
       return countStart;
   }
   
   public int getSN(){
    return countStart;
}
   
   public String getHistoryMessage(String s){
      s = getSN() + "\t"+ getName()+ "\t\t" + getSteps()+"\n";
       historyMessage = s;
       return historyMessage;
   }

    }