USE SgBlogCms_test;

SET SQL_SAFE_UPDATES = 0;

DELETE FROM `Page`;
DELETE FROM PostHashtag;
DELETE FROM Hashtag;
DELETE FROM Post;
DELETE FROM Category;

INSERT INTO Category(categoryId, categoryName) VALUES (1, 'Test Category');

INSERT INTO Post(postId, originalTimestamp, updatedTimestamp, title, content, isApproved, isHidden, categoryId) VALUES (1, '1000-01-01 00:00:00', '2000-02-02 00:00:00', 'Test Post Title', 'Test Post Content', 1, 0, 1);

INSERT INTO Hashtag(hashtagId, hashtagName) VALUES (1, '#test');

INSERT INTO PostHashtag(postId, hashtagId) VALUES (1, 1);

INSERT INTO `Page`(pageId, title, content) VALUES (1, 'Test Page Title', 'Test Page Content');

SET SQL_SAFE_UPDATES = 1;