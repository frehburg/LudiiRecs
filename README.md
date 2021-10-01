# LudiiRecs
In this repository i am going to create a recommender system for coding in the Ludii general game system. The Ludii GGS is part of the Digital Ludeme Project, which under the leadership of C. Browne received a 2m€ [![ERC Consolidator Grant](https://erc.europa.eu/news/erc-2017-consolidator-grants-results)] (#771292). I am a second year Bachelors studnt at the University Maastricht, where I study Data Science and Artificial Intelligence and particpate in the Honours programme of said study. I chose the research based MaRBLe 2.0 branch of the Honours programme, which enabled me to work on the Digital Ludeme Project under the supervision of C. Browne and his team.

For more information about the above mentioned projects, please follow these links:
- [![Digital Ludeme Project](http://ludeme.eu/index.html)]
- [![Ludii General Game System](https://ludii.games/)]
- [![European Research Council](https://erc.europa.eu/news/erc-2017-consolidator-grants-results)]
- [![DKE Honours Programme](https://www.maastrichtuniversity.nl/research/dke/honours-programme)]

[![Digital Ludeme Project](https://github.com/frehburg/LudiiRecs/blob/main/res/pictures/README/Digital%20Ludeme%20Project%20Logo.png)](http://ludeme.eu/index.html)
[![Ludii General Game System](https://github.com/frehburg/LudiiRecs/blob/main/res/pictures/README/Ludii%20General%20Game%20System%20Logo.gif)](https://ludii.games/)
[![European Research Council](https://github.com/frehburg/LudiiRecs/blob/main/res/pictures/README/European%20Research%20Council%20logo.png)](https://erc.europa.eu/news/erc-2017-consolidator-grants-results)
[![DKE Honours Programme](https://github.com/frehburg/LudiiRecs/blob/main/res/pictures/README/UM%20Logo.jpg)](https://www.maastrichtuniversity.nl/research/dke/honours-programme)
[European Union Flag](https://github.com/frehburg/LudiiRecs/blob/main/res/pictures/README/EU%20flag.png)
## Features
- Parser of the Ludii language: Builds tree objects from .lud files that represent the structure of the game file
- Algorithm to gather all .lud files
- Example: Graph resulting from parsing [Tic-Tac-Toe.lud](https://github.com/frehburg/LudiiRecs/blob/main/res/Tic-Tac-Toe.lud), metadata is also getting parsed but not visualized in this example.

![Example Graph](https://github.com/frehburg/LudiiRecs/blob/main/res/pictures/Example/Tic-Tac-Toe%20graph%20example.png)


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
