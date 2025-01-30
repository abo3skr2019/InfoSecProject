#!/bin/bash

# Configuration
password="password"
input_file="plaintext.txt"

# Check input file
if [ ! -f "$input_file" ]; then
    echo "Error: Input file $input_file not found"
    exit 1
fi

echo "Begin Encryption"

# AES-128 Encryption
echo "AES 128 Encryption Started"

openssl enc -aes-128-cfb -in "$input_file" -out "cipher-Aes128-CFB.bin" -pass pass:"$password" -pbkdf2
cat "cipher-Aes128-CFB.bin"
echo ""
openssl enc -aes-128-ofb -in "$input_file" -out "cipher-Aes128-OFB.bin" -pass pass:"$password" -pbkdf2
cat "cipher-Aes128-OFB.bin"
echo ""
openssl enc -aes-128-cbc -in "$input_file" -out "cipher-Aes128-CBC.bin" -pass pass:"$password" -pbkdf2
cat "cipher-Aes128-CBC.bin"
echo ""
echo "AES 128 Encryption Over"

# Blowfish Encryption
echo "BlowFish Encryption Started"

openssl enc -bf-cfb -provider legacy -provider default -in "$input_file" -out "cipher-BlowFish-CFB.bin" -pass pass:"$password" -pbkdf2
cat "cipher-BlowFish-CFB.bin"
echo ""

openssl enc -bf-ofb -provider legacy -provider default -in "$input_file" -out "cipher-BlowFish-OFB.bin" -pass pass:"$password" -pbkdf2
cat "cipher-BlowFish-OFB.bin"
echo ""
openssl enc -bf-cbc -provider legacy -provider default -in "$input_file" -out "cipher-BlowFish-CBC.bin" -pass pass:"$password" -pbkdf2
cat "cipher-BlowFish-CBC.bin"
echo ""
echo "BlowFish Encryption Over"