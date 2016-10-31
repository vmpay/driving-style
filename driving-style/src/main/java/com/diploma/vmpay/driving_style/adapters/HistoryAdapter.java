package com.diploma.vmpay.driving_style.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.diploma.vmpay.driving_style.AppConstants;
import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.database.dbmodels.TripModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Andrew on 22/09/2016.
 */

public class HistoryAdapter extends BaseAdapter
{
	private List<TripModel> tripModelList;
	private List<Long> checkedModelsIDs = new ArrayList<>();
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
		return tripModelList.get(i).getId();
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup)
	{
		View v = view;
		if (v == null) {
			v = layoutInflater.inflate(R.layout.history_item, viewGroup, false);
		}
		v.setTag(i);
		final CheckBox cbHistory = (CheckBox) v.findViewById(R.id.cbHistory);
		cbHistory.setTag(i);
		v.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				cbHistory.setChecked(!cbHistory.isChecked());
				if (cbHistory.isChecked())
				{
					checkedModelsIDs.add(getItemId(Integer.parseInt(cbHistory.getTag().toString())));
				}
				else
				{
					checkedModelsIDs.remove(getItemId(Integer.parseInt(cbHistory.getTag().toString())));
				}
			}
		});

		TripModel tripModel = tripModelList.get(i);

		TextView tvDate = ButterKnife.findById(v, R.id.tvDate);
		TextView tvMark = ButterKnife.findById(v, R.id.tvMark);
		TextView tvType = ButterKnife.findById(v, R.id.tvType);

		tvDate.setText(simpleDateFormat.format(tripModel.getFinishTime()));
		tvMark.setText(String.format(Double.toString(tripModel.getMark())));
		tvType.setText(tripModel.getTripType().toString());

		return v;
	}

	public double getAverageMark()
	{
		//TODO: count only exams and practices
		double mark = 0;
		int count = 0;
		for (TripModel model : tripModelList)
		{
			if (model.getTripType().equals(AppConstants.TripType.EXAM)|| model.getTripType().equals(AppConstants.TripType.PRACTICE ))
			{
				count++;
				mark += model.getMark();
			}
		}
		if (count == 0)
		{
			return 5.0;
		}
		else
		{
			mark = mark / count;
			return mark;
		}
	}

	public List<Long> getCheckedItemsID()
	{
		return checkedModelsIDs;
	}
}
