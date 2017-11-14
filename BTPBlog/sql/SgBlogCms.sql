DROP DATABASE IF EXISTS SgBlogCms;

CREATE DATABASE SgBlogCms;

USE SgBlogCms;

CREATE TABLE Category (
	categoryId INT PRIMARY KEY AUTO_INCREMENT,
    categoryName VARCHAR(255) NOT NULL
);

CREATE TABLE Post (
	postId INT PRIMARY KEY AUTO_INCREMENT,
    originalTimestamp DATETIME NOT NULL,
    updatedTimestamp DATETIME NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    isApproved BOOLEAN NOT NULL,
    isHidden BOOLEAN NOT NULL,
    categoryId INT NOT NULL,
    FOREIGN KEY (categoryId) REFERENCES Category(categoryId)
);

CREATE TABLE Hashtag (
	hashtagId INT PRIMARY KEY AUTO_INCREMENT,
    hashtagName VARCHAR(255)
);

CREATE TABLE PostHashtag (
	postId INT NOT NULL,
    FOREIGN KEY (postId) REFERENCES Post(postId),
    hashtagId INT NOT NULL,
    FOREIGN KEY (hashtagId) REFERENCES Hashtag(hashtagId),
    PRIMARY KEY (postId, hashtagId)
);

CREATE TABLE `Page` (
	pageId INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL
);

INSERT INTO Category (categoryName)
VALUES ('Burritos'),
	('Tacos'),
    ('Pizzas'),
    ('Restaurants'),
    ('Recipes');
    
INSERT INTO Post(originalTimestamp, updatedTimestamp, title, content, isApproved, isHidden, categoryId)
VALUES ('2017-10-15 00:00:00', '2017-10-17 00:00:00', 'Test Post Title 1', '<p>Test Post Content 1</p>', 1, 0, 1),
	('2017-10-15 00:00:00', '2017-10-17 00:00:00', 'Test Post Title 2', '<p>Test Post Content 2</p>', 1, 0, 3),
    ('2017-10-28 00:00:00', '2017-10-30 00:00:00', 'Test Post Title 3', '<p>Test Post Content 3</p>', 1, 0, 1),
    ('2017-10-30 00:00:00', '2017-10-31 00:00:00', 'Test Post Title 4', '<p>Test Post Content 4</p>', 1, 0, 2);