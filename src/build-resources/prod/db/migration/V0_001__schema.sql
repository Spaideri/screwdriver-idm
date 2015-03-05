CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    username VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL
);

CREATE TABLE accessGroup (
  id SERIAL PRIMARY KEY,
  name VARCHAR(200) NOT NULL
);

CREATE TABLE account_accessGroup (
  accounts_id INTEGER NOT NULL,
  accessGroups_id INTEGER NOT NULL,
  CONSTRAINT accountId_accessGroupId PRIMARY KEY(accounts_id, accessGroups_id)
);

ALTER TABLE account_accessGroup ADD CONSTRAINT fkAccessGroupAccount FOREIGN KEY (accounts_id) REFERENCES account (id);
ALTER TABLE account_accessGroup ADD CONSTRAINT fkAccountAccessGroup FOREIGN KEY (accessGroups_id) REFERENCES accessGroup (id);