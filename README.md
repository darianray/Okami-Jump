# Okami-Jump

A JavaFX platform game using Adobe Photoshop to design levels/sprites and rendering algorithms for in-game physics and precise collision detection for individual project. Game includes front-end UI for menus,  leaderboard, file system integration, key input (multi-threading), and enemy actions

## Features

- **Player Movement**: Player can move around the screen using [arrow keys/keyboard/controller/etc.].
- **Collision Detection**: The player can interact with different elements such as obstacles, platforms, and enemies.
- **Audio**: Background music and sound effects such as jumping, landing, and [other sound effects].
- **Game States**: Includes transitions between game states such as:
    - **Main Menu**: Start the game, view options, etc.
    - **Game**: Play the main game.
    - **Game Over**: Triggered when the player falls off the map or loses all health.
    - **Leaderboard**: Display high scores after game over.
- **State Transitions**: Smooth transitions between game states, including stopping background music upon game over.
  
## Technologies Used

- **Language**: [Java]
- **Development Tools**:
  - IDE: [Visual Studio]
  - Version Control: [GitHub]

### Prerequisites

- Ensure that you have [programming language/environment] installed:
  - [Java 11+](https://adoptopenjdk.net/) (for Java)

### Steps to Run

1. Clone the repository to your local machine:
    ```bash
    git clone https://github.com/yourusername/your-repository.git
    ```

2. Navigate into the project directory bin file:
    ```bash
    cd bin
    ```

3. Run the game:
   - For Java:
     ```bash
     java Main.Game
     ```

## Steps to make changes to code

1. Navigate to directory and make changes to code, save files.

2. Run
   ```
    javac -d bin src/Main/*.java src/GameState/*.java src/TileMap/*.java src/Entity/*.java src/Audio/*.java src/Entity/Enemies/*.java
   ```

## Game Controls

- **Movement**: Use arrow keys for movement.
- **Jump**: [SPACE] to jump.
- **Fireball**: [F] to shoot fireballs at enemies.
- **Scratch**: [R] to scratch at enemies.
- **Glide**: [E] to glide after jumping.


## How It Works

The game is divided into several states:
- **Main Menu**: The game starts here, where players can start a new game, access settings, or view the leaderboard.
- **Game Loop**: Once the game starts, the player can move and interact with the environment. The game checks for conditions like collisions and player health.
- **Game Over**: If the player falls off the map or runs out of health, the game transitions to the game-over state, where a high score can be recorded if enough points are collected.
- **Music & Sounds**: Background music is played during gameplay, and sound effects trigger based on player actions like jumping and attacking enemies.