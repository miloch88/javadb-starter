-- -----------------------------------------------------
-- Table library.users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS library.users (
  id INT NOT NULL AUTO_INCREMENT,
  login VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  password VARCHAR(45) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  name VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  is_admin BOOLEAN NOT NULL,
PRIMARY KEY (id))
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table library.categories
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS library.categories (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
PRIMARY KEY (id))
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table library.books
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS library.books (
  id INT NOT NULL AUTO_INCREMENT,
  category_id INT NOT NULL,
  title VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  author VARCHAR(70) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (category_id)
REFERENCES library.categories(id)
ON DELETE NO ACTION
ON UPDATE NO ACTION)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table library.orders
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS library.orders (
  id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  book_id INT NOT NULL,
  order_date DATETIME NOT NULL,
  return_date DATETIME NULL,
PRIMARY KEY (id),
FOREIGN KEY (user_id)
REFERENCES library.users(id)
ON DELETE NO ACTION
ON UPDATE NO ACTION,
FOREIGN KEY (book_id)
REFERENCES library.books(id)
ON DELETE NO ACTION
ON UPDATE NO ACTION)
  ENGINE = InnoDB;