## Introduction

   This is my final project from my CMPT481 (Human Computer Interaction) class where myself and three collaborators were required to design a software product
   intended to test the performance of targeting tasks in regards to Fitts' Law, the Power Law of Practice, and overall user experience. Through this 
   experimentation, we attempted to determine the empirically best menu type for users based upon the data compared to the models we chose to examine and also
   the qualitative data that we gathered by giving the users questionnaires. The software itself is designed to randomly select menu items that a user must click 
   on and there are four different menu options that the user must perform these tasks in:
      1. Linear Menu - This menu served as our baseline as it is very standard across various applications that are PC based and lend themselves to the use of
         the mouse as a cursor.
      2. Radial Menu - This menu was used as a common alternative to the Linear Menu that is familiar to most users, especially those that play video games, but
         is less commonly used in a PC setting.
      3. Grid Menu - This menu is also a relatively common alternative to the Linear Menu, but is less commonly used. Its form and function are similar to the
         Linear Menu, but presents the user with more options initially.
      4. Scroll/Hotkey-Based Menu - This menu was a bit more on the experimental side of things. The idea behind it was that using the CTRL+Scroll functionality
         and hotkeys (assigned to numbers on the keyboard), the user would not actually have to move the cursor over to the menu and could open and use the menu
         from anywhere in the application after opening the menu via CTRL+Left-Click.

  Our experimental design had our subjects running through 41 trials for each menu type where they are given random prompts as to what menu item to find and click.
  Then, after they performed all trials for all menu types, the data was collected by the experimenter and the subject was given a questionnaire to complete regarding
  their experience with all of the menus. Through our experimentation, we determined that, at least in this context, the Radial Menu performed best, followed by the
  Linear Menu, then the Grid Menu, and lastly the Scroll/Hotkey-Based Menu. Through the experimentation, I have noted the following strengths and weaknesses:

  ## Strengths
  
   - The experimentation had a great deal of internal validity. There were no extraneous tasks and not many design elements that could confound the test results
   - The software itself never had any bugs or glitches that skewed the data as subjects were observed using the software

  ## Weaknesses
  
   - The experimentation was severely lacking in external validity: the task was to select a menu item always followed by the next task being another menu item
     selection. In a real world setting, users will likely perform many tasks between menu selections and this could have affected the data significantly for
     menu types such as the Scroll/Hotkey-Based Menu which didn't need the user to move the cursor to target it and the Radial Menu which could be moved wherever
     the user wanted it to be.
   - The experiment design was not informative enough to make the users familiar with the system prior to using it. Especially in the first couple of trials for
     each menu type, the completion time was significantly more than later trials. Some of this can be explained away via models such as the Power Law of Practice,
     but, as corroborated by users' comment on their experience, they were uncertain of how to use the system initially and struggled with the initial presentation
     and lack of instruction for the Scroll/Hotkey-Based Menu.
   - The amount of trials performed was not enough to adequately improve the user for unfamiliar menu types. Whereas most users have used a Linear, Radial, and
     sometimes even a Grid menu, most users have not used a Scroll/Hotkey-Based Menu and there is much data to support that unfamiliar interaction types are often
     less preferable to users initially.
     
## Prerequisites:

   To run this program, you will need the following:
     1. A JDK of 17.0.11 or higher for compatibility (Download here at https://www.oracle.com/ca-en/java/technologies/downloads/)
     2. The JavaFX library (Download here at https://openjfx.io/ or install through your IDE if you use VS Code, Eclipse, IntelliJ Idea, or NetBeans)

## Running Instructions

   1. Clone the repository to your desired directory by navigating to your desired directory via the command line. Then, run the following command to clone the repo:
         `git clone https://github.com/PrestonPeters/MenuExperimentation.git`
   2. Open the MenuExperimentation folder in an IDE that supports JavaFX
   3. Find the file Application.java in the folder MenuExperimentation/src/main/java/com/example/project481 and run it
   4. When finished with the program, exit by simply clicking the exit button at the top right of the JavaFX popup window
