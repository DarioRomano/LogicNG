///////////////////////////////////////////////////////////////////////////
//                   __                _      _   ________               //
//                  / /   ____  ____ _(_)____/ | / / ____/               //
//                 / /   / __ \/ __ `/ / ___/  |/ / / __                 //
//                / /___/ /_/ / /_/ / / /__/ /|  / /_/ /                 //
//               /_____/\____/\__, /_/\___/_/ |_/\____/                  //
//                           /____/                                      //
//                                                                       //
//               The Next Generation Logic Library                       //
//                                                                       //
///////////////////////////////////////////////////////////////////////////
//                                                                       //
//  Copyright 2015 Christoph Zengler                                     //
//                                                                       //
//  Licensed under the Apache License, Version 2.0 (the "License");      //
//  you may not use this file except in compliance with the License.     //
//  You may obtain a copy of the License at                              //
//                                                                       //
//  http://www.apache.org/licenses/LICENSE-2.0                           //
//                                                                       //
//  Unless required by applicable law or agreed to in writing, software  //
//  distributed under the License is distributed on an "AS IS" BASIS,    //
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or      //
//  implied.  See the License for the specific language governing        //
//  permissions and limitations under the License.                       //
//                                                                       //
///////////////////////////////////////////////////////////////////////////

package org.logicng.solvers.sat;

import org.junit.Assert;
import org.junit.Test;
import org.logicng.datastructures.Tristate;
import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parser.ParserException;
import org.logicng.io.parser.PropositionalParser;
import org.logicng.solvers.MiniSat;
import org.logicng.solvers.SATSolver;

import static org.logicng.datastructures.Tristate.*;
import static org.logicng.datastructures.Tristate.TRUE;

/**
 * Unit tests for the assume functionality of the MiniSat style SAT solvers.
 * @author Christoph Zengler
 * @version 1.0
 * @since 1.0
 */
public class AssumeTest {

  private final FormulaFactory f;
  private final SATSolver[] solvers;
  final PropositionalParser parser;

  public AssumeTest() {
    this.f = new FormulaFactory();
    this.parser = new PropositionalParser(f);
    this.solvers = new SATSolver[6];
    this.solvers[0] = MiniSat.miniSat(f, new MiniSatConfig.Builder().incremental(true).build());
    this.solvers[1] = MiniSat.miniSat(f, new MiniSatConfig.Builder().incremental(false).build());
    this.solvers[2] = MiniSat.glucose(f, new MiniSatConfig.Builder().incremental(true).build(),
            new GlucoseConfig.Builder().build());
    this.solvers[3] = MiniSat.glucose(f, new MiniSatConfig.Builder().incremental(false).build(),
            new GlucoseConfig.Builder().build());
    this.solvers[4] = MiniSat.miniCard(f, new MiniSatConfig.Builder().incremental(true).build());
    this.solvers[5] = MiniSat.miniCard(f, new MiniSatConfig.Builder().incremental(false).build());
  }

  @Test
  public void testAssume() throws ParserException {
    for (final SATSolver s : this.solvers) {
      s.add(parser.parse("~a"));
      s.add(parser.parse("b"));
      s.add(parser.parse("b => c"));
      s.add(parser.parse("c => d"));
      s.add(parser.parse("d => e"));
      s.add(parser.parse("e => f"));
      Assert.assertEquals(TRUE, s.sat(f.literal("a", false)));
      Assert.assertEquals(TRUE, s.sat(f.literal("b")));
      Assert.assertEquals(TRUE, s.sat(f.literal("c")));
      Assert.assertEquals(TRUE, s.sat(f.literal("d")));
      Assert.assertEquals(TRUE, s.sat(f.literal("e")));
      Assert.assertEquals(TRUE, s.sat(f.literal("f")));
      Assert.assertEquals(FALSE, s.sat(f.literal("a")));
      Assert.assertEquals(FALSE, s.sat(f.literal("b", false)));
      Assert.assertEquals(FALSE, s.sat(f.literal("c", false)));
      Assert.assertEquals(FALSE, s.sat(f.literal("d", false)));
      Assert.assertEquals(FALSE, s.sat(f.literal("e", false)));
      Assert.assertEquals(FALSE, s.sat(f.literal("f", false)));
      s.reset();
    }
  }
}
