package com.utn.firstapp.entities

class ClubRepository {

    var clubList = mutableListOf<Club>()
    init {
        clubList.add(Club("San Lorenzo de Almagro", "1 de Abril de 1908", "Argentina", "El Ciclón", "https://www.promiedos.com.ar/images/64/19.png", "Liga Profesional de Fútbol", 1))
        clubList.add(Club("Boca Juniors", "3 de Abril de 1905", "Bolivia", "El Xeneize", "https://www.promiedos.com.ar/images/64/6.png", "Liga Profesional de Fútbol", 2))
        clubList.add(Club("River Plate", "25 de Mayo de 1901", "Argentina", "El Millonario", "https://www.promiedos.com.ar/images/64/18.png", "Liga Profesional de Fútbol", 3))
        clubList.add(Club("Independiente", "1 de Enero de 1905", "Argentina", "El Rojo", "https://www.promiedos.com.ar/images/64/12.png", "Liga Profesional de Fútbol",4))
        clubList.add(Club("Racing Club", "25 de Marzo de 1903", "Argentina", "La Academia", "https://www.promiedos.com.ar/images/64/17.png", "Liga Profesional de Fútbol", 5))
        clubList.add(Club("Estudiantes de La Plata", "4 de Agosto de 1905", "Argentina", "El Pincha", "https://www.promiedos.com.ar/images/64/8.png", "Liga Profesional de Fútbol", 6))
        clubList.add(Club("Vélez Sarsfield", "1 de Enero de 1910", "Argentina", "El Fortín", "https://www.promiedos.com.ar/images/64/21.png", "Liga Profesional de Fútbol", 7))
        clubList.add(Club("Rosario Central", "24 de Diciembre de 1889", "Argentina", "El Canalla", "https://www.promiedos.com.ar/images/64/35.png", "Liga Profesional de Fútbol", 8))
        clubList.add(Club("Newell's Old Boys", "3 de Noviembre de 1903", "Argentina", "La Lepra", "https://www.promiedos.com.ar/images/64/14.png", "Liga Profesional de Fútbol", 9))
        clubList.add(Club("Huracán", "1 de Noviembre de 1908", "Argentina", "El Globo", "https://www.promiedos.com.ar/images/64/11.png", "Liga Profesional de Fútbol", 10))
        clubList.add(Club("Lanús", "3 de Enero de 1915", "Argentina", "El Granate", "https://www.promiedos.com.ar/images/64/13.png", "Liga Profesional de Fútbol", 11))
        clubList.add(Club("Talleres", "12 de Septiembre de 1913", "Argentina", "El Matador", "https://www.promiedos.com.ar/images/64/52.png", "Liga Profesional de Fútbol", 12))
        clubList.add(Club("Colón", "05 de Mayo de 1905", "Argentina", "El Sabalero", "https://www.promiedos.com.ar/images/64/7.png", "Liga Profesional de Fútbol", 13))
        clubList.add(Club("Banfield", "21 de Enero de 1896", "Argentina", "El Taladro", "https://www.promiedos.com.ar/images/64/5.png", "Liga Profesional de Fútbol", 14))
    }
}