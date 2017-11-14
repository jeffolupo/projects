<!DOCTYPE html>
<html>
    <head>
        <title>Burritos, Tacos, and Pizzas, Oh My!</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/login.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Burritos, Tacos, and Pizzas, Oh My!</h1>
            <img src="static/foodiesfeed.com_mexican-burrito-with-cream.jpg" alt='burrito' style='width:375px;height:200px'>
            <img src="static/tacos-1613795_640.jpg" alt='tacos' style='width:380px;height:200px'>
            <img src="static/vegetables-italian-pizza-restaurant.jpg" alt='pizza' style='width:375px;height:200px'>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs" id="navbar">
                    <li><a class="navword" href="index.html">Home</a></li>
                    <li><a class="navword" href="search.html">Search</a></li>
                    <li role="presentation" class="dropdown">
                        <a class="dropdown-toggle navword" data-toggle="dropdown" href="#" role="button">Manage Site
                            <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="addPost.html">Add Post</a></li>
                            <li><a href="addPage.html">Add Page</a></li>
                            <li><a href="seeAllPosts.html">See All Posts</a></li>
                            <li><a href="seeAllPages.html">See All Pages</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div>
                <h3>Log in to continue</h3>
                <form action="j_spring_security_check" method="post">
                    <div class="form-group">
                        <label for="j_username">User Name: </label>
                        <input type="text" class="form-control" id="j_username"
                               name="j_username" placeholder="Username">
                    </div>
                    <div class="form-group">
                        <label for="j_password">Password: </label>
                        <input type="password" class="form-control" id="j_password"
                               name="j_password" placeholder="Password">
                    </div>
                    <button type="submit" class="btn btn-primary">Log In</button>
                    <button type="reset" class="btn btn-default">Reset</button>
                </form>
                <div class="alert alert-danger alert-dismissible" role="alert"
                     id="login-error-alert">
                    <button type="button" class="close" data-dismiss="alert"
                            aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    Incorrect username or password.
                </div>
            </div>
        </div>

        <script src="js/jquery-3.1.1.min.js"></script>
        <script src="js/bootstrap.js"></script>
        <script src="js/login.js"></script>
        <script src="js/staticPages.js"></script>
    </body>
</html>