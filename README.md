# Canvas Collab Project
Laurel Anderson, Alexis Brown, Johnathan Chan, Daniel Rehman
CS 455, Spring 2022, WSUV

App description:
Drawing app where multiple clients can connect to a server
and draw together and see everyone else's drawing in real-time.
Typical main cycle of app:
1. Client connects to the server
2. User touches and drags on screen
3. Client asynchronously sends packets to server over TCP
4. Server receives packets, and relays them to all other clients
5. Client asynchronously receives packets from server
6. Client updates the canvas according to packet's information

Code organization:
Server.java is the back-end that is run in IntelliJ with a Networking dependency
Drawing app is run in Android Studio and consists of the following java classes:
MainActivity, DrawingView, and TouchListener

Software requirements:
At least 2 computers
Clients will need to run the app in Android Studio
Server.java will need to run in separate IDE (recommend using IntelliJ)
Both will need dependencies setup to reference the Packet class
Install requirements:
	SDK packages
	2 x Pixel 2 API 32

Required settings:
After downloading repo, Android Studio may change the SDK file location.
To check manually, go to File > Project Structure > SDK Location and then
make sure that the path is where the SDK file is located.
Next, open IntelliJ then File > New > Project from Existing Source...
and locate the server file: C:.\Desktop\Collab-canvas-app\app\server
Click through and accept all the windows that pop-up.
To link the networking dependency, click File > Project Structure > Modules
then click the "+" at the top then "Import Module" then locate the networking folder:
C:.\Desktop\Collab-canvas-app\app\networking
To link it, select the server, then click on the "Dependencies" tab,
then click "+" below the tabs, select "Module Dependency" then select the
networking folder and click "OK".
Once it populates, make sure the "Export" checkbox next to it is checked and click "OK".

How to run the project:
One computer can run the Server and run the app in an emulator.
The second computer will require changing the socket to the ip of the first computer.
On computer 1, open command line and enter ipconfig to get the IPv4 address.
Enter this address into the second computer's MainActivity.java class on line 74.
Now start the Server, start both emulators and have fun drawing!

Workload distribution:
Laurel Anderson: Created base app with basic 1 color drawing, assisted in networking, testing, and project setup
Alexis Brown: Packet class, code commenting/cleaning, paired programming, testing, research, debugging, and
	added color buttons using Android Studio Builder
Johnathan Chan: README, research, debugging, paired programming, and testing
Daniel Rehman: Created networking classes, main contributor to networking, testing, and project setup
NOTE: all teammates attended and contributed to paired programming sessions!

Copyright (c) <2022> <Laurel Anderson, Alexis Brown, Johnathan Chan, Daniel Rehman>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
