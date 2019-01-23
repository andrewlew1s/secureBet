# secureGame
I made this secure game simulator for a Systems Secuirty elective at Pomona College. The project has a basic game Client made in Java Swing that connects to a Server (also in Java) via an encrypted SSL connection. The server then connects to a mySQL database that is hosted locally. Other security features besides the encryption include: All passwords are hashed iteratively and use a salt, the database only uses PreparedStatements to prevent against SQL Injection attacks, passwords are strength tested, and more!

**How to run**

You will need to run Server.java then Client.java in order to establish the SSL connection. If you actually want it to work you'll also need to create a mySQL database the same as it is set up for because I'm not paying to host one for a game nobody will play. See pictures at the bottom of the README.

**Threat model**

I am assuming that the attacked is one from the Dolev-Yao model, and that our admin is trusted.

Threats:

An attacker trying to steal usernames and passwords. This attacker could be motivated by the fact that people often use the same passwords for multiple services. This is substantial enough motive for a skilled attacked with resources to try and attack our system.

An attacker trying to alter their last game score. This attacker will be motivated by pride, but seeing as the game itself isn't that interesting this attacker is less likely to be skilled and have substantial resources behind them. 

Non-threats:

I am assuming that attacks on physical hardware will not work as drives containing sensitive data will be stored in a physically secure location.

I am assuming that the database administrator (me) will not abuse their privileges.

**Things to note:**

The game physics is based off a basic Swing game guide I found online. This project was about implementing security features rather than an interesting front-end/gaming experience.  

The logs lock out a player after a certain number of incorrect logins. This is a basic and somewhat unintelligent security feature as it has the issue that a malicious person could intentionally lock someone out of their account by entering nuemrous wrong passwords. I did this just because I really wanted to display some kind of functionality based off of the audit/logs in the time frame I had. This ended up being a solo project when it was supposed to be a group project, so I didn't have time to really refine this feature as I would have liked.  

Feedback is always appreciated!

**Database pics:**

![alt text](https://raw.githubusercontent.com/andrewlew1s/secureGame/master/mySQL_Player.png)
![alt text](https://raw.githubusercontent.com/andrewlew1s/secureGame/master/mySQL_Auth.png)
