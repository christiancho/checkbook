DROP TABLE IF EXISTS transactions;

CREATE TABLE transactions (
  id INTEGER PRIMARY KEY,
  trans_date DATE NOT NULL,
  post_date DATE,
  description VARCHAR(255) NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  account_id INTEGER NOT NULL,

  FOREIGN KEY (account_id) REFERENCES accounts(id)
);

DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
  id INTEGER PRIMARY KEY,
  name TEXT NOT NULL
);
