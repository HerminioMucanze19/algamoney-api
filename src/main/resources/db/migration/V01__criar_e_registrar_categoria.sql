
CREATE TABLE categoria(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(45) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria(nome) VALUE("alimentacao");
INSERT INTO categoria(nome) VALUE("lazer");
INSERT INTO categoria(nome) VALUE("supermercado");
INSERT INTO categoria(nome) VALUE("farmacia");
INSERT INTO categoria(nome) VALUE("outros");