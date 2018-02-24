package petrisor.eduard.matei.mobiledevicesprogrammingproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ReviewsAdapter extends ArrayAdapter<Review> {
    public ReviewsAdapter(Context context, int resource, List<Review> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.detail_review, null);
        }

        Review r = getItem(position);

        if (r != null) {
            TextView revUserName = (TextView) v.findViewById(R.id.revDetailUserName);
            TextView revText = (TextView) v.findViewById(R.id.revDetailText);
            TextView revRating = (TextView) v.findViewById(R.id.revDetailRating);
            TextView revPlaceName = (TextView) v.findViewById(R.id.revPlaceName);

            if (revUserName != null) {
                revUserName.setText(r.getUsername());
            }

            if (revText != null) {
                revText.setText(r.getReviewText());
            }

            if (revRating != null) {
                revRating.setText(r.getRating() + "");
            }

            if (revPlaceName != null) {
                revPlaceName.setText(r.getPlaceName());
            }
        }
        return v;
    }
}
