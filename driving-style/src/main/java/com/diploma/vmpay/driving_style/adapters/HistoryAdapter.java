package com.diploma.vmpay.driving_style.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.diploma.vmpay.driving_style.AppConstants;
import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.database.dbmodels.TripModel;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Andrew on 22/09/2016.
 */

public class HistoryAdapter extends BaseAdapter
{
	private List<TripModel> tripModelList;
	private LayoutInflater layoutInflater;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DateFormat.DATE_AND_HOUR);

	public HistoryAdapter(List<TripModel> tripModelList, Context context)
	{
		this.tripModelList = tripModelList;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		return tripModelList.size();
	}

	@Override
	public Object getItem(int i)
	{
		return tripModelList.get(i);
	}

	@Override
	public long getItemId(int i)
	{
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup)
	{
		View v = view;
		if (v == null) {
			v = layoutInflater.inflate(R.layout.history_item, viewGroup, false);
		}
		v.setTag(i);

		TripModel tripModel = tripModelList.get(i);

		TextView tvDate = ButterKnife.findById(v, R.id.tvDate);
		TextView tvMark = ButterKnife.findById(v, R.id.tvMark);
		TextView tvType = ButterKnife.findById(v, R.id.tvType);

		tvDate.setText(simpleDateFormat.format(tripModel.getFinishTime()));
		tvMark.setText(Double.toString(tripModel.getMark()));
		tvType.setText(tripModel.getTripType().toString());

		return v;
	}
}
