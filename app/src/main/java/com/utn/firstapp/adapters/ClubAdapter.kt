package com.utn.firstapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utn.firstapp.R
import com.utn.firstapp.entities.Club

class ClubAdapter(
    var ClubList: MutableList<Club?>?,
    var onClick: (Int) -> Unit
) : RecyclerView.Adapter<ClubAdapter.ClubHolder>() {

    class ClubHolder (v: View) : RecyclerView.ViewHolder(v)
        {
            private var view: View
            init {
                this.view = v
            }

            fun setClub(name: String){
                var txtClubName: TextView = view.findViewById(R.id.txtClub)
                txtClubName.text = name
            }
            fun setCountry(name: String){
                var txtCountry: TextView = view.findViewById(R.id.txtCountry)
                txtCountry.text = name
            }
            fun setFlag(name: String){
                lateinit var v: View
                lateinit var imageURL: String

                imageURL =
                        "https://cdn-icons-png.flaticon.com/512/968/968547.png?w=826&t=st=1683033467~exp=1683034067~hmac=8620a0ffceba94b1a2c3eb487f2ff38384bcd12c8f39088b7c3de95ac853972b"

                if (name == "Paraguay") {
                    imageURL =
                        "https://www.promiedos.com.ar/images/paises/py.png"
                }
                if (name == "Países Bajos") {
                    imageURL =
                        "https://www.promiedos.com.ar/images/paises/ho.png"
                }
                if (name == "España") {
                    imageURL =
                        "https://www.promiedos.com.ar/images/paises/es.png"
                }
                if (name == "Alemania") {
                    imageURL =
                        "https://www.promiedos.com.ar/images/paises/de.png"
                }
                if (name == "Uruguay") {
                    imageURL =
                        "https://www.promiedos.com.ar/images/paises/uy.png"
                }

                if (name == "Brasil") {
                    imageURL =
                        "https://www.promiedos.com.ar/images/paises/br.png"
                }
                if (name == "Colombia") {
                    imageURL =
                        "https://www.promiedos.com.ar/images/paises/co.png"
                }
                if (name == "Estados Unidos") {
                    imageURL =
                        "https://www.promiedos.com.ar/images/paises/us.png"
                }
                if (name == "México") {
                    imageURL =
                        "https://www.promiedos.com.ar/images/paises/mx.png"
                }
                if (name == "Chile") {
                    imageURL =
                        "https://www.promiedos.com.ar/images/paises/cl.png"
                }

                if (name == "Argentina") {
                    imageURL =
                        "https://www.promiedos.com.ar/images/paises/ar.png"
                }

                if (name == "Inglaterra") {
                    imageURL = "https://www.promiedos.com.ar/images/paises/in.png"
                }

                if (name == "Italia") {
                    imageURL =
                        "https://www.promiedos.com.ar/images/paises/it.png"
                }
                if (name == "Francia") {
                    imageURL =
                        "https://www.promiedos.com.ar/images/paises/fr.png"
                }


                var imgFlag: ImageView = view.findViewById(R.id.imgFlag)
                Glide.with(view).load(imageURL).into(imgFlag)
            }

            fun setImage(imageURL: String){
                lateinit var v: View


                var imgClubImage: ImageView = view.findViewById(R.id.imgListItem)
                Glide.with(view).load(imageURL).into(imgClubImage)

            }
            fun getCard() : CardView{
                return view.findViewById(R.id.cardClub)

            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_club, parent, false)
        return (ClubHolder(view))
    }

    override fun getItemCount(): Int {
        return ClubList?.size ?:0
    }

    override fun onBindViewHolder(holder: ClubHolder, position: Int) {

        ClubList?.get(position)?.let { holder.setClub(it.name) }
        ClubList?.get(position)?.let { holder.setImage(it.imageURL) }
        ClubList?.get(position)?.let { holder.setCountry(it.country) }
        ClubList?.get(position)?.let { holder.setFlag(it.country) }
        holder.getCard().setOnClickListener{
            onClick(position)

        }
    }

}