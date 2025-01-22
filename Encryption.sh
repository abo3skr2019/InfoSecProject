#!/bin/bash

# Configuration
password="password"
input_file="plaintext.txt"


if [ ! -f "$input_file" ]; then
    echo "Error: Input file $input_file not found"
    exit 1
fi

needs_legacy_provider() {
    local cipher=$1
    case $cipher in
        "bf-cfb"|"bf-ofb"|"bf-cbc")
            return 0 ;;
        *)
            return 1 ;;
    esac
}

encrypt_file() {
    local cipher=$1
    local output=$2
    
    local provider_flag=""
    if needs_legacy_provider "$cipher"; then
        provider_flag="-provider legacy -provider default"
    fi
    
    if openssl enc -"$cipher" \
        $provider_flag \
        -in "$input_file" \
        -out "$output" \
        -pass pass:"$password" \
        -pbkdf2; then
        echo "Encrypted with $cipher"
        cat "$output"
        echo ""
    else
        echo "Encryption failed for $cipher"
        return 1
    fi
}


echo "Begin Encryption"

# AES Encryption
echo "AES 128 Encryption Started"

encrypt_file "aes-128-cfb" "cipher-Aes128-CFB.bin"
encrypt_file "aes-128-ofb" "cipher-Aes128-OFB.bin"
encrypt_file "aes-128-cbc" "cipher-Aes128-CBC.bin"

echo "AES 128 Encryption Over"

# Blowfish Encryption
echo "BlowFish Encryption Started"

encrypt_file "bf-cfb" "cipher-BlowFish-CFB.bin"
encrypt_file "bf-ofb" "cipher-BlowFish-OFB.bin"
encrypt_file "bf-cbc" "cipher-BlowFish-CBC.bin"

echo "BlowFish Encryption Over"