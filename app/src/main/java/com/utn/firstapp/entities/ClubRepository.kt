package com.utn.firstapp.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ClubRepository : Parcelable {

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
        clubList.add(Club("Central Cba (SdE)", "03 Junio de 1919", "Argentina", "El Ferroviario", "https://www.promiedos.com.ar/images/64/78.png", "Liga Profesional de Fútbol", 15))
        clubList.add(Club("Argentinos Juniors", "15 Agosto de 1904", "Argentina", "El Bicho", "https://www.promiedos.com.ar/images/64/3.png", "Liga Profesional de Fútbol", 16))
        clubList.add(Club("Arsenal", "11 Enero de 1957", "Argentina", "El Arse", "https://www.promiedos.com.ar/images/64/4.png", "Liga Profesional de Fútbol", 17))
        clubList.add(Club("Atlético Tucuman", "27 Septiembre de 1902", "Argentina", "El Decano", "https://www.promiedos.com.ar/images/64/25.png", "Liga Profesional de Fútbol", 18))
        clubList.add(Club("Barracas Central", "5 Abril de 1904", "Argentina", "El Camionero", "https://www.promiedos.com.ar/images/64/82.png", "Liga Profesional de Fútbol", 19))
        clubList.add(Club("Club Atlético Belgrano", "19 Marzo de 1905", "Argentina", "El Pirata", "https://www.promiedos.com.ar/images/64/26.png", "Liga Profesional de Fútbol", 20))
        clubList.add(Club("Club y Social Deportivo Defensa y Justicia", "20 Marzo de 1935", "Argentina", "El Halcón", "https://www.promiedos.com.ar/images/64/29.png", "Liga Profesional de Fútbol", 21))
        clubList.add(Club("Club de Gimnasia y Esgrima La Plata", "03 Junio de 1887", "Argentina", "El Lobo", "https://www.promiedos.com.ar/images/64/9.png", "Liga Profesional de Fútbol", 22))
        clubList.add(Club("Club Deportivo Godoy Cruz Antonio Tomba", "01 Junio de 1921", "Argentina", "El Tomba", "https://www.promiedos.com.ar/images/64/10.png", "Liga Profesional de Fútbol", 23))
        clubList.add(Club("Instituto Atlético Central Córdoba", "08 Septiembre de 1918", "Argentina", "La Gloria", "https://www.promiedos.com.ar/images/64/33.png", "Liga Profesional de Fútbol", 24))
        clubList.add(Club("Club Atlético Platense", "25 Mayo de 1905", "Argentina", "El Calamar", "https://www.promiedos.com.ar/images/64/45.png", "Liga Profesional de Fútbol", 25))
        clubList.add(Club("Club Atlético Sarmiento", "1 Abril de 1911", "Argentina", "El Guerrero", "https://www.promiedos.com.ar/images/64/81.png", "Liga Profesional de Fútbol", 26))
        clubList.add(Club("Club Atlético Tigre", "3 Septiembre de 1902", "Argentina", "El Matador", "https://www.promiedos.com.ar/images/64/20.png", "Liga Profesional de Fútbol", 27))
        clubList.add(Club("Club Atlético Unión", "15 Abril de 1907", "Argentina", "El Tatengue", "https://www.promiedos.com.ar/images/64/39.png", "Liga Profesional de Fútbol", 28))




    }

}