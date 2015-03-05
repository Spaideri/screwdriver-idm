INSERT INTO accessGroup (name) VALUES ('Admins');
INSERT INTO accessGroup (name) VALUES ('Basic users');

INSERT INTO account_accessGroup (accounts_id, accessGroups_id) VALUES (
  (SELECT id FROM account WHERE username = 'test1'),
  (SELECT id FROM accessGroup WHERE name = 'Admins'));

INSERT INTO account_accessGroup (accounts_id, accessGroups_id) VALUES (
  (SELECT id FROM account WHERE username = 'test1'),
  (SELECT id FROM accessGroup WHERE name = 'Basic users'));

INSERT INTO account_accessGroup (accounts_id, accessGroups_id) VALUES (
  (SELECT id FROM account WHERE username = 'test2'),
  (SELECT id FROM accessGroup WHERE name = 'Basic users'));