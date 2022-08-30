package org.kryptokrona.sdk;

import inet.ipaddr.HostName;
import org.kryptokrona.sdk.daemon.DaemonImpl;
import org.kryptokrona.sdk.service.WalletService;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		var daemon = new DaemonImpl(new HostName("pool.gamersnest.org:11898"), false);

		var walletService = new WalletService(daemon);
		walletService.start();
		daemon.getGlobalIndexesForRange(1, 2);
		var wallet = walletService.createWallet();
		walletService.stop();
	}
}
