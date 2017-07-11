package com.example.arunans23.smartremotecontroller.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arunans23.smartremotecontroller.R;
import com.example.arunans23.smartremotecontroller.model.Remote;
import com.example.arunans23.smartremotecontroller.model.RemoteLab;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class RemoteListActivityFragment extends Fragment {

    private RecyclerView mRemoteRecylcerView;
    private RemoteAdapter mAdapter;
    private RemoteLab mRemoteLab;

    public RemoteListActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRemoteLab = RemoteLab.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remote_list, container, false);
        mRemoteRecylcerView = (RecyclerView) view.findViewById(R.id.remote_recycler_view);
        mRemoteRecylcerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI(){

        //List<Remote> remotes = this.mRemoteLab.getDummyRemoteList();
        List<Remote> remotes = this.mRemoteLab.getRemotes();

        if (mAdapter == null){
            mAdapter = new RemoteAdapter(remotes);
            mRemoteRecylcerView.setAdapter(mAdapter);
        } else {
            mAdapter.setRemotes(remotes);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class RemoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Remote  mRemote;
        private TextView mRemoteBrandTextView;

        public RemoteHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            mRemoteBrandTextView = (TextView)itemView.findViewById(R.id.list_item_remote_brand_text_view);
        }

        public void bindRemote(Remote remote){
            mRemote = remote;
            mRemoteBrandTextView.setText(mRemote.getRemoteBrand());

        }

        @Override
        public void onClick(View view) {
            Intent intent = RemoteActivity.newIntent(getActivity(), this.mRemote.getRemoteID());
            startActivity(intent);
        }
    }

    private class RemoteAdapter extends RecyclerView.Adapter<RemoteHolder>{
        private List<Remote> mRemotes;

        public RemoteAdapter(List<Remote> remotes){
            mRemotes = remotes;
        }

        @Override
        public RemoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_remote, parent, false);
            return new RemoteHolder(view);
        }

        @Override
        public void onBindViewHolder(RemoteHolder holder, int position) {
            Remote remote = mRemotes.get(position);
            holder.bindRemote(remote);
        }

        @Override
        public int getItemCount() {
            return mRemotes.size();
        }

        public void setRemotes(List<Remote> remotes){
            mRemotes = remotes;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }
}

