package soen6441.team13.wars;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import soen6441.team13.wars.controller.GameEditorViewControllerTest;
import soen6441.team13.wars.domain.ArmyTest;
import soen6441.team13.wars.domain.ContinentTests;
import soen6441.team13.wars.domain.EdgeTest;
import soen6441.team13.wars.domain.PlayerTest;
import soen6441.team13.wars.domain.StateTest;
import soen6441.team13.wars.engine.BattleStageTest;
import soen6441.team13.wars.engine.ProductionStageTest;
import soen6441.team13.wars.engine.ReconStageTest;
import soen6441.team13.wars.engine.WinnerStageTest;
import soen6441.team13.wars.engine.movement.MovementStageTests;
import soen6441.team13.wars.factory.ArtilleryUnitTest;
import soen6441.team13.wars.factory.CavalryUnitTest;
import soen6441.team13.wars.factory.InfantryUnitTest;
import soen6441.team13.wars.factory.UnitFactoryTests;
import soen6441.team13.wars.logger.ActionLoggerTest;
import soen6441.team13.wars.persistence.GameWorldPersistenceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	GameEditorViewControllerTest.class,
	ArmyTest.class,
	ContinentTests.class,
	EdgeTest.class,
	PlayerTest.class,
	StateTest.class,
	MovementStageTests.class,
	BattleStageTest.class,
	ProductionStageTest.class,
	ReconStageTest.class,
	WinnerStageTest.class,
	ArtilleryUnitTest.class,
	CavalryUnitTest.class,
	InfantryUnitTest.class,
	UnitFactoryTests.class,
	ActionLoggerTest.class,
	GameWorldPersistenceTest.class
})
public class AllTests {
}
