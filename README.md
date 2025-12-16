Golf Rounds Tracker

Solo project

Keigo Noguchi 885486746

This app keeps tracks of rounds of golf played by users. Users can enter the name of the course they played and their score. This app makes it easy for users to keep track of the rounds they've played.

Features
User Authentication: Sign up, log in, and log out using Firebase Authentication.
CRUD Operations:
- Create: Add new golf rounds with a course name and score.
- Read: View a list of all past rounds and see the details of each one.
- Update: Edit the course name and score of existing rounds.
- Delete: Remove rounds from the list (with a confirmation dialog).
Modern UI: Built entirely with Jetpack Compose, using Material 3 design components.
Reactive Data Layer: Uses Kotlin Flows and Firestore real-time listeners to ensure the UI is always up-to-date.

Screens:
- Create account
- Login
- Home page
- Add round
- Edit round
- Details
- Profile page

Firebase SetupTo run this project, you need to connect it to your own Firebase project.
1. Create a Firebase Project: Go to the Firebase Console and create a new project.
2. Add an Android App:
- In your Firebase project dashboard, add a new Android app.
- Use com.example.cpsc411final as the package name.
- Follow the instructions to download the google-services.json file.
3. Place google-services.json: Copy the google-services.json file you just downloaded and place it in the app/ directory of this Android Studio project.
4.Enable Authentication:
- In the Firebase Console, go to the Authentication section.
- Click "Get Started".
- Under the "Sign-in method" tab, enable the Email/Password provider.

Local Setup

1.Clone this repository.

2.Open the project in Android Studio.

3.Complete the Firebase Setup steps described above.

4.Let Android Studio sync the Gradle files.5.Build and run the app on an emulator or a physical device.
