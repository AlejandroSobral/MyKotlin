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
    var ClubList: MutableList<Club>,
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
            fun setCountryFlag(imageurl: String){

                var imgFlag: ImageView = view.findViewById(R.id.imgFlag)
                Glide.with(view).load(imageurl).into(imgFlag)
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
        ClubList?.get(position)?.let { holder.setImage(it.imageurl) }
        ClubList?.get(position)?.let { holder.setCountry(it.country) }
        ClubList?.get(position)?.let { holder.setCountryFlag(it.countryflag) }
        holder.getCard().setOnClickListener{
            onClick(position)

        }
    }

}