package ChessObjects.PieceTypes;

import Support.FileEditor;
import Support.StringEditor;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.util.ArrayList;


public class TeamTest extends TestCase {

    public TeamTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TeamTest.class);
    }

    //Testing if the teams are considered opposite
    public void testTeamsOpposite1() {
        Team team1 = Team.White, team2 = Team.Black;
        assertFalse(team1.isInSameTeam(team2));
    }

    public void testTeamsOpposite2() {
        Team team1 = Team.White, team2 = Team.Black;
        assertTrue(team1.isEnemy(team2));
    }

    //Testing if the teams are considered Equal
    public void testTeamsEqual() {
        Team team1 = Team.White;
        assertTrue(team1.isInSameTeam(Team.White));
    }

    //tests if the to string display is correct
    public void testToString(){
        assertEquals("White", Team.White.toString());
        assertEquals("Black", Team.Black.toString());
    }

    //tests if it is possible to get the team from a correct json
    public void testGettingTeamWhiteFromJson(){
        //setup
        FileEditor fileEditor = new FileEditor();
        ArrayList<String> content = fileEditor.read(new File("save/test/testTeamWhite.json"));
        String json = StringEditor.turnJsonListIntoString(content);

        //test
        assertTrue(Team.White.isInSameTeam(Team.getTeamViaJson(json)));
    }

    //tests if it is possible to get the team from a correct json
    public void testGettingTeamBlackFromJson(){
        //setup
        FileEditor fileEditor = new FileEditor();
        ArrayList<String> content = fileEditor.read(new File("save/test/testTeamBlack.json"));
        String json = StringEditor.turnJsonListIntoString(content);

        //test
        assertTrue(Team.Black.isInSameTeam(Team.getTeamViaJson(json)));
    }
}
