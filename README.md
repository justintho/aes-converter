# AES Converter

Overview: This project is a simple AES text converter. If given the path of a plain text file, the program will automatically read it and generate a random 256 bit key. It will then encrypt and decrypt the message with that key and store the output in files called "ciphertext.txt" and "decryptedtext.txt".

```
Please enter the path to the file you wish to encrypt/decrypt: C:\Users\justi\eclipse-workspace\CS4600HW2\src\plaintext.txt
Reading file for plaintext...

Plaintext: If you can read this properly, then the process was a success!

Start generating AES key...
Finished generating random key!
Key: s7wE0gYIZwwCEQyU/YUvN0LhLkcKV5lguSp8Anug1eU=

Starting encryption process...
Writing to ciphertext.txt...
Encryption complete!

Reading ciphertext.txt...
File read successfully!

Starting decryption process...
Writing to decryptedtext.txt...
Decryption complete!

