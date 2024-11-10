package ChessObjects.PieceTypes;

public enum Team {
    White('w',1), Black('b',-1);

    public final char character;
    public final int value;

    Team(char character, int value){
        this.character = character;
        this.value = value;
    }

    public boolean isEqual(Team that){
        return this == that;
    }

    public boolean isEnemy(Team that){
        return this != that;
    }
}
