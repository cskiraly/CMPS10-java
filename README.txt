Copyright (c) 2012-2013 Csaba Kiraly http://csabakiraly.com

This is a java driver for the CMPS09/CMPS10 tilt compensated compass.
The driver was developed as part of the MoteHunter tool. For more
details, see http://csabakiraly.com/content/motehunter or the following
paper:

Kiraly, Csaba, and Gian Pietro Picco. "Where's the Mote? Ask the
MoteHunter!" In 7th IEEE International Workshop on Practical Issues in
Building Sensor Network Applications 2012 (SenseApp 2012), 986-994.
Clearwater, Florida, USA: IEEE, 2012. Abstract


Copyright
=========

UsbIssCmps09 is free software: you can redistribute it and/or modify it
under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or (at
your option) any later version.

UsbIssCmps09 is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero
General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with UsbIssCmps09.  If not, see <http://www.gnu.org/licenses/>.

Compass Setup
=============

The driver assumes that the CMPS09/CMPS10 compass is connected to an USB
port through the USB-ISS Multifunction USB Communications Module. It
uses I2C mode to query the compass module for yaw, pitch, and roll
measurements. Pins 1,2,3 and 6 should be connected, as shown in the
following figure:

                   1----------1
     USB           2----------2
PC ------- USB-ISS 3----------3 CMPS10
                   4-X      X-4
                   5-X      X-5
                   6----------6


Usage
=====

There are various serial drivers in Java. The driver currently uses the
one on TinyOS.

Setup
-----

The driver has to be initialized with the name of the port device it
attaches to:

  public UsbIssCmps09(String portName) throws IOException, UnsupportedCommOperationException;

On Linux, use dmesg command to find out the right port number. A typical
log line is like this:
[62722.299508] cdc_acm 6-2:1.0: ttyACM0: USB ACM device
This means that the device name is "/dev/ttyACM0".

Query interface
---------------

The driver continuously queries the device for orientation data and
stores the most up-to-date values internally. This way, it can provide
the following non-blocking interface:

  public int getYaw();
  public int getRoll();
  public int getPitch();

Testing
-------

The driver also includes some test code. It can be run as an application
as follows:

  java UsbIssCmps09 /dev/ttyACM0

References
==========

CMPS10 - Tilt Compensated Compass Module
http://www.robot-electronics.co.uk/htm/cmps10doc.htm

USB-ISS - Multifunction USB Communications Module
http://www.robot-electronics.co.uk/htm/usb_iss_tech.htm
