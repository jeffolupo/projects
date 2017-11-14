
<html>
    <head>
        <title>Burritos, Tacos, and Pizzas, Oh My!</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/index.css" rel="stylesheet">
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
                    <li class="navword"><a href="index.html">Home</a></li>
                    <li><a class="navword" href="search.html">Search</a></li>
                    <li role="presentation" class="dropdown">
                        <a class="dropdown-toggle navword" data-toggle="dropdown" href="#" role="button">Manage Site
                            <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="addPost.html">Add Post</a></li>
                            <li><a href="approvePost.html">Approve Post</a></li>
                            <li><a href="addPage.html">Add Page</a></li>
                            <li><a href="seeAllPosts.html">See All Posts</a></li>
                            <li><a href="seeAllPages.html">See All Pages</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div>
                <h1>An error has occurred...</h1>
                <h3>${errorMessage}</h3>
            </div>
        </div>

        <script src="js/jquery-3.1.1.min.js"></script>
        <script src="js/bootstrap.js"></script>
        <script src="js/index.js"></script>
        <script src="js/staticPages.js"></script>
    </body>
</html>