# What is Symmetric Encryption?

Symmetric Encryption is the simplest and fastest form of cryptography.

`One single secret key is used for both encryption and decryption.`

```md
[legible data] --encrypt(key)--> Ciphertext

[Ciphertext] --decrypt(key)--> [legible data]
```

AES is a Block Cipher

> Block Cipher = Algorithm that encrypt/decrypt data in fixed-size chunks, using a symmetric key.

AES block size = 128 bits (16 bytes)
Meaning:

- AES always encrypts 16 bytes at a time
- If data is larger → split into many blocks
- If data is smaller → padding is added

## AES variants differ only by key size:

| AES Variant | Key Size | Block Size | Rounds    |
| ----------- | -------- | ---------- | --------- |
| AES-128     | 128 bits | 128 bits   | 10 rounds |
| AES-192     | 192 bits | 128 bits   | 12 rounds |
| AES-256     | 256 bits | 128 bits   | 14 rounds |

## AES Modes

AES Mode = rule to apply AES to real-world data
✔ We need modes because:

- Data > 16 bytes
- Pattern leakage happens
- No integrity
- No randomness
- Multi-block encryption required

❌ AES-256-ECB → insecure
✔ AES-256-CBC → secure for confidentiality
✔ AES-256-GCM → secure for confidentiality + integrity
✔ AES-256-CTR → secure for streams