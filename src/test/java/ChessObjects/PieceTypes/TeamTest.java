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
    public void testTeamsOpposite1(){
        Team team1 = Team.White, team2 = Team.Black;
        assertFalse(team1.isInSameTeam(team2));
    }

    public void testTeamsOpposite2(){
        Team team1 = Team.White, team2 = Team.Black;
        assertTrue(team1.isEnemy(team2));
    }

    //Testing if the teams are considered Equal
    public void testTeamsEqual(){
        Team team1 = Team.White;
        assertTrue(team1.isInSameTeam(Team.White));
    }
}
