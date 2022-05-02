# Canvas Collab Project
Laurel Anderson, Alexis Brown, Johnathan Chan, Daniel Rehman\
CS 455, Spring 2022, WSUV

## App description:
Drawing app where multiple clients connect to a server, draw together, and see everyone else's drawing in real-time

#### Typical main cycle of app:
1. Client connects to the server
2. User touches and drags on screen
3. Client asynchronously sends packets to server over TCP
4. Server receives packets, and relays them to all other clients
5. Client asynchronously receives packets from server
6. Client updates the canvas according to packet's information

#### Code organization:
Server.java is the back-end that is run in IntelliJ with a Networking dependency\
Drawing app is run in Android Studio and consists of the following java classes:\
MainActivity, DrawingView, and TouchListener

#### Software requirements:
* Android Studio
* IntelliJ
* SDK packages
* 2 x Pixel 2 API 32
* Testing will require at least 2 computers

#### Setting up the project:
1. After downloading repo, Android Studio may change the SDK file location\
To check manually, go to File > Project Structure > SDK Location\
Confirm that the path is where the SDK file is located
2. Next, open IntelliJ then File > New > Project from Existing Source...\
Now locate the server file: C:.\Desktop\Collab-canvas-app\app\server\
Click through and accept all the windows that pop-up
3. To link the networking dependency, click File > Project Structure > Modules\
Now click the "+" at the top then "Import Module" then locate the networking folder:\
C:.\Desktop\Collab-canvas-app\app\networking
4. To link it, select the server, then click on the "Dependencies" tab\
Now click "+" below the tabs, select "Module Dependency" then select the networking folder and click "OK"\
Once it populates, make sure the "Export" checkbox is checked and click "OK"

#### Testing:
1. On both computers: download the repo and set everything up
2. The computer hosting Server.java will need to run in separate IDE (recommend using IntelliJ)
3. The second computer will require changing the socket to the ip of the first computer.\
   On computer 1, open command line, enter ipconfig, and get the IPv4 address.
5. Enter this address into the second computer's MainActivity.java class on line 74
6. Now start the Server, start both emulators and have fun drawing!

## Workload distribution:
### Laurel Anderson:
Created base app with basic 1 color drawing, assisted in networking, project setup, recorded demo video
### Alexis Brown:
Created Packet class, added color buttons, code commenting/cleaning
### Johnathan Chan:
Created README, helped identify and fix simultaneous drawing error
### Daniel Rehman:
Helped with project setup, created initial networking implementation, created color and line-width implementation.
#### NOTE: all teammates attended and contributed to paired programming sessions, testing, and debugging!

## License
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

[Back to top](#canvas-collab-project)
