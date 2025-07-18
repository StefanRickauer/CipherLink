# CipherLink
Java demo for secure end-to-end encryption using ECDH and AES-GCM

🔐 End-to-End Encryption Demo in Java

This is a simple proof-of-concept project demonstrating end-to-end encryption (E2EE) using modern cryptographic standards in Java.

It includes:
- Secure key exchange using **Elliptic Curve Diffie-Hellman (ECDH)** with the `secp256r1` curve
- Symmetric encryption of messages using **AES in GCM mode (AES-GCM)** for confidentiality and integrity
- Key derivation using **SHA-256** to normalize ECDH shared secrets

🔧 Technologies:
- Java 21 (Amazon Coretto)
- [Bouncy Castle](https://www.bouncycastle.org/) cryptographic provider (`bcprov-jdk18on`, version 1.81)
- No external libraries beyond Bouncy Castle

🎯 Purpose:
This project is intended as a learning tool or starting point for implementing E2EE communication between two parties in Java. It demonstrates the cryptographic primitives used in protocols like TLS and Signal.

📁 Structure:
- `ECDHKeyExchange.java`: Key pair generation and secure shared secret derivation
- `SecureCommunication.java`: Authenticated encryption/decryption using AES-GCM
- `Main.java`: Integration example simulating two peers (Alice & Bob)

⚠️ Note:
This is **not** production-ready. It lacks identity verification, session management, and full protocol design. Intended for educational or experimental use only.
