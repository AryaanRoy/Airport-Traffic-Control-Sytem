Airport Traffic Control System

This is a group project. It is an Airport Management System developed in Java with a JavaFX/Swing-based GUI. It allows for the efficient scheduling and management of airplane landing and departure queues for cargo, private, and commercial flights.

Features:

Real-Time Queue Management: Handles landing and departure queues using a custom node-based data structure.
Flight Details Display: Shows flight number, make, and type in a dynamic, user-friendly GUI.
Extensible Design: Built with object-oriented principles such as abstract classes and interfaces to allow future enhancements.
Threading: Real-time task execution without GUI freezing, ensuring a responsive experience.
Multiple GUI instances, such as serverGUI and airportGUI, run concurrently without interfering with each other.
Threading bridges the GUI and networking functionality seamlessly.
Client/Server Communication: Utilizes sockets to enable communication between different components of the system.
Technologies Used:

Java
JavaFX/Swing for GUI
Data Structures and Algorithms
Sockets for client/server communication
How to Run the Program:

Clone the repository:
Use the command: git clone <repository-link>
Navigate to the project directory: cd <project-folder>
Compile the code:
Use a Java IDE (e.g., IntelliJ IDEA, Eclipse) or the command line to compile the project.
Run the program:
Execute MainMenuPanel.java to start the application.
