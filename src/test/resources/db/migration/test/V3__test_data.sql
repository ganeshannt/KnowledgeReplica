-- User creation
INSERT INTO users (id, email, first_name, last_name, password, provider, role, verified, created_at, updated_at)
VALUES (1000, 'ganeshan@gmail.com', 'ganeshan', 'nagarajan',
        '$2a$10$IfLjEruioFDGu/RGpOMuie4ad0CawX0QP3aOdzkJgAGbvGPjLWxAa', 'FORM', 'USER', TRUE,
        '2021-11-12 12:11:59.475', '2021-11-12 12:11:59.475');
INSERT INTO users (id, email, first_name, last_name, password, provider, role, verified, created_at, updated_at)
VALUES (1001, 'ram@gmail.com', 'ram', 'kumar', '$2a$10$IfLjEruioFDGu/RGpOMuie4ad0CawX0QP3aOdzkJgAGbvGPjLWxAa', 'FORM',
        'USER', TRUE, '2021-11-12 12:12:59.475', '2021-11-12 12:13:59.475');
INSERT INTO users (id, email, first_name, last_name, password, provider, role, verified, created_at, updated_at)
VALUES (1002, 'raj@gmail.com', 'raj', 'kumar', '$2a$10$IfLjEruioFDGu/RGpOMuie4ad0CawX0QP3aOdzkJgAGbvGPjLWxAa', 'FORM',
        'USER', TRUE, '2021-11-12 12:13:59.475', '2021-11-12 12:16:59.475');

-- Post Creation
INSERT INTO post
(id, content, thumbnail, title, user_id, created_at, updated_at, views, category_category, description)
VALUES (10, '<p>welcome content 1&nbsp;</p>', '1bca4ee2-d1a9-40f6-b673-03471584f7b9.png', 'welcome title 1', 1000,
        '2021-11-12 12:50:59.475', '2021-11-12 12:50:59.475', 11, 'TECHNOLOGY',
        'This is a description section and use your creativity to get some clicks. Ofcourse it is kind of a click bite section.Happy article writing.Keep in mind sharing is always caring.Good luck ... All the best. ');
INSERT INTO post
(id, content, thumbnail, title, user_id, created_at, updated_at, views, category_category, description)
VALUES (11, '<p>welcome content 2&nbsp;</p>', '1bca4ee2-d1a9-40f6-b673-03471584f7b9.png', 'welcome title 2', 1002,
        '2021-11-12 12:55:59.475', '2021-11-12 12:55:59.475', 11, 'FINANCE',
        'This is a description section and use your creativity to get some clicks. Ofcourse it is kind of a click bite section.Happy article writing.Keep in mind sharing is always caring.Good luck ... All the best. ');
INSERT INTO post
(id, content, thumbnail, title, user_id, created_at, updated_at, views, category_category, description)
VALUES (12, '<p>welcome content 3&nbsp;</p>', '1bca4ee2-d1a9-40f6-b673-03471584f7b9.png', 'welcome title 1', 1000,
        '2021-11-12 12:50:59.475', '2021-11-12 12:52:59.475', 11, 'TECHNOLOGY',
        'This is a description section and use your creativity to get some clicks. Ofcourse it is kind of a click bite section.Happy article writing.Keep in mind sharing is always caring.Good luck ... All the best. ');
INSERT INTO post
(id, content, thumbnail, title, user_id, created_at, updated_at, views, category_category, description)
VALUES (13, '<p>welcome content 4&nbsp;</p>', '1bca4ee2-d1a9-40f6-b673-03471584f7b9.png', 'welcome title 2', 1002,
        '2021-11-12 12:55:59.475', '2021-11-12 12:56:59.475', 11, 'FINANCE',
        'This is a description section and use your creativity to get some clicks. Ofcourse it is kind of a click bite section.Happy article writing.Keep in mind sharing is always caring.Good luck ... All the best. ');
INSERT INTO post
(id, content, thumbnail, title, user_id, created_at, updated_at, views, category_category, description)
VALUES (14, '<p>welcome content 3&nbsp;</p>', '1bca4ee2-d1a9-40f6-b673-03471584f7b9.png', 'welcome title 1', 1000,
        '2021-11-12 12:50:59.475', '2021-11-12 12:53:59.475', 11, 'BUSINESS',
        'This is a description section and use your creativity to get some clicks. Ofcourse it is kind of a click bite section.Happy article writing.Keep in mind sharing is always caring.Good luck ... All the best. ');
INSERT INTO post
(id, content, thumbnail, title, user_id, created_at, updated_at, views, category_category, description)
VALUES (15, '<p>welcome content 4&nbsp;</p>', '1bca4ee2-d1a9-40f6-b673-03471584f7b9.png', 'welcome title 2', 1002,
        '2021-11-12 12:55:59.475', '2021-11-12 12:57:59.475', 11, 'OTHERS',
        'This is a description section and use your creativity to get some clicks. Ofcourse it is kind of a click bite section.Happy article writing.Keep in mind sharing is always caring.Good luck ... All the best. ');
INSERT INTO post
(id, content, thumbnail, title, user_id, created_at, updated_at, views, category_category, description)
VALUES (16, '<p>welcome content 1&nbsp;</p>', '1bca4ee2-d1a9-40f6-b673-03471584f7b9.png', 'welcome title 16', 1000,
        '2021-11-12 12:51:59.475', '2021-11-12 12:58:59.475', 11, 'TECHNOLOGY',
        'This is a description section and use your creativity to get some clicks. Ofcourse it is kind of a click bite section.Happy article writing.Keep in mind sharing is always caring.Good luck ... All the best. ');
INSERT INTO post
(id, content, thumbnail, title, user_id, created_at, updated_at, views, category_category, description)
VALUES (17, '<p>welcome content 1&nbsp;</p>', '1bca4ee2-d1a9-40f6-b673-03471584f7b9.png', 'welcome title 17', 1000,
        '2021-11-12 12:52:59.475', '2021-11-12 12:59:59.475', 11, 'TECHNOLOGY',
        'This is a description section and use your creativity to get some clicks. Ofcourse it is kind of a click bite section.Happy article writing.Keep in mind sharing is always caring.Good luck ... All the best. ');
INSERT INTO post
(id, content, thumbnail, title, user_id, created_at, updated_at, views, category_category, description)
VALUES (18, '<p>welcome content 1&nbsp;</p>', '1bca4ee2-d1a9-40f6-b673-03471584f7b9.png', 'welcome title 18', 1000,
        '2021-11-12 12:53:59.475', '2021-11-12 12:57:59.475', 11, 'TECHNOLOGY',
        'This is a description section and use your creativity to get some clicks. Ofcourse it is kind of a click bite section.Happy article writing.Keep in mind sharing is always caring.Good luck ... All the best. ');
INSERT INTO post
(id, content, thumbnail, title, user_id, created_at, updated_at, views, category_category, description)
VALUES (19, '<p>welcome content 1&nbsp;</p>', '1bca4ee2-d1a9-40f6-b673-03471584f7b9.png', 'welcome title 19', 1000,
        '2021-11-12 12:54:59.475', '2021-11-12 12:56:59.475', 11, 'TECHNOLOGY',
        'This is a description section and use your creativity to get some clicks. Ofcourse it is kind of a click bite section.Happy article writing.Keep in mind sharing is always caring.Good luck ... All the best. ');
INSERT INTO post
(id, content, thumbnail, title, user_id, created_at, updated_at, views, category_category, description)
VALUES (20, '<p>welcome content 1&nbsp;</p>', '1bca4ee2-d1a9-40f6-b673-03471584f7b9.png', 'welcome title 20', 1000,
        '2021-11-12 12:52:59.475', '2021-11-12 12:55:59.475', 11, 'TECHNOLOGY',
        'This is a description section and use your creativity to get some clicks. Ofcourse it is kind of a click bite section.Happy article writing.Keep in mind sharing is always caring.Good luck ... All the best. ');


-- Comment creation
INSERT INTO comment
    (id, name, comment, post_id, created_at, updated_at)
VALUES (1, 'ganeshan', 'article comment', 10, '2021-11-12 12:58:59.475', '2021-11-12 12:58:59.475');
INSERT INTO comment
    (id, name, comment, post_id, created_at, updated_at)
VALUES (2, 'ram', 'article comment', 10, '2021-11-12 12:58:59.475', '2021-11-12 12:58:59.475');
INSERT INTO comment
    (id, name, comment, post_id, created_at, updated_at)
VALUES (3, 'ganeshan', 'article comment', 11, '2021-11-12 12:58:59.475', '2021-11-12 12:58:59.475');
INSERT INTO comment
    (id, name, comment, post_id, created_at, updated_at)
VALUES (4, 'ram', 'article comment', 11, '2021-11-12 12:58:59.475', '2021-11-12 12:58:59.475');
INSERT INTO comment
    (id, name, comment, post_id, created_at, updated_at)
VALUES (5, 'ganeshan', 'article comment', 12, '2021-11-12 12:58:59.475', '2021-11-12 12:58:59.475');
INSERT INTO comment
    (id, name, comment, post_id, created_at, updated_at)
VALUES (6, 'ram', 'article comment', 12, '2021-11-12 12:58:59.475', '2021-11-12 12:58:59.475');
INSERT INTO comment
    (id, name, comment, post_id, created_at, updated_at)
VALUES (7, 'ganeshan', 'article comment', 12, '2021-11-12 12:58:59.475', '2021-11-12 12:58:59.475');
INSERT INTO comment
    (id, name, comment, post_id, created_at, updated_at)
VALUES (8, 'ram', 'article comment', 13, '2021-11-12 12:58:59.475', '2021-11-12 12:58:59.475');

-- Contact creation
INSERT INTO contact (id, email, first_name, last_name, message, user_entity_id, created_at, updated_at)
VALUES (1, 'ganeshan@gmail.com', 'ganeshan', 'nagarajan', 'this is contact message', 1000, '2021-11-12 12:58:59.475',
        '2021-11-12 12:58:59.475');
INSERT INTO contact (id, email, first_name, last_name, message, user_entity_id, created_at, updated_at)
VALUES (2, 'ganeshan@gmail.com', 'ganeshan', 'nagarajan', 'this is contact message', 1000, '2021-11-12 12:58:59.475',
        '2021-11-12 12:59:59.475');
INSERT INTO contact (id, email, first_name, last_name, message, user_entity_id, created_at, updated_at)
VALUES (3, 'ganeshan@gmail.com', 'ganeshan', 'nagarajan', 'this is contact message', 1001, '2021-11-12 12:58:59.475',
        '2021-11-12 12:45:59.475');
INSERT INTO contact (id, email, first_name, last_name, message, user_entity_id, created_at, updated_at)
VALUES (4, 'ganeshan@gmail.com', 'ganeshan', 'nagarajan', 'this is contact message', 1001, '2021-11-12 12:58:59.475',
        '2021-11-12 12:46:59.475');
INSERT INTO contact (id, email, first_name, last_name, message, user_entity_id, created_at, updated_at)
VALUES (5, 'ganeshan@gmail.com', 'ganeshan', 'nagarajan', 'this is contact message', 1001, '2021-11-12 12:58:59.475',
        '2021-11-12 12:47:59.475');
INSERT INTO contact (id, email, first_name, last_name, message, user_entity_id, created_at, updated_at)
VALUES (6, 'ganeshan@gmail.com', 'ganeshan', 'nagarajan', 'this is contact message', 1000, '2021-11-12 12:58:59.475',
        '2021-11-12 12:48:59.475');
INSERT INTO contact (id, email, first_name, last_name, message, user_entity_id, created_at, updated_at)
VALUES (7, 'ganeshan@gmail.com', 'ganeshan', 'nagarajan', 'this is contact message', 1000, '2021-11-12 12:58:59.475',
        '2021-11-12 12:49:59.475');
INSERT INTO contact (id, email, first_name, last_name, message, user_entity_id, created_at, updated_at)
VALUES (8, 'ganeshan@gmail.com', 'ganeshan', 'nagarajan', 'this is contact message', 1000, '2021-11-12 12:58:59.475',
        '2021-11-12 12:50:59.475');

-- Newsletter creation
INSERT INTO newsletter (email, news_letter_status, created_at, updated_at)
VALUES ('ganeshan@gmail.com', 'SUBSCRIBED', '2021-11-12 12:58:59.475', '2021-11-12 12:58:59.475');
INSERT INTO newsletter (email, news_letter_status, created_at, updated_at)
VALUES ('ganeshannt@gmail.com', 'SUBSCRIBED', '2021-11-12 12:58:59.475', '2021-11-12 12:58:59.475');
INSERT INTO newsletter (email, news_letter_status, created_at, updated_at)
VALUES ('ganesh@gmail.com', 'SUBSCRIBED', '2021-11-12 12:48:59.475', '2021-11-12 12:58:59.475');
INSERT INTO newsletter (email, news_letter_status, created_at, updated_at)
VALUES ('nagarajan@gmail.com', 'SUBSCRIBED', '2021-11-12 12:58:59.475', '2021-11-12 12:58:59.475');
INSERT INTO newsletter (email, news_letter_status, created_at, updated_at)
VALUES ('google@gmail.com', 'SUBSCRIBED', '2021-11-12 12:38:59.475', '2021-11-12 12:58:59.475');
INSERT INTO newsletter (email, news_letter_status, created_at, updated_at)
VALUES ('yahoo@gmail.com', 'SUBSCRIBED', '2021-11-12 12:28:59.475', '2021-11-12 12:58:59.475');
INSERT INTO newsletter (email, news_letter_status, created_at, updated_at)
VALUES ('ak@gmail.com', 'SUBSCRIBED', '2021-11-12 12:18:59.475', '2021-11-12 12:58:59.475');
INSERT INTO newsletter (email, news_letter_status, created_at, updated_at)
VALUES ('ram@gmail.com', 'SUBSCRIBED', '2021-11-12 12:08:59.475', '2021-11-12 12:58:59.475');