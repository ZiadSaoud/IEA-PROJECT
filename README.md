# IEA-PROJECT
## Description
In this project we implemented a smart vaccuum cleaning agent that finds its path to dirty tiles and tries to clean the room. Three classes of agents with variable visibility were developed and their performance was evaluated regarding the percentage of clean tiles in a 5 second period. For the first class of agents, the agent had fully observable environments and their path finding algorithms were implemented using BFS, Dantizg, Bellman Ford, A*, Greedy-BFS, TSP, and Bidirectional search. For the second class of agents with partially observable environments, the path finding algorithm was implemented using a stochastic agent. For the third approach with the agent with unobservable environment, the agent was implemented using a random path finding approach. This project aims to find the optimal path for cleaning the room in a timely and effecient manner.

### Environments:
1. Fully Observable 
2. Partially Observable
3. Non Observable

### Algorithms Used: 

 1. Breadth First Search (BFS)
 2. A* Algorithm
 3. Dantzig
 4. Bellman Ford
 5. Bidirectional Search
 6. Greedy Best First Search
 7. Simulated Annealing for solving the Travelling Salesman Problem (TSP)
 8. Depth First Search (DFS) for room discovery and dust grouping

### Search options:
- Closest dirt (using the Super Node approach)
- Dust grouping (based on the famous number of Islands in a graph problem)
##
## Tools Used
- Java 8
- JavaFx
- Scene Builder
##
## Authors and acknowledgment
This Application is built by **Ziad Saoud**, **Elie Antoun**, and **Rony Abi Hanna**.
##
## Usage
1. Select height and width of the room
<img width="1000" alt="image" src="https://user-images.githubusercontent.com/69092782/147874666-4b275f79-b072-43dd-ab5f-e8df3ca4fb51.png">
2. Select dust and wall locations and press the Next button: 
- click a tile to add dust (click again to remove the dust)
- right click a tile to add walls (double click to reset the tile)
<img width="1000" alt="image" src="https://user-images.githubusercontent.com/69092782/147874958-cd4738a6-5d37-4ddd-8f29-318d2bd5b49d.png">
3. Select the agent position:
<img width="1000" alt="image" src="https://user-images.githubusercontent.com/69092782/147874998-2d63e3c4-ada3-4a15-9f45-31a9769d25bb.png">
4. Go to the Algorithm tab and choose the algorithm, search mode, and observability of the agent:
<img width="1000" alt="image" src="https://user-images.githubusercontent.com/69092782/147875059-2cae2b91-b6d8-4934-afed-b4adc6f9df40.png">
5. Go back to the Environment tab and press clean!!!
6. Settings:
<img width="1000" alt="image" src="https://user-images.githubusercontent.com/69092782/147875194-8b479fcf-a820-4a5e-8bc2-dc169a61b00b.png">
7. Results:
<img width="1000" alt="image" src="https://user-images.githubusercontent.com/69092782/147875211-12111551-d7cd-494a-a6dc-a6de1ae399e3.png">
8. About:
<img width="1000" alt="image" src="https://user-images.githubusercontent.com/69092782/147875220-653b2513-801d-43be-b55f-69005a517bfc.png">

##

