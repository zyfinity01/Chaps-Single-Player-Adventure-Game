# Chaps Challenge

A single-player adventure game inspired by 1989 Atari game 'Chips Challenge'.

## Get started

<hr>

### Prerequisites

This project uses [Maven](https://maven.apache.org/) and can be used to build, test and execute the project.

### Installation

1) Follow the Maven [install guide](https://maven.apache.org/install.html).

2) Clone the project
   
   ```bash
   git clone https://gitlab.ecs.vuw.ac.nz/course-work/swen225/2022/project1/t16/chaps.git
   ```

3) Enter the directory
   
   ```bash
   cd chaps
   ```

4) Execute
   
   ```bash
   mvn exec:java -Dexec.mainClass="nz.ac.vuw.ecs.swen225.gp22.app.Main"
   ```

### Developer Environment

This project enforces certain code formatting standards, such as Javadocs, and Google's Codestyle format. Maven has been configured to check these standards with the `mvn test` command.

You can also install plugins for your chosen IDE:

##### Eclipse

1) Install `SpotBugs` and `Checkstyle`

2) Configure the Formatter: `Properties -> Java Code Style -> Formatter`
   a) Enable `Project specific settings`
   b) Import [Google Style]([styleguide/eclipse-java-google-style.xml at gh-pages · google/styleguide · GitHub](https://github.com/google/styleguide/blob/gh-pages/eclipse-java-google-style.xml) xml and apply.

3) Configure Javadocs: `Properties -> Java Compiler -> Javadoc`
   a) Enable `Project specific settings`
   b) Select `Error` from all applicable dropdowns and apply.

4) Configure SpotBugs: `Properties -> SpotBugs`
   a) Enable `Project specific settings`
   b) Enable `Run automatically` and apply

5) Configure Checkstyle: `Properties -> Checkstyle`
   a) Enable `Checkstyle active for this project`
   b) Apply and Close
