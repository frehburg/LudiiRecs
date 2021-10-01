# LudiiRecs
In this repository i am going to create a recommender for coding in the Ludii game design language. I am contributing to the Digital Ludeme Project by C. Browne et al. as part of the Honours programme, where I participate in the MaRBLe 2.0 branch, at my faculty DKE@UM.

[![Ludeme](http://ludeme.eu/images/logo-35-e-1.png)](http://ludeme.eu/index.html)
[![Honors Programme](https://ludii.games/images/um-logo-42.jpg)](https://www.maastrichtuniversity.nl/research/dke/honours-programme)

## Features
- Parser of the Ludii language: Builds tree objects from .lud files that represent the structure of the game file
- Algorithm to gather all .lud files
- Example: Graph resulting from parsing [Tic-Tac-Toe.lud](https://github.com/frehburg/LudiiRecs/blob/main/res/Tic-Tac-Toe.lud)

[Example Graph](https://github.com/frehburg/LudiiRecs/blob/main/res/pictures/Example/Tic-Tac-Toe%20graph%20example.png)


## Planning
- Statistical analysis of all .lud files to enable first recommendation
- Improve recommendations

## Installation
To install this project you will need
- [Java](https://www.java.com/) - Java JDK 15 or newer
- To install Java follow this [guide](https://www.java.com/en/download/help/windows_manual_download.html)
- [Maven](https://maven.apache.org/) - Apache Maven
- To install Maven follow this [guide](https://maven.apache.org/guides/getting-started/)
- Open the project in your favorite IDE as a maven project
- run `mvn package` on command line in the directory C:/.../LudiiRecs/ to install all dependencies
Now you are ready to go

## Run
After installing, the program can be run in the following way:
1. Go to `TreeGenerator.java`
2. In `main()` set the path of `File f` to the .lud file that you want to parse
3. Execute `TreeGenerator.main()` 
4. The tree built from the .lud file will be printed into the console

[![Ludii](https://ludii.games/images/logo-clover-c-border.gif)](https://ludii.games/index.php)

Part of:
- https://www.maastrichtuniversity.nl/research/dke/honours-programme
- https://ludii.games/index.php
- http://ludeme.eu/index.Digital
