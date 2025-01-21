#!/bin/bash

# Configuration
password="password"

needs_legacy_provider() {
    local cipher=$1
    case $cipher in
        "bf-cfb"|"bf-ofb"|"bf-cbc")
            return 0 ;;
        *)
            return 1 ;;
    esac
}

decrypt_file() {
    local cipher=$1
    local input=$2
    local output="${input%.bin}_decrypted.txt"
    
    local provider_flag=""
    if needs_legacy_provider "$cipher"; then
        provider_flag="-provider legacy -provider default"
    fi
    
    if [ ! -f "$input" ]; then
        echo "Error: Input file $input not found"
        return 1
    fi
    
    if openssl enc -d -"$cipher" \
        $provider_flag \
        -in "$input" \
        -out "$output" \
        -pass pass:"$password" \
        -iter 1000 \
        -pbkdf2; then
        echo "Decrypted $input with $cipher"
        echo "Output saved to $output"
        echo "Content:"
        cat "$output"
        echo ""
    else
        echo "Decryption failed for $cipher"
        return 1
    fi
}

echo "Begin Decryption"

# AES Decryption
echo "AES 128 Decryption Started"

decrypt_file "aes-128-cfb" "cipher-Aes128-CFB.bin"
decrypt_file "aes-128-ofb" "cipher-Aes128-OFB.bin"
decrypt_file "aes-128-cbc" "cipher-Aes128-CBC.bin"

echo "AES 128 Decryption Over"

# Blowfish Decryption
echo "BlowFish Decryption Started"

decrypt_file "bf-cfb" "cipher-BlowFish-CFB.bin"
decrypt_file "bf-ofb" "cipher-BlowFish-OFB.bin"
decrypt_file "bf-cbc" "cipher-BlowFish-CBC.bin"

echo "BlowFish Decryption Over"