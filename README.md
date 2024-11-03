# Cryptography Application

A Java-based desktop application that implements various cryptographic methods including Caesar Cipher, Vigenere Cipher, Block ECB, RC4, and Super Encryption. Built with JavaFX for a user-friendly graphical interface.

## Features

- Multiple encryption methods:
  - Caesar Cipher
  - Vigenere Cipher
  - Block ECB (Electronic Code Book)
  - RC4 Stream Cipher
  - Super Encryption
- Real-time encryption and decryption
- Input validation for different encryption methods
- History tracking of encryption/decryption operations
- Clean and intuitive user interface

## Requirements

- Java Development Kit (JDK) 8 or higher
- JavaFX
- Lombok

## Installation

1. Clone the repository:
```bash
git clone https://github.com/Zachry2906/cryptography-text-javafx.git
```

2. Navigate to the project directory:
```bash
cd cryptography-app
```

3. Build the project using your preferred IDE or Maven:
```bash
mvn clean install
```

## Usage

### Input Requirements

- **Vigenere Cipher, Block ECB, and RC4**:
  - Input text must contain only letters and spaces
  - For Block ECB, key length must be 16, 24, or 32 characters

- **Caesar Cipher and Super Encryption**:
  - Key must be numeric
  - Input text must contain only letters and spaces

### How to Use

1. Select the encryption method by clicking on the corresponding button
2. Enter your key according to the method requirements
3. Input either plaintext or ciphertext
4. Click "Save" to process the encryption/decryption
5. View results in the designated fields
6. Use "New Item" to clear all fields for a new operation

## Project Structure

```
id.dojo.kriptografi/
├── HelloApplication.java    # Main application class
├── HelloController.java     # Main controller for the UI
├── ItemController.java      # Controller for history items
├── TextModel.java          # Data model for encryption/decryption
└── method/
    └── Method.java         # Implementation of cryptographic methods
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Built using JavaFX for the graphical user interface
- Implements standard cryptographic algorithms
- Uses Lombok for reducing boilerplate code
