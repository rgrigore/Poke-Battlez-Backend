<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![LinkedIn - Marius][linkedin-shield]][linkedin-marius-url]
[![Github - Marius][github-marius-shield]][github-marius-url]
[![Github - Razvan][github-razvan-shield]][github-razvan-url]



<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/rgrigore/Poke-Battlez-Frontend/tree/project_documentation.git">
    <img src="doc_images/pokelogo.png" alt="Logo" width="300">
  </a>

  <h3 align="center">Poke Battlez</h3>

  <p align="center">
    An awesome web multiplayer game for Pokemon fans!
    <br />
    <a href="https://github.com/rgrigore/Poke-Battlez-Frontend.git"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/rgrigore/Poke-Battlez-Frontend.git">View Demo</a>
    ·
    <a href="https://github.com/rgrigore/Poke-Battlez-Frontend/issues">Report Bug</a>
    ·
    <a href="https://github.com/rgrigore/Poke-Battlez-Frontend/issues">Request Feature</a>
  </p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This is an exciting multiplayer strategy game for all Pokemon fans out there, and a way for them to directly interact online.

Here's why:
* Poke Battlez is the place where you can meet online and chat as a group.
* You can send PMs to any other user, for those private thoughts or invitations for direct challenges.
* You can use all your Pokemon skills and knowledge in configuring and modifying your own team of pokemons, up to 6 of them.
* You can challenge any other user for a 1 to 1 battle.
* You can execute a battle where you can test your customized team together with your best Pokemon battle strategies.

Of course, all Pokemon are available to you, together with a comprehensive list of characteristics you can customize, thanks to the Pokemon API used, more details in acknowledgements 

### Built With

This section should list any major frameworks that you built your project using. 

#### Backend
* [Spring Boot (WebSocket/JPA)](https://spring.io/projects/spring-boot)
* [Lombok](https://projectlombok.org/)
* [H2 DataBase](https://www.h2database.com/html/main.html)


#### Frontend
* [React](https://reactjs.org/)
* [React-Bootstrap](https://react-bootstrap.github.io/)
* [npm](https://www.npmjs.com/)

#### Version control
* [Github](https://www.gtihub.com/)

#### Project Management
* [Jira Software](https://www.atlassian.com/software/jira?&aceid=&adposition=&adgroup=89541897982&campaign=9124878150&creative=415542514747&device=c&keyword=jira&matchtype=e&network=g&placement=&ds_kids=p51242161283&ds_e=GOOGLE&ds_eid=700000001558501&ds_e1=GOOGLE&gclid=Cj0KCQiAnKeCBhDPARIsAFDTLTIUjm6m9LQssN_d15V_dYNqPiWaS_df09mdcnHPj-QkqTKrZfAjB6kaAhdEEALw_wcB&gclsrc=aw.ds)



<!-- GETTING STARTED -->
## Getting Started

This application can be tested by installing all prerequisites, clone both the back end and the client app, running them and enjoy!

### Prerequisites

All prerequisites must be installed, accordingly to the technologies used in this project, for example:
* npm
  ```sh
  npm install npm@latest -g
  ```

### Installation

* Backend

1. Clone the repo
   ```sh
   git clone https://github.com/rgrigore/Poke-Battlez-Backend.git
   ```
2. Run the server

* Frontend

1. Clone the repo
   ```sh
   git clone https://github.com/rgrigore/Poke-Battlez-Frontend.git
   ```
2. Install NPM packages
   ```sh
   npm install
   ```
3. Run the app
   ```
    npm start
   ```



<!-- USAGE EXAMPLES -->
## Usage

Further we will shortly name, describe and visualize some main features of the app.

### Lobby
* In order to connect to the main server, there is necessary to register or login.

[![register-png][register-png]]()
[![login-png][login-png]]()
[![login-gif][login-gif]]()

* Here all users join a common "room", where they can check who is online, and they can chat, either on  the main chat, or send PM to specific users.

[![chat-png][chat-png]]()

* Click on any user and see the private actions you can take towards that specific user.

[![PM-gif][PM-gif]]()

* Using the button in the header, you open the TEAM configuration modal, where you can configure all your Pokemon slots, with actual characters and with detailed criteria as seen bellow:

[![team-gif][team-gif]]()

* Select the user you want to engage into battle and by pressing the challenge button, you will be transferred on a private room with him/her, where you can chat or execute the battle.

[![challenge-gif][challenge-gif]]()
[![battle-gif][battle-gif]]()

### Battle
* The battle is turn based, with the player being able to select the Pokemon, and the move which to be executed against the adversary Pokemon. Once both players "locked" their own moves, they will get a log response with the results of the turn, and impacting their Pokemon health accordingly. You can also swith between your Pokemon team at any time.

<!-- ROADMAP -->
## Roadmap

The project development took place through 4 Agile iterations, each iteration taking 4 days, and presenting the results in the 5th. A short complete roadmap bellow:

[![agile][agile]]()

* Sprint 1: Implementing main Lobby (Group chat, Online users, Login System)
* Spring 2: Implementing Team configuration / PM messaging / Send and Receive Challenge
* Sprint 3: Implementing Accept Challenge / Battle page / Battle Logic
* Sprint 4: Refactoring Battle service / Unit Testing / Secure User Login system




<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


<!-- CONTACT -->
## Contact

Marius Ceobanu - [@My Github](https://github.com/marius-ceobanu) [@My LinkedIn](https://www.linkedin.com/in/marius-ciprian-ceobanu-3431157b) - ceobanu.marius@gmail.com

Razvan Grigore - [@My Github](https://github.com/rgrigore) - razvang95@gmail.com


<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements
* [Spring Documentation](https://docs.spring.io/)
* [React Documentation](https://reactjs.org/)
* [PokeApi](https://pokeapi.co/)
* [Bulbapedia](https://bulbapedia.bulbagarden.net/wiki/Main_Page)
* [Codecool Romania :thumbsup:](https://codecool.com/ro/)
* [Img Shields](https://shields.io)


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/badge/Contributers-2-brightgreen
[contributors-url]: https://github.com/rgrigore/Poke-Battlez-Frontend/graphs/contributors
[forks-shield]: https://img.shields.io/badge/Forks-0-blue
[forks-url]: https://github.com/rgrigore/Poke-Battlez-Frontend/network/members
[stars-shield]: https://img.shields.io/badge/Stars-2-blue
[stars-url]: https://github.com/rgrigore/Poke-Battlez-Frontend/stargazers
[issues-shield]: https://img.shields.io/github/issues/rgrigore/Poke-Battlez-Frontend
[issues-url]: https://github.com/rgrigore/Poke-Battlez-Frontend/issues
[linkedin-shield]: https://img.shields.io/twitter/url?label=Linkedin%20-%20Marius&logo=LINKEDIN&style=social&url=https%3A%2F%2Fwww.linkedin.com%2Fin%2Fmarius-ciprian-ceobanu-3431157b
[linkedin-marius-url]: https://www.linkedin.com/in/marius-ciprian-ceobanu-3431157b
[github-marius-shield]: https://img.shields.io/twitter/url?label=GitHub%20-%20Marius&logo=Github&style=social&url=https%3A%2F%2Fgithub.com%2Fmarius-ceobanu
[github-marius-url]: https://github.com/marius-ceobanu
[github-razvan-shield]: https://img.shields.io/twitter/url?label=GitHub%20-%20Razvan&logo=Github&style=social&url=https%3A%2F%2Fgithub.com%2Frgrigore
[github-razvan-url]: https://github.com/rgrigore
[chat-png]: doc_images/chat.png
[register-png]: doc_images/register.png
[login-png]: doc_images/login.png
[login-gif]: doc_images/login.gif
[PM-gif]: doc_images/PM.gif
[team-gif]: doc_images/team.gif
[challenge-gif]: doc_images/challenge.gif
[battle-gif]: doc_images/battle.gif
[agile]: doc_images/agile-logo.png

