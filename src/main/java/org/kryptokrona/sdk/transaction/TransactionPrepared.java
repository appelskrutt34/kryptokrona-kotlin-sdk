package org.kryptokrona.sdk.transaction;

import org.kryptokrona.sdk.exception.model.util.TxInputAndOwner;

import java.util.List;

public class TransactionPrepared {

	private double fee;

	private String paymentID;

	private List<TxInputAndOwner> inputs;

	private String changeAddress;

	private int changeRequired;

	private TransactionRaw createdTransaction; // this is in turtlecoin-utils library (we need to include the code from there into here)
}
