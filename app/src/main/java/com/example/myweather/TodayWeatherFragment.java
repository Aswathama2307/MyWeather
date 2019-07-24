package com.example.myweather;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myweather.Common.Common;
import com.example.myweather.Model.WeatherApiService;
import com.example.myweather.Model.WeatherResult;
import com.example.myweather.Retrofit.IOpenWeatherMap;
import com.squareup.picasso.Picasso;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWeatherFragment extends Fragment {
    private Context context;

    ImageView image_weather;
    TextView txt_city_name, txt_humidity, txt_sunrise, txt_sunset, txt_pressure, txt_temperature, txt_description, txt_date_time, txt_wind, txt_geo_coord;
    LinearLayout weather_panel;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    static TodayWeatherFragment instance;

    public static TodayWeatherFragment getInstance() {
        if(instance==null)
            instance= new TodayWeatherFragment();
        return instance;
    }

    /*public TodayWeatherFragment()
    {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = new Retrofit_Client().getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_today_weather, container, false);
        context = itemView.getContext();
        image_weather = (ImageView)itemView.findViewById(R.id.img_weather);
        txt_city_name = (TextView)itemView.findViewById(R.id.txt_city_name);
        txt_humidity = (TextView)itemView.findViewById(R.id.txt_humidity);
        txt_sunrise = (TextView)itemView.findViewById(R.id.txt_sunrise);
        txt_sunset = (TextView)itemView.findViewById(R.id.txt_sunset);
        txt_pressure = (TextView)itemView.findViewById(R.id.txt_pressure);
        txt_temperature = (TextView)itemView.findViewById(R.id.txt_temperature);
        txt_description = (TextView)itemView.findViewById(R.id.txt_description);
        txt_date_time = (TextView)itemView.findViewById(R.id.txt_date_time);
        txt_wind = (TextView)itemView.findViewById(R.id.txt_wind);
        txt_geo_coord = (TextView)itemView.findViewById(R.id.txt_geo_coord);
        weather_panel = (LinearLayout)itemView.findViewById(R.id.weather_panel);
        loading = (ProgressBar)itemView.findViewById(R.id.loading);


        getWeatherInformation();

        return itemView;
    }

    private void getWeatherInformation()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Common.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService weatherApiService = retrofit.create(WeatherApiService.class);

        Call<WeatherResult> call = weatherApiService.getWeatherReport(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.API_ID);

        call.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                WeatherResult result = response.body();
               // Log.i("data",Common.img_path + response.body().getWeather().get(0).getIcon() + ".png");
                Picasso.get().load(new StringBuilder("https://openweathermap.org/img/wn/")
                           .append(result.getWeather().get(0).getIcon())
                                .append(".png").toString()).into(image_weather);

                txt_city_name.setText(result.getName());
                txt_description.setText(new StringBuilder("Weather in ")
                .append(result.getName()).toString());
                txt_temperature.setText(String.valueOf(result.getMain().getTemp()) + "°C");
                //txt_temperature.setText(String.valueOf(result.getMain().getTemp()));
                txt_date_time.setText(Common.convertUnixToDate(result.getDt()));
                txt_pressure.setText(new StringBuilder(String.valueOf(result.getMain().getPressure())).append("hpa").toString());
                txt_humidity.setText(new StringBuilder(String.valueOf(result.getMain().getHumidity())).append("%").toString());
                txt_sunrise.setText(Common.convertUnixToHour(result.getSys().getSunrise()));
                txt_sunset.setText(Common.convertUnixToHour(result.getSys().getSunset()));
                //can't implement .toString() after getCoord()
               // txt_geo_coord.setText(new StringBuilder("[").append(result.getCoord()).append("]").toString());
                txt_geo_coord.setText(Float.toString(result.getCoord().lat) + "," + Float.toString(result.getCoord().lon));

                //Display

                weather_panel.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);



            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {

            }
        });


         /*compositeDisposable.add((Disposable) mService.getWeatherByLatLng(String.valueOf(Common.current_location),
                String.valueOf(Common.current_location.getLongitude()),
                Common.API_ID,
                "metric")








        );*/


//    {
//         compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(Common.current_location),
//                String.valueOf(Common.current_location.getLongitude()),
//                Common.API_ID,
//                "metric")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn
//                .subscribe(new Consumer<WeatherResult>() {
//                    @Override
//                    public void accept(WeatherResult weatherResult) throws Exception {
//                        //Load Image
//                        //error for getIcon
//                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/wn/")
//                                .append(weatherResult.getWeather().get(0).getIcon())
//                                .append(".png").toString());
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(getActivity(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//        );

    }


}
