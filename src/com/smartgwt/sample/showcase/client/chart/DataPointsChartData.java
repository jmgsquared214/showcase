/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright 2000 and beyond Isomorphic Software, Inc.
 *
 * OWNERSHIP NOTICE
 * Isomorphic Software owns and reserves all rights not expressly granted in this source code,
 * including all intellectual property rights to the structure, sequence, and format of this code
 * and to all designs, interfaces, algorithms, schema, protocols, and inventions expressed herein.
 *
 *  If you have any questions, please email <sourcecode@isomorphic.com>.
 *
 *  This entire comment must accompany any portion of Isomorphic Software source code that is
 *  copied or moved from this file.
 */
package com.smartgwt.sample.showcase.client.chart;

import com.smartgwt.client.data.Record;

public class DataPointsChartData extends Record {

    public DataPointsChartData(String picture, String commonName, String information, Integer lifeSpan, String scientificName, String diet, String status) {
        setAttribute("picture", picture);
        setAttribute("commonName", commonName);
        setAttribute("information", information);
        setAttribute("lifeSpan", lifeSpan);
        setAttribute("scientificName", scientificName);
        setAttribute("diet", diet);
        setAttribute("status", status);
    }

    public static DataPointsChartData[] getData() {
        return new DataPointsChartData[] {
            new DataPointsChartData(
                    "Elephant.jpg",
                    "Elephant (African)",
                    "The African Elephant is the largest of all land animals and also has the biggest brain of any land animal. Both males and females have ivory tusks. Elephants are also wonderful swimmers. Man is the only real enemy of the elephant. Man threatens the elephant by killing it for its tusks and by destroying its habitat.",
                    60,
                    "Loxodonta africana",
                    "Herbivore",
                    "Threatened"),
            new DataPointsChartData(
                    "Alligator.jpg",
                    "Alligator (American)",
                    "In the 16th century, Spanish explorers in what is now Florida encountered a large formidable animal which they called \"el largo\" meaning \"the lizard\". The name \"el largo\" gradually became pronounced \"alligator\".",
                    50,
                    "Allligator mississippiensis",
                    "Carnivore",
                    "Not Endangered"),
            new DataPointsChartData(
                    "AntEater.jpg",
                    "Anteater",
                    "Anteaters can eat up to 35,000 ants daily. Tongue is around 2 feet long and is not sticky but rather covered with saliva. They have very strong sharp claws used for digging up anthills and termite mounds.",
                    25,
                    "Myrmecophaga tridactyla",
                    "Ground dwelling ants/termites",
                    "Not Endangered"),
            new DataPointsChartData(
                    "Camel.jpg",
                    "Arabian Camel",
                    "Can eat any vegetation including thorns. Has one hump for fat storage. Is well known as a beast of burden.",
                    50,
                    "Camelus dromedarius",
                    "Herbivore",
                    "Not Endangered"),
            new DataPointsChartData(
                    "BaldEagle.jpg",
                    "Bald Eagle",
                    "Females lay one to three eggs. Visual acuity is 3-4 times greater than a human. Bald eagles build the largest nest of any North American bird. The largest nest found was 3.2 yds (2.9 m) in diameter and 6.7 yds (6.1 m) tall. Protection of the Bald Eagle is afforded by three federal laws: (1) the Endangered Species Act, (2) the Bald Eagle and Golden Eagle Protection Act, and (3) the Migratory Bird Treaty Act.",
                    50,
                    "southern subspecies: Haliaeetus leucocephalus leuc",
                    "Carnivore",
                    "Endangered"),
            new DataPointsChartData(
                    "BlackSpiderMonkey.jpg",
                    "Black Spider Monkey",
                    "They can perform remarkable feats with their tails.",
                    20,
                    "Ateles panicus",
                    "Herbivore",
                    "Not Endangered"),
            new DataPointsChartData(
                    "BottlenoseDolphin.jpg",
                    "Bottlenosed Dolphin",
                    "The bottlenosed dolphin is classified as a toothed whale. They are excellent hunters. They use sound waves to \"see\" their environment and where they are going through a process known as echolocation.",
                    35,
                    "Tursiops truncatus",
                    "Fish shrimp eels and squid",
                    "Threatened"),
            new DataPointsChartData(
                    "BoaConstrictor.jpg",
                    "Boa Constrictor",
                    "Boas kill by constriction, actually suffocating rather than crushing their prey. Spend time in trees but are primarily ground dwellers.",
                    40,
                    "Boa constrictor constrictor",
                    "Carnivore",
                    "Not Endangered"),
            new DataPointsChartData(
                    "ScreechOwl.jpg",
                    "Screech Owl",
                    "Generally does not migrate, most common in North America. Three color phases: red, grey and brown.",
                    10,
                    "Otus asio",
                    "Carnivore",
                    "Not Endangered"),
            new DataPointsChartData(
                    "CostasHummingbird.jpg",
                    "Costas Hummingbird",
                    "Costas habit of soaring between flower beds is helpful in distinguishing it. Has a preference for red flowers. Males do not participate in nesting or raising young. Of the seven major species breeding in the West, the Costas prefers the driest climates. Nests are frequently held together with spider webs.",
                    12,
                    "Calypte costae",
                    "Nectar and small insects",
                    "Not Endangered"),
            new DataPointsChartData(
                    "CubanGroundIguana.jpg",
                    "Cuban Iguana",
                    "Easily become obese in captivity so must be fed sparingly. The ability to store up fat helps them to survive during times of drought.",
                    10,
                    "Cyclura nubila nubila",
                    "Fruit and small vertebrates",
                    "Threatened"),
            new DataPointsChartData(
                    "DesertIguana.jpg",
                    "Desert Iguana",
                    "Require much heat during the day. When in danger they flee by running in part on hind legs only. Able to remain active up to 115 degrees F; cool off by climbing into low shrubs.",
                    10,
                    "Dipsosaurus dorsalis",
                    "Plants and insects",
                    "Not Endangered"),
            new DataPointsChartData(
                    "Emu.jpg",
                    "Emu",
                    "The Emu is Australias largest bird and is second in size only to the ostrich. The Emu does not fly, but can run up to 30 mph (50 kph).",
                    20,
                    "Dromaius novaehollandiae",
                    "Herbivore",
                    "Not currently listed"),
            new DataPointsChartData(
                    "Stingray.jpg",
                    "Freshwater Stingray",
                    "Locate their prey by using the digging motion of their pectoral fins to excavate the substrate to expose the animals buried in the sand.",
                    9,
                    "Potamotrygen motoro",
                    "Crustaceans worms and mollusks",
                    "Not Endangered"),
            new DataPointsChartData(
                    "Octopus.jpg",
                    "Giant Octopus",
                    "This is one of the largest known species of Octopus. Its diet will include almost anything it can overpower. Octopuses constantly flash many colors to blend in instantly with their surroundings. The female dies shortly after laying her eggs.",
                    3,
                    "Octopus dofleini",
                    "Carnivore",
                    "Not endangered"),
            new DataPointsChartData(
                    "Zebra.jpg",
                    "Zebra (Grants Zebra)",
                    "The stripe patterns of a zebra distinguish one subspecies from another. Zebras have a high resistance to the effects of drought, and they can eat dried grasses. The young are weaned at 6-8 months. Lions and hyenas are enemies.",
                    20,
                    "Equus burchelli bohmi",
                    "Herbivore",
                    "Not currently listed"),
            new DataPointsChartData(
                    "Baboon.jpg",
                    "Guinea Baboon",
                    "These baboons live in large groups which consist of a hierarchical group structure based on a dominating male while breeding occurs; after breeding, the male leaves and a dominant female leads the troop. Much of their time is spent in feeding, grooming, and sleeping.",
                    30,
                    "Papio papio",
                    "Omnivore",
                    "May become threatened"),
            new DataPointsChartData(
                    "HowlerMonkey.jpg",
                    "Howler Monkey",
                    " These animals produce a low growling sound that has been compared to the roar of a lion. These calls can be heard up to 3 km through the forest and are used to communicate danger as well as keeping the troop together.",
                    20,
                    "Alouatta spp.",
                    "Herbivore",
                    "Endangered"),
            new DataPointsChartData(
                    "IndianRockPython.jpg",
                    "Indian Rock Python",
                    "Can become very tame. The Python species are covered by import regulations and are therefore most available as captive bred specimens. Frightening numbers of these snakes have been killed for the leather trade.",
                    30,
                    "Python molurus molurus",
                    "Carnivore",
                    "Not Endangered"),
            new DataPointsChartData(
                    "Koala.jpg",
                    "Koala",
                    "Koalas do not require water. Males larger than females. Noctural/crepuscular, sleeps up to 19 hours a day. Young are carried on mothers back after leaving pouch.",
                    15,
                    "Phascolarctos cinereus",
                    "Herbivore (Eucalyptus leaves)",
                    "Protected"),
            new DataPointsChartData(
                    "Lion.jpg",
                    "Lion",
                    "Males have manes on cheeks and throat, some species have manes on shoulders and bellies. Young lions have leopard like markings. A pride usually consists of 4-12 related adult females, their offspring and 1-6 adult males. Lions eat animals weighing 110 or 1100 lbs.",
                    15,
                    "Panthera leo - various subspecies",
                    "Carnivore",
                    "Endangered"),
            new DataPointsChartData(
                    "MarbledSalamander.jpg",
                    "Salamander",
                    "It is a beautiful animal, black with white or silvery bands. Lives well in captivity.",
                    8,
                    "Ambystoma opacum",
                    "Carnivore",
                    "Not Endangered"),
            new DataPointsChartData(
                    "Orangutan.jpg",
                    "Orangutan",
                    "Orangutan means \"man of the forest\". Enemies include tigers, clouded leopard, humans.",
                    50,
                    "Pongo pygmaeus",
                    "Fruits, Vegetation and Birds eggs",
                    "Endangered"),
            new DataPointsChartData(
                    "Ostrich.jpg",
                    "Ostrich",
                    "Largest of living birds. Adult males may exceed 8 feet in height and 300 lbs. in weight. In Egypt, the ostrich was the emblem of the Goddess of Truth and Justice.",
                    50,
                    "Struthio camelus",
                    "Insects, Fruit, seeds and grasses",
                    "Endangered"),
            new DataPointsChartData(
                    "Piranha.jpg",
                    "Piranha",
                    "Afraid of nothing; will attack any animal regardless of size. Is attracted by the smell of blood. Considered the most dangerous fish in the Amazon",
                    10,
                    "Plecostomus - Hypostomus plecostomus",
                    "Carnivore (mainly smaller fish)",
                    "Not Endangered"),
            new DataPointsChartData(
                    "Platypus.jpg",
                    "Platypus",
                    "Were thought to be a hoax when first discovered. The male has a venemous spur on his hind legs.  Capable of many vocalizations including sounds like a growling puppy and a brooding hen.",
                    15,
                    "Ornithorhynchus Anatinus",
                    "Insects and Larvae",
                    "Not Endangered"),
            new DataPointsChartData(
                    "PolarBear.jpg",
                    "Polar Bear",
                    " The polar bear is the largest non-aquatic carnivore. Its stomach can hold more than 150 lbs of food. The polar bears fur is water repellant and the individual hairs are clear due to hollow hair shafts. The feet of the polar bear serve as snow boots and as paddles when they are in the water.",
                    40,
                    "Ursus maritimus",
                    "Carnivore",
                    "Protected"),
            new DataPointsChartData(
                    "Tarantula.jpg",
                    "Red Legged Tarantula",
                    "Tarantulas shed their skin about once a month as hatchlings and will slow down to about once every year or two as adults. Their number one enemy is the Spider Hunting Wasp.",
                    20,
                    "Brachypelma smithi",
                    "Insects, small reptiles and mammals",
                    "Not Endangered"),
            new DataPointsChartData(
                    "Gazelle.jpg",
                    "Thomsons Gazelle",
                    "The stiff-legged bouncing movement common to gazelles is called stotting or pronking. They can run quickly, 36 mph (57.6 km/hr), and if fleeing from an enemy, they can run as fast as 48 mph (76.8 km/hr).",
                    10,
                    "Gazella thomsoni",
                    "Herbivore",
                    "Not currently listed")
        };
    }
}
