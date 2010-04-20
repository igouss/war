package soen6441.team13.wars.engine;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
  ProductionStageTest.class, 
  BattleStageTest.class, 
  ReconStageTest.class,
  WinnerStageTest.class
})

public class EngineTestSuite {
    // the class remains completely empty,
    // being used only as a holder for the above annotations
}
