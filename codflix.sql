-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 25 juin 2020 à 20:14
-- Version du serveur :  10.4.13-MariaDB
-- Version de PHP : 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `codflix`
--

-- --------------------------------------------------------

--
-- Structure de la table `episode`
--

CREATE TABLE `episode` (
  `id` int(11) NOT NULL,
  `title` varchar(100) CHARACTER SET latin1 NOT NULL,
  `episode_number` int(11) NOT NULL,
  `season_number` int(11) NOT NULL,
  `episode_url` varchar(100) CHARACTER SET latin1 NOT NULL,
  `media_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `episode`
--

INSERT INTO `episode` (`id`, `title`, `episode_number`, `season_number`, `episode_url`, `media_id`) VALUES
(1, 'Lacrim - Force & Honneur', 1, 1, 'https://www.youtube.com/embed/CvtQkprxprI', 4),
(2, 'Lacrim - Force & Honneur', 2, 1, 'https://www.youtube.com/embed/kBj31rqR2j4', 4),
(3, 'Lacrim - Force & Honneur', 1, 2, 'https://www.youtube.com/embed/unhKZ8ewWMk', 4);

-- --------------------------------------------------------

--
-- Structure de la table `genre`
--

CREATE TABLE `genre` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `genre`
--

INSERT INTO `genre` (`id`, `name`) VALUES
(1, 'Action'),
(2, 'Horreur'),
(3, 'Science-Fiction');

-- --------------------------------------------------------

--
-- Structure de la table `history`
--

CREATE TABLE `history` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `media_id` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `finish_date` datetime DEFAULT NULL,
  `watch_duration` int(11) NOT NULL DEFAULT 0 COMMENT 'in seconds'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `history`
--

INSERT INTO `history` (`id`, `user_id`, `media_id`, `start_date`, `finish_date`, `watch_duration`) VALUES
(6, 9, 4, '2020-06-25 00:00:00', NULL, 938);

-- --------------------------------------------------------

--
-- Structure de la table `media`
--

CREATE TABLE `media` (
  `id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `type` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  `release_date` date NOT NULL,
  `summary` longtext NOT NULL,
  `trailer_url` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `media`
--

INSERT INTO `media` (`id`, `genre_id`, `title`, `type`, `status`, `release_date`, `summary`, `trailer_url`) VALUES
(2, 2, 'SAW V', 'movie', 'released', '2008-09-05', 'Convicted murderer Seth Baxter wakes up chained to a table beneath a pendulum blade. A videotape informs him that he can release himself by crushing his hands between two presses. He does so, but the blade still swings down and cuts him in half as someone watches through a hole in the wall.\r\n\r\nAfter being locked in the sickroom at the meatpacking plant by Hoffman, FBI Agent Peter Strahm escapes through a hidden doorway but is attacked in the tunnel by a figure in a pig mask. He wakes up with his head sealed in a box being filled with water, but survives by performing a tracheotomy with his pen. Outside, Hoffman delivers Corbett Denlon to the police and claims they are the only survivors, but is shocked when Strahm is brought out alive as well.\r\n\r\nDuring a press conference, Hoffman is promoted to detective lieutenant and credited with closing the case. He finds a note in his office that reads \"I know who you are\", and he learns of Lindsey Perez\'s death while taking Strahm\'s phone. At the hospital, Strahm tells Hoffman that Perez\'s last words were \"Detective Hoffman\" and questions how he escaped the plant. Strahm is also visited by his boss, Dan Erickson, who puts him on medical leave. Now suspicious of Hoffman and determined to piece together his involvement with Jigsaw, Strahm takes case files of past victims and begins researching them on his own.\r\n\r\nIn an underground sewer level, five people awaken with collars connected to mounted blades locked around their necks, the keys to which are in individual glass boxes across the room. A videotape says that all five of them are connected and implores them to \"do the opposite\" of their instincts throughout the game. They are also told to solve the puzzles and leave the rooms before the timer runs out, and the jars of explosives in each corner of the rooms will explode. Mallick, an arsonist, activates the timer and everyone must reach for their key, but the wires are connected to the other participants, so they must go one at a time. Everyone manages to reach their keys except for Ashley, a former fire inspector, who is decapitated when the collars retract. The four proceed to the next room, where they learn that the overhead jars contain keys to three bomb shelters. Charles, an investigative journalist, strikes Mallick and begins smashing the jars one by one. Brit, a real estate vice president, and Luba, a city planner, each find a key and Charles takes Mallick\'s, only to be struck by Luba and left to die when the bombs detonate. In the third room, five short cables must be connected to a full bathtub to unlock the door. They deduce that someone will have to stay in the bathtub while the other two escape, so Luba attacks Mallick to use his body to close the circuits, but Brit stabs her and they use her body to open the door. In the fourth room, Mallick and Brit find a machine fitted with five circular saws and a beaker requiring ten pints of blood to open the door. Brit tries to use the keys from the first room to open the door, but sees all the keys are the same, Mallick notes the five holes and they realize that all five of them were meant to work together to survive the game: in the first room, they could have used the keys on each other to unlock the collars, in the second room the bomb shelters were big enough for more than one person, and in the third room they all needed to attach a cable to themselves, which would open the door. Brit also works out their connection: they were all involved with a building fire that killed eight people. With no other options, they saw their arms to provide the blood needed to leave the room.\r\n\r\nMeanwhile, Strahm learns that Baxter killed Hoffman\'s sister and was released from prison on a technicality. As revenge, Hoffman abducted and killed Baxter, using the pendulum trap to frame Jigsaw. It is revealed through flashbacks that he was later abducted by John Kramer, who blackmailed Hoffman into working with him. They set up most of the games together, with Hoffman planting Lawrence Gordon\'s penlight and providing the police files for the nerve gas house victims. Strahm realizes in the end that everyone was meant to die in the plant except for Corbett and Hoffman, who would appear to be a hero. His activities soon draw Erickson\'s concern, which is further fueled when Jill, who received a box and a videotape from John\'s will, approaches Erickson and claims Strahm is stalking her. After Hoffman calls Erickson to tell him about Strahm\'s theory of a second accomplice, Erickson tries to call Strahm but Hoffman, having Strahm\'s cellphone, turns it off. After trying to reach Strahm but no avail, Erickson puts a tracker on Strahm\'s cellphone.\r\n\r\nErickson follows the tracker to the observation room and finds the cellphone and his own personnel file, which were planted by Hoffman. He also finds Brit, who managed to crawl from the fourth room after Mallick passed out from blood loss. Erickson calls for medical attention for the victims, and he puts out an all-points bulletin on Strahm. Meanwhile, Strahm follows Hoffman to the renovated nerve gas house and finds an underground room containing a clear box filled with shards of broken glass. Hoffman\'s tape urges Strahm to enter the box, but he stops it short and ambushes Hoffman. After a brief struggle he seals Hoffman in the box, which seals the room\'s exit. Hoffman points to the tape, which warns Strahm that if he does not enter the box he will die and Jigsaw\'s legacy will become his. The box is safely lowered into the floor as the walls close in on Strahm, who unsuccessfully attempts to escape through the ceiling grid and is crushed to death. ', 'https://www.youtube.com/embed/3MiXFmlVPOY'),
(3, 3, 'Star Wars : A New Hope', 'movie', 'released', '1977-10-19', 'A young boy discover the way of the Force to destroy a great Evil', 'https://www.youtube.com/embed/1g3_CFmnU7k'),
(4, 1, 'Force & Honneur', 'serie', 'released', '2017-02-02', 'Toujours dans cet univers carcéral proche du polar, Lacrim joue le rôle d’un trafiquant intraitable. Le premier épisode de la saison 2 s’ouvre sur des coups de téléphone étranges qui laissent entrevoir les contours de cette nouvelle saison. Cette fois, Lacrim est libre, et il est plus déterminé que jamais à se débarrasser de ses rivaux. Il rêve d’agrandir son empire, mais ses projets risquent de lui coûter cher…', 'https://www.youtube.com/embed/CvtQkprxprI');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(254) NOT NULL,
  `password` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `password`) VALUES
(9, 'coding@gmail.com', '$2a$10$xbSni6rhJJGDjBnEW0ksxegGhWXUAVaxKXtsgQl0QAhi4Ax3uGjKi'),
(10, 'codingfac@gmail.com', '$2a$10$6H/EJ5I.sPFHLRhzAdeWW.j4IT80.j9T/JIII9inrHE.FKi9cxThu');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `episode`
--
ALTER TABLE `episode`
  ADD PRIMARY KEY (`id`),
  ADD KEY `media_id` (`media_id`),
  ADD KEY `media_id_2` (`media_id`);

--
-- Index pour la table `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `history_user_id_fk_media_id` (`user_id`),
  ADD KEY `history_media_id_fk_media_id` (`media_id`);

--
-- Index pour la table `media`
--
ALTER TABLE `media`
  ADD PRIMARY KEY (`id`),
  ADD KEY `media_genre_id_fk_genre_id` (`genre_id`) USING BTREE;

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `episode`
--
ALTER TABLE `episode`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `genre`
--
ALTER TABLE `genre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `history`
--
ALTER TABLE `history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `media`
--
ALTER TABLE `media`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `episode`
--
ALTER TABLE `episode`
  ADD CONSTRAINT `media_id` FOREIGN KEY (`media_id`) REFERENCES `media` (`id`);

--
-- Contraintes pour la table `history`
--
ALTER TABLE `history`
  ADD CONSTRAINT `history_media_id_fk_media_id` FOREIGN KEY (`media_id`) REFERENCES `media` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `history_user_id_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `media`
--
ALTER TABLE `media`
  ADD CONSTRAINT `media_genre_id_b1257088_fk_genre_id` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
