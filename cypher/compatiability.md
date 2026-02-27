# COMPATIBILITY

## MessageDigest (Hash)

- Safe:: SHA-256, SHA-384, SHA-512
- Unsafe:: MD5, SHA-1

## Cipher (Symmetric)

- Safe:: AES (Mode: GCM, CBC, CTR)
- Unsafe:: DES, 3DES, RC4

### Modern & Secure

| Algorithm                | Mode       | Use Case               | Notes        |
| ------------------------ | ---------- | ---------------------- | ------------ |
| **AES/GCM/NoPadding**    | AEAD       | API data, JSON, tokens | Best choice  |
| **AES/CBC/PKCS5Padding** | encryption | Legacy systems         | Use HMAC too |
| **AES/CTR/NoPadding**    | streaming  | real-time data         | Needs a MAC  |

## Cipher (Asymmetric)

- Safe:: RSA-OAEP
- Unsafe(Legacy):: RSA PKCS#1 v1.5, ECIES(not recommended)

## Signature

- Safe:: RSA-PSS, ECDSA, Ed25519(Best)
- Unsafe:: SHA1withRSA

## HMAC

NOTE: .... =>1, 224, 256, 382, 512;

- Safe:: HmacSHA..., HMACPBESHA..., PBEWITHHMACSHA...,
- Safe:: HmacPBESHA512/224, HmacPBESHA512/256
- Safe:: PBEwithHMACSHA512/224, PBEwithHMACSHA512/256
- Safe:: HmacSHA512/224, HmacSHA512/256
- Unsafe:: HmacMD5, SSLMACSHA1, SSLMACMD5

## Key Derivation

- PBKDF2WithHmacSHA256
- scrypt
- Argon2

## KeyAgreement

- ECDH
- X25519
