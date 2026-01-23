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

---

KeyStore / Key Handling

Both `RsfileEncryptor.jar` and `RsfileDecryptor.jar` use the same common Java KeyStore.

### KeyStore details

- Type: PKCS12
- File Name: rskeystore.p12
- Provider: Bouncy Castle
- Path: where the JAR is executed or passed as an argument (optional)

### Purpose

- Store RSA Public and Private Keys for encrypting and decrypting AES keys and `rskeystore.p12` shared between both JARs
- Public Key: Used by `RsfileEncryptor.jar` for encrypting the AES key
- Private Key: Used by `RsfileDecryptor.jar` for decrypting the AES key

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

## What's strategy:

### Encryption:

#### Case 1:

- Source: test_data/fold1/test1.txt, test_data/fold1/fold2/test1.txt
- Destination: NULL

Result:

- Encrypted files saved in the same folder as source files:
  - test_data/fold1/test1.txt.rslocked
  - test_data/fold1/fold2/test1.txt.rslocked

#### Case 2:
- Source: test_data/fold1/test1.txt, test_data/fold1/fold2/test1.txt
- Destination: test_data/encrypted_files/

Result:
- Encrypted files saved in the specified destination folder:
  - test_data/encrypted_files/test1.rslocked
  - test_data/encrypted_files/test1.txt.rslocked (overwrite behavior NOTE: no need to handle, user should manage)


### Decryption:
#### Case 1:
- Source: test_data/fold1/test1.txt.rslocked, test_data/fold1/fold2/test1.txt.rslocked
- Destination: NULL
Result:
- Decrypted files saved in the same folder as source files:
  - test_data/fold1/test1.txt
  - test_data/fold1/fold2/test1.txt
#### Case 2:
- Source: test_data/fold1/test1.txt.rslocked, test_data/fold1/fold2/test1.txt.rslocked
- Destination: test_data/decrypted_files/
Result:
- Decrypted files saved in the specified destination folder:
  - test_data/decrypted_files/test1.txt
  - test_data/decrypted_files/test1.txt (overwrite behavior NOTE: no need to handle, user should manage)