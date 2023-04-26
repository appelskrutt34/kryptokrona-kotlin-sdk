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

package org.kryptokrona.sdk.crypto

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString
import org.kryptokrona.sdk.crypto.model.Wallet
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64.Default.encodeToByteArray

private const val IV_SIZE = 12 // size of the initialization vector in bytes
private const val MIN_ENCRYPTED_FILE_SIZE = 16 // minimum size of the encrypted file in bytes
private const val TAG_LENGTH_BITS = 128 // length of the gcm authentication tag in bits

/**
 * This class is used to encrypt and decrypt wallet files.
 *
 * @author Marcus Cvjeticanin
 * @since 0.2.0
 * @param wallet The wallet file object to encrypt
 */
class WalletEncryption(private val wallet: Wallet? = null) {

    private val logger = LoggerFactory.getLogger("WalletEncryption")

    /**
     * Encrypt the wallet file object with the password using AES encryption.
     *
     * @author Marcus Cvjeticanin
     * @since 0.2.0
     * @param password The password to encrypt the wallet with
     */
    fun encryptToFile(fileName: String, password: String) {
        // generate a 256-bit AES key from the password
        val passwordBytes = password.toByteArray(Charsets.UTF_8)
        val passwordSpec = SecretKeySpec(passwordBytes, "AES")

        // create cipher object for encryption
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, passwordSpec)

        // get IV (initialization vector) from cipher object
        val iv = cipher.iv

        // serialize the wallet object to a byte array
        wallet.let {
            it?.let { walletFile ->
                val walletBytes = serialize(walletFile)

                // encrypt the wallet with the password using AES encryption
                val encryptedWallet = cipher.doFinal(walletBytes)

                // concatenate IV and encrypted wallet into a single byte array
                val encryptedBytes = ByteArray(iv.size + encryptedWallet.size)
                System.arraycopy(iv, 0, encryptedBytes, 0, iv.size)
                System.arraycopy(encryptedWallet, 0, encryptedBytes, iv.size, encryptedWallet.size)

                // write encrypted byte array to file
                val homeDir = System.getProperty("user.home")
                val file = File(homeDir, fileName)
                FileOutputStream(file).use { outputStream ->
                    outputStream.write(encryptedBytes)
                }
            }
        } ?: logger.error("Can not encrypt to file when wallet file object is null!")
    }

    /**
     * Load the wallet file object from a file.
     *
     * @author Marcus Cvjeticanin
     * @since 0.2.0
     * @param fileName The name of the wallet file
     * @param password The password to decrypt the wallet with
     * @return the deserialized wallet file object
     */
    fun loadWallet(fileName: String, password: String): Wallet {
        logger.debug("Loading wallet from file...")

        // load encrypted bytes from file
        val encryptedBytes = loadEncryptedBytesFromFile(fileName)

        // verify that the file is not too small
        require(encryptedBytes.size >= MIN_ENCRYPTED_FILE_SIZE) {
            "Encrypted file is too small to contain an IV and ciphertext"
        }

        // split encrypted bytes into IV and ciphertext
        val iv = encryptedBytes.sliceArray(0 until IV_SIZE)
        val ciphertext = encryptedBytes.sliceArray(IV_SIZE until encryptedBytes.size)

        // decrypt ciphertext with password and IV using AES encryption
        val decryptedBytes = decryptWallet(ciphertext, password, iv)

        // TODO deserialize decrypted bytes into WalletFile object
        // need to figure out the structure of the WalletFile first

        // add the data to the wallet file object
        // TODO: not done, should add more properties here

        return Wallet(
            publicSpendKey = "",
        )
    }

    /**
     * Deserialize the wallet file object from a byte array.
     *
     * @author Marcus Cvjeticanin
     * @since 0.2.0
     * @param fileName The name of the wallet file
     * @return the deserialized wallet file object in byte array
     */
    private fun loadEncryptedBytesFromFile(fileName: String): ByteArray {
        logger.debug("Loading encrypted bytes from file...")

        // read encrypted bytes from file
        val homeDir = System.getProperty("user.home")
        val file = File(homeDir, fileName)
        return file.readBytes()
    }

    /**
     * Decrypt the wallet file object with the password using AES encryption.
     *
     * @author Marcus Cvjeticanin
     * @since 0.2.0
     * @param encryptedBytes The encrypted wallet file object
     * @param password The password to decrypt the wallet with
     * @param iv The initialization vector
     * @return the decrypted wallet file object
     */
    private fun decryptWallet(encryptedBytes: ByteArray, password: String, iv: ByteArray): ByteArray {
        logger.debug("Decrypting wallet...")

        // generate a 256-bit AES key from the password
        val keySpec = SecretKeySpec(password.toByteArray(), "AES")

        // create cipher object for decryption
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val gcmSpec = GCMParameterSpec(TAG_LENGTH_BITS, iv)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec)

        // decrypt encrypted bytes using AES encryption
        return cipher.doFinal(encryptedBytes)
    }

    /**
     * Serialize the wallet file object to a byte array.
     *
     * @author Marcus Cvjeticanin
     * @since 0.2.0
     * @param wallet the wallet file object
     * @return the serialized wallet file object
     */
    private fun serialize(wallet: Wallet): ByteArray {
        val json = Json { encodeDefaults = true }
        return json.encodeToString(wallet).toByteArray()
    }
}