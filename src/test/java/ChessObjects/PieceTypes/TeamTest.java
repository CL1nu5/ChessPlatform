package ChessObjects.PieceTypes;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class TeamTest extends TestCase{

    public TeamTest(String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(TeamTest.class);
    }

    //Testing if the teams are considered opposite
    public void testTeamsOpposite(){
        Team team1 = Team.White, team2 = Team.Black;
        assertFalse(team1.equals(team2));
    }

    //Testing if the teams are considered Equal
    public void testTeamsEqual(){
        Team team1 = Team.White;
        assertTrue(team1.equals(Team.White));
    }
}
