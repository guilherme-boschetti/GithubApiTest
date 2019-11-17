# GithubApiTest
Test Application. Consume GitHub API.

-------------------------------------------------------------------------

This is a native android app made in java language.

The app is a prototype made as a test to consume the GitHub API.

It allows the app user to view the public repositories of any GitHub user and, if the password is entered, also allows viewing the private repositories. The app user can also view the last 5 commits of each repository.

The app user can enter the GitHub username manually to view the repositories or can also scan a QR code with the username and mode of the repositories (public or private). 

The QR code content must consist of the username and mode separated by 2 characters '#', in the following format: 'username:the_username_here##mode:the_mode_here' (without quotes), for example: username:guilherme-boschetti##mode:public.

To generate the QR code you can use any online generator, for example: https://www.the-qrcode-generator.com/.
