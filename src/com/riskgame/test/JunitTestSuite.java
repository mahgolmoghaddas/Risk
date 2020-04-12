package com.riskgame.test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 * This is a test suite class for running all the tests in the game.
 * 
 * @author Jasmeet
 * @version 1.0
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({
	CardTypeTest.class,
	ContinentTest.class,
	GameControllerTest.class,
	GameTest.class,
	MapReaderTest.class,
	PlayerDiceNumberComparatorTest.class,
	TurnManagerTest.class,
	ViewUtilityTest.class,
	ScoreConfigTest.class,
	GameUtilityTest.class
})

public class JunitTestSuite {   
}  	