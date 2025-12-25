Golf Rounds Tracker

Solo project

Keigo Noguchi 885486746

This app keeps tracks of rounds of golf played by users. Users can enter the name of the course they played and their score. This app makes it easy for users to keep track of the rounds they've played.

Features

User Authentication: Sign up, log in, and log out using Firebase Authentication.

CRUD Operations 1:
- Create: Add new golf rounds with a course name and score.
- Read: View a list of all past rounds and see the details of each one.
- Update: Edit the course name and score of existing rounds.
- Delete: Remove rounds from the list (with a confirmation dialog).

CRUD Operations 2:
- Create: Create a golf course.
- Read: View a list of all the golf courses that you have added.
- Update: Update the details of a golf course.
- Delete: Remove a course from your list.

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

Firebase Setup to run this project, you need to connect it to your own Firebase project.
1. Create a Firebase Project: Go to the Firebase Console and create a new project.
2. Add an Android App:
- In your Firebase project dashboard, add a new Android app.
- Use com.example.cpsc411final as the package name.
- Follow the instructions to download the google-services.json file.
3. Place google-services.json: Copy the google-services.json file you just downloaded and place it in the app/ directory of this Android Studio project.
4. Enable Authentication:
- In the Firebase Console, go to the Authentication section.
- Click "Get Started".
- Under the "Sign-in method" tab, enable the Email/Password provider.

Local Setup

1.Clone this repository.

2.Open the project in Android Studio.

3.Complete the Firebase Setup steps described above.

4.Let Android Studio sync the Gradle files.5.Build and run the app on an emulator or a physical device.


Screenshots:

Login screen

<img width="376" height="829" alt="Screenshot 2025-12-19 at 12 49 04 PM" src="https://github.com/user-attachments/assets/302fabea-f475-46f7-90da-8258f3bb8542" />

Signup screen

<img width="381" height="828" alt="Screenshot 2025-12-19 at 12 49 29 PM" src="https://github.com/user-attachments/assets/02b74ac6-5f3b-4b53-a1c4-e34f142b8e02" />

List/home screen

<img width="372" height="825" alt="Screenshot 2025-12-19 at 12 50 54 PM" src="https://github.com/user-attachments/assets/f4f9707d-82d5-4cdb-92d9-38872b18d8ae" />

Add round

<img width="375" height="831" alt="Screenshot 2025-12-19 at 12 50 25 PM" src="https://github.com/user-attachments/assets/b2270e69-27a6-4ca0-baa6-d0821fd58a93" />

Round details

<img width="377" height="824" alt="Screenshot 2025-12-19 at 12 51 18 PM" src="https://github.com/user-attachments/assets/63bebfc9-1f2b-490c-a792-9be76a5465de" />

Edit details

<img width="388" height="836" alt="Screenshot 2025-12-19 at 12 51 34 PM" src="https://github.com/user-attachments/assets/599b4a5d-a432-4111-bc55-172e748874ba" />

Delete round

<img width="370" height="823" alt="Screenshot 2025-12-19 at 12 51 51 PM" src="https://github.com/user-attachments/assets/6cefacdd-9b1a-4ae2-a87e-45f47c73d3e1" />

Profile screen

<img width="379" height="829" alt="Screenshot 2025-12-19 at 12 52 11 PM" src="https://github.com/user-attachments/assets/ca03d701-215a-4f51-8a01-fc7c80c791e3" />

