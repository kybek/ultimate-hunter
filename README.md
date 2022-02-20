# ultimate-hunter
The quest that every CS student wishes for: To improve that one heuristic based programming homework, after its deadline.

---

## The homework
The homework is a somewhat edited version of the problem given below. I found the source below after the deadline, as searching for the problem would be a clear violation of the code of academic honesty.

> From Absolute Java, Fifth Edition by Walter Savitch

> Chapter 8, Programming Project #4

The goal for this programming project is to create a simple 2D hunter–prey simulation. These creatures live in a world composed of a M x N grid of cells. Only one creature may occupy a cell at a time. The grid is enclosed, so a creature is not allowed to move off the edges of the grid. Time is simulated in time steps. Each creature performs some action every time step.

The preys behave according to the following model:

• Move. Every time step, randomly try to stand still or move up, down, left, right.

• Breed. If a prey survives for three time steps, then at the end of the third time step (i.e., after moving), the prey will breed. This is simulated by creating a new prey in an adjacent (up, down, left, or right) cell that is empty. If there is no empty cell available, no breeding occurs. Once an offspring is produced, the prey cannot produce an offspring until three more time steps have elapsed.

The hunters behave according to the following model:

• Move. Every time step, if there is an adjacent cell (up, down, left, or right) occupied by a prey, then the hunter will move to that cell and eat the prey. Otherwise, the hunter moves according to the same rules as the prey. Note that a hunter cannot eat other hunter.

• Breed. If a hunter survives for eight time steps, then at the end of the time step, it will spawn off a new hunter in the same manner as the prey.

• Starve. If a hunter has not eaten a prey within the last three time steps, then at the end of the third time step, it will starve and die. The hunter should then be removed from the grid of cells.

During one turn, all the hunters should move before the preys.

Write a program to implement this simulation and draw the world using ASCII characters of 'P' for a prey and 'H' for a hunter. Bonus points to create an ultimate hunter that is a hunter except the move and breed functions are coded by you. You will get bonus points based on the amount of your ultimate hunters surviving at the end of the simulation.

---

## The Submit™️ edition:
Files on this edition are submitted as is -- except `Simulate.java`, which was already given with the asignment and needed no submission.

---

## UltimateHunter.java
The quest is to create an intelligent hunter. I rushed the homework and almost missed the deadline, so I had to code a quick but dirty "go to the middle of preys" algorithm. Yes, it fails on so many levels but I wanted to submit something. It took me only about 5 minutes to code so I'm somewhat proud.
