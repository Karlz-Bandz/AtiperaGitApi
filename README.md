<h1>Atipera GitApi</h1>

This application connects with https://api.github.com and proceed
some operations on RestApi. First version can display all repositories
with its all branches with the last commit sha. You have to only provide
the existing username. If user doesn't exist or user exists but don't have
any repos the application returns code 404 with a suitable message.