public class DiceGame {
    public static void main(String[] args) {
        try{
        String[] content = FileInput.readFile(args[0], false, false);
        String[] names=content[1].split(",");

        int playerCount=Integer.parseInt(content[0]);
        // Create an array to hold Player objects
        Player[] players = new Player[playerCount];
        //assing player objects to player array
        int i=0;
        for(String name:names){
            Player player=new Player(name);
            players[i]=player;
            i++; 
        }
        //below this comment is about to dice conditions
        int t=0;
        int removedPlayers=0;
        String[] outputContent = new String[content.length];
        for(int j=2;j<content.length;j++){
            if(t==playerCount){
                t=0;
            }
            if(players[t].getDisqualified()){
                t++;
                continue;
            }

            String[] diceValues = content[j].split("-");
            int dice1 = Integer.parseInt(diceValues[0]);
            int dice2 = Integer.parseInt(diceValues[1]);
        
            if((dice1==0 && dice2==0)){
                outputContent[j-2]=String.format("%s skipped the turn and %s’s score is %d.",players[t].getName(),players[t].getName(),players[t].getScore());
                t++;    
                continue;
            }
            if((dice1==1 && dice2==1)){
                
                //removed player string
                outputContent[j-2]=String.format("%s threw %s. Game over %s!",players[t].getName(),content[j],players[t].getName());
                removedPlayers++;
                players[t].setDisqualified(true);
                if(playerCount-removedPlayers==1){
                    
                    gameOver(args[1],players,outputContent);
                    System.out.println(outputContent[4]);
                    break;
                }
                t++;
                continue;
            }
            if((dice1==1 || dice2==1)&& !(dice1==1 && dice2==1)){
                //skip player string
                outputContent[j-2]=String.format("%s threw %s and %s’s score is %d.",players[t].getName(),content[j],players[t].getName(),players[t].getScore());
                t++;
                continue;
            }
            if(dice1>0 && dice2>0){
                //score update string
                players[t].updateScore((dice1+dice2));
                outputContent[j-2]=String.format("%s threw %s and %s’s score is %d.",players[t].getName(),content[j],players[t].getName(),players[t].getScore());
                t++;
                continue;
            }
        }}catch(Exception err){
            System.out.println(err);
        }
    
        
    }

    public static void gameOver(String PATH,Player[] players,String[]outputContent){
        
        for(Player b:players){
            if(b.getDisqualified()==false){
                outputContent[outputContent.length-2]=String.format("%s is the winner of the game with the score of %d. Congratulations %s!",b.getName(),b.getScore(),b.getName());
            }
        }
        FileOutput.writeToFile(PATH, outputContent, true,true);
    }
}
