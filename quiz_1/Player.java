public class Player {
    private String name;
    private int score;
    private boolean disqualified;
    //constructor
    public Player(String name){
        this.name=name;
        this.score=0;
        this.disqualified=false;
    }
    public String getName(){
        return name;

    }
    public void setName(String name){
        this.name=name;
    }

    public boolean getDisqualified(){
        return this.disqualified;
    }
    public void setDisqualified(boolean disqualified){
        this.disqualified=disqualified;
    }
    public int getScore(){
        return score;

    }
    public void updateScore(int score){
        this.score+=score;

    }
    
}
