#!/bin/bash
picture="PROJECTWALLPAPER.bmp"

# Encryption
openssl enc -aes-128-cbc -in $picture -out pic_cbc.bmp -pass pass:"password" -pbkdf2

openssl enc -aes-128-ecb -in $picture -out pic_ecb.bmp -pass pass:"password" -pbkdf2

# Valid image header
head -c 54 $picture > imgheader

# removed invalid Image header
tail -c +55 pic_ecb.bmp > ecbbody

tail -c +55 pic_cbc.bmp > cbcbody

# Appended the encrypted image data to the valid header
cat imgheader ecbbody > pic_ecb.bmp

cat imgheader cbcbody > pic_cbc.bmp
# Cleaning 
rm imgheader 

rm ecbbody

rm cbcbody
