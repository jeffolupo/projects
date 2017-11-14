DROP DATABASE IF EXISTS SgBlogCms_test;

CREATE DATABASE SgBlogCms_test;

USE SgBlogCms_test;

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

INSERT INTO Category(categoryId, categoryName)
VALUES (1, 'Test Category');

INSERT INTO Post(postId, originalTimestamp, updatedTimestamp, title, content, isApproved, isHidden, categoryId)
VALUES (1, '1000-01-01 00:00:00', '2000-02-02 00:00:00', 'Test Post Title', 'Test Post Content', 1, 0, 1);

INSERT INTO Hashtag(hashtagId, hashtagName)
VALUES (1, '#test');

INSERT INTO PostHashtag(postId, hashtagId)
VALUES (1, 1);

INSERT INTO `Page`(pageId, title, content)
VALUES (1, 'Test Page Title', 'Test Page Content');