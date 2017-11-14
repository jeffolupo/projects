use

delete from Post;
delete from Hashtag;
delete from Categories;
delete from `Page`;

INSERT INTO Category(categoryName)
VALUES ('Test Category'); -- categoryId = 1

INSERT INTO Post(originalTimestamp, updatedTimestamp, title, content, isApproved, isHidden, categoryId)
VALUES ('1000-01-01 00:00:00', '2000-02-02 00:00:00', 'Test Post Title', 'Test Post Content', 1, 0, 1); -- postId = 1

INSERT INTO Hashtag(hashtagName)
VALUES ('#test'); -- hashtagId = 1

INSERT INTO PostHashtag(postId, hashtagId)
VALUES (1, 1);

INSERT INTO `Page`(title, content)
VALUES ('Test Page Title', 'Test Page Content'); -- pageId = 1