package rofaeil.ashaiaa.idea.paginationfromsqlite;

import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Rofaeil Ashaiaa
 *         Created on 29/10/17.
 */

public class AsyncAdapter extends RecyclerView.Adapter<ViewHolder> {


    private DataCallback dataCallback;
    private AsyncListUtil<Item> listUtil;
    private ScrollListener onScrollListener;

    public AsyncAdapter(Item.ItemSource itemSource, RecyclerView recyclerView) {

        dataCallback = new DataCallback(itemSource);
        listUtil = new AsyncListUtil(Item.class, 100, dataCallback, new ViewCallback(recyclerView));
        onScrollListener = new ScrollListener(listUtil);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(listUtil.getItem(position), position);
    }

    @Override
    public int getItemCount() {
        return listUtil.getItemCount();
    }

    public void onStop(RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(onScrollListener);
    }

    public void onStart(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(onScrollListener);
        listUtil.refresh();
    }
}

class DataCallback extends AsyncListUtil.DataCallback<Item> {

    private Item.ItemSource itemSource;

    public DataCallback(Item.ItemSource itemSource) {
        this.itemSource = itemSource;
    }

    @Override
    public int refreshData() {
        return itemSource.getCount();
    }

    @Override
    public void fillData(Item[] data, int startPosition, int itemCount) {
        if (data != null) {
            for (int i = 0; i < itemCount; i++) {
                data[i] = itemSource.getItem(startPosition + i);
            }
        }
    }
}

class ViewCallback extends AsyncListUtil.ViewCallback {

    private RecyclerView recyclerView;

    public ViewCallback(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void getItemRangeInto(int[] outRange) {
        if (outRange == null) {
            return;
        }

        outRange[0] = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        outRange[1] = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

        if (outRange[0] == -1 && outRange[1] == -1) {
            outRange[0] = 0;
            outRange[1] = 0;
        }
    }

    @Override
    public void onDataRefresh() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemLoaded(int position) {
        recyclerView.getAdapter().notifyItemChanged(position);
    }
}

class ScrollListener extends RecyclerView.OnScrollListener {
    private AsyncListUtil<Item> listUtil;

    public ScrollListener(AsyncListUtil<Item> listUtil) {
        this.listUtil = listUtil;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        listUtil.onRangeChanged();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView content;


    public ViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        content = itemView.findViewById(R.id.content);

    }

    public void bindView(Item item, int position) {

        if (item != null) {

            title.setText(position + " " + item.getTitle());
            content.setText(position + " " + item.getContent());
        } else {

            title.setText(position + " loading");
            content.setText(position + " loading");
        }

    }

}