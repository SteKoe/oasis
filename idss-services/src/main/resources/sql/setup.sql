INSERT INTO ` SystemRole `
       VALUES (
              'e4d00da9-0efc-4a78-8f2e-f1ea85ae1566',
              'USER') , ( 'fead0981-1280-48a7-ac3c-538df87e3340' , 'ADMIN' );
INSERT INTO ` ProjectRole `
       VALUES (
              'a5447b50-3120-11e3-aa6e-0800200c9a66',
              'LEADER') , ( 'acfd1af0-3120-11e3-aa6e-0800200c9a66' , 'MEMBER' );

INSERT INTO ` UserProfile ` VALUES (
                                   'fead0981-1280-48a7-ac3c-538df87e3340',
                                   NULL,
                                   NULL,
                                   NULL);
INSERT INTO ` UserProfile ` VALUES (
                                   '9202d0e0-3446-11e3-aa6e-0800200c9a66',
                                   NULL,
                                   NULL,
                                   NULL);

INSERT INTO ` User `
       VALUES (
              'fead0981-1280-48a7-ac3c-538df87e3340',
              NULL,
              'admin@example.com',
              '$2a$10$IJb9d/WeOAREJ81OPMs8hOkDYxtn7OUxLZQHzhi4rukLZAyItQO1O',
              'TEST',
              'Test Leader');
INSERT INTO ` User `
       VALUES (
              '9202d0e0-3446-11e3-aa6e-0800200c9a66',
              NULL,
              'user@example.com',
              '$2a$10$IJb9d/WeOAREJ81OPMs8hOkDYxtn7OUxLZQHzhi4rukLZAyItQO1O',
              'TEST',
              'Test Member');
INSERT INTO ` UserToSystemRole `
       VALUES (
              'fead0981-1280-48a7-ac3c-538df87e3340',
              'fead0981-1280-48a7-ac3c-538df87e3340');
INSERT INTO ` UserToSystemRole `
       VALUES (
              '9202d0e0-3446-11e3-aa6e-0800200c9a66',
              'e4d00da9-0efc-4a78-8f2e-f1ea85ae1566');