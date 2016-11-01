require 'sqlite3'
require 'byebug'
require 'singleton'

class TransactionDBConnection < SQLite3::Database

  include Singleton

  def initialize
    super('transactions.db')
    self.type_translation = true
    self.results_as_hash = true
  end

end

class Transaction



  def self.all
    transactions = TransactionDBConnection.instance.execute("SELECT * FROM transactions")
    transactions.map { |transaction| Transaction.new(transaction) }
  end

  def self.find_by_account_name(account_name)
    account = Account.find_by_name(account_name)
    transactions = TransactionDBConnection.instance.execute(<<-SQL, account.id)
      SELECT
        *
      FROM
        transactions
      WHERE
        account_id = ?
    SQL
    transactions.map { |transaction| Transaction.new(transaction) }
  end

  def initialize(options)
    @id = options['id']
    @trans_date = options['trans_date']
    @post_date = options['post_date']
    @description = options['description']
    @amount = options['amount']
    @account_id = options['account_id']
  end

  def create
    raise "#{self} is already in the database" if @id
    TransactionDBConnection.instance.execute(<<-SQL, @trans_date, @post_date, @description, @amount, @account_id)
      INSERT INTO
        transactions (trans_date, post_date, description, amount, account_id)
      VALUES
        (?, ?, ?, ?, ?)
    SQL
    @id = TransactionDBConnection.instance.last_insert_row_id
  end

  def update
    raise "#{self} not in database" unless @id
    TransactionDBConnection.instance.execute(<<-SQL, @trans_date, @post_date, @description, @amount, @account_id, @id)
      UPDATE
        transactions
      SET
        trans_date = ?, post_date = ?, description = ?, amount = ?, account_id = ?
      WHERE
        id = ?
    SQL
  end

end

class Account

  attr_accessor :name

    def self.all
      accounts = TransactionDBConnection.instance.execute("SELECT * FROM accounts")
      accounts.map { |account| Account.new(account) }
    end

    def self.find_by_name(name)
      account = TransactionDBConnection.instance.execute(<<-SQL, name)
        SELECT
          *
        FROM
          accounts
        WHERE
          name = ?
      SQL
      return nil if account.empty?
      Account.new(account.first)
    end

    def initialize(options)
      @id = options['id']
      @name = options['name']
    end

    def create
      raise "#{self} is already in database" if @id
      TransactionDBConnection.instance.execute(<<-SQL, @name)
        INSERT INTO
          accounts (name)
        VALUES
          (?)
      SQL
      @id = TransactionDBConnection.instance.last_insert_row_id
    end

    def update
      raise "#{self} is not in database" unless @id
      TransactionDBConnection.instance.execute(<<-SQL, @name, @id)
        UPDATE
          accounts
        SET
          name = ?
        WHERE
          id = ?
      SQL
    end

    def account_balance
      result = TransactionDBConnection.instance.execute(<<-SQL, @id)
        SELECT
          SUM(transactions.amount) balance
        FROM
          accounts
        JOIN
          transactions ON transactions.account_id = accounts.id
        WHERE
          accounts.id = ?
        GROUP BY
          accounts.id
      SQL
      result.first['balance']
    end

end
