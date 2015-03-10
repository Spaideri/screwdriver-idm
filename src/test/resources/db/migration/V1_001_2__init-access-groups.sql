INSERT INTO accessGroup (name) VALUES ('Super users');
INSERT INTO accessGroup (name) VALUES ('Test group');

INSERT INTO account_accessGroup (accounts_id, accessGroups_id) VALUES (
  (SELECT id FROM account WHERE username = 'testUsername'),
  (SELECT id FROM accessGroup WHERE name = 'Super users'));