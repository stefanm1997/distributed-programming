package org.unibl.etf.mdp.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface RMIInterface extends Remote {

	Set<String> getAllFiles(String root, String username) throws RemoteException;

	byte[] getFile(String root, String username, String fileName) throws RemoteException;

	boolean sendFile(String root, String toUser, String fileName, byte[] content) throws RemoteException;
}
