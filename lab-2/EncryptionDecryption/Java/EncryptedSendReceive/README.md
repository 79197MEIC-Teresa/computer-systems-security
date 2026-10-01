
Here you have an implementation to send/receive encrypted messages

See
SendEncrypt
-----------
Code for a client sending an encrypted message to th server

ReceiveDecrypt:
--------------
Code for a server receiving the encrypted message that will be decryptd

Notes:

Client (SendEcrypt) and server (ReceiveDecrypt) use locally "keyrings", that share the same generated key to be used (to support the encryption and decryptio using. SendEncrypt and ReceiveDecrypt use (in common) a class named
KeyRing to manipulate the keyring stored keys (in keyring file)

For setup you need to generate initially the keys for keyring and then
you can copy the geerated keyring to be used by the client and server
(e.g., a very simple form of ... "key distribution" ;-)

You must note that in the implementation, client sends ONE MESSAGE each time to the server, and the message is sent/received in TCP (using TCP Sockets).
Moreover, ... defaut port for the server is 5999 as you will see, but you
can express the port as one input argument

To start the server (in ReceiveDecrypt dir):

java ReceiveDecrypt           // Runs in defaut port 5999
java ReceiveDecrypt <port>    // Runs in the provided port

To use the client (in SendEncrypt dir):

java SendEncrypt <hostname> <port>

hostname: the hostname (machine) where the server runs
port: the port where the server is








