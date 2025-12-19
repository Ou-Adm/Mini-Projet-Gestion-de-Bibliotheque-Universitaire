-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Ven 19 Décembre 2025 à 23:34
-- Version du serveur: 5.5.20
-- Version de PHP: 5.3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `biblio_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `adherent`
--

CREATE TABLE IF NOT EXISTS `adherent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `statut` varchar(20) DEFAULT 'ACTIF',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Contenu de la table `adherent`
--

INSERT INTO `adherent` (`id`, `nom`, `prenom`, `email`, `statut`) VALUES
(1, 'Alami', 'Ahmed', 'ahmed@estm.ma', 'ACTIF'),
(2, 'ouaaziz', 'adam', 'adamouaaziz23@gmail.com', 'ACTIF'),
(4, 'kamal', 'amjed', 'lesbian@estm.hwini', 'ACTIF'),
(5, 'ouaaziz', 'adam', 'adamouaaziz25@gmail.com', 'ACTIF'),
(6, 'houcein', 'behja', 'houcein@gmail.com', 'ACTIF'),
(7, 'charifi', 'ahmed', 'ahmed@gmail.com', 'ACTIF');

-- --------------------------------------------------------

--
-- Structure de la table `emprunt`
--

CREATE TABLE IF NOT EXISTS `emprunt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `livre_isbn` varchar(20) DEFAULT NULL,
  `adherent_id` int(11) DEFAULT NULL,
  `date_emprunt` date DEFAULT NULL,
  `date_retour_prevue` date DEFAULT NULL,
  `date_retour_effective` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `livre_isbn` (`livre_isbn`),
  KEY `adherent_id` (`adherent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Contenu de la table `emprunt`
--

INSERT INTO `emprunt` (`id`, `livre_isbn`, `adherent_id`, `date_emprunt`, `date_retour_prevue`, `date_retour_effective`) VALUES
(6, 'L01', 5, '2025-12-19', '2026-01-02', NULL),
(7, '978-2253006329', 5, '2025-12-20', '2026-01-03', NULL),
(8, '978-2070368228', 5, '2025-12-20', '2026-01-03', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `livre`
--

CREATE TABLE IF NOT EXISTS `livre` (
  `isbn` varchar(20) NOT NULL,
  `titre` varchar(200) DEFAULT NULL,
  `auteur` varchar(100) DEFAULT NULL,
  `categorie` varchar(50) DEFAULT NULL,
  `nbr_exemplaires` int(11) DEFAULT '1',
  PRIMARY KEY (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `livre`
--

INSERT INTO `livre` (`isbn`, `titre`, `auteur`, `categorie`, `nbr_exemplaires`) VALUES
('978-0132350884', 'Clean Code', 'Robert C. Martin', 'Informatique', 5),
('978-0134685991', 'Effective Java', 'Joshua Bloch', 'Informatique', 3),
('978-0201633610', 'Design Patterns', 'Erich Gamma', 'Informatique', 2),
('978-1491950357', 'Building Microservices', 'Sam Newman', 'Informatique', 4),
('978-2020005789', 'L''Étranger', 'Albert Camus', 'Roman', 6),
('978-2070368228', 'Le Petit Prince', 'Antoine de Saint-Exupéry', 'Roman', 11),
('978-2100491122', 'Algèbre Linéaire', 'Seymour Lipschutz', 'Mathématiques', 10),
('978-2100754166', 'Physique Tout-en-un', 'Marie-Noëlle Sanz', 'Physique', 3),
('978-2247160892', 'Introduction au Droit', 'Muriel Fabre-Magnan', 'Droit', 5),
('978-2253006329', '1984', 'George Orwell', 'Roman', 7),
('L01', 'Java Base', 'Oracle', 'Info', 2);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `adherent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  KEY `adherent_id` (`adherent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `login`, `password`, `role`, `adherent_id`) VALUES
(1, 'admin', 'admin123', 'ADMIN', NULL),
(5, 'hmida', '1212', 'USER', NULL),
(8, 'adam', 'adam123', 'USER', 5),
(9, 'houcein', '123', 'USER', 6),
(10, 'ahmed', '123', 'USER', 7);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `emprunt`
--
ALTER TABLE `emprunt`
  ADD CONSTRAINT `emprunt_ibfk_1` FOREIGN KEY (`livre_isbn`) REFERENCES `livre` (`isbn`),
  ADD CONSTRAINT `emprunt_ibfk_2` FOREIGN KEY (`adherent_id`) REFERENCES `adherent` (`id`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `utilisateur_ibfk_1` FOREIGN KEY (`adherent_id`) REFERENCES `adherent` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
