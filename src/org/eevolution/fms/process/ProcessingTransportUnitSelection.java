/**
 * Copyright (C) 2003-2017, e-Evolution Consultants S.A. , http://www.e-evolution.com
 * This program is free software, you can redistribute it and/or modify it
 * under the terms version 2 of the GNU General Public License as published
 * or (at your option) any later version.
 * by the Free Software Foundation. This program is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along
 * with this program, if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 * For the text or an alternative of this public license, you may reach us
 * or via info@adempiere.net or http://www.adempiere.net/license.html
 * Email: victor.perez@e-evolution.com, http://www.e-evolution.com , http://github.com/e-Evolution
 * Created by victor.perez@e-evolution.com , www.e-evolution.com
 */

package org.eevolution.fms.process;

import org.compiere.model.PO;
import org.idempiere.tms.model.MDDDriver;
import org.idempiere.tms.model.MDDTransportUnit;

import java.util.List;
import java.util.Optional;

/**
 * Process for (Transport Unit Selection)
 * This process allows to Transport Unit update values
 * Copy Transport Unit
 *
 * @author victor.perez@e-evolution.com, http://www.e-evolution.com , http://github.com/e-Evolution
 * @version Release 3.9.0
 */
public class ProcessingTransportUnitSelection extends ProcessingTransportUnitSelectionAbstract {
    @Override
    protected void prepare() {
        super.prepare();
    }

    @Override
    protected String doIt() throws Exception {
        List<MDDTransportUnit> transportUnits = (List<MDDTransportUnit>) getInstancesForSelection(get_TrxName());
        if (getRecord_ID() > 0 && getSelectionKeys().size() > 0 && getTableSelectionId() == MDDTransportUnit.Table_ID) {
            if (getProcessInfo().getTable_ID() == MDDTransportUnit.Table_ID) {
                MDDTransportUnit transportUnitFrom = transportUnits.stream().findFirst().get();
                MDDTransportUnit transportUnitTo = (MDDTransportUnit) getInstance(get_TrxName());
                if (transportUnitFrom != null && transportUnitTo != null && transportUnitFrom.get_ID() != transportUnitTo.get_ID())
                    CopyTransportUnit(transportUnitFrom, transportUnitTo);
            }
        } else if (getRecord_ID() == 0 && getSelectionKeys().size() > 0) {
            UpdatingTransportUnit(transportUnits);
        }
        return "@Ok@";
    }

    /**
     * Update Values for Transport Unit
     *
     * @param transportUnits
     */
    private void UpdatingTransportUnit(List<MDDTransportUnit> transportUnits) {
        transportUnits.stream()
                .filter(transportUnit -> transportUnit != null)
                .forEach(transportUnit -> {
                    int columns = transportUnit.get_ColumnCount();
                    for (int index = 0; index < columns; index++) {
                        String columnName = transportUnit.get_ColumnName(index);
                        Optional.ofNullable(getSelection(transportUnit.get_ID(), getPrefixAliasForTableSelection() + columnName))
                                .ifPresent(value -> transportUnit.set_ValueOfColumn(columnName, value));
                    }
                    transportUnit.saveEx();
                });
    }

    /**
     * Copy Transport Unit
     *
     * @param transportUnitFrom
     * @param transportUnitTo
     */
    protected void CopyTransportUnit(MDDTransportUnit transportUnitFrom, MDDTransportUnit transportUnitTo) {
        PO.copyValues(transportUnitFrom, transportUnitTo);
        transportUnitTo.saveEx();
    }
    
  //maximea hard code<	
  	private MDDTransportUnit getInstance(String get_TrxName) {
  		// TODO Auto-generated method stub
  		return null;
  	}

  	private int getTableSelectionId() {
  		// TODO Auto-generated method stub
  		return 0;
  	}

  	private List<MDDDriver> getSelectionKeys() {
  		// TODO Auto-generated method stub
  		return null;
  	}

  	private List<MDDTransportUnit> getInstancesForSelection(String get_TrxName) {
  		// TODO Auto-generated method stub
  		return null;
  	}
  	private Object getSelection(int get_ID, String string) {
  		// TODO Auto-generated method stub
  		return null;
  	}

  	private String getPrefixAliasForTableSelection() {
  		// TODO Auto-generated method stub
  		return null;
  	}
  //maximea hard code<
}