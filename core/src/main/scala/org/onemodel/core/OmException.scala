/*  This file is part of OneModel, a program to manage knowledge.
    Copyright in each year of 2014, 2016-2016 inclusive, Luke A Call; all rights reserved.
    OneModel is free software, distributed under a license that includes honesty, the Golden Rule, guidelines around binary
    distribution, and the GNU Affero General Public License as published by the Free Software Foundation, either version 3
    of the License, or (at your option) any later version.  See the file LICENSE for details.
    OneModel is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License along with OneModel.  If not, see <http://www.gnu.org/licenses/>
*/
package org.onemodel.core

class OmException(msg:String) extends Exception(msg) {
  def this(msg: String, t:Throwable)  {
    this(msg)
    this.initCause(t)
  }
}

class OmFileTransferException(msg:String) extends OmException(msg) {
  def this(msg: String, t:Throwable)  {
    this(msg)
    this.initCause(t)
  }
}

class OmDatabaseException(msg:String) extends OmException(msg) {
  def this(msg: String, t:Throwable)  {
    this(msg)
    this.initCause(t)
  }
}
