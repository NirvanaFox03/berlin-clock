This project has implemented the Berlin clock as specified in "requirements.txt" with some extensions.

The original requirements only need a function. However it's not convenient to run a function - an IDE must be opened and some setup must be done. 
For the convenience of the users, a full execute program with shell scripts has been implemented. 
Simply run "mvn install" under the project root folder (assume Maven is installed) and the executables can be found under target\executable\bin (both Linux and Windows scripts are generated).

Another extension is the original requirement is to take three parameters (hour, minute, and second) as input. 
However, in real world we rarely pass around the time using separate parameters. So the date formatter is used for customised patterns and the time is input as a whole string.