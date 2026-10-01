from cryptography.fernet import Fernet

def generate_key(key_file="secret.key"):
    """Generate a key and save it into a file."""
    key = Fernet.generate_key()
    with open(key_file, "wb") as f:
        f.write(key)
    print(f"[+] Key saved to {key_file}")
    return key

def load_key(key_file="secret.key"):
    """Load the previously generated key."""
    with open(key_file, "rb") as f:
        return f.read()

def encrypt_file(input_file, output_file, key):
    """Encrypt a file and write it to a new file."""
    fernet = Fernet(key)
    with open(input_file, "rb") as f:
        data = f.read()

    encrypted = fernet.encrypt(data)

    with open(output_file, "wb") as f:
        f.write(encrypted)

    print(f"[+] Encrypted '{input_file}' → '{output_file}'")

def decrypt_file(input_file, output_file, key):
    """Decrypt an encrypted file and write the original data back."""
    fernet = Fernet(key)
    with open(input_file, "rb") as f:
        encrypted = f.read()

    decrypted = fernet.decrypt(encrypted)

    with open(output_file, "wb") as f:
        f.write(decrypted)

    print(f"[+] Decrypted '{input_file}' → '{output_file}'")

if __name__ == "__main__":
    # 1. Generate a key (run once and reuse secret.key)
    key = generate_key()

    # 2. Load the key (in practice, you would just load it)
    # key = load_key()

    # 3. Encrypt and decrypt example
    encrypt_file("secret.txt", "secret.enc", key)
    decrypt_file("secret.enc", "secret_decrypted.txt", key)
    
