# Posterr
This is a solution to a coding exercise

I developed the project with the main purpose to show: 
* how I organize the project files and folders using **CLEAN ARCHITECTURE**;
* Using the new features from Kotlin such as coroutines, extensions and so on;
* Using newest libraries from Jetpack such as **ROOM** and JETPACK COMPOSE;
* I'm able to write unit tests to cover as much code as possible;
* I can write custom views old way with canvas and the new with jetpack compose composing a dsc;
* The project is divided by module   

### Critique
Like every projects there will always be room for improvements as the team continue to learn better 
ways to write code, such as:
* Cover integration and UI tests;
* Improve the UI UX Design;
* Assuming I've got multiple crash reports and reviews saying the app is not working properly and 
is slow for specific models first I'd try to simulate the specific version of the device, studying the **Profiler** 
to discover the reasons to crash and why the app is slow;
* Assuming the app has now thousands of users thus a lot of posts to show in the feed. I believe the API 
should return the data paginated so the app start to show a infinite list.

