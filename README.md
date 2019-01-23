# secureBet
I made this game simulator for a Systems Secuirty elective at Pomona College. 

*How to run*

You will need to run Server.java then Client.java in order to establish the SSL connection. If you actually want it to work you'll also need to create a mySQL database the same as it is set up for because I'm not paying to host one for a game nobody will play. See attached picture:

![alt text](https://raw.githubusercontent.com/andrewlew1s/secureGame/master/mySQL Player.png)


*Threat model*

We will assume that we are using the Dolev-Yao model, and that our admin is trusted.

Threats:

An attacker trying to steal usernames and passwords. This attacker could be motivated by the fact that people often use the same passwords for multiple services. This is substantial enough motive for a skilled attacked with resources to try and attack our system.

An attacker trying to alter their last game score. This attacker will be motivated by pride, but seeing as the game itself isn't that interesting this attacker is less likely to be skilled and have substantial resources behind them. 

Non-threats:

I am assuming that attacks on physical hardware will not work as drives containing sensitive data will be stored in a physically secure location.

I am assuming that the database administrator (me) will not abuse their privileges.


