package com.example.allprintapp.ui.Utils

import android.R
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import java.net.MalformedURLException
import java.net.URL
import java.text.ParsePosition
import java.text.SimpleDateFormat

class Utils(var ma: Activity) {

    /*  int[] estadoIcons = new int[]{
            R.drawable.ic_menu_manage,
            R.drawable.progresso,
            R.drawable.completo,
            R.drawable.cancelado,
            R.drawable.canceladmin,
            R.drawable.espera};

    int[] estadoObj = new int[]{
            R.drawable.ic_viajoemlazer,
            R.drawable.ic_viajoemtrabalho};*/
    var estadoString = arrayOf(
        "",
        "Progresso",
        "Completo",
        "Cancelado",
        "Cancelado Admin"
    )

    /**devolve String data no formato pretendido */
    fun stringParaData(
        aData: String?,
        FormatoOriginal: String?,
        FormatoNovo: String?
    ): String? {
        if (aData == null) return null
        val pos = ParsePosition(0)
        val dt = SimpleDateFormat(FormatoOriginal)

        /** Formato original */
        val date = dt.parse(aData, pos)
        val DataOut = SimpleDateFormat(FormatoNovo)
        return DataOut.format(date)
    }

    /**
     * Converting dp to pixel
     */
    fun dpToPx(dp: Int): Int {
        val r = ma.resources
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                r.displayMetrics
            )
        )
    }

    fun objetivo(obj: String?): String? {
        if (obj == null) return null
        var _objetivo = ""
        _objetivo = if (obj === "1") "Pessoal" else "Profissional"
        return _objetivo
    }

    inner class userid protected constructor() {
        private var mInstance: userid? = null
        var UserID = 0

        @get:Synchronized
        val instance: userid
            get() {
                if (null == mInstance) {
                    mInstance = userid()
                }
                return mInstance!!
            }
    }



    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int,
        private val includeEdge: Boolean
    ) : ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column
            if (includeEdge) {
                outRect.left =
                    spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right =
                    (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right =
                    spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }

    }

    fun stringToURL(urlString: String?): URL? {
        try {
            return URL(urlString)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }



    /*  public static int[] swappos(int a, int b){
        int tmp=0;
        int _a=a;
        int _b=b;

        return new int[] {_a, _b+};
    }*/
    /*  public String imgEstado(String estado) {

        ImageView image = DownInfoUserBoleias.findViewById(R.id.imageView_estado);
        image.setImageResource(R.drawable.canceladmin)
        if(estado==null) return null;
        String _objetivo="";
        if (estado=="1")
            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        else
            _objetivo="Profissional";

        return _objetivo;

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }*/
    /**public void saveUser(String SHARED_PREF_NAME, String nfunc, String nome, String email, String tel, String local ) {
     *
     *
     * private static final String SHARED_PREF_NAME = "asminhascenas";
     * final String KEY_USER = "";
     * final String KEY_NAME = "";
     * final String KEY_EMAIL = "";
     * final String KEY_TEL = "";
     * final String KEY_LOCAL = "";
     *
     *
     * SharedPreferences preferences = ma.getSharedPreferences("xx", Context.MODE_PRIVATE);
     * SharedPreferences.Editor editor = preferences.edit();
     * editor.putString(KEY_USER, nfunc);
     * editor.putString(KEY_NAME, nome);
     * editor.putString(KEY_EMAIL, email);
     * editor.putString(KEY_TEL, tel);
     * editor.putString(KEY_LOCAL, local);
     * editor.apply();
     *
     *
     *
     *
     *
     *
     * } */
    companion object {

        fun formatoHora(hour: Int): Int {
            var hour = hour
            val format: String
            if (hour == 0) {
                hour += 12
                format = "AM"
            } else if (hour == 12) {
                format = "PM"
            } else if (hour > 12) {
                hour -= 12
                format = "PM"
            } else {
                format = "AM"
            }
            return hour
        }

        fun cortaString(str:String, token:String):String {
            val str1 = str.substring(str.indexOf(token) + 1)
            str1.trim { it <= ' ' }
            return str1
        }

        fun minhaTosta(
            mAct: Context,
            imageResId: Int,
            toastText: String,
            toastLength: String,
            succTypeColor: String
        ): Toast {
            val toast: Toast
            toast = if (toastLength == "short") {
                Toast.makeText(mAct, "$toastText   ", Toast.LENGTH_SHORT)
            } else {
                Toast.makeText(mAct, "$toastText   ", Toast.LENGTH_LONG)
            }
            val tView = toast.view
            val mText = tView.findViewById<TextView>(R.id.message)

            /** */
            var linearLayout: LinearLayout? = null
            val density = mAct.resources.displayMetrics.density
            val imageSize = (density * 25 + 1f).toInt()
            val imageMargin = (density * 15 + 1).toInt()
            linearLayout = tView as LinearLayout
            val imageParams =
                LinearLayout.LayoutParams(imageSize, imageSize)
            imageParams.setMargins(imageMargin + 10, 0, imageMargin, 0)
            imageParams.gravity = Gravity.CENTER_VERTICAL
            val imageView = ImageView(mAct)
            imageView.setImageResource(imageResId)
            imageView.layoutParams = imageParams
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout!!.addView(imageView, 0)
            /** */
            val textParams = mText.layoutParams
            (textParams as LinearLayout.LayoutParams).gravity =
                Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
            mText.setTypeface(null, Typeface.NORMAL)
            mText.textSize = 17f
            //mText.setTypeface(applyFont(mAct));
            mText.setShadowLayer(10f, 2f, 2f, 0)
            tView.setOnClickListener { toast.cancel() }
            tView.invalidate()
            if (succTypeColor == "erro") {
                mText.setTextColor(Color.parseColor("#ffffff"))
                // tView.setBackground(mAct.getResources().getDrawable(R.drawable.ic_tosta_erro));
                // mensagem de erro
            }
            if (succTypeColor == "sucesso") {
                mText.setTextColor(Color.parseColor("#ffffff"))
                // tView.setBackground(mAct.getResources().getDrawable(R.drawable.ic_tosta));
                // mensagem sucesso
            }
            return toast
        }
    }

}