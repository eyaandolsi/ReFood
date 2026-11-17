-- Une base par microservice MySQL (MS01, MS04, MS05)
CREATE DATABASE IF NOT EXISTS refood_offers    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS refood_donations CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS refood_recipes   CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'refood'@'%' IDENTIFIED BY 'refood';
GRANT ALL PRIVILEGES ON refood_offers.*    TO 'refood'@'%';
GRANT ALL PRIVILEGES ON refood_donations.* TO 'refood'@'%';
GRANT ALL PRIVILEGES ON refood_recipes.*   TO 'refood'@'%';
FLUSH PRIVILEGES;
