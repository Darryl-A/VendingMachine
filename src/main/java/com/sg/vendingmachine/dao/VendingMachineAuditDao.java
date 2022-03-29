package com.sg.vendingmachine.dao;

/**
 *
 * @author darrylanthony
 */
public interface VendingMachineAuditDao {
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
}
