# Java-based encryption and decryption utility JAR using the Bouncy Castle

Need to use AES and RSA

## RsfileEncryptor.jar

### How to use RsfileEncryptor.jar
user can generate encrypted file via running cmd like
     java -jar RsfileEncryptor.jar "My Folder\file.txt"

then it will generate the encryptedfile with same file name with extension `.rslocked`

## RsfileDecryptor.jar

### How to use RsfileDecryptor.jar

user can decrypted file via running cmd like
     java -jar RsfileDecryptor.jar "My Folder\file.rslocked"

then it will generate the orignal file with the same file name as previous and saved with same previous extension

--------
KeyStore / Key Handling

Both `RsfileEncryptor.jar` and `RsfileDecryptor.jar` use the same common Java KeyStore.

### KeyStore details
- Type: PKCS12
- File Name: rskeystore.p12
- Provider: Bouncy Castle
- Path: where the JAR is executed or passed as an argument

### Encryption & Decryption Flow
- Encryptor JAR
  - Uses the Public Key from the keystore
  - Encrypts the AES key (RSA)
  - Encrypts file data (AES)

- Decryptor JAR
  - Uses the Private Key from the keystore
  - Decrypts the AES key (RSA)
  - Decrypts the file data (AES)


---
User Input variation:

Commands:
s: source file path (required)
d: destination folder path (optional, default: same as source path [not including file name])
k: keystore file path (optional)

```
// 1. 
 java -jar RsfileEncryptor.jar -s "My Folder\file.txt", "My Folder\file2.txt"

// 2.
 java -jar RsfileEncryptor.jar  -s   "My Folder\file.txt"

// 3.
 java -jar RsfileEncryptor.jar  -s   "My Folder\file.txt" -d "SomePath\"

 // 3.
 java -jar RsfileEncryptor.jar  -s   "My Folder\file.txt"    -d      "SomePath\"

 // 3.
 java -jar RsfileEncryptor.jar -s    "My Folder\file.txt" -d "SomePath\" -k "rskeystore.p12"

 // 4.
 java -jar RsfileEncryptor.jar   -s  "My Folder\file.txt" -d "SomePath\"     -k     "rskeystore.p12"

 ```