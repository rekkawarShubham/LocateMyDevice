package com.simplifiers.locatemydevice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>{

    private Context mCtx;
    private List<Device> deviceList;
    Intent i;
    private String lati,longi;
    public DeviceAdapter(Context mCtx, List<Device> deviceList) {
        this.mCtx = mCtx;
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public DeviceAdapter.DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.device_layout, null);
        return new DeviceViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.DeviceViewHolder holder,final int position) {

        final Device device =deviceList.get(position);
        holder.textViewimei.setText(device.getIMEI());
        holder.textViewlat.setText(device.getLatitude());
        holder.textViewlong.setText(device.getLongitude());
        holder.getDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx, "hello", Toast.LENGTH_SHORT).show();
                lati = deviceList.get(position).getLatitude();
                longi = deviceList.get(position).getLongitude();
                Toast.makeText(mCtx, "hello" + lati +longi, Toast.LENGTH_SHORT).show();
                i = new Intent(mCtx, MainActivity.class);
                Bundle extras = new Bundle();
                extras.putString("lat", lati);
                extras.putString("long", longi);
                i.putExtras(extras);
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView textViewimei, textViewlat, textViewlong;
        Button getDevice;
        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewimei = itemView.findViewById(R.id.textViewimei);
            textViewlat =itemView.findViewById(R.id.textViewlat);
            textViewlong =itemView.findViewById(R.id.textViewlong);
            getDevice = itemView.findViewById(R.id.getDevice);
            //apply = itemView.findViewById(R.id.applybutton);
        }
    }
}
