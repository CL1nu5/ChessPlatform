# Server Client Protocol
## Basic information
- Server and Client are both able to read and write
- The Server rests on the port: 4891

## Steps
### Step 1 (handshake):
- The transmitter sends ("ENQ", 0x05) that it wants to transmit information
- The receiver sends ("ACK", 0x06) to signal it is ready for receiving the information

### Step 2 (transmit):
- The transmitter sends the information starting in the following order:
  - ("SOH", 0x01)
  - Number of lines
  - ("STX", 0x02)
  - content (every line ends wit a line feed (LF, 0xA, '\n'))
  - ("ETX", 0x03)

- After receiving all the Data, the receiver has two options:
  - If everything was transmitted correctly it sends back ("ACK", 0x06)
  - If there were errors transmitting, sends ("NAK", 0x15) and repeats the process

- If the transfer is carried out incorrectly 3 times, the transfer is aborted and the sender throws an error

