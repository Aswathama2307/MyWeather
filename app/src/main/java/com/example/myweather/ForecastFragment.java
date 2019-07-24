package com.example.myweather;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweather.Adapter.WeatherForecastAdapter;
import com.example.myweather.Common.Common;
import com.example.myweather.Model.WeatherForecastApiService;
import com.example.myweather.Model.WeatherForecastResult;
import com.example.myweather.Retrofit.IOpenWeatherMap;
import com.example.myweather.Retrofit.Retrofit_Client;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment
{

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    TextView txt_city_name, txt_geo_coord;
    RecyclerView recycler_forecast;
    Context context;



    static ForecastFragment instance;

    public static ForecastFragment getInstance()
    {
        if(instance==null)
            instance=new ForecastFragment();
        return instance;
    }

    public ForecastFragment() {

        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = new Retrofit_Client().getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_forecast, container, false);

        txt_city_name=(TextView)itemView.findViewById(R.id.txt_city_name);
        txt_geo_coord=(TextView)itemView.findViewById(R.id.txt_geo_coord);

        context = itemView.getContext();
        recycler_forecast=(RecyclerView)itemView.findViewById(R.id.recycler_forecast);

        getForecastWeatherInformation();
        
        return itemView;
    }

    private void getForecastWeatherInformation()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Common.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherForecastApiService weatherForecastApiService =retrofit.create(WeatherForecastApiService.class);

        Call<WeatherForecastResult> call = weatherForecastApiService.getWeatherReport(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.API_ID);

        call.enqueue(new Callback<WeatherForecastResult>() {
            @Override
            public void onResponse(Call<WeatherForecastResult> call, Response<WeatherForecastResult> response)
            {
                WeatherForecastResult weatherForecastResult = response.body();
                txt_city_name.setText(weatherForecastResult.getCity().getName());
                txt_geo_coord.setText(Float.toString(weatherForecastResult.getCity().getCoord().lat)
                      + "," + Float.toString((weatherForecastResult.getCity().getCoord().lon)));

                WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(), weatherForecastResult);
                recycler_forecast.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycler_forecast.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<WeatherForecastResult> call, Throwable t)
            {
                Log.e("er", t.toString());
            }
        });
    }

//    private void displayWeatherForecastResult(WeatherForecastResult weatherForecastResult)
//    {
//        txt_city_name.setText(weatherForecastResult.getCity().getName());
//        //txt_geo_coord.setText(new StringBuilder((CharSequence) weatherForecastResult.getCity().getCoord()).toString());
//        txt_geo_coord.setText(Float.toString(weatherForecastResult.getCity().getCoord().lat)
//                + "," + Float.toString((weatherForecastResult.getCity().getCoord().lon)));
//
//        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(), weatherForecastResult);
//        recycler_forecast.setAdapter(adapter);
//
//    }

}
