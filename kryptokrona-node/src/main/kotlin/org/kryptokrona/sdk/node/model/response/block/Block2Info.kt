// Copyright (c) 2022-2023, The Kryptokrona Developers
//
// Written by Marcus Cvjeticanin
//
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without modification, are
// permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice, this list of
//    conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright notice, this list
//    of conditions and the following disclaimer in the documentation and/or other
//    materials provided with the distribution.
//
// 3. Neither the name of the copyright holder nor the names of its contributors may be
//    used to endorse or promote products derived from this software without specific
//    prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
// EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
// THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
// PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
// STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
// THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package org.kryptokrona.sdk.node.model.response.block

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.kryptokrona.sdk.node.model.response.transaction.TransactionInfo

@Serializable
data class Block2Info(
    @SerialName("alreadyGeneratedCoins") val alreadyGeneratedCoins: String,
    @SerialName("alreadyGeneratedTransactions") val alreadyGeneratedTransactions: Int,
    @SerialName("baseReward") val baseReward: Long,
    @SerialName("blockSize") val blockSize: Int,
    val depth: Int,
    val difficulty: Int,
    @SerialName("effectiveSizeMedian") val effectiveSizeMedian: Int,
    val hash: String,
    val height: Int,
    @SerialName("major_version") val majorVersion: Int,
    @SerialName("minor_version") val minorVersion: Int,
    val nonce: Int,
    @SerialName("orphan_status") val orphanStatus: Boolean,
    val penalty: Double,
    @SerialName("prev_hash") val prevHash: String,
    val reward: Long,
    @SerialName("sizeMedian") val sizeMedian: Int,
    val timestamp: Int,
    @SerialName("totalFeeAmount") val totalFeeAmount: Long,
    val transactions: List<TransactionInfo>,
    @SerialName("transactionsCumulativeSize") val transactionsCumulativeSize: Int
)
