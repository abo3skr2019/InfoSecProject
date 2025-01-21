#!/bin/bash

# Configuration
password="password"
iter_count="1000"

echo "Begin Decryption"

# AES-128 Decryption
echo "AES 128 Decryption Started"

openssl enc -d -aes-128-cfb -in "cipher-Aes128-CFB.bin" -out "cipher-Aes128-CFB_decrypted.txt" -pass pass:"$password" -iter "$iter_count" -pbkdf2
cat "cipher-Aes128-CFB_decrypted.txt"
echo ""

openssl enc -d -aes-128-ofb -in "cipher-Aes128-OFB.bin" -out "cipher-Aes128-OFB_decrypted.txt" -pass pass:"$password" -iter "$iter_count" -pbkdf2
cat "cipher-Aes128-OFB_decrypted.txt"
echo ""

openssl enc -d -aes-128-cbc -in "cipher-Aes128-CBC.bin" -out "cipher-Aes128-CBC_decrypted.txt" -pass pass:"$password" -iter "$iter_count" -pbkdf2
cat "cipher-Aes128-CBC_decrypted.txt"
echo ""

echo "AES 128 Decryption Over"

# Blowfish Decryption
echo "BlowFish Decryption Started"

openssl enc -d -bf-cfb -provider legacy -provider default -in "cipher-BlowFish-CFB.bin" -out "cipher-BlowFish-CFB_decrypted.txt" -pass pass:"$password" -iter "$iter_count" -pbkdf2
cat "cipher-BlowFish-CFB_decrypted.txt"
openssl enc -d -bf-ofb -provider legacy -provider default -in "cipher-BlowFish-OFB.bin" -out "cipher-BlowFish-OFB_decrypted.txt" -pass pass:"$password" -iter "$iter_count" -pbkdf2
cat "cipher-BlowFish-OFB_decrypted.txt"
openssl enc -d -bf-cbc -provider legacy -provider default -in "cipher-BlowFish-CBC.bin" -out "cipher-BlowFish-CBC_decrypted.txt" -pass pass:"$password" -iter "$iter_count" -pbkdf2
cat "cipher-BlowFish-CBC_decrypted.txt"
echo ""
echo "BlowFish Decryption Over"