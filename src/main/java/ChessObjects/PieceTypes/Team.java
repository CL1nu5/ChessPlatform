package ChessObjects.PieceTypes;

import Support.StringEditor;

import java.util.HashMap;

public enum Team{
    White('w', 1, "white"), Black('b', -1, "black");

    public final char character;
    public final int value;
    public final String string;

    Team(char character, int value, String string) {
        this.character = character;
        this.value = value;
        this.string = string;
    }

    public boolean isInSameTeam(Team that) {
        return this == that;
    }

    public boolean isEnemy(Team that) {
        return this != that;
    }

    public Team getOpposite(){
        if (this.isInSameTeam(Team.White))
            return Team.Black;

        return Team.White;
    }

    public static Team getTeamViaJson(String json){
        int index = 0;
        char current;

        while (index < json.length() && (current = json.charAt(index)) != ']') {
            if (current == '{') {
                String teamJson = StringEditor.collectFromTill(++index, '}', json);
                HashMap<String, String> teamValues =
                        StringEditor.getValuesFromJson(teamJson, Integer.MAX_VALUE, Integer.MAX_VALUE);

                if (teamValues.get("colour").equals("white")){
                    return Team.White;
                }

                return Team.Black;
            }

            index++;
        }

        return null;
    }
}
