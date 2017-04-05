/*  This file is part of OneModel, a program to manage knowledge.
    Copyright in each year of 2017-2017 inclusive, Luke A. Call; all rights reserved.
    OneModel is free software, distributed under a license that includes honesty, the Golden Rule, guidelines around binary
    distribution, and the GNU Affero General Public License as published by the Free Software Foundation.
    See the file LICENSE for license version and details.
    OneModel is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License along with OneModel.  If not, see <http://www.gnu.org/licenses/>
*/
package org.onemodel.core.controllers;

import net.sf.expectit.*;
import static net.sf.expectit.matcher.Matchers.*;
import org.onemodel.core.OmException;
import org.onemodel.core.model.Database;
import org.onemodel.core.model.PostgreSQLDatabase;
import org.testng.annotations.*;
import java.lang.Exception;
import java.util.concurrent.TimeUnit;

@Test
public class EntityMenuIT {
  @BeforeClass
  protected void setUp() {
    // start w/ a very clean environment so can test that scenario also
    PostgreSQLDatabase db = new PostgreSQLDatabase(Database.TEST_USER(), Database.TEST_USER());
    db.destroyTables();
  }
  @AfterClass
  protected void tearDown() {
  }

  public void testOmUiEtc() throws Exception {
    String osName = System.getProperty("os.name");
    if (! osName.equalsIgnoreCase("linux")) {
      throw new OmException("This test isn't yet expected to work on anything but Linux (or maybe other unix), until the om-expect-tests " +
                              "script in the code is adapted to that, and also probably others.");
    }

    // Using expectit here to call dejagnu instead of doing everything directly with expectit, because expectit was less clear how to debug than with
    // dejagnu/expect (maybe just my unfamiliarity), and dejagnu etc seem very mature and documented, and have shorter turnaround time when making
    // mods and retesting from the command-line (no compile step).  To debug the "om-expect-tests" script, run it (maybe via the "oet"
    // convenience script), and see the logs it shows, or other
    // related documentation (such as mentioned in the core/testsuite/README file).
    // More info about "expectit" (being used below to run om-expect-tests which runs dejagnu which in turn calls the original "expect"), is at:
    //    https://github.com/Alexey1Gavrilov/expectit
    Process process = Runtime.getRuntime().exec("om-expect-tests");
    Result result = null;
    try(Expect expect = new ExpectBuilder()
      .withInputs(process.getInputStream())
      .withOutput(process.getOutputStream())
        // For some debugging, can change the the next line.  Details in first.exp under "Useful during testing". Or better yet, debug
        // by calling the om-expect-tests script directly.  Also, this test at this writing takes ~100 seconds on my laptop.
      .withTimeout(5, TimeUnit.MINUTES)
      .withAutoFlushEcho(true)
      .withExceptionOnFailure()
      .withAutoFlushEcho(true)
      .withEchoInput(System.out)
      .withEchoOutput(System.out)
      .build()
    ) {
/*    # Raise this # of expected passes as tests are added--the test output says what the # is if you
      # run "om-expect-tests" manually, to see the number.
      # The check is here because ^C (or other interruptions?) while "mvn verify" (or similar) was running.
      # caused runtest to exit but it returned a 0 (success) code to om-expect-tests, and so
      # a failure could be missed.  This makes sure it runs to completion.
      # (If you dont know what # to use, comment the line out, check the testrun.log that was just
      # updated, and update the #, then uncomment the line. Or just uncomment it, but don't commit that.) */
//      result = expect.expect(contains("# of expected passes.*474.*Return code from dejagnu tests: 0"));
      result = expect.expect(contains("# of expected passes.*474"));
//      result = expect.expect(contains("passes"));

      //%%del after commit?:
      // (This opens then closes the two "read" and "less" commands in the script, since they are
      // probably only needed in manual/interactive calls to the above "om-expect-tests" script:)
      // these also seem not needed (like the waitFor, below):
//      expect.sendLine();
//      expect.send("q");
//      Thread.sleep(250);
//      expect.sendLine();
//      expect.send("q");
      /*
       */
     /* more expectit example code, but see the file core/testsuite/README for better references:
      import static net.sf.expectit.matcher.Matchers.*;
      expect.sendLine("ls -lh");
      // capture the total
      String total = expect.expect(regexp("^total (.*)")).group(1);
      System.out.println("Size: " + total);
      // capture file list
      String list = expect.expect(regexp("\n$")).getBefore();
      // print the result
      System.out.println("List: " + list);
      expect.sendLine("exit");
      // expect the process to finish
      expect.expect(eof());    */

      // seems not needed, causes ide to pause too long under some conditions (like a long timeout for debugging, and an expect (match) failure):
      //process.waitFor();
    } catch (ExpectIOException e) {
      if (result == null) {
        throw e;
      } else {
        System.out.println("Didn't match with: " + result.getBefore());
      }
    }
  }
}