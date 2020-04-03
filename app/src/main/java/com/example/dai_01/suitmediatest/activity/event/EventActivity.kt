package com.example.dai_01.suitmediatest.activity.event

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.dai_01.suitmediatest.App
import com.example.dai_01.suitmediatest.R
import com.example.dai_01.suitmediatest.activity.guest.GuestPresenter
import com.example.dai_01.suitmediatest.activity.guest.RecyclerGuestAdapter
import com.example.dai_01.suitmediatest.model.DataEvent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_guest.*
import java.lang.Double
import javax.inject.Inject

class EventActivity : AppCompatActivity(),EventView, ViewPager.OnPageChangeListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Inject
    lateinit var presenter: EventPresenter
    private lateinit var adapter: RecyclerEventAdapter
    private var map: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null
    private var isMapActive = true
    private lateinit var listEventHorizontal : MutableList<String>
    private var listEvent : MutableList<DataEvent> = mutableListOf()
    private var positionSelected = 0
    private val INDONESIA = LatLngBounds(
            LatLng(-10.1718, 95.31644), LatLng(5.88969, 140.71813))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        App.component.inject(this)

        onAttach()
    }

    override fun onAttach() {

        mapFragment = getSupportFragmentManager().findFragmentById(R.id.map) as SupportMapFragment
        if (mapFragment != null)
            mapFragment!!.getMapAsync(this)

        presenter.onAttach(this)
        presenter.loadEventList()

        btnBackEvent.setOnClickListener{
            finish()
        }

        btnFlipper.setOnClickListener {

            if (isMapActive){
                flipper.displayedChild = 1
                btnFlipper.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.btn_article_selected))
                isMapActive  = false
            }else{
                flipper.displayedChild = 0
                btnFlipper.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.btn_article_normal))
                isMapActive  = true
            }

        }
    }

    private fun initViewPagerImage(list:MutableList<String>){

        viewPagerEvent.adapter = ViewPageMainImageAdapter(this,list)
        viewPagerEvent.addOnPageChangeListener(this)
        viewPagerEvent.pageMargin = 20
        viewPagerEvent.clipToPadding = false
        viewPagerEvent.clipChildren = false
        viewPagerEvent.setPadding(70,0,70,0)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        positionSelected = position
        map?.clear()
        mapFragment?.getMapAsync(this)
        drawMarker(listEvent)
    }

    override fun onLoadSuccessEvent(message: List<DataEvent>) {
        rv_event.layoutManager = LinearLayoutManager(this)
        adapter= RecyclerEventAdapter(this,message)
        rv_event.adapter=adapter

        listEvent = mutableListOf()
        this.listEvent.addAll(message)

        listEventHorizontal = mutableListOf()

        //viewpager
        message.forEach{
            listEventHorizontal.add(it.image)
        }
        initViewPagerImage(listEventHorizontal)

        drawMarker(listEvent)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
        map?.setOnMarkerClickListener(this)
        map?.getUiSettings()?.isMapToolbarEnabled = false
        drawDefaultMap(map,this)
        drawMarker(listEvent)
    }

    private fun drawMarker(listEvent:MutableList<DataEvent>){
        if(listEvent.size>0) {
            val padding = 30; // offset from edges of the map in pixels
            val builder = LatLngBounds.Builder();

            listEvent.forEachIndexed { index, dataEvent ->
                if (positionSelected == index) {
                    map?.addMarker(getLatLng(dataEvent.location)?.let {
                        MarkerOptions().position(it)
                                .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(this, "selected", dataEvent.name))).title(dataEvent.name)
                    })
                } else {
                    map?.addMarker(getLatLng(dataEvent.location)?.let {
                        MarkerOptions().position(it)
                                .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(this, "unselected", dataEvent.name))).title(dataEvent.name)
                    })
                }

                builder.include(getLatLng(dataEvent.location));
            }

            val bounds = builder.build();
            val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            map?.animateCamera(cu);


        }
    }

    fun createCustomMarker(context: Context, status: String,title:String): Bitmap {
        val marker = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.custom_marker_layout, null)

        val imageMarker = marker.findViewById(R.id.imageEvent) as ImageView
        val textmarker = marker.findViewById(R.id.textEventMap) as TextView
        textmarker.text = title

        if(status.equals("selected")){
            imageMarker.setColorFilter(ContextCompat.getColor(context, R.color.selectedMap));
            textmarker.background = ContextCompat.getDrawable(this,R.drawable.bg_rectangle_rounded_selected)
        }else{
            imageMarker.setColorFilter(ContextCompat.getColor(context, R.color.unselectedMap));
            textmarker.background = ContextCompat.getDrawable(this,R.drawable.bg_rectangle_rounded_gray)

        }

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        marker.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        marker.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(marker.measuredWidth, marker.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        marker.draw(canvas)

        return bitmap
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return true
    }

    override fun onDestroy() {
        if (mapFragment != null)
            getSupportFragmentManager().beginTransaction()
                    .remove(mapFragment)
                    .commitAllowingStateLoss()
        super.onDestroy()
    }

    fun drawDefaultMap(map: GoogleMap?, context: Context?) {
        map?.setOnMapLoadedCallback {
            map.setLatLngBoundsForCameraTarget(INDONESIA)
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(INDONESIA, 10))

            if (context != null) {
                map.setPadding(0, 0, 0, 0)
            }
        }
    }

    fun getLatLng(location:String): LatLng? {

        val temp = location.split("\\s*,\\s*".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        val longitude = temp[1]
        val latitude = temp[0]

        return if (longitude!="" && latitude!="") {
            LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))
        } else null
    }

    override fun onLoadFailedEvent(message: String) {
        println("MESSAGE ERROR $message")
    }

    override fun onDetach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lifecycle(): Observable<ActivityEvent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> bindTolifeCycle(): LifecycleTransformer<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
