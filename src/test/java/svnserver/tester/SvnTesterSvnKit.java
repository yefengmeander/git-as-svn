/*
 * This file is part of git-as-svn. It is subject to the license terms
 * in the LICENSE file found in the top-level directory of this distribution
 * and at http://www.gnu.org/licenses/gpl-2.0.html. No part of git-as-svn,
 * including this file, may be copied, modified, propagated, or distributed
 * except according to the terms contained in the LICENSE file.
 */
package svnserver.tester;

import org.jetbrains.annotations.NotNull;
import org.tmatesoft.svn.core.SVNErrorCode;
import org.tmatesoft.svn.core.SVNErrorMessage;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import svnserver.SvnTestServer;
import svnserver.TestHelper;

import java.io.IOException;
import java.nio.file.Path;

/**
 * SvnKit subversion implementation.
 *
 * @author Artem V. Navrotskiy <bozaro@users.noreply.github.com>
 */
public final class SvnTesterSvnKit implements SvnTester {
  @NotNull
  private static final String USER_NAME = "tester";
  @NotNull
  private static final String PASSWORD = "passw0rd";

  @NotNull
  private final Path repoDir;
  @NotNull
  private final SVNURL url;

  public SvnTesterSvnKit() throws SVNException {
    try {
      repoDir = TestHelper.createTempDir("git-as-svn");
      url = SVNRepositoryFactory.createLocalRepository(repoDir.toFile(), true, true);
    } catch (IOException e) {
      throw new SVNException(SVNErrorMessage.create(SVNErrorCode.IO_ERROR, e));
    }
  }

  @NotNull
  @Override
  public SVNURL getUrl() {
    return url;
  }

  @NotNull
  @Override
  public SVNRepository openSvnRepository() throws SVNException {
    return SvnTestServer.openSvnRepository(url, USER_NAME, PASSWORD);
  }

  @Override
  public void close() throws Exception {
    TestHelper.deleteDirectory(repoDir);
  }
}
